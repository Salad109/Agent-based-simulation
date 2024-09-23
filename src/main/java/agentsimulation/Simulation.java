package agentsimulation;

import agentsimulation.logic.FileLogger;

import java.util.Random;

/**
 * Main class of the simulation
 * <a href="https://github.com/Salad109/Agent-based-simulation">Github repository</a>
 */

public class Simulation {
    public static final Random RNG = new Random(System.currentTimeMillis());
    public static final int SIMULATION_SIZE = 50;
    public static final int TICK_LENGTH_MS = 25;
    public static int animalCount;

    public static void main(String[] args) {
        SimulationThread simulationThread = new SimulationThread();

        GUIThread guiThread = new GUIThread(simulationThread.getAgentHandler());

        FileLogger fileLogger = new FileLogger();

        runSimulation(simulationThread, guiThread, fileLogger);
    }

    private static void runSimulation(SimulationThread simulationThread, GUIThread guiThread, FileLogger fileLogger) {
        simulationThread.thread.start();
        guiThread.thread.start();
        while (simulationThread.thread.isAlive()) {
            try {
                if (simulationThread.logReady())
                    fileLogger.logStatus(simulationThread.getAgentHandler().getAnimals(), simulationThread.getTickCounter());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        int timeSeconds = simulationThread.getTickCounter() * TICK_LENGTH_MS / 1000;
        String finalMessage = String.format("Simulation ended, %d ticks have passed, or %d seconds.", simulationThread.getTickCounter(), timeSeconds);
        System.out.println(finalMessage);
        System.exit(0);
    }
}
