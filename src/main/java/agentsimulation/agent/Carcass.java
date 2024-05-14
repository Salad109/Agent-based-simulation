package agentsimulation.agent;

import agentsimulation.logic.*;
import agentsimulation.Simulation;

import java.util.ArrayList;
import java.util.LinkedList;

public class Carcass extends Animal {
    private int decomposingTurns;

    public Carcass(int x, int y) {
        super(x, y);
        decomposingTurns = 10;
    }

    @Override
    public void act(LinkedList<Animal> animals, ArrayList<ArrayList<Tile>> tiles) {
        if (decomposingTurns++ >= 30) {
            markedForDeath = true;
            fertilizeSurroundings(tiles);
        }
    }

    // Upon decomposing, set tiles in 3x3 radius to have grass
    private void fertilizeSurroundings(ArrayList<ArrayList<Tile>> tiles) {
        for (int i = -1; i < 2; i++)
            for (int j = -1; j < 2; j++) {
                int x = getPositionY() + i;
                int y = getPositionX() + j;
                if (x < Simulation.simulationSize && x >= 0 && y < Simulation.simulationSize && y >= 0)
                    tiles.get(x).get(y).setGrass(true);
            }
    }
}