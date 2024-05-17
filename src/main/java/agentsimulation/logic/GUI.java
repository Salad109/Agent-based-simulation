package agentsimulation.logic;

import agentsimulation.Simulation;
import agentsimulation.agent.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GUI extends JPanel {
    private ConcurrentLinkedQueue<Animal> actors;
    private ArrayList<ArrayList<TileGrid.Tile>> tiles;
    private JFrame frame;
    private final Map<String, Image> animalImages;

    public GUI(ConcurrentLinkedQueue<Animal> actors, ArrayList<ArrayList<TileGrid.Tile>> tiles) {
        this.actors = actors;
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
            java.net.URL imgUrl = getClass().getClassLoader().getResource(imagePath);
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
        int cellSize = Math.min(getWidth(), getHeight()) / Simulation.simulationSize;

        // Draw grass
        g.fillRect(cellSize, cellSize, cellSize, cellSize);
        for (int i = 0; i < Simulation.simulationSize; i++) {
            for (int j = 0; j < Simulation.simulationSize; j++) {
                if (tiles.get(j).get(i).getGrass())
                    g.setColor(Color.getHSBColor(120 / 360f, 0.8f, 0.75f));
                else
                    g.setColor(Color.getHSBColor(20 / 360f, 0.6f, 0.45f));
                g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }

        // Draw actors
        for (Animal actor : actors) {
            if (actor == null)
                continue;

            int x = actor.getPositionX() * cellSize;
            int y = actor.getPositionY() * cellSize;

            // Get the image for the current actor's type
            String actorType = actor.getSpecies();
            Image image = animalImages.get(actorType);

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
        for (int i = 0; i <= Simulation.simulationSize; i++) {
            int linePos = i * cellSize;
            g.drawLine(0, linePos, getWidth(), linePos);
            g.drawLine(linePos, 0, linePos, getHeight());
        }
    }

    public void display() {
        frame = new JFrame("Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set program icon
        String imagePath = "images/Icon.png";
        java.net.URL imgUrl = getClass().getClassLoader().getResource(imagePath);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(imgUrl));

        frame.add(this);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void update(ConcurrentLinkedQueue<Animal> actors, ArrayList<ArrayList<TileGrid.Tile>> tiles) {
        this.actors = actors;
        this.tiles = tiles;
        frame.repaint();
    }

    public void finalMessage(long tickCount, long totalTimeSeconds) {
        long minutes = totalTimeSeconds / 60;
        long seconds = totalTimeSeconds % 60;

        String endMessage = String.format("All actors died, %d ticks have passed, or %d:%2d", tickCount, minutes, seconds);
        System.out.println(endMessage);

    }
}