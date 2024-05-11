package Simulation;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class GUI extends JPanel {
    private LinkedList<Animal> actors;
    private final Map<String, Image> animalImages;
    private JFrame frame;

    public GUI(LinkedList<Animal> actors) {
        this.actors = actors;
        setPreferredSize(new Dimension(800, 800)); // Set the preferred size of the panel

        animalImages = new HashMap<>();
        animalImages.put("Sheep", Toolkit.getDefaultToolkit().getImage("Simulation/Sheep.png"));
        animalImages.put("Wolf", Toolkit.getDefaultToolkit().getImage("Simulation/Wolf.png"));
        animalImages.put("Bear", Toolkit.getDefaultToolkit().getImage("Simulation/Bear.png"));
        animalImages.put("Vulture", Toolkit.getDefaultToolkit().getImage("Simulation/Vulture.png"));
        animalImages.put("Carcass", Toolkit.getDefaultToolkit().getImage("Simulation/Carcass.png"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Calculate cell size
        int cellSize = Math.min(getWidth(), getHeight()) / Simulation.simulationSize;

        // Draw grass
        g.setColor(Color.getHSBColor(1/3f,0.8f,0.75f));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw actors
        for (Animal actor : actors) {
            int x = actor.getPositionX() * cellSize;
            int y = actor.getPositionY() * cellSize;

            // Get the image for the current actor's type
            Image image = animalImages.get(actor.getSpecies());

            // Draw the image
            if (image != null) {
                g.drawImage(image, x, y, cellSize, cellSize, this);
            } else {
                // Draw a placeholder or handle missing image gracefully
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

        frame.add(this);

        // Pack and display the frame
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    protected void update(LinkedList<Animal> actors) {
        this.actors = actors;
        frame.repaint();
    }
}