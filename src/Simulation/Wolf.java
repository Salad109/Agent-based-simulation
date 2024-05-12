package Simulation;

import java.util.LinkedList;

public class Wolf extends Animal {
    public Wolf(int x, int y) {
        super(x, y);
        diet = new LinkedList<>();
        diet.add("Sheep");
    }

    @Override
    protected void evaluateFood(LinkedList<Animal> animals) {
        storedFood -= 1;
        if (storedFood >= 30)
            attemptBirth(animals);
        else if (storedFood < 0)
            starving = true;
    }
}
