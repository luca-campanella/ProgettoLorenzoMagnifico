package it.polimi.ingsw.server.network.rmi;

import it.polimi.ingsw.client.exceptions.IllegalMoveException;
import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

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

    /**
     * Method used to signal that that player has played a leader card
     * @param leaderName the name of the leader card played
     * @throws RemoteException if something goes wrong with RMI communication
     */
    public void playLeaderCard(String leaderName) throws RemoteException;

    /**
     * this method is used to deliver the move of a family member on a tower
     * @param familyMemberColor the color of the chosen family member
     * @param numberTower the tower index (from left to right)
     * @param floorTower the number of the floor (from top to bottom)
     * @param playerChoices this is a map that contains all the choices of the client when an effect asks
     * @throws NetworkException if something goes wrong during the connection
     * @throws IllegalMoveException //todo remove or implement server side
     */
    public void placeOnTower(DiceAndFamilyMemberColorEnum familyMemberColor, int numberTower, int floorTower, HashMap<String, Integer> playerChoices) throws NetworkException, IllegalMoveException;
}
