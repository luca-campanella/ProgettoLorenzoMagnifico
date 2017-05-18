package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.NetworkException;

import java.rmi.RemoteException;

/**
 * This class is the Player via RMI
 */
public class RMIPlayer extends AbstractConnectionPlayer implements RMIPlayerInterface {

    public RMIPlayer(String nickname)
    {
        super(nickname);
    }

    /**
     * This method is called by the room to send a chat message arrived from another client. (Direction: server -> client)
     * @param msg
     * @throws NetworkException
     */
    @Override
    public void floodChatMsg(String msg) throws NetworkException {

    }

    @Override
    public void sendChatMsg(String msg) throws RemoteException {

    }
}
