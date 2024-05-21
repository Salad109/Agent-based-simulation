package agentsimulation.agent;

import agentsimulation.logic.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Sheep extends Animal {
    public Sheep(int x, int y) {
        super(x, y);
        diet = new LinkedList<>();

        foodBirthThreshold -= 50;
    }

    @Override
    public void hunt(ConcurrentLinkedQueue<Animal> animals, ArrayList<ArrayList<TileGrid.Tile>> tiles) {
        int newX = getNewRandomCoordinate(positionX);
        int newY = getNewRandomCoordinate(positionY);

        int action = lookAtTile(newX, newY, animals);
        if (action == 0)
            moveToTile(newX, newY);

        if (tiles.get(positionY).get(positionX).getGrass()) {
            tiles.get(positionY).get(positionX).setGrass(false);
            storedFood += foodFromEating;
        }
    }
}
