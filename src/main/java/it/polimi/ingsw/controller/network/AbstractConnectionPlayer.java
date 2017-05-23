package it.polimi.ingsw.controller.network;

import it.polimi.ingsw.controller.Room;
import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.model.player.Player;

/**
 * The abstract class that extends player and handles connections either via socker or via rmi
 */
public abstract class AbstractConnectionPlayer extends Player {

    /**
     * this instance is used to call methods of the room the player is in. There is redundancy
     */
    private Room roomContr;

    public AbstractConnectionPlayer() {
        super();
    }

    public AbstractConnectionPlayer(String nickname)
    {
        super(nickname);
    }


    /**
     * This method is called by the room to send a chat message arrived from another client. (Direction: controller -> client)
     * @param msg message
     * @param senderNickname the nickname of the sender
     * @throws NetworkException if something went wrong on the network
     */
    public abstract void receiveChatMsg(String senderNickname, String msg) throws NetworkException;

    protected Room getRoomContr() {
        return roomContr;
    }


    public void setRoom(Room room)
    {
        roomContr = room;
    }
}
