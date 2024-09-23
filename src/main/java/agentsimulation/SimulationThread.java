package agentsimulation;

import agentsimulation.logic.AgentHandler;

public class SimulationThread implements Runnable {

    AgentHandler agentHandler;
    public Thread thread;
    private int tickCounter;

    SimulationThread() {
        thread = new Thread(this, "The Simulation Thread");
        this.agentHandler = new AgentHandler();
        tickCounter = 0;
    }

    public void run() {
        do {
            synchronized (this) {
                agentHandler.nextFrame();
                tickCounter++;
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

    public boolean logReady() {
        return tickCounter % 10 == 0;
    }

    public int getTickCounter() {
        return tickCounter;
    }
}
