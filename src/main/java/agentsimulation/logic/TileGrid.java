package agentsimulation.logic;

import agentsimulation.Simulation;

import java.util.ArrayList;

public class TileGrid {
    public static class Tile {

        public void setGrass(boolean grass) {
            this.grass = grass;
        }

        private boolean grass;
        Tile(double randomInitializer) {
            grass = randomInitializer > 0.75; // if (random > 0.75) grass = true; else grass = false;
        }

        public boolean getGrass() {
            return grass;
        }
        public void attemptRegrow(){
            if(Simulation.RNG.nextDouble() > 0.993)
                grass = true;
        }
    }

    ArrayList<ArrayList<Tile>> grassGrid;
    public ArrayList<ArrayList<Tile>> getGrassGrid() {
        return grassGrid;
    }


    protected void spawnTiles(){
        grassGrid = new ArrayList<>(Simulation.SIMULATION_SIZE);
        for (int i = 0; i < Simulation.SIMULATION_SIZE; i++) {
            ArrayList<Tile> row = new ArrayList<>(Simulation.SIMULATION_SIZE);
            for (int j = 0; j < Simulation.SIMULATION_SIZE; j++) {
                Tile tile = new Tile(Simulation.RNG.nextDouble());
                row.add(tile);
            }
            grassGrid.add(row);
        }
    }
    public void tilesAct() {
        for (ArrayList<Tile> row : grassGrid) {
            for (Tile tile : row) {
                if (!tile.getGrass())
                    tile.attemptRegrow();
            }
        }

    }
}
