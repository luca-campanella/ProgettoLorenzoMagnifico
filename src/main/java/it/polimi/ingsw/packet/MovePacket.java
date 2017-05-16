package it.polimi.ingsw.packet;

import it.polimi.ingsw.gamelogic.FamilyMemberColor;

import java.io.Serializable;

/**
 * Created by federico on 16/05/2017.
 */
public class MovePacket implements Serializable {
    /**
     * color of the family member moved
     */
    private FamilyMemberColor familyMemberColor;
    /**
     * number of servant used to increase the value of the family member
     */
    private int servantUsed;
    /**
     * Action space chose to move the family member
     */
    private AbstractActionSpaces spaceOfMove;

    /**
     * constructor
     */
    public void MovePacket (FamilyMemberColor familyMemberColor, int servantUsed, AbstractActionSpaces spaceOfMove){
        this.familyMemberColor=familyMemberColor;
        this.servantUsed=servantUsed;
        this.spaceOfMove=spaceOfMove;
    }
    public FamilyMemberColor getFamilyMember(){
        return familyMemberColor;
    }
    public int getServantUsed(){
        return servantUsed;
    }
    public AbstractActionSpaces getSpace(){
        return spaceOfMove;
    }
}
