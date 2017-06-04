package it.polimi.ingsw.server.network;

import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.server.Room;
import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The abstract class that extends player and handles connections either via socker or via rmi
 */
public abstract class AbstractConnectionPlayer extends Player {

    /**
     * this instance is used to call methods of the room the player is in. There is redundancy
     */
    private Room room;

    public AbstractConnectionPlayer() {
        super();
    }

    public AbstractConnectionPlayer(String nickname)
    {
        super(nickname);
    }


    /**
     * This method is called by the room to send a chat message arrived from another client. (Direction: server -> client)
     * @param msg message
     * @param senderNickname the nickname of the sender
     * @throws NetworkException if something went wrong on the network
     */
    public abstract void receiveChatMsg(String senderNickname, String msg) throws NetworkException;

    /**
     * this method is called by the room to deliver a move on tower of another player
     * @throws NetworkException if something went wrong on the network
     */
    public abstract void receivePlaceOnTower(FamilyMember familyMember, int towerIndex, int floorIndex, HashMap<String, Integer> playerChoices) throws NetworkException;

    /**
     * this method is called by the room to deliver a move on market of another player
     * @throws NetworkException if something went wrong on the network
     */
    public abstract void receivePlaceOnMarket(FamilyMember familyMember, int marketIndex, HashMap<String, Integer> playerChoices) throws NetworkException;

    /**
     * this method is called by the room to deliver a build move of another player
     * @throws NetworkException if something went wrong on the network
     */
    public abstract void receiveBuild(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices) throws NetworkException;

    /**
     * this method is called by the room to deliver a harvest move of another player
     * @throws NetworkException if something went wrong on the network
     */
    public abstract void receiveHarvest(FamilyMember familyMember, int servant) throws NetworkException;

    public abstract void receiveEndPhase(AbstractConnectionPlayer player) throws NetworkException;

    protected Room getRoom() {
        return room;
    }


    public void setRoom(Room room)
    {
        this.room = room;
    }

    public abstract  void receiveDices(ArrayList<Dice> dices) throws NetworkException;
}
