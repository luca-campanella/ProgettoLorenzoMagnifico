package it.polimi.ingsw.server.network.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The interface for rmi calls from client to server
 */
public interface RMIPlayerInterface extends Remote {

    /**
     * This method is used to send chat message to all players in the room
     * @param msg The message
     **/
    public void sendChatMsg(String msg) throws RemoteException;

    public String getNickname() throws RemoteException;
}
