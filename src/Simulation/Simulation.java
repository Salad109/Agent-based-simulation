package Simulation;

import java.util.Random;

public class Simulation {
    public static Random RNG = new Random();
    public static int simulationSize = 20;

    public static void main(String[] args) {
        Map simulationMap = new Map(simulationSize, 0.25);

        GUI grid = new GUI(simulationMap.animals);
        grid.display();

        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                simulationMap.nextFrame();
                grid.update(simulationMap.animals);
                //noinspection BusyWait
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            }
        }
    }
}
