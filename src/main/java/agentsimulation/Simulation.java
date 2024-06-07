package agentsimulation;

import agentsimulation.logic.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

public class Simulation {
    public static Random RNG = new Random(System.currentTimeMillis());
    public final static int SIMULATION_SIZE = 50;
    public static int animalCount;
    private final static int TICK_LENGTH_MS = 25;

    public static void main(String[] args) {
        AgentHandler agentHandler = new AgentHandler();
        GUI gui = new GUI(agentHandler.getAnimals(), agentHandler.getTiles());
        //noinspection unused
        Graph graph = new Graph();

        runSimulation(agentHandler, gui);
    }

    private static void runSimulation(AgentHandler agentHandler, GUI gui) {
        gui.display();
        long previousTime = System.currentTimeMillis();

        long tickCount = 0;

        do {
            try {
                // Simulation actions
                agentHandler.nextFrame();
                gui.revalidate();
                gui.update(agentHandler.getAnimals(), agentHandler.getTiles());

                // Log simulation status
                if (tickCount % 25 == 0) {
                    gui.logger.logStatus(agentHandler.getAnimals(), tickCount);
                }

                // Time tracker
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - previousTime;
                if (elapsedTime < Simulation.TICK_LENGTH_MS) {
                    TimeUnit.MILLISECONDS.sleep(Simulation.TICK_LENGTH_MS - elapsedTime);
                }
                tickCount += 1;
                previousTime = currentTime;

            } catch (InterruptedException e) {
                Logger logger = Logger.getLogger("Simulation logger");
                logger.log(Level.SEVERE, "Simulation loop interrupted", e);
            }
        } while (animalCount > 5);

        long totalTimeS = Simulation.TICK_LENGTH_MS * tickCount / 1000;
        System.out.println(gui.finalMessage(tickCount, totalTimeS));

        System.exit(0);
    }

}

