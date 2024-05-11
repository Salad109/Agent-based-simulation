package Simulation;

import Actors.*;

import java.util.LinkedList;

public class Map {
    protected double startingSpawnRate;
    public LinkedList<Animal> animals;


    Map(int simulationSize, double startingSpawnRate) {
        Simulation.simulationSize = simulationSize;
        this.startingSpawnRate = startingSpawnRate;
        animals = new LinkedList<>();

        for (int i = 0; i < simulationSize; i++) {
            for (int j = 0; j < simulationSize; j++) {
                if (startingSpawnRate >= Simulation.RNG.nextDouble()) {
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

    public void nextFrame() {
        for (Animal a : animals) {
            a.move();
        }
    }
}
