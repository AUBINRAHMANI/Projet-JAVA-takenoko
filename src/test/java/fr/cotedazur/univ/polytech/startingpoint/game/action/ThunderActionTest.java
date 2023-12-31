package fr.cotedazur.univ.polytech.startingpoint.game.action;

import fr.cotedazur.univ.polytech.startingpoint.game.action.Action;
import fr.cotedazur.univ.polytech.startingpoint.game.action.ActionType;
import fr.cotedazur.univ.polytech.startingpoint.game.action.ThunderAction;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.GameEngine;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.map.Position;
import fr.cotedazur.univ.polytech.startingpoint.game.Game;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ThunderActionTest {
    @Mock
    GameEngine mockedGameEngine = mock(GameEngine.class);
    @Mock
    Game mockedGame = mock(Game.class);

    @Test
    void play() {
        Position position = new Position(2, 1);
        when(mockedGameEngine.thunderAction(position)).thenReturn(true);

        ThunderAction thunderAction = new ThunderAction(position);
        assertTrue(thunderAction.play(null, mockedGameEngine));
    }

    @Test
    void verifyObjectiveAfterAction() {
        when(mockedGame.computeObjectivesPanda()).thenReturn(true);
        ThunderAction thunderAction = new ThunderAction(null);
        assertTrue(thunderAction.verifyObjectiveAfterAction(mockedGame, null));
    }

    @Test
    void getPosition() {
        Position position = new Position(3, 6);
        ThunderAction thunderAction = new ThunderAction(position);
        assertEquals(thunderAction.getPosition(), position);
    }

    @Test
    void toType() {
        Position position = new Position(3, 6);
        ThunderAction thunderAction = new ThunderAction(position);
        assertEquals(ActionType.THUNDER, thunderAction.toType());
    }

    @Test
    void equals() {
        Action action1 = new ThunderAction(new Position(1, 1));
        Action action2 = new ThunderAction(new Position(0, 1));
        assertEquals(action1, action2);
    }
}
