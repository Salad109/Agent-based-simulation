package Simulation;

import java.util.ArrayList;
import java.util.LinkedList;

public class Carcass extends Animal{
    public Carcass(int x, int y){
        super(x,y);
    }

    @Override
    protected void act(LinkedList<Animal> animals, ArrayList<ArrayList<Tile>> tiles){}
}
