package agentsimulation;

import agentsimulation.logic.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

public class Simulation {
    public static Random RNG = new Random(System.currentTimeMillis());
    public final static int simulationSize = 40;
    public static int animalCount;
    public final static double startingSpawnRate = 0.25;
    private final static int tickLengthMS = 25;

    public static void main(String[] args) {
        AgentHandler agentHandler = new AgentHandler();
        GUI grid = new GUI(agentHandler.getAnimals(), agentHandler.getTiles());

        runSimulation(agentHandler, grid);
    }

    private static void runSimulation(AgentHandler agentHandler, GUI grid) {
        grid.display();
        long previousTime = System.currentTimeMillis();

        long tickCount = 0;
        long tickTarget = Long.MAX_VALUE; // TODO demo

        do {
            try {
                agentHandler.nextFrame();
                grid.revalidate();
                grid.update(agentHandler.getAnimals(), agentHandler.getTiles());

                // Calculate time elapsed since last iteration
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - previousTime;

                // Sleep for remaining time (if needed)
                if (elapsedTime < Simulation.tickLengthMS) {
                    TimeUnit.MILLISECONDS.sleep(Simulation.tickLengthMS - elapsedTime);
                }

                tickCount += 1;
                previousTime = currentTime; // Update for next iteration
            } catch (InterruptedException e) {
                Logger logger = Logger.getLogger("Simulation logger");
                logger.log(Level.SEVERE, "Simulation loop interrupted", e);
            }
        } while (animalCount > 0 && tickCount < tickTarget); // Simulation ends when tick target is reached or the last animal is dead

        long totalTimeS = Simulation.tickLengthMS * tickCount / 1000;
        long minutes = totalTimeS / 60;
        long seconds = totalTimeS % 60;

        String endMessage = String.format("All actors died, %d ticks have passed, or %d:%2d", tickCount, minutes, seconds);
        System.out.println(endMessage);

        System.exit(0);
    }

}

