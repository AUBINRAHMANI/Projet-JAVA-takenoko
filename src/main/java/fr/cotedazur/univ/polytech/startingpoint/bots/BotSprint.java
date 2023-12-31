package fr.cotedazur.univ.polytech.startingpoint.bots;

import fr.cotedazur.univ.polytech.startingpoint.game.action.Action;
import fr.cotedazur.univ.polytech.startingpoint.game.action.ActionType;
import fr.cotedazur.univ.polytech.startingpoint.game.action.PickObjectiveAction;
import fr.cotedazur.univ.polytech.startingpoint.bots.tools.GardenerBotResolver;
import fr.cotedazur.univ.polytech.startingpoint.bots.tools.PandaBotResolver;
import fr.cotedazur.univ.polytech.startingpoint.bots.tools.PatternBotResolver;
import fr.cotedazur.univ.polytech.startingpoint.game.Referee;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.items.Bamboo;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.items.WeatherType;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.map.Map;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.map.Pattern;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.map.PlotType;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.map.Position;
import fr.cotedazur.univ.polytech.startingpoint.game.objectives.Objective;

import java.util.ArrayList;
import java.util.List;

public class BotSprint implements Playable {

    Referee referee;
    Map map;
    List<Bamboo> myBamboos;

    public BotSprint() {
        this(null, null);
    }

    public BotSprint(Referee referee, Map map) {
        this.referee = referee;
        this.map = map;
        this.myBamboos = new ArrayList<>();
    }

    public void setEnvironment(Referee referee, Map map) {
        this.referee = referee;
        this.map = map;
    }

    @Override
    public Action play(List<ActionType> banActionTypes, WeatherType weather) {
        List<ActionType> defaultBanActionTypes = new ArrayList<>(banActionTypes);
        //Try to have 5 Objectives
        if (referee.getMyObjectives(this).size() < 5 && !banActionTypes.contains(ActionType.PICK_OBJECTIVE)) {
            return new PickObjectiveAction(this);
        }

        //Try to place Irrigation for QUESTIONMARK
        if (weather == WeatherType.QUESTIONMARK) {
            banActionTypes = new ArrayList<>();
            for (ActionType actionType : ActionType.values()) {
                if (actionType != ActionType.PUT_IRRIGATION) {
                    banActionTypes.add(actionType);
                }
            }
        }
        Action action = tryToFillObjective(banActionTypes, weather);
        if (action != null) {
            return action;
        }

        //Try to eat Bamboo
        banActionTypes = new ArrayList<>();
        for (ActionType actionType : ActionType.values()) {
            if (actionType != ActionType.MOVE_PANDA) {
                banActionTypes.add(actionType);
            }
        }
        action = tryToFillObjective(banActionTypes, weather);
        if (action != null) {
            return action;
        }

        //Try block opponents
        List<Action> previousActions = referee.getPreviousActions();
        action = tryToCounterOpponents(banActionTypes, previousActions);
        if (action != null) {
            return action;
        }


        //Default
        action = tryToFillObjective(defaultBanActionTypes, weather);
        if (action != null) {
            return action;
        }
        return new PickObjectiveAction(this);
    }

    private Action tryToCounterOpponents(List<ActionType> banActionTypes, List<Action> previousActions) {
        int numberOpponents = referee.getNumberOfPlayers() - 1;
        for (int i = 0; i < numberOpponents; ++i) {
            Action previousAction = previousActions.get(i);
            if (previousAction.toType() == ActionType.MOVE_GARDENER) {
                PandaBotResolver pandaBotResolver = new PandaBotResolver(map, referee, this);
                pandaBotResolver.movePandaOnPlantation(previousAction.getPosition());
            } else if (previousActions.get(i).toType() == ActionType.PUT_PLOT) {
                PatternBotResolver patternBotResolver = new PatternBotResolver(map, referee);
                for (Position position : map.closestAvailableSpace(previousAction.getPosition())) {
                    patternBotResolver.placePLot(null, position, banActionTypes);
                }
            }
        }
        return null;
    }


    public Action tryToFillObjective(List<ActionType> banActionTypes, WeatherType weather) {
        for (Objective objective : referee.getMyObjectives(this)) {
            Action action = objective.tryToFillObjective(this, banActionTypes, weather);
            if (action != null) {
                return action;
            }
        }
        return null;
    }

    public Action fillObjectiveGardener(PlotType bambooType, boolean improvement, List<ActionType> banActionTypes, WeatherType weather) {
        GardenerBotResolver gardenerBotResolver = new GardenerBotResolver(map, referee);
        return gardenerBotResolver.fillObjectiveGardener(bambooType, banActionTypes, weather);
    }

    public Action fillObjectivePanda(List<Bamboo> bambooSections, List<ActionType> banActionTypes, WeatherType weather) {
        PandaBotResolver pandaBotResolver = new PandaBotResolver(map, referee, this);
        return pandaBotResolver.fillObjectivePanda(bambooSections, myBamboos, banActionTypes, weather);
    }

    public Action fillObjectivePlots(Pattern pattern, List<ActionType> banActionTypes) {
        PatternBotResolver patternBotResolver = new PatternBotResolver(map, referee);
        return patternBotResolver.fillObjectivePlots(pattern, banActionTypes);
    }
}
