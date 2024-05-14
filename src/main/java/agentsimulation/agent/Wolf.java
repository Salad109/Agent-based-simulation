package agentsimulation.agent;

import java.util.LinkedList;

public class Wolf extends Animal {
    public Wolf(int x, int y) {
        super(x, y);
        diet = new LinkedList<>();
        diet.add("Sheep");
        diet.add("Bear");
        foodLossPerTurn -= 0.5;
    }
}
