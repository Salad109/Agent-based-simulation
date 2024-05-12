package Simulation;

import java.util.ArrayList;
import java.util.LinkedList;

public class Sheep extends Animal {
    public Sheep(int x, int y) {
        super(x, y);
        diet = new LinkedList<>();
    }

    @Override
    protected void hunt(LinkedList<Animal> animals, ArrayList<ArrayList<Tile>> tiles) {
        int newX = getNewRandomCoordinate(PositionX);
        int newY = getNewRandomCoordinate(PositionY);

        int action = lookAtTile(newX, newY, animals);
        if (action == 0)
            moveToTile(newX, newY);

        if (tiles.get(PositionY).get(PositionX).getGrass()) {
            tiles.get(PositionY).get(PositionX).setGrass(false);
            storedFood += 10;
        }
    }

    @Override
    protected void evaluateFood(LinkedList<Animal> animals) {
        storedFood -= 1;
        if (storedFood >= 60)
            attemptBirth(animals);
        else if (storedFood < 0)
            starving = true;
    }
}
