package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;

import java.util.HashMap;

/**
 * the packet used to receive the move on build from the other players
 */
public class ReceiveBuildOrHarvestPacket extends BuildOrHarvest {

    private String nickname;

    public ReceiveBuildOrHarvestPacket(String nickname, DiceAndFamilyMemberColorEnum familyMemberColor, int servantUsed, HashMap<String, Integer> playerChoices){

        super(familyMemberColor,servantUsed, playerChoices);
        this.nickname = nickname;

    }

    public String getNickname(){
        return nickname;
    }
}