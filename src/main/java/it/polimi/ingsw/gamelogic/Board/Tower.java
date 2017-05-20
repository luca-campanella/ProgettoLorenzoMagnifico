package it.polimi.ingsw.gamelogic.Board;

import java.util.ArrayList;

/**
 * Created by higla on 16/05/2017.
 */
public class Tower {
    private String towerColor;
    final int NUMBER_OF_FLOORS = 4;
    TowerFloorAS[] floors = new TowerFloorAS[NUMBER_OF_FLOORS];

    public Tower(String towerColor) {
        this.towerColor = towerColor;
    }

    public void addFloorsToTower(TowerFloorAS[] floor)
    {
        floors = floor;
    }
    public TowerFloorAS getFloorByIndex(int index)
    {
        return floors[index];
    }
    public void printTower(){
        int i = 0;
        System.out.println("I'm tower  " + towerColor);
        for(i=0; i< NUMBER_OF_FLOORS; i++)
            System.out.println("Floor  " + i + " " + floors[i].printFloor());
    }
}
