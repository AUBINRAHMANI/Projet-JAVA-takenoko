package fr.cotedazur.univ.polytech.startingpoint.action;
import fr.cotedazur.univ.polytech.startingpoint.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.game.Referee;
import fr.cotedazur.univ.polytech.startingpoint.GameEngine;
import fr.cotedazur.univ.polytech.startingpoint.Position;

public class MovePandaAction implements Action{

    Bot bot;
    Position position;
    public MovePandaAction(Bot bot, Position position){
        this.bot = bot;
        this.position = position;
    }

    @Override
    public boolean play(Referee referee, GameEngine gameEngine) {
        return gameEngine.movePanda(referee, bot, position);
    }

    @Override
    public boolean verifyObjectiveAfterAction(Referee referee){

        return referee.computeObjectivesPanda();
    }

    @Override
    public String toString() {
        return "MovePandaAction{" +
                "_position=" + position +
                '}';
    }
}
