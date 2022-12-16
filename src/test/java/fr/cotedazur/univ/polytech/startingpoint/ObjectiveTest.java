package fr.cotedazur.univ.polytech.startingpoint;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveTest {

    @Test
    void getPoint() {
        Objective Obj = new Objective(4, ObjectiveType.PLOT);
        assertEquals(4, Obj.getPoint());
    }

    @Test
    void getType() {
        Objective objective = new Objective(4, ObjectiveType.PLOT);
        assertEquals(ObjectiveType.PLOT, objective.getType());
    }
}