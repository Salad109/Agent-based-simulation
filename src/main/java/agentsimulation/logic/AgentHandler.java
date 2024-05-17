package agentsimulation.logic;

import agentsimulation.agent.*;
import agentsimulation.Simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AgentHandler {
    private void killAllMarked(ConcurrentLinkedQueue<Animal> animals) {
        Iterator<Animal> it = animals.iterator();
        while (it.hasNext()) {
            Animal animal = it.next();
            if (animal.getMarkedForDeath()) {
                it.remove();
                Simulation.animalCount -= 1;
                if (!animal.getSpecies().equals("Carcass")) { // Place a carcass in place of starved animals
                    int posX = animal.getPositionX();
                    int posY = animal.getPositionY();
                    Animal corpse = new Carcass(posX, posY);
                    animals.add(corpse);
                }
            }
        }
    }


    private void act(ConcurrentLinkedQueue<Animal> animals) {
        for (Animal animal : animals) {
            animal.act(getAnimals(), getTiles());
        }
    }


    private ConcurrentLinkedQueue<Animal> animals;
    private final TileGrid grid;

    public AgentHandler() {
        animalSpawner();
        grid = new TileGrid();
        grid.spawnTiles();
    }

    public ConcurrentLinkedQueue<Animal> getAnimals() {
        return animals;
    }

    public ArrayList<ArrayList<TileGrid.Tile>> getTiles() {
        return grid.getGrassGrid();
    }

    public void nextFrame() {
        animalsAct();
        grid.tilesAct();
    }

    private void animalsAct() {
        killAllMarked(getAnimals());
        act(getAnimals());
    }


    private void animalSpawner() {
        double startingSpawnRate = 0.25;
        animals = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < Simulation.simulationSize; i++) {
            for (int j = 0; j < Simulation.simulationSize; j++) {
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

}