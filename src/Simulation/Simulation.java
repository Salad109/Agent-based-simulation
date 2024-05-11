package Simulation;

import java.util.Random;

public class Simulation {
    public static Random RNG = new Random(System.currentTimeMillis());
    protected final static int simulationSize = 20;
    protected final static double startingSpawnRate = 0.25;
    public static void main(String[] args) {
        AnimalHandler animalHandler = new AnimalHandler();

        GUI grid = new GUI(animalHandler.getAnimals());
        grid.display();

        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                animalHandler.nextFrame();
                grid.update(animalHandler.getAnimals());
                //noinspection BusyWait
                Thread.sleep(500);
            } catch (InterruptedException e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            }
        }
    }
}
