package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.board.Dice;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * the packet to deliver the dices to the player
 */
public class DicesPacket implements Serializable {

    private ArrayList<Dice> dices;

    public DicesPacket(ArrayList<Dice> dices){
        dices = new ArrayList<>(dices);
        dices.forEach(dice -> System.out.println("Dice color " + dice.getColor() + "value: " + dice.getValue()));
    }

    public ArrayList<Dice> getDices(){

        return  dices;

    }
}
