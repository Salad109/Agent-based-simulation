package agentsimulation;

import agentsimulation.logic.AgentHandler;

public class SimulationThread implements Runnable {

    AgentHandler agentHandler;

    SimulationThread() {
        Thread t = new Thread(this, "The Simulation Thread");
        this.agentHandler = new AgentHandler();
    }

    public void run() {
        agentHandler.nextFrame();
    }

    public AgentHandler getAgentHandler() {
        return agentHandler;
    }
}
