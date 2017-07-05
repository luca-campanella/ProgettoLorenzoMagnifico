package it.polimi.ingsw.client.network.rmi;

import it.polimi.ingsw.client.network.socket.packet.PlayerPositionEndGamePacket;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.PersonalTile;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface RMIClientInterface extends Remote {

    /**
     * This method is called from the server to communicate that a chat message has arrived to the client (Direction: server -> client)
     * @param senderNick the nickname of the player who sent the msg
     * @param msg
     * @throws RemoteException
     */
    public void receiveChatMsg(String senderNick, String msg) throws RemoteException;

    /**
     * this method is called by the server to notify that another player has moved on a tower
     *
     * @param playerNickname    the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param towerIndex        the index of the tower he placed the family member in
     * @param floorIndex        the index of the floor he placed the family member in
     * @param playerChoices     the hashmao with his choices correlated with this action
     */
    public void receivePlaceOnTower(String playerNickname,
                                    DiceAndFamilyMemberColorEnum familyMemberColor,
                                    int towerIndex, int floorIndex,
                                    HashMap<String, Integer> playerChoices) throws RemoteException;

    /**
     * this method is called by the server to notify that another player has moved in a market action space
     *
     * @param playerNickname    the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param marketIndex       the index of the market action space he placed the family member in
     * @param playerChoices     the hashmao with his choices correlated with this action
     */
    public void receivePlaceOnMarket(String playerNickname,
                                     DiceAndFamilyMemberColorEnum familyMemberColor,
                                     int marketIndex,
                                     HashMap<String, Integer> playerChoices) throws RemoteException;

    /**
     * this method is called by the server to notify that another player has moved in the harvest action space
     *
     * @param playerNickname    the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param servantsUsed      the number of servants used to perform the action
     * @param playerChoices     the hashmao with his choices correlated with this action
     */
    public void receiveHarvest(String playerNickname,
                               DiceAndFamilyMemberColorEnum familyMemberColor,
                               int servantsUsed,
                               HashMap<String, Integer> playerChoices) throws RemoteException;

    /**
     * this method is called by the server to notify that another player has moved in the build action space
     *
     * @param playerNickname    the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param servantsUsed      the number of servants used to perform the action
     * @param playerChoices     the hashmao with his choices correlated with this action
     */
    public void receiveBuild(String playerNickname,
                             DiceAndFamilyMemberColorEnum familyMemberColor,
                             int servantsUsed,
                             HashMap<String, Integer> playerChoices) throws RemoteException;

    /**
     * this method is called by the server to notify that another player has moved in the council
     *
     * @param playerNickname    the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param playerChoices     the hashmap with his choices correlated with this action
     */
    public void receivePlaceOnCouncil(String playerNickname,
                                      DiceAndFamilyMemberColorEnum familyMemberColor,
                                      HashMap<String, Integer> playerChoices) throws RemoteException;

    /**
     * receive from the server when a player ends his turn
     * @param nickname the nickname of the player that had ended the turn
     * @throws RemoteException if something goaes wrong during RMI communication
     */
    public void receiveEndPhase(String nickname) throws RemoteException;

    /**
     * receive at the start of every turn the dice throws by the controller of the game
     * @param dices the dice to save on the client board
     * @throws RemoteException if something goaes wrong during RMI communication
     */
    public void receiveDice(ArrayList<Dice> dices) throws RemoteException;

    /**
     * receive all the board at the start of the game
     * @param gameBoard clean, with only the excommunication tiles, without cards or dice
     * @throws RemoteException if something goaes wrong during RMI communication
     */
    public void receiveBoard(Board gameBoard) throws RemoteException;

    /**
     * receive the information when is the phase of this client
     * @throws RemoteException if something goaes wrong during RMI communication
     */
    public void receiveStartOfTurn() throws RemoteException;

    /**
     * receive the order of the player in this turn
     * @param orderPlayer all the players in order of turn
     * @throws RemoteException if something goaes wrong during RMI communication
     */
    public void receiveOrderPlayer(ArrayList<String> orderPlayer) throws RemoteException;

    public void receiveNicknamePlayer(String nicknamePlayer) throws RemoteException;

    /**
     * this method is calle by the server to deliver the leader card at the start of the game
     * then the player can chose one of them to keep
     * @param cardToPlayer the different leader the client receives
     * @throws RemoteException if something goaes wrong during RMI communication
     */
    public void receiveLeaderCardChoice(ArrayList<LeaderCard> cardToPlayer) throws RemoteException;

    /**
     * This method is called to deliver the cards to the player, order should be consistent
     * @param cardsToPlace the list of cards to place
     * @throws RemoteException if something goaes wrong during RMI communication
     */
    public void receiveCardToPlace(ArrayList<AbstractCard> cardsToPlace) throws RemoteException;

    /**
     * this method is called by the server to deliver the peronal tiles the client can choose
     * @param standardPersonalTile the standard personal tiles
     * @param specialPersonalTile the special personal tiles
     * @throws RemoteException if something goaes wrong during RMI communication
     */
    public void receivePersonalTiles(PersonalTile standardPersonalTile, PersonalTile specialPersonalTile) throws RemoteException;

    /**
     * this method is called by the RMI of the server to deliver the personal tiles chosn by other players
     * @param nickname the nickname of the player that had chosen this personal tile
     * @param personalTile the personal tile chosen
     */
    public void floodPersonalTileChosen(String nickname, PersonalTile personalTile) throws RemoteException;

    /**
     * this method is called by the server to inform the client that in the last move there was an error
     * @throws RemoteException if the server cannot reach the client
     */
    public void receiveError() throws RemoteException;

    /**
     * this method is called by the server to deliver to the client the leader card discarded by another player
     * @param nameCard name of the card discarded
     * @param nickname nickname of the player that had discarded the card
     * @param resourceGet the type of resources the player got
     * @throws RemoteException if the server cannot reach the client
     */
    public void receiveDiscardedLeaderCard(String nameCard, String nickname, HashMap<String, Integer> resourceGet) throws RemoteException;

    /**
     * this method is called by the server to deliver the leader card played by a player
     * @param nameCard the name of the leader card
     * @param choicesOnCurrentActionString the choices done while playing the leader card
     * @param nickname the nickname of the player
     */
    void receivePlayLeaderCard(String nameCard, HashMap<String, String> choicesOnCurrentActionString, String nickname) throws  RemoteException;

    /**
     * this method is used to receive the leader card chosen by the other players
     * @param leaderCard the leader card chosen by the player
     * @param nickname the nickname of the player that ha chosen the leader card
     */
    void receiveChosenLeaderCard(LeaderCard leaderCard, String nickname) throws RemoteException;

    /**
     * this method is called by the networ to deliver the leader card activated by another player
     * @param nameCard the name of the leader card
     * @param resourceGet the resource gotten
     * @param nickname the nickname of the player that had activated the card
     * @throws RemoteException
     */
    void receiveActivatedLeader(String nameCard, HashMap<String, Integer> resourceGet, String nickname) throws RemoteException;

    /**
     * this method is called by the rmi network to receive results of the end game
     * @param playerPositionEndGames the results of the game(the winner, the positions,...)
     */
    void receiveEndGame(ArrayList<PlayerPositionEndGamePacket> playerPositionEndGames) throws RemoteException;
}
