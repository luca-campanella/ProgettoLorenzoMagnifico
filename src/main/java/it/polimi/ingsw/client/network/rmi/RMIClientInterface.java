package it.polimi.ingsw.client.network.rmi;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;

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

    public void receiveEndPhase(String nickname) throws RemoteException;

    public void receiveDice(ArrayList<Dice> dices) throws RemoteException;

    public void receiveBoard(Board gameBoard) throws RemoteException;

    public void receiveStartOfTurn() throws RemoteException;

    public void receiveOrderPlayer(ArrayList<String> orderPlayer) throws RemoteException;

    public void receiveNicknamePlayer(String nicknamePlayer) throws RemoteException;

    public void receiveLeaderCardChoice(ArrayList<LeaderCard> cardToPlayer) throws RemoteException;

    public void receiveCardToPlace(ArrayList<AbstractCard> cardsToPlace) throws RemoteException;
}
