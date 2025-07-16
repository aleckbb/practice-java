package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EcosystemSimulatorMockitoTest {

    private Random random;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        random = mock(Random.class);
        scanner = new Scanner("q\n");
    }

    @Test
    void testInitializeOrganisms() {
        when(random.nextInt(EcosystemSimulator.FOREST_SIZE)).thenReturn(0, 1, 2, 3, 4, 5);

        EcosystemSimulator simulator = new EcosystemSimulator(1, 2, 0, random, scanner);
        List<Organism> list = simulator.getOrganisms();

        assertEquals(3, list.size(), "Должно быть 3 организма (1 Plant + 2 Herbivore)");

        assertInstanceOf(Plant.class, list.get(0), "Первый должен быть Plant");
        assertInstanceOf(Herbivore.class, list.get(1), "Второй должен быть Herbivore");
        assertInstanceOf(Herbivore.class, list.get(2), "Третий должен быть Herbivore");

        verify(random, times(6)).nextInt(EcosystemSimulator.FOREST_SIZE);

        Plant plant = (Plant) list.get(0);
        assertEquals(0, plant.x, "Plant.x должен быть 0");
        assertEquals(1, plant.y, "Plant.y должен быть 1");
    }

    @Test
    void testHerbivoreEatsPlantDuringSimulation() {
        when(random.nextInt(EcosystemSimulator.FOREST_SIZE)).thenReturn(0, 0, 0, 0);

        EcosystemSimulator simulator = new EcosystemSimulator(1, 1, 0, random, new Scanner("q\n"));
        simulator.startSimulation();

        List<Organism> list = simulator.getOrganisms();
        assertEquals(1, list.size(), "После одного дня симуляции должен остаться 1 организм");
        assertInstanceOf(Herbivore.class, list.get(0), "Оставшийся организм должен быть Herbivore");
    }
}
