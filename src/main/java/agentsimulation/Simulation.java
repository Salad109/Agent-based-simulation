package agentsimulation;

import agentsimulation.logic.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

public class Simulation {
    public static Random RNG = new Random(System.currentTimeMillis());
    public final static int simulationSize = 40;
    public static int animalCount;
    private final static int tickLengthMS = 25;

    public static void main(String[] args) {
        AgentHandler agentHandler = new AgentHandler();
        GUI gui = new GUI(agentHandler.getAnimals(), agentHandler.getTiles());

        runSimulation(agentHandler, gui);
    }

    private static void runSimulation(AgentHandler agentHandler, GUI gui) {
        gui.display();
        long previousTime = System.currentTimeMillis();

        long tickCount = 0;
        long tickTarget = Long.MAX_VALUE; // TODO demo value

        do {
            try {
                agentHandler.nextFrame();
                gui.revalidate();
                gui.update(agentHandler.getAnimals(), agentHandler.getTiles());

                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - previousTime;

                if (elapsedTime < Simulation.tickLengthMS) {
                    TimeUnit.MILLISECONDS.sleep(Simulation.tickLengthMS - elapsedTime);
                }

                tickCount += 1;
                previousTime = currentTime;
            } catch (InterruptedException e) {
                Logger logger = Logger.getLogger("Simulation logger");
                logger.log(Level.SEVERE, "Simulation loop interrupted", e);
            }
            // Simulation ends when tick target is reached or the last animal is dead
        } while (animalCount > 0 && tickCount < tickTarget);

        long totalTimeS = Simulation.tickLengthMS * tickCount / 1000;
        gui.finalMessage(tickCount, totalTimeS);

        System.exit(0);
    }

}

