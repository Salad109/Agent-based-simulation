package Simulation;

import java.util.Random;

public class Simulation {
    public static Random RNG = new Random(System.currentTimeMillis());
    protected final static int simulationSize = 40;
    public static int animalCount;
    protected final static double startingSpawnRate = 0.25;
    public static void main(String[] args) {
        AnimalHandler animalHandler = new AnimalHandler();

        GUI grid = new GUI(animalHandler.getAnimals(), animalHandler.getTiles());
        grid.display();

        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                animalHandler.nextFrame();
                grid.update(animalHandler.getAnimals(), animalHandler.getTiles());
                //noinspection BusyWait
                Thread.sleep(50);
            } catch (InterruptedException e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            }
        }
    }
}
