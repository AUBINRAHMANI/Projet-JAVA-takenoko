package fr.cotedazur.univ.polytech.startingpoint.game.action;

import fr.cotedazur.univ.polytech.startingpoint.game.action.Action;
import fr.cotedazur.univ.polytech.startingpoint.game.action.ActionType;
import fr.cotedazur.univ.polytech.startingpoint.game.action.RainAction;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.GameEngine;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.map.Position;
import fr.cotedazur.univ.polytech.startingpoint.game.Game;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RainActionTest {
    @Mock
    GameEngine mockedGameEngine = mock(GameEngine.class);
    @Mock
    Game mockedGame = mock(Game.class);

    @Test
    void play() {
        Position position = new Position(2, 1);
        when(mockedGameEngine.rainAction(position)).thenReturn(true);

        RainAction rainAction = new RainAction(position);
        assertTrue(rainAction.play(null, mockedGameEngine));
    }

    @Test
    void verifyObjectiveAfterAction() {
        when(mockedGame.computeObjectivesGardener()).thenReturn(true);
        RainAction rainAction = new RainAction(null);
        assertTrue(rainAction.verifyObjectiveAfterAction(mockedGame, null));
    }


    @Test
    void getPosition() {
        Position position = new Position(3, 6);
        RainAction rainAction = new RainAction(position);
        assertEquals(rainAction.getPosition(), position);
    }

    @Test
    void toType() {
        Position position = new Position(3, 6);
        RainAction rainAction = new RainAction(position);
        assertEquals(ActionType.RAIN, rainAction.toType());
    }

    @Test
    void equals() {
        Action action1 = new RainAction(new Position(1, 1));
        Action action2 = new RainAction(new Position(0, 1));
        assertEquals(action1, action2);
    }
}