package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColor;
import it.polimi.ingsw.model.player.FamilyMember;

import java.io.Serializable;

/**
 * the packet created to deliver the action on socket
 */
public class MovePacket implements Serializable {

    private DiceAndFamilyMemberColor familyMemberColor;

    public MovePacket(DiceAndFamilyMemberColor familyMemberColor){

        this.familyMemberColor=familyMemberColor;

    }

    public DiceAndFamilyMemberColor getFamilyMemberColor() {
        return familyMemberColor;
    }

}

