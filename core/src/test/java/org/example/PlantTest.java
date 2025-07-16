package org.example;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlantTest {

    @Test
    void testEatIncreasesEnergy() {
        Plant plant = new Plant(5, 5);
        int before = plant.energy;
        plant.eat();
        assertEquals(before + 1, plant.energy);
    }

    @Test
    void testMoveDoesNothing() {
        Plant plant = new Plant(2, 3);
        int x0 = plant.x, y0 = plant.y;
        plant.move();
        assertEquals(x0, plant.x);
        assertEquals(y0, plant.y);
    }

    @Test
    void testReproduceWhenEnergyBelowThreshold() {
        Plant plant = new Plant(1, 1);
        plant.energy = 7;
        List<Organism> children = new ArrayList<>();
        plant.reproduce(children);
        assertTrue(children.isEmpty());
        assertEquals(7, plant.energy);
    }

    @Test
    void testReproduceWhenEnergyAtOrAboveThreshold() {
        Plant plant = new Plant(1, 1);
        plant.energy = 8;
        List<Organism> children = new ArrayList<>();
        plant.reproduce(children);
        assertEquals(1, children.size());
        assertInstanceOf(Plant.class, children.get(0));
        assertEquals(8 - 5, plant.energy);
    }
}
