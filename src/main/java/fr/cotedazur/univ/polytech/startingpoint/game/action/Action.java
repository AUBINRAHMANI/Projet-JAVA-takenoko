package fr.cotedazur.univ.polytech.startingpoint.game.action;

import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.GameEngine;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.map.Map;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.map.Position;
import fr.cotedazur.univ.polytech.startingpoint.bots.Playable;
import fr.cotedazur.univ.polytech.startingpoint.game.Referee;
import fr.cotedazur.univ.polytech.startingpoint.statistique_manager.StatisticManager;

public interface Action {

    boolean play(Referee referee, GameEngine gameEngine);

    boolean verifyObjectiveAfterAction(Referee referee, Map map);

    String toString();

    void incrementAction(StatisticManager statisticManager, Playable bot);

    Position getPosition();

    ActionType toType();
}
