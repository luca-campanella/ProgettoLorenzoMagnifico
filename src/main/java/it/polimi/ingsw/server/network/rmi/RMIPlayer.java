package it.polimi.ingsw.server.network.rmi;

import it.polimi.ingsw.server.network.AbstractConnectionPlayer;
import it.polimi.ingsw.client.network.rmi.RMIClientInterface;
import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.utils.Debug;

import java.rmi.RemoteException;

/**
 * This class is the player via rmi
 */
public class RMIPlayer extends AbstractConnectionPlayer implements RMIPlayerInterface {

    RMIClientInterface RMIClientInterfaceInst;

    /**
     * Constructor, calls the super constructor and saves the interface to communicate with the client
     * @param nickname
     * @param RMIClientInterfaceInst
     */
    public RMIPlayer(String nickname, RMIClientInterface RMIClientInterfaceInst)
    {
        super(nickname);
        this.RMIClientInterfaceInst = RMIClientInterfaceInst;
    }

    /**
     * This method is called by the room to send a chat message arrived from another client. (Direction: server -> client)
     * @param msg message
     * @throws NetworkException if somthing went wrong on the network
     */
    @Override
    public void receiveChatMsg(String senderNickname, String msg) throws NetworkException {

        try {
            RMIClientInterfaceInst.receiveChatMsg(senderNickname, msg);
        } catch (RemoteException e) {
            Debug.printError("rmi: cannot send chat message to" + getNickname(), e);
            throw new NetworkException("rmi: cannot send chat message to" + getNickname(), e);
        }

    }

    /**
     * This method is used by the client to send chat message to all other players in the room (Direction: client -> sever)
     * @param msg the message
     * @throws NetworkException if somthing went wrong on the network
     */
    @Override
    public void sendChatMsg(String msg) throws RemoteException {
        getRoomContr().floodChatMsg(this, msg);
    }
}
