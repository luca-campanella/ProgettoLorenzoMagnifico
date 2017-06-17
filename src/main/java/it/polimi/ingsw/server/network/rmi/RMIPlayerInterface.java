package it.polimi.ingsw.server.network.rmi;

import it.polimi.ingsw.client.exceptions.IllegalMoveException;
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
     * @throws RemoteException if something goes wrong during the connection
     * @throws IllegalMoveException //todo remove or implement server side
     */
    public void placeOnTower(DiceAndFamilyMemberColorEnum familyMemberColor, int numberTower, int floorTower, HashMap<String, Integer> playerChoices) throws RemoteException, IllegalMoveException;

    /**
     * this method is used to deliver the move of a family member on a marketplace
     * @param familyMemberColor is the color of the chosen family member
     * @param marketIndex is the index of the market space  (from left to right) //todo: Check this assert
     * @param playerChoices is the map that cointains all the choices of the client when an effect asks
     * @throws RemoteException if something goes wrong during the connection
     * @throws IllegalMoveException //todo remove or implement server side
     */
    public void placeOnMarket(DiceAndFamilyMemberColorEnum familyMemberColor,int marketIndex, HashMap<String, Integer> playerChoices) throws  RemoteException, IllegalMoveException;

    /**
     * this method is used to deliver the move of a family member on a council
     * @param familyMemberColor is the color of the chosen family member
     * @param playerChoices is the map that cointains all the choices of the client when an effect asks
     * @throws RemoteException if something goes wrong during the connection
     * @throws IllegalMoveException //todo remove or implement server side
     */
    public void placeOnCouncil(DiceAndFamilyMemberColorEnum familyMemberColor, HashMap<String, Integer> playerChoices) throws  RemoteException, IllegalMoveException;

    /**
     * this method is used to harvest
     * @param familyMemberColor is the color of the chosen family member
     * @param servantsUsed is the numberof additional servants used to increase value of the dice
     * @param playerChoices is the map that cointains all the choices of the client when an effect asks
     * @throws RemoteException if something goes wrong during the connection
     * @throws IllegalMoveException //todo remove or implement server side
     */
    public void harvest(DiceAndFamilyMemberColorEnum familyMemberColor,int servantsUsed, HashMap<String, Integer> playerChoices) throws  RemoteException, IllegalMoveException;

    /**
     * this method is used to build
     * @param familyMemberColor is the color of the chosen family member
     * @param servantsUsed is the numberof additional servants used to increase value of the dice
     * @param playerChoices is the map that cointains all the choices of the client when an effect asks
     * @throws RemoteException if something goes wrong during the connection
     * @throws IllegalMoveException //todo remove or implement server side
     */
    public void build(DiceAndFamilyMemberColorEnum familyMemberColor,int servantsUsed, HashMap<String, Integer> playerChoices) throws  RemoteException, IllegalMoveException;

}

