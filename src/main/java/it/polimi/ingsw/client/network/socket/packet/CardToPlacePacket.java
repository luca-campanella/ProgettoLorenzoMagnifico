package it.polimi.ingsw.client.network.socket.packet;

import it.polimi.ingsw.model.cards.AbstractCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this is the packet used to deliver to the client the cards to place on the board
 */
public class CardToPlacePacket implements Serializable {

    private ArrayList<AbstractCard> cards;

    public CardToPlacePacket(ArrayList<AbstractCard> cards){
        this.cards = cards;
    }

    public ArrayList<AbstractCard> getCards(){
        return cards;
    }
}
