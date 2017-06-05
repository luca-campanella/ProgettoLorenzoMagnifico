package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColor;
import it.polimi.ingsw.model.player.FamilyMember;

import java.util.HashMap;

/**
 * this packet is send when the player move his family member in the market
 */
public class PlaceOnMarketPacket extends MovePacket {
    /**
     * number to recognise the place on the market
     */
    private int marketIndex;

    /**
     * the choices of the player when has to choose to target of the effect
     */
    private HashMap<String, Integer> playerChoices;

    /**
     * constructor
     * @param familyMemberColor the color of the family member used
     * @param marketIndex numberID of the place on the market
     */
    public PlaceOnMarketPacket(DiceAndFamilyMemberColor familyMemberColor, int marketIndex, HashMap<String, Integer> playerChoices){

        super(familyMemberColor);
        this.marketIndex =marketIndex;
        this.playerChoices = playerChoices;

    }

    public int getMarketIndex() {
        return marketIndex;
    }

    public HashMap<String, Integer> getPlayerChoices(){

        return playerChoices;

    }

}
