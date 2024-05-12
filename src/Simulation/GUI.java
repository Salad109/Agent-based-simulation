package Simulation;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GUI extends JPanel {
    private LinkedList<Animal> actors;
    private ArrayList<ArrayList<Tile>> tiles;
    private final Map<String, Image> animalImages;
    private JFrame frame;

    public GUI(LinkedList<Animal> actors, ArrayList<ArrayList<Tile>> tiles) {
        this.actors = actors;
        this.tiles = tiles;
        setPreferredSize(new Dimension(800, 800)); // Set the preferred size of the panel
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
            animalImages.put(name, Toolkit.getDefaultToolkit().getImage(name.concat(".png")));
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
        LinkedList<Animal> newActors = new LinkedList<>();
        for (int i = 0; i < Simulation.animalCount; i++) {
                newActors.add(actors.get(i));
        }

        Iterator<Animal> it = newActors.iterator();
        while (it.hasNext()) {
            Animal actor = it.next();
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
        g.setColor(Color.BLACK); // Set color for grid lines
        for (int i = 0; i <= Simulation.simulationSize; i++) {
            int linePos = i * cellSize;
            g.drawLine(0, linePos, getWidth(), linePos);
            g.drawLine(linePos, 0, linePos, getHeight());
        }
    }

    protected void display() {
        frame = new JFrame("Actor Grid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("Icon.png"));


        frame.add(this);

        // Pack and display the frame
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    protected void update(LinkedList<Animal> actors, ArrayList<ArrayList<Tile>> tiles) {
        this.actors = actors;
        this.tiles = tiles;
        frame.repaint();
    }
}