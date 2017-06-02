package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColor;

import java.io.Serializable;

/**
 *  the packet created to deliver the action on socket
 */
public class MovePacket implements Serializable {
    private DiceAndFamilyMemberColor familyMemberColor;
    private int servantUsed;
    public MovePacket(DiceAndFamilyMemberColor familyMemberColor, int servantUsed){
        this.familyMemberColor=familyMemberColor;
        this.servantUsed=servantUsed;
    }

    public DiceAndFamilyMemberColor getFamilyMemberColor() {
        return familyMemberColor;
    }

    public int getServantUsed() {
        return servantUsed;
    }
}
