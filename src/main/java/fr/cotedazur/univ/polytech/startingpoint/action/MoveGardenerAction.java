package fr.cotedazur.univ.polytech.startingpoint.action;

import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.GameEngine;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.map.Map;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.map.Position;
import fr.cotedazur.univ.polytech.startingpoint.bot.Playable;
import fr.cotedazur.univ.polytech.startingpoint.game.Referee;
import fr.cotedazur.univ.polytech.startingpoint.statistique_manager.StatisticManager;

import java.util.Objects;

public class MoveGardenerAction implements Action {

    Position position;

    public MoveGardenerAction(Position position) {
        this.position = position;
    }

    @Override
    public boolean play(Referee referee, GameEngine gameEngine) {
        return gameEngine.moveGardener(position);
    }

    @Override
    public boolean verifyObjectiveAfterAction(Referee referee, Map map) {
        return referee.computeObjectivesGardener();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return ActionType.MOVE_GARDENER.equals(toType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(toType());
    }

    @Override
    public String toString() {
        return "MoveGardenerAction{" +
                "_position=" + position +
                '}';
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public ActionType toType() {
        return ActionType.MOVE_GARDENER;
    }

    @Override
    public void incrementAction(StatisticManager statisticManager, Playable bot) {
        statisticManager.incrementGardenerAction(bot);
    }
}
