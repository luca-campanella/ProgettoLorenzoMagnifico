package it.polimi.ingsw.model.board;

/**
 * Created by higla on 16/05/2017.
 */
public class Tower {
    private CardColorEnum colorTower;
    final int NUMBER_OF_FLOORS = 4;
    TowerFloorAS[] floors = new TowerFloorAS[NUMBER_OF_FLOORS];


    public void addFloorsToTower(TowerFloorAS[] floor)
    {
        floors = floor;
    }
    public TowerFloorAS getFloorByIndex(int index)
    {
        return floors[index];
    }

    public CardColorEnum getTowerColor() {
        return colorTower;
    }

    public int getNUMBER_OF_FLOORS() {
        return NUMBER_OF_FLOORS;
    }
    public TowerFloorAS getFloor(int index)
    {
        return floors[index];
    }
    public TowerFloorAS[] getFloors() {
        return floors;
    }
}
