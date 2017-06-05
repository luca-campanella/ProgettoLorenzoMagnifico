package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColor;

import java.util.HashMap;

/**
 * the packet used to receive the move on market from the other players
 */
public class ReceivePlaceOnMarketPacket extends PlaceOnMarketPacket{

    private String nickname;

    ReceivePlaceOnMarketPacket(String nickname, DiceAndFamilyMemberColor familyMemberColor, int marketIndex, HashMap<String, Integer> playerChoices){

        super(familyMemberColor,marketIndex, playerChoices);
        this.nickname = nickname;

    }

    public String getNickname(){
        return nickname;
    }
}