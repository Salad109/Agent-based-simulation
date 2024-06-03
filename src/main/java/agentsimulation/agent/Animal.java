package agentsimulation.agent;

import agentsimulation.logic.*;
import agentsimulation.Simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Animal {
    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    protected int positionX;
    protected int positionY;
    protected double storedFood;
    public boolean markedForDeath;
    protected LinkedList<String> diet;
    protected double foodFromEating = 15;
    protected double foodLossPerTurn = 1;
    protected double foodBirthThreshold = 250;


    public boolean getMarkedForDeath() {
        return markedForDeath;
    }

    Animal(int PositionX, int PositionY) {
        Simulation.animalCount += 1;
        this.positionX = PositionX;
        this.positionY = PositionY;
        storedFood = 29;
        markedForDeath = false;
    }

    public String getSpecies() {
        return getClass().getSimpleName();
    }

    public void act(ConcurrentLinkedQueue<Animal> animals, ArrayList<ArrayList<TileGrid.Tile>> tiles) {
        hunt(animals, tiles);
        evaluateFood(animals);
    }

    protected void hunt(ConcurrentLinkedQueue<Animal> animals, ArrayList<ArrayList<TileGrid.Tile>> tiles) {
        int newX = getNewRandomCoordinate(positionX);
        int newY = getNewRandomCoordinate(positionY);

        String tileStatus = lookAtTile(newX, newY, animals);
        if (tileStatus.equals("Empty"))
            moveToTile(newX, newY);
        else if (tileStatus.equals("Edible")) { // 2 = Target edible
            eatTarget(newX, newY, animals);
            moveToTile(newX, newY);
        }
    }

    protected void evaluateFood(ConcurrentLinkedQueue<Animal> animals) {
        storedFood -= foodLossPerTurn;
        if (storedFood >= foodBirthThreshold)
            attemptBirth(animals);
        else if (storedFood < 0)
            markedForDeath = true;
    }

    // 2 = Target edible, 1 = Target inedible(tile obstructed), 0 = Empty tile
    protected String lookAtTile(int newX, int newY, ConcurrentLinkedQueue<Animal> animals) {
        for (Animal animal : animals) {
            if (animal.getPositionX() == newX && animal.getPositionY() == newY) {
                if (canEat(animal))
                    return "Edible";
            }
        }
        return "Empty";
    }

    protected void moveToTile(int newX, int newY) {
        // Update position
        positionX = newX;
        positionY = newY;
    }

    private boolean canEat(Animal target) {
        for (String animal : diet) {
            if (animal.equals(target.getSpecies()))
                return true;
        }
        return false;
    }

    protected void eatTarget(int targetX, int targetY, ConcurrentLinkedQueue<Animal> animals) {
        Iterator<Animal> it = animals.iterator();
        while (it.hasNext()) {
            Animal animal = it.next();
            if (animal.getPositionX() == targetX && animal.getPositionY() == targetY) {
                it.remove(); // Remove using iterator method
                Simulation.animalCount -= 1;
                storedFood += foodFromEating;
                break; // Stop iterating after finding the target
            }
        }
    }


    protected void attemptBirth(ConcurrentLinkedQueue<Animal> animals) {
        int newX = getNewRandomCoordinate(positionX);
        int newY = getNewRandomCoordinate(positionY);

        String tileStatus = lookAtTile(newX, newY, animals);
        if (tileStatus.equals("Empty")) {
            Animal newborn;
            switch (getSpecies()) {
                case "Wolf":
                    newborn = new Wolf(newX, newY);
                    animals.add(newborn);
                    break;

                case "Bear":
                    newborn = new Bear(newX, newY);
                    animals.add(newborn);
                    break;

                case "Vulture":
                    newborn = new Vulture(newX, newY);
                    animals.add(newborn);
                    break;

                case "Sheep":
                    newborn = new Sheep(newX, newY);
                    animals.add(newborn);
                    break;

                default:
            }
        }
    }


    protected int getNewRandomCoordinate(int oldA) {
        int newA;
        do {
            newA = oldA;
            double randomDouble = Simulation.RNG.nextDouble();
            if (randomDouble < 1 / 3d)
                newA += 1;
            else if (randomDouble < 2 / 3d)
                newA -= 1;
        } while (newA < 0 || newA >= Simulation.SIMULATION_SIZE);
        return newA;
    }

    @Override
    public String toString() {
        return getSpecies() + "(" + getPositionX() + ", " + getPositionY() + ")";
    }

}

