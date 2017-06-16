package it.polimi.ingsw.client.network.rmi;

import com.sun.org.apache.regexp.internal.RE;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.FamilyMember;

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

    public void receivePlaceOnTower(FamilyMember familyMember, int towerIndex, int floorIndex, HashMap<String, Integer> playerChoices) throws RemoteException;

    public void receivePlaceOnMarket(FamilyMember familyMember, int marketIndex, HashMap<String, Integer> playerChoices) throws RemoteException;

    public void receiveBuild(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices) throws RemoteException;

    public void receiveHarvest(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices) throws RemoteException;

    public void receiveEndPhase(String nickname) throws RemoteException;

    public void receiveDice(ArrayList<Dice> dices) throws RemoteException;

    public void receiveBoard(Board gameBoard) throws RemoteException;

    public void receiveStartOfTurn() throws RemoteException;

    public void receiveOrderPlayer(ArrayList<String> orderPlayer) throws RemoteException;

    public void receiveNicknamePlayer(String nicknamePlayer) throws RemoteException;

    public void receiveLeaderCardChoice(ArrayList<LeaderCard> cardToPlayer) throws RemoteException;

    public void receiveCardToPlace(ArrayList<AbstractCard> cardsToPlace) throws RemoteException;
}
