package fr.cotedazur.univ.polytech.startingpoint.game.objectives;

import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.GameEngine;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.map.Plot;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.items.WeatherType;
import fr.cotedazur.univ.polytech.startingpoint.game.action.Action;
import fr.cotedazur.univ.polytech.startingpoint.game.action.ActionType;
import fr.cotedazur.univ.polytech.startingpoint.bots.BotProfile;
import fr.cotedazur.univ.polytech.startingpoint.bots.Playable;
import fr.cotedazur.univ.polytech.startingpoint.statistique_manager.StatisticManager;

import java.util.List;


public abstract class Objective {
    protected int points;

    protected Objective(int points) {
        this.points = points;
    }

    public int getPoint() {
        return points;
    }

    public abstract boolean verifyPlotObj(GameEngine gameEngine, Plot lastPlacedPlot);

    public abstract boolean verifyGardenerObj(GameEngine gameEngine);

    public abstract boolean verifyPandaObj(GameEngine gameEngine, BotProfile botProfile);

    public abstract Action tryToFillObjective(Playable bot, List<ActionType> banActionTypes, WeatherType weather);

    public abstract void incrementationObjective(StatisticManager statisticManager, Playable bot);

    public abstract void incrementationPointsObjective(StatisticManager statisticManager, Playable bot);


    public abstract String toString();

}
