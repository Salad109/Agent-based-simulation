package agentsimulation.agent;


public class Bear extends Animal {
    public Bear(int x, int y) {
        super(x, y);
        diet.add("Sheep");
        diet.add("Wolf");
        foodLossPerTurn -= 0.6;
        foodBirthThreshold += 50;
    }
}
