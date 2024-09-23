package agentsimulation;

import agentsimulation.logic.FileLogger;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class of the simulation
 * <a href="https://github.com/Salad109/Agent-based-simulation">Github repository</a>
 */

public class Simulation {
    public static final Random RNG = new Random(System.currentTimeMillis());
    public static final int SIMULATION_SIZE = 50;
    public static int animalCount;
    private static final int TICK_LENGTH_MS = 25;

    public static void main(String[] args) {
        SimulationThread simulationThread = new SimulationThread();

        GUIThread guiThread = new GUIThread(simulationThread.getAgentHandler());

        FileLogger fileLogger = new FileLogger();

        runSimulation(simulationThread, guiThread, fileLogger);
    }

    private static void runSimulation(SimulationThread simulationThread, GUIThread guiThread, FileLogger fileLogger) {
        long previousTime = System.currentTimeMillis();
        long tickCount = 0;

        do {
            try {
                // Progress simulation and update the display
                simulationThread.thread.start();
                guiThread.thread.start();

                // Log simulation status every 10 ticks
                if (tickCount % 10 == 0) {
                    fileLogger.logStatus(simulationThread.getAgentHandler().getAnimals(), tickCount);
                }
                if (tickCount % 1000 == 0)
                    System.out.println(Thread.getAllStackTraces());

                // Time and tick tracker
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - previousTime;
                if (elapsedTime < TICK_LENGTH_MS) {
                    TimeUnit.MILLISECONDS.sleep(TICK_LENGTH_MS - elapsedTime);
                }

                tickCount++;
                previousTime = currentTime;

            } catch (InterruptedException e) {
                Logger logger = Logger.getLogger(Simulation.class.getName());
                logger.log(Level.SEVERE, "Simulation loop interrupted", e);
                Thread.currentThread().interrupt();
            }
        } while (animalCount > 5);

        System.out.printf("Simulation ended, %d ticks have passed.%n", tickCount);
        System.exit(0);
    }
}
