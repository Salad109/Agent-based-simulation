package Simulation;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

public class Simulation {
    public static Random RNG = new Random(System.currentTimeMillis());
    protected final static int simulationSize = 40;
    public static int animalCount;
    protected final static double startingSpawnRate = 0.25;
    private final static int tickLengthMS = 25;

    public static void main(String[] args) {
        AnimalHandler animalHandler = new AnimalHandler();
        GUI grid = new GUI(animalHandler.getAnimals(), animalHandler.getTiles());

        runSimulation(animalHandler, grid, tickLengthMS);
    }

    private static void runSimulation(AnimalHandler animalHandler, GUI grid, int tickLengthMS) {
        grid.display();
        long previousTime = System.currentTimeMillis();

        while (true) {
            try {
                animalHandler.nextFrame();
                grid.revalidate();
                grid.update(animalHandler.getAnimals(), animalHandler.getTiles());

                // Calculate time elapsed since last iteration
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - previousTime;

                // Sleep for remaining time (if needed)
                if (elapsedTime < tickLengthMS) {
                    TimeUnit.MILLISECONDS.sleep(tickLengthMS - elapsedTime);
                }

                previousTime = currentTime; // Update for next iteration
            } catch (InterruptedException e) {
                Logger logger = Logger.getLogger("Simulation logger");
                logger.log(Level.SEVERE, "Simulation loop interrupted", e);
            }
        }

    }

}

