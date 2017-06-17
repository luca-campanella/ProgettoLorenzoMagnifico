package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;

import java.util.HashMap;

/**
 * this packet is used by the server to deliver to the other client the move on council of a player
 */
public class ReceivePlaceOnCouncilPacket extends PlaceOnCouncilPacket{

    private String nicknamePlayer;

    public ReceivePlaceOnCouncilPacket (DiceAndFamilyMemberColorEnum familyMemberColorEnum, HashMap<String, Integer> playerChoices, String nicknamePlayer){

        super(familyMemberColorEnum,playerChoices);
        this.nicknamePlayer = nicknamePlayer;

    }

    public String getNicknamePlayer(){
        return nicknamePlayer;
    }

}
