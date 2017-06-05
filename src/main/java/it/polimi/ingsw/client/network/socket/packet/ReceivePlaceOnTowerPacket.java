package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColor;

import java.util.HashMap;

/**
 * the packet used to receive the move on tower from the other players
 */
public class ReceivePlaceOnTowerPacket extends PlaceOnTowerPacket{

    private String nickname;

    ReceivePlaceOnTowerPacket(String nickname, DiceAndFamilyMemberColor familyMemberColor, int towerIndex, int floorIndex, HashMap<String, Integer> playerChoices){

        super(familyMemberColor,towerIndex, floorIndex, playerChoices);
        this.nickname = nickname;

    }

    public String getNickname(){
        return nickname;
    }
}