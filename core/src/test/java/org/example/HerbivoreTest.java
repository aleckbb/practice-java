package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HerbivoreTest {

    @Test
    void testMoveReducesEnergyAndBoundsPosition() {
        Herbivore h = new Herbivore(0, EcosystemSimulator.FOREST_SIZE - 1);
        int beforeEnergy = h.energy;
        h.move();
        assertEquals(beforeEnergy - 1, h.energy);
        assertTrue(h.x >= 0 && h.x < EcosystemSimulator.FOREST_SIZE);
        assertTrue(h.y >= 0 && h.y < EcosystemSimulator.FOREST_SIZE);
    }

    @Test
    void testReproduceBelowThreshold() {
        Herbivore h = new Herbivore(5, 5);
        h.energy = 14;
        List<Organism> children = new ArrayList<>();
        h.reproduce(children);
        assertTrue(children.isEmpty());
        assertEquals(14, h.energy);
    }

    @Test
    void testReproduceAtThreshold() {
        Herbivore h = new Herbivore(5, 5);
        h.energy = 15;
        List<Organism> children = new ArrayList<>();
        h.reproduce(children);
        assertEquals(1, children.size());
        assertInstanceOf(Herbivore.class, children.get(0));
        assertEquals(15 - (15/2), h.energy);
    }
}
