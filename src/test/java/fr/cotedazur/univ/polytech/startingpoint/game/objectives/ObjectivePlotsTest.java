package fr.cotedazur.univ.polytech.startingpoint.game.objectives;

import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.GameEngine;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.map.Pattern;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ObjectivePlotsTest {

    @Mock
    GameEngine mockedGameEngine = mock(GameEngine.class);

    @Test
    void verifyPlotObj() {
        Pattern pattern = new Pattern();
        when(mockedGameEngine.computeObjectivePlot(pattern, null)).thenReturn(true);
        ObjectivePlots objectivePlots = new ObjectivePlots(0, pattern);
        assertTrue(objectivePlots.verifyPlotObj(mockedGameEngine, null));
    }

    @Test
    void verifyGardenerObj() {
        assertFalse(new ObjectivePlots(0, null).verifyGardenerObj(null));
    }

    @Test
    void verifyPandaObj() {
        assertFalse(new ObjectivePlots(0, null).verifyPandaObj(null, null));
    }
}