package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

class Plant extends Organism {
    private static final int GROW_ENERGY = 5;
    private static final int REPRODUCTION_THRESHOLD = 8;

    private static final Logger logger = LogManager.getLogger(Plant.class);

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
        logger.debug("{} провёл фотосинтез, энергия={}", getPositionInfo(), energy);
    }

    @Override
    public void reproduce(List<Organism> newOrganisms) {
        if (energy >= REPRODUCTION_THRESHOLD) {
            energy -= GROW_ENERGY;
            Plant plant = new Plant(x, y);
            newOrganisms.add(plant);
            logger.info("{} размножилось → {}", getPositionInfo(), plant.getPositionInfo());
        }
    }
}
