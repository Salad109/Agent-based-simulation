package agentsimulation.logic;

import agentsimulation.Simulation;
import agentsimulation.agent.Animal;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Main class responsible for the visual display of the simulation
 */
public class GUI extends JPanel {
    private ConcurrentLinkedQueue<Animal> animals;
    private ArrayList<ArrayList<TileGrid.Tile>> tiles;
    private JFrame frame;
    private final Map<String, Image> animalImages;

    public GUI(ConcurrentLinkedQueue<Animal> animals, ArrayList<ArrayList<TileGrid.Tile>> tiles) {
        this.animals = animals;
        this.tiles = tiles;
        setPreferredSize(new Dimension(1600, 900));
        animalImages = new HashMap<>();
        loadImages();
        initGUI();
    }

    /**
     * Load the icons of the different animal species
     */
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

    /**
     * Set up the grid and the graph in split-screen
     */
    private void initGUI() {
        this.setLayout(new BorderLayout());

        Graph graph = new Graph();

        JPanel simulationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawSimulation(g);
            }
        };
        simulationPanel.setPreferredSize(new Dimension(800, 800));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, simulationPanel, graph);
        splitPane.setDividerLocation(900);

        this.add(splitPane, BorderLayout.CENTER);
    }

    private void drawSimulation(Graphics g) {
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


}
