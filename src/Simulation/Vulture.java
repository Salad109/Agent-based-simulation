package Simulation;

import java.util.ArrayList;
import java.util.LinkedList;

public class Vulture extends Animal {
    public Vulture(int x, int y) {
        super(x, y);
        diet = new LinkedList<>();
        diet.add("Carcass");
    }
    @Override
    protected void act(LinkedList<Animal> animals, ArrayList<ArrayList<Tile>> tiles) {
        hunt(animals, tiles);
        hunt(animals, tiles);
        hunt(animals, tiles);
        hunt(animals, tiles);
        evaluateFood(animals);
    }
}
