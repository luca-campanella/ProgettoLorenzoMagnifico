package it.polimi.ingsw.packet;

import it.polimi.ingsw.gamelogic.Player.FamilyMemberColor;

/**
 * Created by federico on 16/05/2017.
 */
public class MoveInTowerPacket extends MovePacket {
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
    public MoveInTowerPacket (FamilyMemberColor familyMemberColor, int servantUsed, int numberTower, int floorTower){
        super(familyMemberColor, servantUsed);
        this.numberTower=numberTower;
        this. floorTower=floorTower;
    }
    public FamilyMemberColor getFamilyMemberColor(){
        return super.getFamilyMemberColor();
    }
    public int getServantUsed(){
        return super.getServantUsed();
    }
    public int getNumberTower(){
        return numberTower;
    }
    public int getFloorTower(){
        return floorTower;
    }
}
