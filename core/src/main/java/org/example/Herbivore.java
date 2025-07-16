package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;

class Herbivore extends Organism {
    private static final int MOVE_ENERGY = 1;
    private static final int REPRODUCTION_ENERGY = 15;
    public static final int EAT_ENERGY = 5;

    private static final Logger logger = LogManager.getLogger(Herbivore.class);

    public Herbivore(int x, int y) {
        super(x, y, 10, "H");
    }

    @Override
    public void move() {
        if (!alive) return;

        Random rand = new Random();
        int oldX = this.x;
        int oldY = this.y;
        this.x += rand.nextInt(3) - 1; // -1, 0, 1
        this.y += rand.nextInt(3) - 1;

        // Ограничиваем перемещение в пределах леса
        x = Math.max(0, Math.min(EcosystemSimulator.FOREST_SIZE - 1, x));
        y = Math.max(0, Math.min(EcosystemSimulator.FOREST_SIZE - 1, y));

        energy -= MOVE_ENERGY;
        logger.debug("H переместилось с ({},{}) на ({},{}) энергия={}", oldX, oldY, x, y, energy);
    }

    @Override
    public void eat() {
        // Логика питания реализована в EcosystemSimulator
    }

    @Override
    public void reproduce(List<Organism> newOrganisms) {
        if (energy >= REPRODUCTION_ENERGY) {
            energy -= REPRODUCTION_ENERGY / 2;
            Herbivore child = new Herbivore(x, y);
            newOrganisms.add(child);
            logger.info("{} размножилось → {}", getPositionInfo(), child.getPositionInfo());
        }
    }
}

