package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.client.network.NetworkTypeEnum;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.PersonalTile;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
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
     * @param boardNeedsToBeRefreshed
     */
    void clientChoices(boolean boardNeedsToBeRefreshed);

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
     * This method returns to the view a true if the player was suspended
     *
     * @return true if the player was suspended
     */
     boolean callbackObtainIsThisPlayerSuspended();

    /**
     * This method returns to the view a true if the board needs to be refreshed with new cards
     *
     * @return true if the board needs to be refreshed with new cards
     */
     boolean callbackObtainBoardNeedsToBeRefreshed();

    /**
     * This method lets the view set the attribute to false after it refreshed the board
     * @param boardNeedsToBeRefreshed false if the board has been refreshed
     */
    void setBoardNeedsToBeRefreshed(boolean boardNeedsToBeRefreshed);

    /**
     * This method returns to the view a reference to the map of choices other players
     *
     * @return the choices handler for other players
     */
    public ChoicesHandlerInterface callbackObtainOtherPlayerChoicesHandler();

    /**
     * This method returns to the view a reference to the player the client represents
     * this method is usually called to show the personal board of the player
     * @return the player the clietn represents
     */
    Player callbackObtainPlayer();

    /**
     * This method returns to the view a list of other players
     * this method is usually called by the view to show the personal boards of the other players
     * @return the list of other players
     */
    List<Player> callbackObtainOtherPlayers();

    /**
     * This method returns to the view the list of dices
     * this method is usually called by the view to show the dices value
     * @return the list of the dices
     */
    List<Dice> callbackObtainDices();

    /**
     * This method returns to the view a list of all the players in order
     * this method is usually called by the view to show the correct order of players in the board
     *
     * @return the list of all players in order
     */
    public List<Player> callbackObtainPlayersInOrder();

    /**
     * this method is called by the view to activate the effect of a leader card
     * @param leaderCard the leader card that activates the effect
     */
    void callbackActivateLeader(LeaderCard leaderCard);


    /**
     * This method returns to the view true if this turn the palyer already played a family member
     *
     * @return true if this turn the palyer already played a family member
     */
    public FamilyMember callbackObtainSelectedFamilyMember();

    /**
     * this method returns to the client the response of the excommunication choice
     * @param numTile the num of tile that had to be taken
     * @param response the excommunication response: "yes" if the player wants to avoid the excommunication
     *                                               "no" if the player wants to be excommunicate without losing his faith points
     */
    void callbackExcommunicationChoice(String response, int numTile);

    /**
     * this method is called by the menu to ask to the controller the leader cards on his hand
     */
    ArrayList<LeaderCard> callbackObtainLeaderCardsNotPlayed();

    /**
     * This method is called by the view in order to reconnect a player that was suspended
     */
    void callbackConnectPlayerAgain();
}
