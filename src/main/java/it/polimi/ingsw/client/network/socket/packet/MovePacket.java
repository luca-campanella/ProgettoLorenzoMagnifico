package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.FamilyMember;

import java.io.Serializable;

/**
 * the packet created to deliver the action on socket
 */
public class MovePacket implements Serializable {

    private FamilyMember familyMember;

    public MovePacket(FamilyMember familyMember){

        this.familyMember=familyMember;

    }

    public FamilyMember getFamilyMember() {
        return familyMember;
    }

}

