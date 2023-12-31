package fr.cotedazur.univ.polytech.startingpoint.game.game_engine.items;

import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.items.Irrigation;
import fr.cotedazur.univ.polytech.startingpoint.game.game_engine.map.Position;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IrrigationTest {

    @Test
    void setPosition() {
        Irrigation irrigation = new Irrigation(new Position(0, 0), new Position(0, 1));
        Position position1 = new Position(0, 0);
        Position position2 = new Position(0, 1);
        Position position3 = new Position(34, 18);
        assertNotNull(irrigation);
        assertTrue(irrigation.setPosition(position1, position2));
        assertTrue(irrigation.getPositions().containsAll(Arrays.asList(position1, position2)));
        assertFalse(irrigation.setPosition(position1, position3));
    }

    @Test
    void getNeighbours() {
        Irrigation irrigation1 = new Irrigation(new Position(3, 3), new Position(4, 2));
        Irrigation irrigation2 = new Irrigation(new Position(3, 3), new Position(3, 2));
        Irrigation irrigation3 = new Irrigation(new Position(4, 2), new Position(3, 2));
        Irrigation irrigation4 = new Irrigation(new Position(4, 3), new Position(4, 2));
        Irrigation irrigation5 = new Irrigation(new Position(3, 3), new Position(4, 3));

        List<Irrigation> neighbours = irrigation1.getNeighbours();
        assertTrue(neighbours.containsAll(Arrays.asList(irrigation2, irrigation3, irrigation4, irrigation5)));
        neighbours.removeAll(Arrays.asList(irrigation2, irrigation3, irrigation4, irrigation5));
        assertEquals(0, neighbours.size());
    }

    @Test
    void equals() {
        Irrigation irrigation1 = new Irrigation(new Position(2, 1), new Position(3, 1));
        Irrigation irrigation2 = new Irrigation(new Position(3, 1), new Position(2, 1));

        assertEquals(irrigation1, irrigation2);

    }
}