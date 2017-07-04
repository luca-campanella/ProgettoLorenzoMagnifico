package it.polimi.ingsw.server.network.rmi;

import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.client.network.rmi.RMIClientInterface;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.PersonalTile;
import it.polimi.ingsw.model.player.PersonalTileEnum;
import it.polimi.ingsw.server.network.AbstractConnectionPlayer;
import it.polimi.ingsw.utils.Debug;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is the player via rmi
 */
 class RMIPlayer extends AbstractConnectionPlayer implements RMIPlayerInterface {

    private RMIClientInterface RMIClientInterfaceInst;

    /**
     * this is the thread pool to generate thread on the method called by the client
     */
    private ExecutorService generatorOfThread;
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
     *
     * @param familyMember the family member placed in the market
     * @param playerChoices the choices of the player if the effects on the card had different alternatives
     */
    @Override
    public void receivePlaceOnCouncil(FamilyMember familyMember, HashMap<String, Integer> playerChoices) throws NetworkException {
        try{
            RMIClientInterfaceInst.receivePlaceOnCouncil(familyMember.getPlayer().getNickname(), familyMember.getColor(), playerChoices);
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot send move on council to " + getNickname(), e);
            throw new NetworkException(e);
        }
    }

    /**
     * this method is called by room to deliver the personal tiles to the player
     * @param personalTilesToDeliver the personal tiles the player can receive
     * @throws NetworkException if something went wrong on the network
     */
    @Override
    public void deliverPersonalTiles(ArrayList<PersonalTile> personalTilesToDeliver) throws NetworkException {

        PersonalTile standardPersonalTile = null;
        PersonalTile specialPersonalTile = null;
        for(PersonalTile personalTile : personalTilesToDeliver){
            if(personalTile.getPersonalTileEnum() == PersonalTileEnum.STANDARD)
                standardPersonalTile = personalTile;
            else
                specialPersonalTile = personalTile;
        }

        try{
            RMIClientInterfaceInst.receivePersonalTiles(standardPersonalTile, specialPersonalTile);
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot deliver the personal tiles to " + getNickname(), e);
            throw new NetworkException(e);
        }
    }

    /**
     * this method is called from the room to deliver the personal tiles chosen by other players
     * @param nickname the nickname of the client that had chosen the personal tile
     * @param personalTile the personal tile chosen by the client
     * @throws NetworkException if something went wrong on the network
     */
    @Override
    public void otherPlayerPersonalTile(String nickname, PersonalTile personalTile) throws NetworkException {
        try{
            RMIClientInterfaceInst.floodPersonalTileChosen(nickname, personalTile);
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot deliver the personal tiles of " + nickname +" to " + getNickname(), e);
            throw new NetworkException(e);
        }
    }

    /**
     * this method is called by room to inform the client of a error of the move
     * @throws NetworkException if something went wrong on the network
     */
    @Override
    public void deliverErrorMove() throws NetworkException {
        try{
            RMIClientInterfaceInst.receiveError();
        }
        catch (RemoteException e){
            Debug.printError("cannot deliver the error to the client");
            throw new NetworkException(e);
        }
    }

    /**
     * this method is called by the room to deliver to all the players the information that a player had discarded a leader card
     * @param nameCard the name of the card discarded
     * @param nickname the nickname of the player
     * @param resourceGet the resource got by the player
     * @throws NetworkException if something went wrong on the network
     */
    @Override
    public void deliverDiscardLeaderCard(String nameCard, String nickname, HashMap<String, Integer> resourceGet) throws NetworkException {

        try{
            RMIClientInterfaceInst.receiveDiscardedLeaderCard(nameCard, nickname, resourceGet);
        }
        catch (RemoteException e){
            Debug.printError("cannot deliver the leader card discarded to the client");
            throw new NetworkException(e);
        }

    }

    /**
     * this method is used to deliver to a player that a different players had left the game
     * @param nickname the nickname of the player that is not more in the game
     * @throws NetworkException if something went wrong on the network
     */
    @Override
    public void deliverDisconnectionPlayer(String nickname) throws NetworkException {

    }

    /**
     * this method is used to deliver to all the players the leader card played by a player
     * @param nameCard the name of the leader card
     * @param choicesOnCurrentActionString the choices done while playing the card
     * @param nickname the nickname of the player that had played the card
     * @throws NetworkException if something went wrong on the network
     */
    @Override
    public void deliverPlayLeaderCard(String nameCard, HashMap<String, String> choicesOnCurrentActionString, String nickname) throws NetworkException {

        try{
            RMIClientInterfaceInst.receivePlayLeaderCard(nameCard, choicesOnCurrentActionString, nickname);
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot deliver the leader card played to " + getNickname(), e);
            throw new NetworkException(e);
        }
    }

    /**
     * this method is used to deliver to the player client the chosen leader card to other player
     * @param leaderCard the leader card that has be chosen by a player
     * @param player the player that has chosen the leader card
     * @throws NetworkException if something went wrong on the network
     */
    @Override
    public void deliverLeaderChose(LeaderCard leaderCard, AbstractConnectionPlayer player) throws NetworkException {

        try{
            RMIClientInterfaceInst.receiveChosenLeaderCard(leaderCard, player.getNickname());
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot deliver the leader card chosen by " + player.getNickname() + " to " + getNickname(), e);
            throw new NetworkException(e);
        }

    }

    /**
     * this method is used to deliver to the client the information that a player had activated the effect of a leader card
     * @param nameCard the name of the leader card
     * @param resourceGet the resource gotten thanks to the effect
     * @param nickname the nickname of the player that had activated the leader card
     * @throws NetworkException
     */
    @Override
    public void deliverActivatedLeaderCard(String nameCard, HashMap<String, Integer> resourceGet, String nickname) throws NetworkException {

        try{
            RMIClientInterfaceInst.receiveActivatedLeader(nameCard, resourceGet, nickname);
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot send move on tower to " + getNickname(), e);
            throw new NetworkException(e);
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
            RMIClientInterfaceInst.receivePlaceOnTower(familyMember.getPlayer().getNickname(), familyMember.getColor(), towerIndex, floorIndex, playerChoices);
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
            RMIClientInterfaceInst.receivePlaceOnMarket(familyMember.getPlayer().getNickname(), familyMember.getColor(), marketIndex, playerChoices);
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot send move on market to " + getNickname(), e);
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
            RMIClientInterfaceInst.receiveHarvest(familyMember.getPlayer().getNickname(), familyMember.getColor(), servant, playerChoices);
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot send move on harvest to " + getNickname(), e);
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
            RMIClientInterfaceInst.receiveBuild(familyMember.getPlayer().getNickname(), familyMember.getColor(), servant, playerChoices);
        }
        catch (RemoteException e){
            Debug.printError("rmi: cannot send move on build to " + getNickname(), e);
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
     * @throws NetworkException if something went wrong on the network
     */
    @Override
    public void sendChatMsg(String msg) throws RemoteException {

        generatorOfThread = Executors.newCachedThreadPool();
        generatorOfThread.submit(() -> getRoom().floodChatMsg(this, msg));
    }

    /**
     * Method used to signal that that player has played a leader card
     *
     * @param leaderName the name of the leader card played
     * @throws RemoteException if something goes wrong with RMI communication
     */
    @Override
    public void playLeaderCard(String leaderName,HashMap<String, String> choicesOnCurrentActionString) throws RemoteException {

        generatorOfThread = Executors.newCachedThreadPool();
        generatorOfThread.submit(() -> getRoom().playLeaderCard(leaderName, choicesOnCurrentActionString, this));

    }

    /**
     * this method is used to deliver the move of a family member on a tower
     * @param familyMemberColor the color of the chosen family member
     * @param numberTower the tower index (from left to right)
     * @param floorTower the number of the floor (from top to bottom)
     * @param playerChoices this is a map that contains all the choices of the client when an effect asks
     * @throws RemoteException if something goes wrong during the connection
     */
    @Override
    public void placeOnTower(DiceAndFamilyMemberColorEnum familyMemberColor, int numberTower, int floorTower, HashMap<String, Integer> playerChoices) throws RemoteException {

        generatorOfThread = Executors.newCachedThreadPool();
        generatorOfThread.submit(() -> getRoom().placeOnTower(getFamilyMemberByColor(familyMemberColor), numberTower, floorTower, playerChoices));
    }

    /**
     * this method is used to deliver the move of a family member on a marketplace
     * @param familyMemberColor is the color of the chosen family member
     * @param marketIndex is the index of the market space  (from left to right) //todo: Check this assert
     * @param playerChoices is the map that cointains all the choices of the client when an effect asks
     * @throws RemoteException if something goes wrong during the connection
     */
    @Override
    public void placeOnMarket(DiceAndFamilyMemberColorEnum familyMemberColor, int marketIndex, HashMap<String, Integer> playerChoices) throws RemoteException {

        generatorOfThread = Executors.newCachedThreadPool();
        generatorOfThread.submit(() -> getRoom().placeOnMarket(getFamilyMemberByColor(familyMemberColor),marketIndex,playerChoices));
    }

    /**
     * this method is used to deliver the move of a family member on a council
     * @param familyMemberColor is the color of the chosen family member
     * @param playerChoices is the map that cointains all the choices of the client when an effect asks
     * @throws RemoteException if something goes wrong during the connection
     */
    @Override
    public void placeOnCouncil(DiceAndFamilyMemberColorEnum familyMemberColor, HashMap<String, Integer> playerChoices) throws  RemoteException
    {
        generatorOfThread = Executors.newCachedThreadPool();
        generatorOfThread.submit(() -> getRoom().placeOnCouncil(getFamilyMemberByColor(familyMemberColor),playerChoices));
    }

    /**
     * this method is used to harvest
     * @param familyMemberColor is the color of the chosen family member
     * @param servantsUsed is the numberof additional servants used to increase value of the dice
     * @param playerChoices is the map that cointains all the choices of the client when an effect asks
     * @throws RemoteException if something goes wrong during the connection
     */
    @Override
    public void harvest(DiceAndFamilyMemberColorEnum familyMemberColor,int servantsUsed, HashMap<String, Integer> playerChoices) throws  RemoteException
    {
        generatorOfThread = Executors.newCachedThreadPool();
        generatorOfThread.submit(() -> getRoom().harvest(getFamilyMemberByColor(familyMemberColor),servantsUsed, playerChoices));
    }

    /**
     * this method is used to build
     * @param familyMemberColor is the color of the chosen family member
     * @param servantsUsed is the numberof additional servants used to increase value of the dice
     * @param playerChoices is the map that cointains all the choices of the client when an effect asks
     * @throws RemoteException if something goes wrong during the connection
     */
    @Override
    public void build(DiceAndFamilyMemberColorEnum familyMemberColor,int servantsUsed, HashMap<String, Integer> playerChoices) throws  RemoteException
    {
        generatorOfThread = Executors.newCachedThreadPool();
        generatorOfThread.submit(() -> getRoom().build(getFamilyMemberByColor(familyMemberColor),servantsUsed, playerChoices));
    }

    /**
     * this metthod is called bu the client to deliver to the server the personal tile kept by the client
     * @param tileChosen the personal tile chosen by the client
     * @throws RemoteException if something goes wrong with the connection
     */
    @Override
    public void receivePersonalTile(PersonalTile tileChosen) throws RemoteException {

        generatorOfThread = Executors.newCachedThreadPool();
        generatorOfThread.submit(() -> getRoom().chosePersonalTile(tileChosen, this));

    }

    /**
     * this method is called by the client to infor the server that the player had ended his turn
     */
    @Override
    public void receiveEndPhase() throws RemoteException{

        generatorOfThread = Executors.newCachedThreadPool();
        generatorOfThread.submit(() -> getRoom().endPhase(this));
    }

    /**
     * this method is called by the client to deliver to the server the leader card activated
     * @param leaderName the name of the leader card
     * @param choicesOnCurrentAction the choices done if the player had to choose recources
     * @throws RemoteException
     */
    @Override
    public void receiveActivatedLeader(String leaderName, HashMap<String, Integer> choicesOnCurrentAction) throws RemoteException {

        generatorOfThread = Executors.newCachedThreadPool();
        generatorOfThread.submit(() -> getRoom().receiveActivatedLeader(leaderName, choicesOnCurrentAction, this));
    }

    /**
     * this method is called by the client to deliver to the server the leader card choose by the client
     * @param leaderCard the leader card chosen
     * @throws RemoteException
     */
    @Override
    public void receivedLeaderChosen(LeaderCard leaderCard) throws RemoteException {

        generatorOfThread = Executors.newCachedThreadPool();
        generatorOfThread.submit(() -> getRoom().receiveLeaderCards(leaderCard, this));

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
