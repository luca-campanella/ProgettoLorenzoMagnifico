package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.client.network.NetworkTypeEnum;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.FamilyMember;

/**
 * This class is the interface of ModelController on the client
 */
public interface ControllerCallbackInterface {

    /**
     * This method is used to make a callback from view to model when the user chooses the network type
     * @param networkChoice the network type chosen by the user
     */
    public void callbackNetworkType(NetworkTypeEnum networkChoice);

    /**
     * this method is called when a user is trying to login.
     * @param userID the username
     * @param userPW the password
     */
    public void callbackLogin(String userID, String userPW);

    /**
     * Called by the UI when the user wants to create a new account to connect to the server
     * @param userID the username
     * @param userPW the password
     */
    public void callbackCreateAccount(String userID, String userPW);

    /**
     * this method is a callback method called from abstractUiType when a family member is selected
     * @param selectdFM the family member selected.
     */
    public void callbackFamilyMemberSelected(FamilyMember selectdFM);

    /**
     * this method allows player to place a family member on a build action space
     * No parameter needed, the {@link ClientMain} saves the parameters of the current move
     */
    public void callbackPlacedFMOnBuild();

    /**
     * this method allows player to place a family member on a harvest action space
     * No parameter needed, the {@link ClientMain} saves the parameters of the current move
     */
    public void callbackPlacedFMOnHarvest();

    /**
     * this method allows player to place a family member on a tower floor action space
     * @param towerIndex the identifier of the tower
     * @param floorIndex the identifier of the floor
     */
    public void callbackPlacedFMOnTower(int towerIndex, int floorIndex);

    /**
     * this method allows player to place a family member on a market action space
     * @param marketASIndex the selected market AS
     */
    public void callbackPlacedFMOnMarket(int marketASIndex);

    /**
     * this method allows player to place a family member in the council action space
     */
    public void callbackPlacedFMOnCouncil();

    /**
     * this is the call back method to send a message to all other players in the room (Direction: {@link AbstractUIType} -> {@link ClientMain}; general direction: Client -> server)
     * @param msg
     * @throws NetworkException
     */
    public void callbackSendChatMsg(String msg) throws NetworkException;

    public void callbackOnLeaderCardChosen(LeaderCard leaderCardChoice);
}
