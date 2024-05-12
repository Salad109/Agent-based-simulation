package Simulation;

import java.util.ArrayList;
import java.util.LinkedList;

public class AnimalHandler {
    private LinkedList<Animal> animals;
    private ArrayList<ArrayList<Tile>> grassGrid;

    AnimalHandler() {
        animalSpawner();
        grassSpawner();
    }

    public LinkedList<Animal> getAnimals() {
        return animals;
    }

    public ArrayList<ArrayList<Tile>> getTiles() {
        return grassGrid;
    }

    public void nextFrame() {
        animalsAct();
        tilesAct();
    }

    private void animalsAct() {
        for (int i = 0; i < Simulation.animalCount; i++) {
            Animal animal = animals.get(i);

            killStarving(animal);

            animal.act(getAnimals(), getTiles());

            Simulation.animalCount += Animal.newbornCount;
            Animal.newbornCount = 0;
        }
    }
    private void killStarving(Animal animal){
        if (animal.isStarving()) {
            int posX = animal.getPositionX();
            int posY = animal.getPositionY();
            animals.remove(animal);
            Simulation.animalCount -= 1;
            Animal corpse = new Carcass(posX, posY);
            animals.add(corpse);
        }
    }

    private void tilesAct() {
        for (ArrayList<Tile> row : grassGrid) {
            for (Tile tile : row) {
                if (!tile.getGrass())
                    tile.attemptRegrow();
            }
        }

    }

    private void grassSpawner() {
        grassGrid = new ArrayList<>(20);
        for (int i = 0; i < Simulation.simulationSize; i++) {
            ArrayList<Tile> row = new ArrayList<>(20);
            for (int j = 0; j < Simulation.simulationSize; j++) {
                Tile tile = new Tile(Simulation.RNG.nextDouble());
                row.add(tile);
            }
            grassGrid.add(row);
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