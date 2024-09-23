package agentsimulation;

import agentsimulation.logic.AgentHandler;
import agentsimulation.logic.GUI;

public class GUIThread implements Runnable {
    GUI gui;
    AgentHandler agentHandler;
    public Thread thread;

    GUIThread(AgentHandler agentHandler) {
        thread = new Thread(this, "The GUI Thread");
        this.agentHandler = agentHandler;
        this.gui = new GUI(agentHandler.getAnimals(), agentHandler.getTiles());
        gui.display();
    }

    public void run() {
        do {
            synchronized (this) {
                gui.update(agentHandler.getAnimals(), agentHandler.getTiles());
                try {
                    wait(Simulation.TICK_LENGTH_MS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } while (Simulation.animalCount > 5);
    }
}
