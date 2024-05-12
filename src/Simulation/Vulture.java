package Simulation;

import java.util.ArrayList;
import java.util.LinkedList;

public class Vulture extends Animal {
    public Vulture(int x, int y) {
        super(x, y);
        diet = new LinkedList<>();
        diet.add("Carcass");

        foodBirthThreshold *= 25;
        foodFromEating += 5;
        foodLossPerTurn += 1;
    }

    @Override
    protected void act(LinkedList<Animal> animals, ArrayList<ArrayList<Tile>> tiles) {
        for (int i = 0; i < 20; i++)
            hunt(animals, tiles);
        evaluateFood(animals);
    }
}
