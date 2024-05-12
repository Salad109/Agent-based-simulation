package Simulation;

import java.util.ArrayList;
import java.util.LinkedList;

public class Carcass extends Animal {
    private int decomposingTurns;

    public Carcass(int x, int y) {
        super(x, y);
        decomposingTurns = 10;
    }

    @Override
    protected void act(LinkedList<Animal> animals, ArrayList<ArrayList<Tile>> tiles) {
        if (decomposingTurns++ >= 10)
            markedForDeath = true;
    }
}
