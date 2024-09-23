package agentsimulation;

import agentsimulation.logic.AgentHandler;
import agentsimulation.logic.FileLogger;

public class SimulationThread implements Runnable {

    AgentHandler agentHandler;
    public Thread thread;
    private int tickCounter;
    FileLogger fileLogger;

    SimulationThread() {
        thread = new Thread(this, "The Simulation Thread");
        this.agentHandler = new AgentHandler();
        fileLogger = new FileLogger();
        tickCounter = 0;
    }

    public void run() {
        do {
            synchronized (this) {
                agentHandler.nextFrame();
                tickCounter++;

                try {
                    if (tickCounter % 10 == 0)
                        fileLogger.logStatus(agentHandler.getAnimals(), tickCounter);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                try {
                    wait(Simulation.TICK_LENGTH_MS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } while (Simulation.animalCount > 5);
    }

    public AgentHandler getAgentHandler() {
        return agentHandler;
    }

    public int getTickCounter() {
        return tickCounter;
    }
}
