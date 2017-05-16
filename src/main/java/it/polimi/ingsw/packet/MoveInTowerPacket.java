package it.polimi.ingsw.packet;

import it.polimi.ingsw.gamelogic.FamilyMemberColor;

import java.io.Serializable;

/**
 * Created by federico on 16/05/2017.
 */
public class MoveInTowerPacket implements Serializable {
    /**
     * color of the family member moved
     */
    private FamilyMemberColor familyMemberColor;
    /**
     * number of servant used to increase the value of the family member
     */
    private int servantUsed;
    /**
     * number of tower where the family member is moved
     */
    private int numberTower;
    /**
     * floor of the tower where the family member is moved
     */
    private int floorTower;

    /**
     * constructor
     */
    public void MovePacket (FamilyMemberColor familyMemberColor, int servantUsed, int numberTower, int floorTower){
        this.familyMemberColor=familyMemberColor;
        this.servantUsed=servantUsed;
        this.numberTower=numberTower;
        this. floorTower=floorTower;
    }
    public FamilyMemberColor getFamilyMember(){
        return familyMemberColor;
    }
    public int getServantUsed(){
        return servantUsed;
    }
    public int getNumberTower(){
        return numberTower;
    }
    public int getFloorTower(){
        return floorTower;
    }
}
