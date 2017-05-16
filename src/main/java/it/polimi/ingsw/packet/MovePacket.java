package it.polimi.ingsw.packet;

import it.polimi.ingsw.gamelogic.Player.FamilyMemberColor;

import java.io.Serializable;

/**
 * Created by federico on 16/05/2017.
 */
public class MovePacket implements Serializable {
    private FamilyMemberColor familyMemberColor;
    private int servantUsed;
    public MovePacket(FamilyMemberColor familyMemberColor, int servantUsed){
        this.familyMemberColor=familyMemberColor;
        this.servantUsed=servantUsed;
    }

    public FamilyMemberColor getFamilyMemberColor() {
        return familyMemberColor;
    }

    public int getServantUsed() {
        return servantUsed;
    }
}
