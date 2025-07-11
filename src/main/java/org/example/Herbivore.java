package org.example;

import java.util.List;
import java.util.Random;

class Herbivore extends Organism {
    private static final int MOVE_ENERGY = 1;
    private static final int REPRODUCTION_ENERGY = 15;
    public static final int EAT_ENERGY = 5;

    public Herbivore(int x, int y) {
        super(x, y, 10, "H");
    }

    @Override
    public void move() {
        if (!alive) return;

        Random rand = new Random();
        this.x += rand.nextInt(3) - 1; // -1, 0, 1
        this.y += rand.nextInt(3) - 1;

        // Ограничиваем перемещение в пределах леса
        x = Math.max(0, Math.min(EcosystemSimulator.FOREST_SIZE - 1, x));
        y = Math.max(0, Math.min(EcosystemSimulator.FOREST_SIZE - 1, y));

        energy -= MOVE_ENERGY;
    }

    @Override
    public void eat() {
        // Логика питания реализована в EcosystemSimulator
    }

    @Override
    public void reproduce(List<Organism> newOrganisms) {
        if (energy >= REPRODUCTION_ENERGY) {
            energy -= REPRODUCTION_ENERGY / 2;
            newOrganisms.add(new Herbivore(x, y));
        }
    }
}

