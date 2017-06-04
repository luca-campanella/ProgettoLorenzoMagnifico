package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.board.Dice;

import java.util.ArrayList;

/**
 * the packet to deliver the dices to the player
 */
public class DicesPacket {

    private ArrayList<Dice> dices;

    public DicesPacket(ArrayList<Dice> dices){

        this.dices.addAll(dices);

    }

    public ArrayList<Dice> getDices(){

        return  dices;

    }
}
