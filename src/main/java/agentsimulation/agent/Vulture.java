package agentsimulation.agent;

import agentsimulation.logic.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import static agentsimulation.Simulation.RNG;

public class Vulture extends Animal {
    public Vulture(int x, int y) {
        super(x, y);
        diet = new LinkedList<>();
        diet.add("Carcass");

        foodBirthThreshold += 100;
        foodFromEating += 15;
    }

    @Override
    public void act(ConcurrentLinkedQueue<Animal> animals, ArrayList<ArrayList<TileGrid.Tile>> tiles) {
        if (RNG.nextBoolean()) {
            hunt(animals, tiles);
            evaluateFood(animals);
        }
    }
}
