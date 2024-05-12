package Simulation;

public class Tile {

    public void setGrass(boolean grass) {
        Grass = grass;
    }

    private boolean Grass;
    Tile(double randomInitializer) {
        if (randomInitializer > 0.75)
            Grass = true;
        else
            Grass = false;
    }

    public boolean getGrass() {
        return Grass;
    }
    public void attemptRegrow(){
        if(Simulation.RNG.nextDouble() > 0.99)
            Grass = true;
    }
}
