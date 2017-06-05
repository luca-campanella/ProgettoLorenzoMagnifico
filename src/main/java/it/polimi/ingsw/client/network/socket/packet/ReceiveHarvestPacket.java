package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColor;

/**
 * the packet used to receive the move on harvest from the other players
 */
public class ReceiveHarvestPacket extends HarvestPacket{

    private String nickname;

    ReceiveHarvestPacket(String nickname, DiceAndFamilyMemberColor familyMemberColor, int servantUsed){

        super(familyMemberColor,servantUsed);
        this.nickname = nickname;

    }

    public String getNickname(){
        return nickname;
    }
}
