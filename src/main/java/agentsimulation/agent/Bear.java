package agentsimulation.agent;

import java.util.LinkedList;

public class Bear extends Animal {
    public Bear(int x, int y) {
        super(x, y);
        diet = new LinkedList<>();
        diet.add("Sheep");
        diet.add("Wolf");
        foodLossPerTurn -= 0.5;
        foodBirthThreshold += 75;
    }
}
