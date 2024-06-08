package agentsimulation.agent;


public class Wolf extends Animal {
    public Wolf(int x, int y) {
        super(x, y);
        diet.add("Sheep");
        diet.add("Bear");
        foodLossPerTurn -= 0.5;
    }
}
