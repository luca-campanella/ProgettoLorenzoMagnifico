package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;

import java.util.HashMap;

/**
 * this is the packet delivered to the server that contains all the information to place a family member on the council
 */
public class PlaceOnCouncilPacket extends MovePacket{

    /**
     * this attribute represents the choices of the client when some effects asks the client to respond
     */
    private HashMap<String, Integer> playerChoices;

    public PlaceOnCouncilPacket(DiceAndFamilyMemberColorEnum familyMemberColor, HashMap<String, Integer> playerChoices){

        super(familyMemberColor);
        this.playerChoices = playerChoices;

    }

    public HashMap<String, Integer> getPlayerChoices(){
        return playerChoices;
    }

}
