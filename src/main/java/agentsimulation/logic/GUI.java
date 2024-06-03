package agentsimulation.logic;

import agentsimulation.Simulation;
import agentsimulation.agent.*;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.io.File;
import java.io.IOException;

public class GUI extends JPanel {
    public class FileLogger {
        private FileWriter writer;

        FileLogger() {
            String fileName = "log.csv";
            File file = new File(fileName);
            try {
                if (file.exists()) {
                    if (file.delete()) {
                        System.out.println("Old file deleted: " + fileName);
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

        public void logStatus(ConcurrentLinkedQueue<Animal> animals, long tickCount) {
            try {
                if (statusMessage(animals).contains(",")) {
                    String logMessage = (tickCount + ",").concat(statusMessage(animals).concat("\n"));
                    writer.write(logMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public FileLogger logger;
    private ConcurrentLinkedQueue<Animal> animals;
    private ArrayList<ArrayList<TileGrid.Tile>> tiles;
    private JFrame frame;
    private final Map<String, Image> animalImages;

    public GUI(ConcurrentLinkedQueue<Animal> animals, ArrayList<ArrayList<TileGrid.Tile>> tiles) {
        logger = new FileLogger();
        this.animals = animals;
        this.tiles = tiles;
        setPreferredSize(new Dimension(800, 800));
        animalImages = new HashMap<>();
        loadImages();
    }

    private void loadImages() {
        ArrayList<String> animalTypes = new ArrayList<>();
        animalTypes.add("Sheep");
        animalTypes.add("Wolf");
        animalTypes.add("Bear");
        animalTypes.add("Vulture");
        animalTypes.add("Carcass");

        for (String name : animalTypes) {
            String imagePath = "images/" + name + ".png";
            URL imgUrl = getClass().getClassLoader().getResource(imagePath);
            if (imgUrl != null) {
                animalImages.put(name, Toolkit.getDefaultToolkit().getImage(imgUrl));
            } else {
                System.err.println("Could not find image: " + imagePath);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Calculate cell size
        int cellSize = Math.min(getWidth(), getHeight()) / Simulation.SIMULATION_SIZE;

        // Draw grass
        g.fillRect(cellSize, cellSize, cellSize, cellSize);
        for (int i = 0; i < Simulation.SIMULATION_SIZE; i++) {
            for (int j = 0; j < Simulation.SIMULATION_SIZE; j++) {
                if (tiles.get(j).get(i).getGrass())
                    g.setColor(Color.getHSBColor(120 / 360f, 0.8f, 0.75f));
                else
                    g.setColor(Color.getHSBColor(20 / 360f, 0.6f, 0.45f));
                g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }

        // Draw animals
        for (Animal animal : animals) {
            if (animal == null)
                continue;

            int x = animal.getPositionX() * cellSize;
            int y = animal.getPositionY() * cellSize;

            // Get the image for the current animal's type
            String animalType = animal.getSpecies();
            Image image = animalImages.get(animalType);

            // Draw the image
            if (image != null) {
                g.drawImage(image, x, y, cellSize, cellSize, this);
            } else {
                g.setColor(Color.RED);
                g.fillRect(x, y, cellSize, cellSize);
            }
        }


        // Draw grid lines
        g.setColor(Color.BLACK);
        for (int i = 0; i <= Simulation.SIMULATION_SIZE; i++) {
            int linePos = i * cellSize;
            g.drawLine(0, linePos, getWidth(), linePos);
            g.drawLine(linePos, 0, linePos, getHeight());
        }
    }

    public void display() {
        frame = new JFrame("Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set program icon
        String imagePath = "images/Bear.png";
        URL imgUrl = getClass().getClassLoader().getResource(imagePath);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(imgUrl));

        frame.add(this);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void update(ConcurrentLinkedQueue<Animal> animals, ArrayList<ArrayList<TileGrid.Tile>> tiles) {
        this.animals = animals;
        this.tiles = tiles;
        frame.repaint();
    }

    public String finalMessage(long tickCount, long totalTimeSeconds) {
        long minutes = totalTimeSeconds / 60;
        long seconds = totalTimeSeconds % 60;

        return String.format("Simulation ended, %d ticks have passed, or %d:%2d", tickCount, minutes, seconds);
    }

    public String statusMessage(ConcurrentLinkedQueue<Animal> animals) {
        int bearCount = 0, carcassCount = 0, sheepCount = 0, vultureCount = 0, wolfCount = 0;
        for (Animal animal : animals) {
            String species = animal.getSpecies();
            switch (species) {
                case ("Bear"):
                    bearCount += 1;
                    break;
                case ("Carcass"):
                    carcassCount += 1;
                    break;
                case ("Sheep"):
                    sheepCount += 1;
                    break;
                case ("Vulture"):
                    vultureCount += 1;
                    break;
                case ("Wolf"):
                    wolfCount += 1;
                    break;
            }
        }
        return String.format("%d,%d,%d,%d,%d", bearCount, carcassCount, sheepCount, vultureCount, wolfCount);
    }
}