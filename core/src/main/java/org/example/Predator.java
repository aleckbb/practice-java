package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;

class Predator extends Organism {
    private static final int MOVE_ENERGY = 2;
    private static final int REPRODUCTION_ENERGY = 25;
    public static final int EAT_ENERGY = 10;

    private static final int MAX_AGE = 30;

    private static final Logger logger = LogManager.getLogger(Predator.class);

    public Predator(int x, int y) {
        super(x, y, 15, "X");
    }

    @Override
    public void move() {
        if (!alive) return;

        Random rand = new Random();
        int oldX = this.x;
        int oldY = this.y;
        this.x += rand.nextInt(5) - 2; // -2,-1,0,1,2
        this.y += rand.nextInt(5) - 2;

        // Ограничиваем перемещение в пределах леса
        x = Math.max(0, Math.min(EcosystemSimulator.FOREST_SIZE - 1, x));
        y = Math.max(0, Math.min(EcosystemSimulator.FOREST_SIZE - 1, y));

        energy -= MOVE_ENERGY;
        logger.debug("X переместился с ({},{}) на ({},{}) энергия={}", oldX, oldY, x, y, energy);
    }

    @Override
    public void eat() {
        // Логика питания реализована в EcosystemSimulator
    }

    @Override
    public void reproduce(List<Organism> newOrganisms) {
        if (energy >= REPRODUCTION_ENERGY) {
            energy -= REPRODUCTION_ENERGY / 2;
            Predator child = new Predator(x, y);
            newOrganisms.add(child);
            logger.info("{} размножился → {}", getPositionInfo(), child.getPositionInfo());
        }
    }

    @Override
    protected int getMaxAge() {
        return MAX_AGE;
    }
}
