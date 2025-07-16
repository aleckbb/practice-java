package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

abstract class Organism {
    protected int x;
    protected int y;
    protected int energy;
    protected int age;
    protected boolean alive;
    protected final String symbol;

    private static final Logger logger = LogManager.getLogger(Organism.class);

    public Organism(int x, int y, int energy, String symbol) {
        this.x = x;
        this.y = y;
        this.energy = energy;
        this.age = 0;
        this.alive = true;
        this.symbol = symbol;
        logger.debug("{} создан в ({}, {}) с энергией {}", symbol, x, y, energy);
    }

    public abstract void move();

    public abstract void eat();

    public abstract void reproduce(List<Organism> newOrganisms);

    public void age() {
        if (!alive) return;
        age++;
        energy--;
        logger.debug("{} состарился до {} дней, энергия {}", getPositionInfo(), age, energy);
        if (energy <= 0) {
            alive = false;
            logger.info("{} умер от голода", getPositionInfo());
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public String getPositionInfo() {
        return symbol + "(" + x + "," + y + ")";
    }

    public String getStatus() {
        return symbol + " [Энергия: " + energy + ", Возраст: " + age + "]";
    }
}

