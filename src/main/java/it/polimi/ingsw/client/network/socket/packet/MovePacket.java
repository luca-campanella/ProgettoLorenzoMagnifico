package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;

import java.io.Serializable;

/**
 * the packet created to deliver the action on socket
 */
public class MovePacket implements Serializable {

    private DiceAndFamilyMemberColorEnum familyMemberColor;

    public MovePacket(DiceAndFamilyMemberColorEnum familyMemberColor){

        this.familyMemberColor=familyMemberColor;

    }

    public DiceAndFamilyMemberColorEnum getFamilyMemberColor() {
        return familyMemberColor;
    }

}

