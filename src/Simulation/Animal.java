package Simulation;

public abstract class Animal{
    public int getPositionX() {
        return PositionX;
    }

    public int getPositionY() {
        return PositionY;
    }

    protected int PositionX, PositionY;

    Animal(int PositionX, int PositionY) {
        this.PositionX = PositionX;
        this.PositionY = PositionY;
    }

    public String getSpecies(){
        return getClass().getSimpleName();
    }

    protected void act(){
        moveRandomly();
    }

    private void moveRandomly() {
        int newX;
        int newY;

        do {
            newX = PositionX;
            newY = PositionY;

            int direction = Simulation.RNG.nextInt(4); // 0 = Left, 1 = Right, 2 = Up, 3 = Down
            System.out.print("Attempting to move (" + PositionX + "; " + PositionY + ") ");
            switch (direction) {
                case 0: // Left
                    newX--;
                    System.out.println("left");
                    break;
                case 1: // Right
                    newX++;
                    System.out.println("right");
                    break;
                case 2: // Up
                    newY++;
                    System.out.println("up");
                    break;
                case 3: // Down
                    newY--;
                    System.out.println("down");
                    break;
            }
        } while (newX < 0 || newX >= Simulation.simulationSize || newY < 0 || newY >= Simulation.simulationSize); // Check if the new position is within bounds

        // Update position only if new position is within bounds
        PositionX = newX;
        PositionY = newY;
    }

}

