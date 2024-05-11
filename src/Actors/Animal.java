package Actors;
import Simulation.*;

public abstract class Animal {
    public int getPositionX() {
        return PositionX;
    }

    public int getPositionY() {
        return PositionY;
    }

    protected int PositionX, PositionY;

    Animal(int PositionX, int PositionY) {
        this.PositionX = PositionX;
        this.PositionY = PositionY;
    }

    public String getType(){
        return getClass().getSimpleName();
    }

    public void move() {
        int newX = PositionX;
        int newY = PositionY;

        do {
            int direction = Simulation.RNG.nextInt(4); // 0 = Left, 1 = Right, 2 = Up, 3 = Down
            switch (direction) {
                case 0: // Left
                    newX--;
                    break;
                case 1: // Right
                    newX++;
                    break;
                case 2: // Up
                    newY++;
                    break;
                case 3: // Down
                    newY--;
                    break;
            }
        } while (newX < 0 || newX >= Simulation.simulationSize || newY < 0 || newY >= Simulation.simulationSize); // Check if the new position is within bounds
        System.out.println(getType() + " (" + PositionX + "; " + PositionY + ") -> (" + newX + "; " + newY + ")");
        PositionX = newX;
        PositionY = newY;

    }
}

