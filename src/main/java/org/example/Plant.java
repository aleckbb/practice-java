package org.example;

import java.util.List;

class Plant extends Organism {
    private static final int GROW_ENERGY = 5;
    private static final int REPRODUCTION_THRESHOLD = 8;

    public Plant(int x, int y) {
        super(x, y, 3, "P");
    }

    @Override
    public void move() {
        // Растения не двигаются
    }

    @Override
    public void eat() {
        // Фотосинтез - получают энергию от солнца
        energy += 1;
    }

    @Override
    public void reproduce(List<Organism> newOrganisms) {
        if (energy >= REPRODUCTION_THRESHOLD) {
            energy -= GROW_ENERGY;
            newOrganisms.add(new Plant(x, y));
        }
    }
}
