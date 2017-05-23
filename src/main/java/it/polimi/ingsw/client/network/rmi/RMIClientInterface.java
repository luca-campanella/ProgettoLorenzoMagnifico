package it.polimi.ingsw.client.network.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {

    /**
     * This method is called from the controller to communicate that a chat message has arrived to the client (Direction: controller -> client)
     * @param senderNick the nickname of the player who sent the msg
     * @param msg
     * @throws RemoteException
     */
    public void receiveChatMsg(String senderNick, String msg) throws RemoteException;

}
