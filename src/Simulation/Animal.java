package Simulation;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Animal {
    public int getPositionX() {
        return PositionX;
    }

    public int getPositionY() {
        return PositionY;
    }

    protected int PositionX;
    protected int PositionY;
    protected double storedFood;
    public boolean markedForDeath;
    protected LinkedList<String> diet;
    protected double foodFromEating = 15;
    protected double foodLossPerTurn = 1;
    protected double foodBirthThreshold = 200;



    public boolean isMarkedForDeath() {
        return markedForDeath;
    }

    Animal(int PositionX, int PositionY) {
        Simulation.animalCount += 1;
        this.PositionX = PositionX;
        this.PositionY = PositionY;
        storedFood = 29;
        markedForDeath = false;
    }

    public String getSpecies() {
        return getClass().getSimpleName();
    }

    protected void act(LinkedList<Animal> animals, ArrayList<ArrayList<Tile>> tiles) {
        hunt(animals, tiles);
        evaluateFood(animals);
    }

    protected void hunt(LinkedList<Animal> animals,  ArrayList<ArrayList<Tile>> tiles) {
        int newX = getNewRandomCoordinate(PositionX);
        int newY = getNewRandomCoordinate(PositionY);

        int action = lookAtTile(newX, newY, animals);
        if (action == 0)
            moveToTile(newX, newY);
            // else if (action == 2)  Means tile is occupied by something inedible, unable to act
        else if (action == 2) {
            eatTarget(newX, newY, animals);
            moveToTile(newX, newY);
        }
    }

    protected void evaluateFood(LinkedList<Animal> animals) {
        storedFood -= foodLossPerTurn;
        if (storedFood >= foodBirthThreshold)
            attemptBirth(animals);
        else if (storedFood < 0)
            markedForDeath = true;
    }

    protected int lookAtTile(int newX, int newY, LinkedList<Animal> animals) { // 2 = Target edible, 1 = Target inedible, 0 = Empty tile
        for (Animal animal : animals) {
            if (animal.getPositionX() == newX && animal.getPositionY() == newY) {
                if (canEat(animal)) {
                    return 2;
                } else if (animal.getSpecies().equals("Carcass")) {
                    return 0;
                } else {
                    return 1;
                }
            }
        }
        return 0;
    }

    protected void moveToTile(int newX, int newY) {
        // Update position
        PositionX = newX;
        PositionY = newY;
    }

    private boolean canEat(Animal target) {
        for (String animal : diet) {
            if (animal.equals(target.getSpecies()))
                return true;
        }
        return false;
    }

    protected void eatTarget(int targetX, int targetY, LinkedList<Animal> animals) {
        int i = 0;
        while (i < animals.size()) {
            Animal animal = animals.get(i);
            if (animal.getPositionX() == targetX && animal.getPositionY() == targetY) {
                animals.remove(i); // Remove element at current index
                Simulation.animalCount -= 1;
                storedFood += foodFromEating;
            } else {
                i++; // Only increment i if not removed
            }
        }
    }

    protected void attemptBirth(LinkedList<Animal> animals) {
        int newX = getNewRandomCoordinate(PositionX);
        int newY = getNewRandomCoordinate(PositionY);

        int action = lookAtTile(newX, newY, animals);
        if (action == 0) {
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
        } while (newA < 0 || newA >= Simulation.simulationSize);
        return newA;
    }
}

