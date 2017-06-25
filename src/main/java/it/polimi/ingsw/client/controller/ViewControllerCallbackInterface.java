package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.client.network.NetworkTypeEnum;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.PersonalTile;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

/**
 * This interface is used for callbacks from view to controller
 * Direction: {@link AbstractUIType} to {@link ClientMain}
 */
public interface ViewControllerCallbackInterface {

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
     * @param servantsUsed the number of servants the user decided to use
     */
    public void callbackPlacedFMOnBuild(int servantsUsed);

    /**
     * this method allows player to place a family member on a harvest action space
     * @param servantsUsed the number of servants the user decided to use
     */
    public void callbackPlacedFMOnHarvest(int servantsUsed);

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

    /**
     * This callback is used to call from view to controller to communicate the user choice
     * @param leaderCardChoice the choice the user has made
     */
    public void callbackOnLeaderCardChosen(LeaderCard leaderCardChoice);

    /**
     * this method is called by the view and used to pass the turn
     */
    void callBackPassTheTurn();

    /**
     * this method is called by the view to deliver the special tile chosen to the server
     * @param tileChosen
     */
    void callbackOnTileChosen(PersonalTile tileChosen);

    /**
     * this method is called by the view to discard a leader card
     * @param leaderIter the leader card to discard
     */
    void callbackDiscardLeader(LeaderCard leaderIter);

    /**
     * this method is called to turn back at the start of the initial choices
     */
    void clientChoices();

    /**
     * this method is called to play a leader card on the hand of the player
     * @param leaderCard the leader card played by the player
     */
    void callbackPlayLeader(LeaderCard leaderCard);

    /**
     * This method returns to the view a reference to the board
     * this method is called to obtain the board of the game inside the view
     * @return the current board
     */
    Board callbackObtainBoard();

    /**
     * This method returns to the view a reference to the player the client represents
     * this method is usually called to show the personal board of the player
     * @return the player the clietn represents
     */
    Player callbackObtainPlayer();

    /**
     * This method returns to the view a list of other players
     * this method is usually called by the view to show the personal boards of the other players
     * @return the list of other playes
     */
    List<Player> callbackObtainOtherPlayers();

}
