package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PredatorTest {

    private static final int MOVE_ENERGY = 2;
    private static final int REPRODUCTION_ENERGY = 25;

    @Test
    void testMoveReducesEnergyAndBoundsPosition() {
        Predator p = new Predator(10, 10);
        int beforeEnergy = p.energy; // 15
        p.move();
        assertEquals(beforeEnergy - MOVE_ENERGY, p.energy);
        assertTrue(p.x >= 0 && p.x < EcosystemSimulator.FOREST_SIZE);
        assertTrue(p.y >= 0 && p.y < EcosystemSimulator.FOREST_SIZE);
    }

    @Test
    void testReproduceBelowThreshold() {
        Predator p = new Predator(3, 3);
        p.energy = REPRODUCTION_ENERGY - 1;
        List<Organism> children = new ArrayList<>();
        p.reproduce(children);
        assertTrue(children.isEmpty());
        assertEquals(24, p.energy);
    }

    @Test
    void testReproduceAtThreshold() {
        Predator p = new Predator(3, 3);
        p.energy = REPRODUCTION_ENERGY;
        List<Organism> children = new ArrayList<>();
        p.reproduce(children);
        assertEquals(1, children.size());
        assertInstanceOf(Predator.class, children.get(0));
        assertEquals(25 - (REPRODUCTION_ENERGY / 2), p.energy);
    }
}
