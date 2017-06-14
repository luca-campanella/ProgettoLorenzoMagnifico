package it.polimi.ingsw.model.resource;

/**
 * this is the class used to deliver the coordinates of the tower spaces needed on the classes and the servant needed
 */
public class TowerWrapper {

    /**
     * the floor of the tower
     */
    private int towerFloor;

    /**
     * the number of the tower
     */
    private int towerIndex;

    /**
     * the number of servant needed to place se family member on the tower
     */
    private int servantNeeded;

    public TowerWrapper(int towerFloor, int towerIndex, int servantNeeded){

        this.towerFloor = towerFloor;
        this.towerIndex = towerIndex;
        this.servantNeeded = servantNeeded;
    }

    public int getServantNeeded() {
        return servantNeeded;
    }

    public int getTowerFloor() {
        return towerFloor;
    }

    public int getTowerIndex() {
        return towerIndex;
    }
}
