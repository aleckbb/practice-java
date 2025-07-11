package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class EcosystemSimulator {
    public static final int FOREST_SIZE = 20;
    private final List<Organism> organisms;

    public EcosystemSimulator(int plants, int herbivores, int predators) {
        organisms = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < plants; i++) {
            organisms.add(new Plant(rand.nextInt(FOREST_SIZE), rand.nextInt(FOREST_SIZE)));
        }
        for (int i = 0; i < herbivores; i++) {
            organisms.add(new Herbivore(rand.nextInt(FOREST_SIZE), rand.nextInt(FOREST_SIZE)));
        }
        for (int i = 0; i < predators; i++) {
            organisms.add(new Predator(rand.nextInt(FOREST_SIZE), rand.nextInt(FOREST_SIZE)));
        }
    }

    public void startSimulation() {
        Scanner scanner = new Scanner(System.in);
        int day = 0;

        while (true) {
            day++;
            System.out.println("\n=== День " + day + " ===");
            printStatistics();

            for (Organism org : organisms) {
                if (org instanceof Herbivore) {
                    eatPlants((Herbivore) org);
                } else if (org instanceof Predator) {
                    eatHerbivores((Predator) org);
                }
            }

            for (Organism org : organisms) {
                org.move();
            }

            List<Organism> newOrganisms = new ArrayList<>();
            for (Organism org : organisms) {
                org.age();
                org.eat();
                org.reproduce(newOrganisms);
            }

            organisms.addAll(newOrganisms);

            organisms.removeIf(org -> !org.isAlive());

            printEcosystem();

            System.out.print("\nНажмите Enter для следующего дня (q для выхода)...");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("q")) {
                break;
            }
        }
        scanner.close();
    }

    private void eatPlants(Herbivore herbivore) {
        if (!herbivore.isAlive()) return;

        for (Organism org : organisms) {
            if (org instanceof Plant && org.isAlive() &&
                    herbivore.x == org.x && herbivore.y == org.y) {
                org.alive = false;
                herbivore.energy += Herbivore.EAT_ENERGY;
                return;
            }
        }
    }

    private void eatHerbivores(Predator predator) {
        if (!predator.isAlive()) return;

        for (Organism org : organisms) {
            if (org instanceof Herbivore && org.isAlive() &&
                    predator.x == org.x && predator.y == org.y) {
                org.alive = false;
                predator.energy += Predator.EAT_ENERGY;
                return;
            }
        }
    }

    private void printStatistics() {
        int plants = 0;
        int herbivores = 0;
        int predators = 0;

        for (Organism org : organisms) {
            if (org instanceof Plant && org.isAlive()) plants++;
            else if (org instanceof Herbivore && org.isAlive()) herbivores++;
            else if (org instanceof Predator && org.isAlive()) predators++;
        }

        System.out.println("Растения: " + plants);
        System.out.println("Травоядные: " + herbivores);
        System.out.println("Хищники: " + predators);
    }

    private void printEcosystem() {
        char[][] grid = new char[FOREST_SIZE][FOREST_SIZE];

        for (int i = 0; i < FOREST_SIZE; i++) {
            for (int j = 0; j < FOREST_SIZE; j++) {
                grid[i][j] = '.';
            }
        }

        for (Organism org : organisms) {
            if (org.isAlive()) {
                char current = grid[org.x][org.y];
                if (current == '.' || (current == 'P' && !(org instanceof Plant))) {
                    grid[org.x][org.y] = org.symbol.charAt(0);
                }
            }
        }

        System.out.println("\nКарта экосистемы:");
        for (int i = 0; i < FOREST_SIZE; i++) {
            for (int j = 0; j < FOREST_SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("\nУсловные обозначения:");
        System.out.println("P - Растение  H - Травоядное  X - Хищник");
    }
}
