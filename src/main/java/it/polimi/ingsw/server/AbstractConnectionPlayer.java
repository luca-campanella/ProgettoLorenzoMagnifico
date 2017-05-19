package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.NetworkException;
import it.polimi.ingsw.gamelogic.Player.Player;

/**
 * The abstract class that extends Player and handles connections either via socker or via RMI
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
     * This method is called by the room to send a chat message arrived from another client. (Direction: server -> client)
     * @param msg
     * @param senderNickname the nickname of the sender
     * @throws NetworkException
     */
    public abstract void floodChatMsg(String senderNickname, String msg) throws NetworkException;

    protected Room getRoomContr() {
        return roomContr;
    }


}
