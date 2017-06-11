package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.network.NetworkTypeEnum;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;

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
     * @param color refers to the color of the family member selected.
     * @param servants the number of servants the user wants to add to the fm
     */
    public void callbackFamilyMemberAndServantsSelected(DiceAndFamilyMemberColorEnum color, int servants);

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

}
