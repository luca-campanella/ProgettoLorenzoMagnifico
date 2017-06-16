package it.polimi.ingsw.server.network.rmi;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.server.network.AbstractConnectionPlayer;
import it.polimi.ingsw.client.network.rmi.RMIClientInterface;
import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.utils.Debug;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

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
        deliverNickname();
    }

    /**
     * This method is called by the room to send a chat message arrived from another client. (Direction: server -> client)
     * @param msg message
     * @throws NetworkException if something went wrong on the network
     */
    @Override
    public void receiveChatMsg(String senderNickname, String msg) throws NetworkException {

        try {
            RMIClientInterfaceInst.receiveChatMsg(senderNickname, msg);
        } catch (RemoteException e) {
            Debug.printError("rmi: cannot send chat message to " + getNickname(), e);
            throw new NetworkException("rmi: cannot send chat message to " + getNickname(), e);
        }

    }

    /**
     * This method is called by the room to send a move on tower arrived from another client. (Direction: server -> client)
     * @param familyMember the family member placed on the tower
     * @param towerIndex the number of the tower
     * @param floorIndex the number of the floor on the tower
     * @param playerChoices the choices of the player if the effects on the card had different alternatives
     * @throws NetworkException if something went wrong on the network
     */
    @Override
    public void receivePlaceOnTower(FamilyMember familyMember, int towerIndex, int floorIndex, HashMap<String, Integer> playerChoices) throws NetworkException {

        try{
            RMIClientInterfaceInst.receivePlaceOnTower(familyMember, towerIndex, floorIndex, playerChoices);
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot send move on tower to " + getNickname(), e);
            throw new NetworkException(e);
        }
    }

    /**
     * This method is called by the room to send a move on market arrived from another client. (Direction: server -> client)
     * @param familyMember the family member placed on the tower
     * @param marketIndex the number of the market
     * @param playerChoices the choices of the player if the effects on the card had different alternatives
     * @throws NetworkException if something went wrong on the network
     */
    @Override
    public void receivePlaceOnMarket(FamilyMember familyMember, int marketIndex, HashMap<String, Integer> playerChoices) throws NetworkException {

        try{
            RMIClientInterfaceInst.receivePlaceOnMarket(familyMember, marketIndex, playerChoices);
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot send move on market to " + getNickname(), e);
            throw new NetworkException(e);
        }
    }

    /**
     * This method is called by the room to send a move on build arrived from another client. (Direction: server -> client)
     * @param familyMember the family member placed on the tower
     * @param servant the number of servant used to increase the value of the family member
     * @param playerChoices the choices of the player if the effects on the card had different alternatives
     * @throws NetworkException if something went wrong on the network
     */
    @Override
    public void receiveBuild(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices) throws NetworkException {

        try{
            RMIClientInterfaceInst.receiveBuild(familyMember, servant, playerChoices);
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot send move on build to " + getNickname(), e);
            throw new NetworkException(e);
        }
    }

    /**
     * This method is called by the room to send a move on harvest arrived from another client. (Direction: server -> client)
     * @param familyMember the family member placed on the tower
     * @param servant the number of servant used to increase the value of the family member
     * @param playerChoices the choices of the player if the effects on the card had different alternatives
     * @throws NetworkException if something went wrong on the network
     */
    @Override
    public void receiveHarvest(FamilyMember familyMember, int servant, HashMap<String, Integer> playerChoices) throws NetworkException {

        try{
            RMIClientInterfaceInst.receiveHarvest(familyMember, servant, playerChoices);
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot send move on harvest to " + getNickname(), e);
            throw new NetworkException(e);
        }
    }

    /**
     * This method is called by the room to send a end of phase arrived from another client. (Direction: server -> client)
     * @param player the player that had ended the phase
     * @throws NetworkException if something went wrong on the network
     */
    @Override
    public void receiveEndPhase(AbstractConnectionPlayer player) throws NetworkException {

        try{
            RMIClientInterfaceInst.receiveEndPhase(player.getNickname());
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot send the end of turn of "+ player.getNickname()+ "to " + getNickname(), e);
            throw new NetworkException(e);
        }
    }

    /**
     * This method is called by the room to send the new dice to load on the board. (Direction: server -> client)
     * @throws NetworkException if something went wrong on the network
     */
    @Override
    public void receiveDices(ArrayList<Dice> dices) throws NetworkException {

        try{
            RMIClientInterfaceInst.receiveDice(dices);
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot send the dice to " + getNickname(), e);
            throw new NetworkException(e);
        }
    }

    /**
     * This method is called by the room to send the board to the players. (Direction: server -> client)
     * @param gameBoard the loaded board
     * @throws NetworkException if the connection goes wrong
     */
    @Override
    public void receiveStartGameBoard(Board gameBoard) throws NetworkException{

        try{
            RMIClientInterfaceInst.receiveBoard(gameBoard);
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot send move on harvest to" + getNickname(), e);
            throw new NetworkException(e);
        }
    }

    /**
     * this method is called by the room to inform the player that his turn is started
     * @throws NetworkException if the connection goes wrong
     */
    @Override
    public void receiveStartOfTurn() throws NetworkException{

        try{
            RMIClientInterfaceInst.receiveStartOfTurn();
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot send move on harvest to" + getNickname(), e);
            throw new NetworkException(e);
        }
    }

    /**
     * this method is called by the room to deliver the order of player to the client
     * @throws NetworkException if the connection goes wrong
     */
    @Override
    public void deliverOrderPlayers(ArrayList<String> orderPlayers) throws NetworkException {

        try{
            RMIClientInterfaceInst.receiveOrderPlayer(orderPlayers);
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot send the order of the player to" + getNickname(), e);
            throw new NetworkException(e);
        }
    }

    /**
     * this method is used to deliver to the client the different leader card recived by the room
     * @throws NetworkException
     */
    @Override
    public void receiveLeaderCards(ArrayList<LeaderCard> cardToPlayer) throws NetworkException {

        try{
            RMIClientInterfaceInst.receiveLeaderCardChoice(cardToPlayer);
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot send the leader cards to" + getNickname(), e);
            throw new NetworkException(e);
        }
    }

    /**
     * this method is called by the room and is used to deliver the cards to place on the board to the client
     */
    @Override
    public void deliverCardToPlace(ArrayList<AbstractCard> cards) throws NetworkException {

        try{
            RMIClientInterfaceInst.receiveCardToPlace(cards);
        }

        catch (RemoteException e){
            Debug.printError("rmi: cannot send the cards to place to" + getNickname(), e);
            throw new NetworkException(e);
        }
    }

    /**
     * This method is used by the client to send chat message to all other players in the room (Direction: client -> sever)
     * @param msg the message
     * @throws NetworkException if somthing went wrong on the network
     */
    @Override
    public void sendChatMsg(String msg) throws RemoteException {
        getRoom().floodChatMsg(this, msg);
    }

    /**
     * this method is used to deliver to the client his nickname
     */
    private void deliverNickname(){
        try{
            RMIClientInterfaceInst.receiveNicknamePlayer(getNickname());
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot send the nickname to" + getNickname(), e);
        }
    }
}
