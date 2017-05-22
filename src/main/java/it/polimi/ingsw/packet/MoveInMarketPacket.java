package it.polimi.ingsw.packet;

import it.polimi.ingsw.gamelogic.Player.DiceAndFamilyMemberColor;

/**
 * this packet is send when the player move his family member in the market
 */
public class MoveInMarketPacket extends MovePacket {
    /**
     * number to recognise the place on the market
     */
    private int placeNumber;

    /**
     * constructor
     * @param familyMemberColor the color of the family member
     * @param servantUsed number of servant used on the family member
     * @param placeNumber numberID of the place on the market
     */
    public MoveInMarketPacket(DiceAndFamilyMemberColor familyMemberColor, int servantUsed, int placeNumber){
        super(familyMemberColor, servantUsed);
        this.placeNumber=placeNumber;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }
}
