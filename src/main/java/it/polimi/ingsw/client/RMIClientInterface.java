package it.polimi.ingsw.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {

    /**
     * This method is called from the server to communicate that a chat message has arrived to the client (Direction: server -> client)
     * @param senderNick the nickname of the player who sent the msg
     * @param msg
     * @throws RemoteException
     */
    public void receiveChatMsg(String senderNick, String msg) throws RemoteException;

}
