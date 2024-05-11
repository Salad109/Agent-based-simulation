package Simulation;

import java.util.LinkedList;

public class AnimalHandler {
    private LinkedList<Animal> animals;
    private Tile[][] grassGrid;

    AnimalHandler() {
        animalSpawner();
    }

    public LinkedList<Animal> getAnimals() {
        return animals;
    }

    public void nextFrame() {
        for (Animal a : animals) {
            a.act();
        }
    }

    private void grassSpawner() {
        grassGrid = new Tile[Simulation.simulationSize][Simulation.simulationSize];
        for (int i = 0; i < Simulation.simulationSize; i++) {
            for (int j = 0; j < Simulation.simulationSize; j++) {
                grassGrid[i][j] = new Tile();
            }
        }
    }

    private void animalSpawner() {
        animals = new LinkedList<>();

        for (int i = 0; i < Simulation.simulationSize; i++) {
            for (int j = 0; j < Simulation.simulationSize; j++) {
                if (Simulation.startingSpawnRate >= Simulation.RNG.nextDouble()) {
                    double seed = Simulation.RNG.nextDouble();
                    Animal animal;
                    if (seed < 0.2)
                        animal = new Bear(i, j);
                    else if (seed < 0.4)
                        animal = new Carcass(i, j);
                    else if (seed < 0.6)
                        animal = new Sheep(i, j);
                    else if (seed < 0.8)
                        animal = new Vulture(i, j);
                    else
                        animal = new Wolf(i, j);

                    animals.add(animal);
                }
            }
        }
    }
}