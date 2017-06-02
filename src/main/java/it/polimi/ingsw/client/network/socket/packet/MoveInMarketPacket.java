package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.FamilyMember;

/**
 * this packet is send when the player move his family member in the market
 */
public class MoveInMarketPacket extends MovePacket {
    /**
     * number to recognise the place on the market
     */
    private int marketIndex;

    /**
     * constructor
     * @param familyMember the family member used
     * @param marketIndex numberID of the place on the market
     */
    public MoveInMarketPacket(FamilyMember familyMember, int marketIndex){
        super(familyMember);
        this.marketIndex =marketIndex;
    }

    public int getMarketIndex() {
        return marketIndex;
    }

}
