package fr.cotedazur.univ.polytech.startingpoint.action;
import fr.cotedazur.univ.polytech.startingpoint.game.Referee;
import fr.cotedazur.univ.polytech.startingpoint.GameEngine;

public interface Action {

    boolean play(Referee referee, GameEngine gameEngine);
    boolean verifyObjectiveAfterAction(Referee referee);
    String toString();
}
