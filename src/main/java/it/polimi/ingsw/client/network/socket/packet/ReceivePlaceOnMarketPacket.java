package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;

import java.util.HashMap;

/**
 * the packet used to receive the move on market from the other players
 */
public class ReceivePlaceOnMarketPacket extends PlaceOnMarketPacket{

    private String nickname;

    public ReceivePlaceOnMarketPacket(String nickname, DiceAndFamilyMemberColorEnum familyMemberColor, int marketIndex, HashMap<String, Integer> playerChoices){

        super(familyMemberColor,marketIndex, playerChoices);
        this.nickname = nickname;

    }

    public String getNickname(){
        return nickname;
    }
}