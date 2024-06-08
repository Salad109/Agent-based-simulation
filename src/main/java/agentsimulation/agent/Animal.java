package agentsimulation.agent;

import agentsimulation.logic.*;
import agentsimulation.Simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Abstract animal class with default animal methods and values
 */
public abstract class Animal {
    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    protected int positionX;
    protected int positionY;
    public boolean markedForDeath;
    protected LinkedList<String> diet;
    protected double storedFood;
    protected double foodFromEating = 15;
    protected double foodLossPerTurn = 1;
    protected double foodBirthThreshold = 250;



    Animal(int PositionX, int PositionY) {
        Simulation.animalCount += 1;
        this.positionX = PositionX;
        this.positionY = PositionY;
        diet = new LinkedList<>();
        storedFood = 100;
        markedForDeath = false;
    }

    public String getSpecies() {
        return getClass().getSimpleName();
    }

    public void act(ConcurrentLinkedQueue<Animal> animals, ArrayList<ArrayList<TileGrid.Tile>> tiles) {
        hunt(animals, tiles);
        evaluateFood(animals);
    }

    /**
     * Randomly moves to a new, neighboring tile. Will eat the animal inhabiting the new tile if it matches its diet
     * @param animals Animal list
     * @param tiles Tile grid
     */
    protected void hunt(ConcurrentLinkedQueue<Animal> animals, ArrayList<ArrayList<TileGrid.Tile>> tiles) {
        int newX = generateNewRandomCoordinate(positionX);
        int newY = generateNewRandomCoordinate(positionY);

        String tileStatus = lookAtTile(newX, newY, animals);
        if (tileStatus.equals("Empty"))
            moveToTile(newX, newY);
        else if (tileStatus.equals("Edible")) {
            eatTarget(newX, newY, animals);
            moveToTile(newX, newY);
        }
    }


    /**
     * Makes an action depending on its food reserve value. Attempts birth if stored food is high, or marks itself for deletion if it's negative
     * @param animals Animal list
     */
    protected void evaluateFood(ConcurrentLinkedQueue<Animal> animals) {
        storedFood -= foodLossPerTurn;
        if (storedFood >= foodBirthThreshold)
            attemptBirth(animals);
        else if (storedFood < 0)
            markedForDeath = true;
    }

    /**
     * @param newX X coordinate of the tile to be analyzed
     * @param newY X coordinate of the tile to be analyzed
     * @param animals Animal list
     * @return "Edible" if the animal which inhabits the target tile is deemed edible by the function's caller. Returns "Empty" otherwise
     */
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
        positionX = newX;
        positionY = newY;
    }

    /**
     * @param target Animal instance which will be checked for its edibleness by the function caller.
     * @return True if the target's species is a part of the function caller's diet.
     */
    private boolean canEat(Animal target) {
        for (String animal : diet) {
            if (animal.equals(target.getSpecies()))
                return true;
        }
        return false;
    }

    /**
     * Removes the instance of the animal inhabiting the target tile, then adds a certain amount of food to the killer.
     * @param targetX X coordinate of the animal to be eaten
     * @param targetY Y coordinate of the animal to be eaten
     * @param animals Animal list
     */
    protected void eatTarget(int targetX, int targetY, ConcurrentLinkedQueue<Animal> animals) {
        Iterator<Animal> it = animals.iterator();
        while (it.hasNext()) {
            Animal animal = it.next();
            if (animal.getPositionX() == targetX && animal.getPositionY() == targetY) {
                it.remove();
                Simulation.animalCount -= 1;
                storedFood += foodFromEating;
                break;
            }
        }
    }

    /**
     * Looks in a random direction, and if the target tile is empty - creates a new instance of its species on the target tile
     * @param animals Animal list
     */
    protected void attemptBirth(ConcurrentLinkedQueue<Animal> animals) {
        int newX = generateNewRandomCoordinate(positionX);
        int newY = generateNewRandomCoordinate(positionY);

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


    /**
     * Get a new, neighboring coordinate value based on random chance. 33% chance to get a value that's lower by 1, 33% (...) higher, and 33% to return the input untouched.
     * Makes sure that the returned value is within bounds of the simulation size.
     * @param oldCoordinate The coordinate which will be used to generate the new, neighboring one
     * @return New, neighboring coordinate value
     */
    protected int generateNewRandomCoordinate(int oldCoordinate) {
        int newCoordinate;
        do {
            newCoordinate = oldCoordinate;
            double randomDouble = Simulation.RNG.nextDouble();
            if (randomDouble < 1 / 3d)
                newCoordinate += 1;
            else if (randomDouble < 2 / 3d)
                newCoordinate -= 1;
        } while (newCoordinate < 0 || newCoordinate >= Simulation.SIMULATION_SIZE);
        return newCoordinate;
    }

    public boolean getMarkedForDeath() {
        return markedForDeath;
    }

}

