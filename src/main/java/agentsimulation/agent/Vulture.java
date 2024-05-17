package agentsimulation.agent;

import agentsimulation.logic.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Vulture extends Animal {
    public Vulture(int x, int y) {
        super(x, y);
        diet = new LinkedList<>();
        diet.add("Carcass");

        foodBirthThreshold *= 25;
        foodFromEating += 15;
        foodLossPerTurn += 3;
    }

    @Override
    public void act(ConcurrentLinkedQueue<Animal> animals, ArrayList<ArrayList<Tile>> tiles) {
        for (int i = 0; i < 20; i++)
            hunt(animals, tiles);
        evaluateFood(animals);
    }
}
