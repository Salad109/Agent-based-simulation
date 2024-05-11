package Simulation;

import Actors.Animal;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class GUI extends JPanel {
    private LinkedList<Animal> actors;
    final private Map<String, Image> animalImages;
    private JFrame frame;
    private GUI GUI;

    public GUI(LinkedList<Animal> actors) {
        this.actors = actors;
        setPreferredSize(new Dimension(400, 400)); // Set the preferred size of the panel

        animalImages = new HashMap<>();
        animalImages.put("Sheep", Toolkit.getDefaultToolkit().getImage("Actors/Sheep.png"));
        animalImages.put("Wolf", Toolkit.getDefaultToolkit().getImage("Actors/Wolf.png"));
        animalImages.put("Bear", Toolkit.getDefaultToolkit().getImage("Actors/Bear.png"));
        animalImages.put("Vulture", Toolkit.getDefaultToolkit().getImage("Actors/Vulture.png"));
        animalImages.put("Carcass", Toolkit.getDefaultToolkit().getImage("Actors/Carcass.png"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Calculate cell width and height
        int cellWidth = getWidth() / Simulation.simulationSize;
        int cellHeight = getHeight() / Simulation.simulationSize;

        // Draw actors
        for (Animal actor : actors) {
            int x = actor.getPositionX() * cellWidth;
            int y = actor.getPositionY() * cellHeight;

            // Get the image for the current actor's type
            Image image = animalImages.get(actor.getType());

            // Draw the image
            g.drawImage(image, x, y, cellWidth, cellHeight, this);
        }

        // Draw grid lines
        g.setColor(Color.BLACK); // Set color for grid lines
        for (int i = 0; i <= Simulation.simulationSize; i++) {
            int x = i * cellWidth;
            int y = i * cellHeight;
            g.drawLine(x, 0, x, getHeight()); // Vertical line
            g.drawLine(0, y, getWidth(), y); // Horizontal line
        }
    }

    protected void display() {
        frame = new JFrame("Actor Grid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the actor grid panel
        GUI = new GUI(actors);
        frame.add(GUI);

        // Pack and display the frame
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    protected void update(LinkedList<Animal> actors) {
        this.actors = actors;
        frame.remove(GUI);
        GUI = new GUI(actors);
        frame.add(GUI);
        frame.validate();
        frame.repaint();
    }
}