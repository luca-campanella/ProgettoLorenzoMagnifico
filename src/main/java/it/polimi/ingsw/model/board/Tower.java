package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.cards.AbstractCard;

import java.io.Serializable;

/**
 * This class represents the single tower and collects action spaces regarding cards
 */
public class Tower implements Serializable{
    private CardColorEnum colorTower;
    private final int NUMBER_OF_FLOORS = 4;
    TowerFloorAS[] floors;

    public Tower(){
        floors = new TowerFloorAS[NUMBER_OF_FLOORS];
    }

    public void setColorTower(CardColorEnum colorTower) {
        this.colorTower = colorTower;
    }

    public void setFloors(TowerFloorAS[] floor)
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
    public TowerFloorAS[] getFloors() {
        return floors;
    }

    public void setCardOnFloor(int i, AbstractCard card)
    {
        this.floors[i].setCard(card);
    }

    public void clearTower(){

        for(int i = 0 ; i < NUMBER_OF_FLOORS ; i++)
            floors[i].clearFloor();
    }
}
