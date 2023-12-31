package fr.cotedazur.univ.polytech.startingpoint.game.action;

import fr.cotedazur.univ.polytech.startingpoint.game.action.Action;
import fr.cotedazur.univ.polytech.startingpoint.game.action.ActionType;
import fr.cotedazur.univ.polytech.startingpoint.game.action.MovePandaAction;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.GameEngine;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.map.Position;
import fr.cotedazur.univ.polytech.startingpoint.bots.BotMbappe;
import fr.cotedazur.univ.polytech.startingpoint.game.Game;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MovePandaActionTest {

    @Mock
    GameEngine mockedGameEngine = mock(GameEngine.class);
    @Mock
    Game mockedGame = mock(Game.class);

    @Test
    void play() {
        Position position = new Position(2, 1);
        when(mockedGameEngine.movePanda(null, null, position)).thenReturn(true);

        MovePandaAction movePandaAction = new MovePandaAction(null, position);
        assertTrue(movePandaAction.play(null, mockedGameEngine));
    }

    @Test
    void verifyObjectiveAfterAction() {
        when(mockedGame.computeObjectivesPanda()).thenReturn(true);
        MovePandaAction movePandaAction = new MovePandaAction(null, null);
        assertTrue(movePandaAction.verifyObjectiveAfterAction(mockedGame, null));
    }

    @Test
    void getPosition() {
        Position position = new Position(2, 1);
        MovePandaAction movePandaAction = new MovePandaAction(null, position);
        assertEquals(movePandaAction.getPosition(), position);
    }

    @Test
    void toType() {
        Position position = new Position(2, 1);
        MovePandaAction movePandaAction = new MovePandaAction(null, position);
        assertEquals(ActionType.MOVE_PANDA, movePandaAction.toType());
    }

    @Test
    void equals() {
        Action action1 = new MovePandaAction(new BotMbappe(), new Position(1, 0));
        Action action2 = new MovePandaAction(null, new Position(3, 0));
        assertEquals(action1, action2);
    }
}