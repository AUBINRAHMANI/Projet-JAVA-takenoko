package fr.cotedazur.univ.polytech.startingpoint.game.action;

import fr.cotedazur.univ.polytech.startingpoint.game.action.Action;
import fr.cotedazur.univ.polytech.startingpoint.game.action.ActionType;
import fr.cotedazur.univ.polytech.startingpoint.game.action.PutIrrigationAction;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.GameEngine;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.items.Irrigation;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.map.Position;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PutIrrigationActionTest {
    @Mock
    GameEngine mockedGameEngine = mock(GameEngine.class);

    @Test
    void play() {
        Irrigation irrigation = new Irrigation(new Position(0, 0), new Position(0, 1));
        when(mockedGameEngine.askToPutIrrigation(irrigation)).thenReturn(true);

        PutIrrigationAction putIrrigationAction = new PutIrrigationAction(irrigation);
        assertTrue(putIrrigationAction.play(null, mockedGameEngine));
    }

    @Test
    void toType() {
        Irrigation irrigation = new Irrigation(new Position(0, 0), new Position(0, 1));
        PutIrrigationAction putIrrigationAction = new PutIrrigationAction(irrigation);
        assertEquals(ActionType.PUT_IRRIGATION, putIrrigationAction.toType());
    }

    @Test
    void equals() {
        Action action1 = new PutIrrigationAction(new Irrigation(new Position(0, 1), new Position(1, 1)));
        Action action2 = new PutIrrigationAction(new Irrigation(new Position(1, 1), new Position(0, 1)));
        assertEquals(action1, action2);
    }
}
