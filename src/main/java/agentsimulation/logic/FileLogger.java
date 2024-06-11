package agentsimulation.logic;

import agentsimulation.agent.Animal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Class responsible for logging the simulation status to a .csv file
 */
public class FileLogger {
    private FileWriter writer;

    public FileLogger() {
        String fileName = "log.csv";
        File file = new File(fileName);
        try {
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("File deleted: " + fileName);
                } else {
                    System.out.println("Failed to delete the file: " + fileName);
                }
            }

            if (file.createNewFile()) {
                System.out.println("File created: " + fileName);
            } else {
                System.out.println("Failed to create the file: " + fileName);
            }

            writer = new FileWriter(fileName, true);
            writer.write("Ticks,Bears,Carcasses,Sheep,Vultures,Wolves\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write the current status of the simulation to a line inside log.csv
     * @param animals Animal list
     * @param tickCount Current tick
     */
    public void logStatus(ConcurrentLinkedQueue<Animal> animals, long tickCount) {
        try {
            String logMessage = (tickCount + ",").concat(statusMessage(animals).concat("\n"));
            writer.write(logMessage);
            //System.out.print(logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Neatly format the species populations inside a string
     * @param animals Animal list
     * @return String listing all species' populations
     */
    private String statusMessage(ConcurrentLinkedQueue<Animal> animals) {
        int bearCount = 0;
        int carcassCount = 0;
        int sheepCount = 0;
        int vultureCount = 0;
        int wolfCount = 0;
        for (Animal animal : animals) {
            String species = animal.getSpecies();
            switch (species) {
                case "Bear":
                    bearCount += 1;
                    break;
                case "Carcass":
                    carcassCount += 1;
                    break;
                case "Sheep":
                    sheepCount += 1;
                    break;
                case "Vulture":
                    vultureCount += 1;
                    break;
                case "Wolf":
                    wolfCount += 1;
                    break;
            }
        }
        return String.format("%d,%d,%d,%d,%d", bearCount, carcassCount, sheepCount, vultureCount, wolfCount);
    }
}