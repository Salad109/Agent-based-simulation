package agentsimulation;

import agentsimulation.logic.AgentHandler;

public class SimulationThread implements Runnable {

    AgentHandler agentHandler;
    public Thread thread;

    SimulationThread() {
        thread = new Thread(this, "The Simulation Thread");
        this.agentHandler = new AgentHandler();
    }

    public void run() {
        agentHandler.nextFrame();
    }

    public AgentHandler getAgentHandler() {
        return agentHandler;
    }
}
