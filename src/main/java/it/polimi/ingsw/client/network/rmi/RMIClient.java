package it.polimi.ingsw.client.network.rmi;

import it.polimi.ingsw.client.controller.NetworkControllerClientInterface;
import it.polimi.ingsw.client.exceptions.*;
import it.polimi.ingsw.client.network.AbstractClientType;
import it.polimi.ingsw.client.network.socket.packet.PlayerPositionEndGamePacket;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.PersonalTile;
import it.polimi.ingsw.server.network.rmi.RMIPlayerInterface;
import it.polimi.ingsw.server.network.rmi.RMIServerInterface;
import it.polimi.ingsw.utils.Debug;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The class to handle the rmi client side, publishes itself and passes its reference to the server, so the server can call it back with RMIClientInterface
 */
public class RMIClient extends AbstractClientType implements RMIClientInterface {

    private Registry registry;
    private RMIServerInterface RMIServerInterfaceInst;
    private RMIPlayerInterface RMIPlayerInterfaceInst;

    /**
     * this is the thread pool to generate thread on the method called by the server
     */
    private ExecutorService generatorOfThread;

    /**
     * Constructor of RMIClient, it should be called before connect(), sets the parmeters using the super constructor of AbstractClientType
     * @param controllerMain the istance of ClientMain, to call callback functions
     * @param serverAddress the address to connect to
     * @param port the port to connect to
     */
    public RMIClient(NetworkControllerClientInterface controllerMain, String serverAddress, int port) {
        super(controllerMain, serverAddress, port);
        generatorOfThread = Executors.newCachedThreadPool();
    }

    /**
     * this method is used when an user already exists and decides to login with his username and password, real implementation of the abstract method
     *
     * @param nickname
     * @param password
     * @throws NetworkException if something goes wrong during the connection
     * @throws LoginException   if username doesn't exist or if password is wrong
     */
    @Override
    public void loginPlayer(String nickname, String password) throws NetworkException, LoginException {
        try {
            RMIPlayerInterfaceInst = RMIServerInterfaceInst.loginPlayer(nickname, password, this);
        } catch(RemoteException e) {
            Debug.printError("Cannot login player due to a network problem on rmi");
            throw new NetworkException(e);
        }

        try {
            Debug.printVerbose("Got the remote object to control the player " + RMIPlayerInterfaceInst.getNickname());
        } catch (RemoteException e) {
            Debug.printError("Cannot use the passed interface");
            throw new NetworkException(e);
        }
    }

    /**
     * this method is used when the user has never played and wants to create an account, real implementation of the abstract method
     *
     * @param nickname to register in the server DB
     * @param password to register in the server DB
     * @throws NetworkException if something goes wrong during the connection
     */
    @Override
    public void registerPlayer(String nickname, String password) throws NetworkException,UsernameAlreadyInUseException {
        try {
            RMIPlayerInterfaceInst = RMIServerInterfaceInst.registerPlayer(nickname, password, this);
        } catch(RemoteException e) {
            Debug.printError("Cannot register player due to a network problem on rmi");
            throw new NetworkException(e);
        }
    }


    /**
     * this method is used to discard a leader card
     *  @param nameLeader     is the name of the card
     * @param resourceChoose is the resource chose to obtain when the leader is sacrificed
     */
    @Override
    public void discardLeaderCard(String nameLeader, HashMap<String, Integer> resourceChoose) {
        //TODO implement abstract method
    }

    /**
     * this method is used to inform the room that the player had ended his phase
     */
    @Override
    public void endPhase() throws NetworkException {

        try{
            RMIPlayerInterfaceInst.receiveEndPhase();
        }
        catch (RemoteException e){
            Debug.printError("Cannot deliver the end phase of the player to the server");
            throw new NetworkException(e);
        }
    }

    /**
     * This method is used to send chat message to all players in the room
     *
     * @param msg The message
     * @throws NetworkException
     */
    @Override
    public void sendChatMsg(String msg) throws NetworkException {
        try {
            RMIPlayerInterfaceInst.sendChatMsg(msg);
        } catch (RemoteException e) {
            throw new NetworkException("rmi problem with chat message", e);
        }
    }

    /**
     * this method is called by the client to deliver the leader chosen to the server
     * @param leaderCard the leader card choose by the client
     * @throws NetworkException if something goes wrong with the network
     */
    @Override
    public void deliverLeaderChose(LeaderCard leaderCard) throws NetworkException {

        try {
            RMIPlayerInterfaceInst.receivedLeaderChosen(leaderCard);
        } catch (RemoteException e) {
            throw new NetworkException("rmi problem with chat message", e);
        }
    }
    /**
     * this method is used to deliver a leader card that the client wants to play
     * @param nameLeader the name of the chosen leader
     * @param choicesOnCurrentActionString the map of choices for leaders
     * @param choicesOnCurrentAction the map of the choices for activate or not
     * @throws NetworkException if something goes wrong during the connection
     */
    @Override
    public void playLeaderCard(String nameLeader, HashMap<String, String> choicesOnCurrentActionString,
                               HashMap<String, Integer> choicesOnCurrentAction) throws NetworkException {
        try {
            RMIPlayerInterfaceInst.playLeaderCard(nameLeader, choicesOnCurrentActionString, choicesOnCurrentAction);
        } catch (RemoteException e) {
            Debug.printError("RMI: Cannot deliver the leader card chosen to the server", e);
            throw new NetworkException("RMI: Cannot deliver the leader card chosen to the server", e);
        }
    }

    /**
     * this method is used to deliver the move of a family member on a tower
     * @param familyMember the chosen family member
     * @param numberTower the tower index (from left to right)
     * @param floorTower the number of the floor (from top to bottom)
     * @param playerChoices this is a map that contains all the choices of the client when an effect asks
     * @throws NetworkException if something goes wrong during the connection
     */
    @Override
    public void placeOnTower(FamilyMember familyMember, int numberTower, int floorTower, HashMap<String, Integer> playerChoices) throws NetworkException {
        try {
            RMIPlayerInterfaceInst.placeOnTower(familyMember.getColor(), numberTower, floorTower, playerChoices);
        } catch (RemoteException e) {
            Debug.printError("RMI: cannot place on tower, throwing new network exception up", e);
            throw new NetworkException("RMI: cannot place on tower", e);
        }
    }

    /**
     * this method is used to deliver the move of a family member on a marketplace
     * @param familyMember is the family member chosen
     * @param marketIndex is the index of the merket (from left to right) todo: check this assertion --Arto
     * @param playerChoices this is a map that contains all the choices of the client when an effect asks
     * @throws NetworkException if something goes wrong during the connection
     */
    @Override
    public void placeOnMarket(FamilyMember familyMember, int marketIndex, HashMap<String, Integer> playerChoices) throws NetworkException {
        try {
        RMIPlayerInterfaceInst.placeOnMarket(familyMember.getColor(), marketIndex, playerChoices);
        } catch (RemoteException e) {
            Debug.printError("RMI: cannot place on market, throwing new network exception up", e);
            throw new NetworkException("RMI: cannot place on market", e);
        }
    }

    /**
     * this method is used to deliver the placement a family member on council
     * @param familyMember is the family member chosen
     * @param playerChoices is a map that contains all the choices of the client when af effect is asked
     * @throws NetworkException if something goes wrong during the connection
     */
    @Override
    public void placeOnCouncil(FamilyMember familyMember, HashMap<String, Integer> playerChoices) throws NetworkException {
        try {
        RMIPlayerInterfaceInst.placeOnCouncil(familyMember.getColor(), playerChoices);
        } catch (RemoteException e) {
            Debug.printError("RMI: cannot place on council, throwing new network exception up", e);
            throw new NetworkException("RMI: cannot place on council", e);
        }
    }

    /**
     * this method is used to deliver the information about the harvest of a player
     * @param familyMember is the family member chosen
     * @param servantUsed is the number of family member used to increase the power of the action
     * @param playerChoices this is a map that contains all the choices of the client when an effect asks
     * @throws NetworkException if something foes wrong with the connection
     */
    @Override
    public void harvest(FamilyMember familyMember, int servantUsed, HashMap<String, Integer> playerChoices) throws NetworkException {
        try {
        RMIPlayerInterfaceInst.harvest(familyMember.getColor(), servantUsed, playerChoices);
        } catch (RemoteException e) {
            Debug.printError("RMI: cannot place on harvest, throwing new network exception up", e);
            throw new NetworkException("RMI: cannot place on harvest", e);
        }
    }

    /**
     * this method is used to deliver the information that a player has built
     * @param familyMember is the family member used to build
     * @param servantUsed is the number of family member used to increase the power of the action
     * @param playerChoices this is a map that contains all the choices of the client when an effect asks
     * @throws NetworkException if something goes wrong with the connection
     */
    @Override
    public void build(FamilyMember familyMember, int servantUsed, HashMap<String, Integer> playerChoices) throws NetworkException {
        try {
            RMIPlayerInterfaceInst.build(familyMember.getColor(), servantUsed, playerChoices);
        }
        catch (RemoteException e) {
            Debug.printError("RMI: cannot place on build, throwing new network exception up", e);
            throw new NetworkException("RMI: cannot place on build", e);
        }
    }

    /**
     * this method is called to deliver the personal tile chose by the client
     * @param tileChosen the tile chose
     * @throws NetworkException
     */
    @Override
    public void deliverTileChosen(PersonalTile tileChosen) throws NetworkException {
        try{
            RMIPlayerInterfaceInst.receivePersonalTile(tileChosen);
        }
        catch (RemoteException e){
            Debug.printError("RMI: cannot deliver the personal tile chosen by the client", e);
            throw new NetworkException("RMI: cannot deliver the personal tile", e);
        }
    }

    /**
     * this method is called by the client main to deliver to the server that this player
     * @param leaderName the name of the leader card
     * @param choicesOnCurrentAction the choices done on the effect
     * @throws NetworkException
     */
    @Override
    public void activateLeaderCard(String leaderName, HashMap<String, Integer> choicesOnCurrentAction) throws NetworkException {

        try{
            RMIPlayerInterfaceInst.receiveActivatedLeader(leaderName, choicesOnCurrentAction);
        }
        catch (RemoteException e){
            Debug.printError("RMI: cannot deliver the activated leader card", e);
            throw new NetworkException("RMI: cannot deliver the activated leader", e);
        }
    }

    /**
     * this method is called by the client to deliver to the server the choices one on the excommunication
     * @param response the choice of the client
     * @throws NetworkException if something goes wrong with the network
     */
    @Override
    public void excommunicationChoice(String response) throws NetworkException {
        try{
            RMIPlayerInterfaceInst.receiveExcommunicationChoice(response);
        }
        catch (RemoteException e){
            Debug.printError("RMI: cannot deliver the excommunicationChoice", e);
            throw new NetworkException("RMI: cannot deliver excommunicationChoice", e);
        }
    }

    /**
     * This method is called by the controller when the player suspended makes a new input and thus wants to be
     * reconnected
     * @throws NetworkException if something goes wrong with the network
     */
    @Override
    public void reconnectPlayer() throws NetworkException {
        try{
            RMIPlayerInterfaceInst.receiveReconnectPlayer();
        }
        catch (RemoteException e){
            Debug.printError("RMI: cannot deliver the reconnectPlayer", e);
            throw new NetworkException("RMI: cannot deliver the reconnectPlayer", e);
        }
    }

    /**
     * Performs the rmi operations to get "open" a rmi connection with the server
     * @throws ClientConnectionException if it can't find either the sever either the server class or it can't pulbish itself on the registry
     */
    @Override
    public void connect() throws ClientConnectionException {
        try {
            registry = LocateRegistry.getRegistry(getServerAddress(), getPort());
            RMIServerInterfaceInst = (RMIServerInterface) registry.lookup("RMIServerInterface");
            UnicastRemoteObject.exportObject(this, 0); //with 0 exports the object on a random port
            Debug.printDebug("rmi Client connected succesfully");

        } catch(RemoteException | NotBoundException e) {
            Debug.printError("Cannot connect rmi client", e);
            throw new ClientConnectionException(e);
        }

    }

    /**
     * This method is called from the server to communicate that a chat message has arrived to the client (Direction: server -> client)
     * @param senderNick the nickname of the player who sent the msg
     * @param msg
     * @throws RemoteException
     */
    @Override
    public void receiveChatMsg(String senderNick, String msg) throws RemoteException {
        generatorOfThread.submit(() -> getControllerMain().receiveChatMsg(senderNick, msg));
    }

    /**
     * this method is called by the server to notify that another player has moved on a tower
     *
     * @param playerNickname    the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param towerIndex        the index of the tower he placed the family member in
     * @param floorIndex        the index of the floor he placed the family member in
     * @param playerChoices     the hashmao with his choices correlated with this action
     */
    @Override
    public void receivePlaceOnTower(String playerNickname, DiceAndFamilyMemberColorEnum familyMemberColor, int towerIndex, int floorIndex, HashMap<String, Integer> playerChoices) throws RemoteException {

        generatorOfThread.submit(() -> getControllerMain().receivedPlaceOnTower(playerNickname,
                familyMemberColor, towerIndex, floorIndex, playerChoices));
    }

    /**
     * this method is called by the server to notify that another player has moved in a market action space
     *
     * @param playerNickname    the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param marketIndex       the index of the market action space he placed the family member in
     * @param playerChoices     the hashmao with his choices correlated with this action
     */
    @Override
    public void receivePlaceOnMarket(String playerNickname,
                                     DiceAndFamilyMemberColorEnum familyMemberColor,
                                     int marketIndex,
                                     HashMap<String, Integer> playerChoices) throws RemoteException {

        generatorOfThread.submit(() -> getControllerMain().receivedPlaceOnMarket(playerNickname,
                familyMemberColor, marketIndex, playerChoices));

    }

    /**
     * this method is called by the server to notify that another player has moved in the harvest action space
     *
     * @param playerNickname    the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param servantsUsed      the number of servants used to perform the action
     * @param playerChoices     the hashmao with his choices correlated with this action
     */
    @Override
    public void receiveHarvest(String playerNickname,
                               DiceAndFamilyMemberColorEnum familyMemberColor,
                               int servantsUsed,
                               HashMap<String, Integer> playerChoices) throws RemoteException {

        generatorOfThread.submit(() -> getControllerMain().receivedHarvest(playerNickname,
                familyMemberColor, servantsUsed, playerChoices));
    }

    /**
     * this method is called by the server to notify that another player has moved in the build action space
     *
     * @param playerNickname    the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param servantsUsed      the number of servants used to perform the action
     * @param playerChoices     the hashmap with his choices correlated with this action
     */
    @Override
    public void receiveBuild(String playerNickname,
                               DiceAndFamilyMemberColorEnum familyMemberColor,
                               int servantsUsed,
                               HashMap<String, Integer> playerChoices) throws RemoteException {

        generatorOfThread.submit(() -> getControllerMain().receivedBuild(playerNickname,
                familyMemberColor, servantsUsed, playerChoices));
    }

    /**
     * this method is called by the server to notify that another player has moved in the council
     *
     * @param playerNickname    the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param playerChoices     the hashmap with his choices correlated with this action
     */
    @Override
    public void receivePlaceOnCouncil(String playerNickname,
                             DiceAndFamilyMemberColorEnum familyMemberColor,
                             HashMap<String, Integer> playerChoices) throws RemoteException {

        generatorOfThread.submit(() -> getControllerMain().receivedPlaceOnCouncil(playerNickname,
                familyMemberColor, playerChoices));
    }

    /**
     * this method is called to receive the end of the phase of another player
     * @param nickname the nickname of the player that had ended the turn
     * @throws RemoteException
     */
    @Override
    public void receiveEndPhase(String nickname) throws RemoteException {

        generatorOfThread.submit(() -> getControllerMain().receiveEndPhase(nickname));
    }

    /**
     * this method is called to receive the dice value of the server
     * @param dices the dice to save on the client board
     * @throws RemoteException
     */
    @Override
    public void receiveDice(ArrayList<Dice> dices) throws RemoteException {

        generatorOfThread.submit(() -> getControllerMain().receivedDices(dices));
    }

    /**
     * this method is called to receive the board of the game at the start of the turn
     * @param gameBoard clean, with only the excommunication tiles, without cards or dice
     * @throws RemoteException
     */
    @Override
    public void receiveBoard(Board gameBoard) throws RemoteException {

        generatorOfThread.submit(() -> getControllerMain().receivedStartGameBoard(gameBoard));
    }

    @Override
    public void receiveStartOfTurn() throws RemoteException {

        generatorOfThread.submit(() -> getControllerMain().receivedStartTurnNotification());
    }

    @Override
    public void receiveOrderPlayer(ArrayList<String> orderPlayers) throws RemoteException {

        generatorOfThread.submit(() -> getControllerMain().receivedOrderPlayers(orderPlayers));

    }

    @Override
    public void receiveNicknamePlayer(String nicknamePlayer) throws RemoteException {

    }

    /**
     * this method is called by the remote server to receive the leader cards the client can choice
     * @param cardToPlayer
     * @throws RemoteException
     */
    @Override
    public void receiveLeaderCardChoice(ArrayList<LeaderCard> cardToPlayer) throws RemoteException {

        generatorOfThread.submit(() -> getControllerMain().receivedLeaderCards(cardToPlayer));

    }

    /**
     * This method is called to deliver the cards to the player, order should be consistent
     * @param cardsToPlace the list of cards to place
     * @throws RemoteException if something goaes wrong during RMI communication
     */
    @Override
    public void receiveCardToPlace(ArrayList<AbstractCard> cardsToPlace) throws RemoteException {

        generatorOfThread.submit(() -> getControllerMain().receiveCardsToPlace(cardsToPlace));
    }

    /**
     * this method is called by RMI server to receive the personal tiles which the client can chose to play
     * @param standardPersonalTile the standard personal tiles
     * @param specialPersonalTile the special personal tiles
     * @throws RemoteException
     */
    @Override
    public void receivePersonalTiles(PersonalTile standardPersonalTile, PersonalTile specialPersonalTile) throws RemoteException {

        generatorOfThread.submit(() -> getControllerMain().receivedPersonalTiles(standardPersonalTile,specialPersonalTile));
    }

    /**
     * this merhod is called by RMI server to deliver the personal tile choices done by the players
     * @param nickname the nickname of the player that had chosen this personal tile
     * @param personalTile the personal tile chosen
     * @throws RemoteException
     */
    @Override
    public void floodPersonalTileChosen(String nickname, PersonalTile personalTile) throws RemoteException {

        generatorOfThread.submit(() -> getControllerMain().receiveFloodPersonalTile(nickname, personalTile));

    }

    /**
     * this method is called by the server to inform the client that in the last move there was an error
     * @throws RemoteException
     */
    @Override
    public void receiveError() throws RemoteException {

    }

    /**
     * this method is called by the server to receive the leader card discarded
     * @param nameCard name of the card discarded
     * @param nickname nickname of the player that had discarded the card
     * @param resourceGet the type of resources the player got
     * @throws RemoteException
     */
    @Override
    public void receiveDiscardedLeaderCard(String nameCard, String nickname, HashMap<String, Integer> resourceGet) throws RemoteException {

        generatorOfThread.submit(() -> getControllerMain().receivedDiscardLeaderCard(nickname, nameCard, resourceGet));
    }

    /**
     * this method is called by the server to deliver the leader card played by a player
     * @param nameCard the name of the leader card
     * @param choicesOnCurrentActionString the choices done while playing the leader card
     * @param nickname the nickname of the player
     * @param choicesOnCurrentAction
     * @throws RemoteException
     */
    @Override
    public void receivePlayLeaderCard(String nameCard, HashMap<String, String> choicesOnCurrentActionString,
                                      String nickname, HashMap<String, Integer> choicesOnCurrentAction)
            throws RemoteException {

        generatorOfThread.submit(() -> getControllerMain().receivePlayLeaderCard(nameCard, choicesOnCurrentActionString,
                nickname, choicesOnCurrentAction));

    }

    /**
     * this method is used to receive the leader card chosen by the other players
     * @param leaderCard the leader card chosen by the player
     * @param nickname the nickname of the player that ha chosen the leader card
     * @throws RemoteException
     */
    @Override
    public void receiveChosenLeaderCard(LeaderCard leaderCard, String nickname) throws RemoteException {

        generatorOfThread.submit(() -> getControllerMain().receiveChosenLeader(nickname, leaderCard));
    }

    /**
     * this method is called by the networ to deliver the leader card activated by another player
     * @param nameCard the name of the leader card
     * @param resourceGet the resource gotten
     * @param nickname the nickname of the player that had activated the card
     * @throws RemoteException
     */
    @Override
    public void receiveActivatedLeader(String nameCard, HashMap<String, Integer> resourceGet, String nickname) throws RemoteException {
        generatorOfThread.submit(() -> getControllerMain().receiveActivateLeaderCard(nickname, nameCard, resourceGet));
    }

    /**
     * this method is called by the rmi network to receive results of the end game
     * @param playerPositionEndGames the results of the game(the winner, the positions,...)
     * @throws RemoteException if something goes wrong with the network
     */
    @Override
    public void receiveEndGame(ArrayList<PlayerPositionEndGamePacket> playerPositionEndGames) throws RemoteException {
        generatorOfThread.submit(() -> getControllerMain().receiveEndGame(playerPositionEndGames));
    }

    /**
     * this method is called by the room to deliver the players excommunicated
     * @param nicknamePlayerExcommunicated the nickname of the players excommunicated
     * @param numTile the number of excommunication tile to take
     * @throws RemoteException if something goes wrong with the network
     */
    @Override
    public void receiveExcommunicatedPlayers(ArrayList<String> nicknamePlayerExcommunicated, int numTile) throws RemoteException {
        generatorOfThread.submit(() -> getControllerMain().receiveExcommunicatedPlayers(nicknamePlayerExcommunicated, numTile));
    }

    /**
     * this method is called by the server to deliver the choice of a excommunication choice
     * @param nickname the nickname of the player that had done the choice
     * @param response the response of the excommunication
     * @param numTile the number of the tile to take if the player is excommunicated
     * @throws RemoteException if something goes wrong with the network
     */
    @Override
    public void receiveExcommunicationChoice(String nickname, String response, int numTile) throws RemoteException {
        generatorOfThread.submit(() -> getControllerMain().manageExcommunicationChoice(nickname, response, numTile));
    }

    /**
     * this method is called by the room to deliver the fact that a player has disconnected due to the timeout
     *
     * @param nickname the nickname of the player that disconnected
     */
    @Override
    public void receiveNotificationSuspendedPlayer(String nickname) throws RemoteException {
        generatorOfThread.submit(() -> getControllerMain().receivedNotificationSuspendedPlayer(nickname));
    }

    /**
     * this method is called by the room to deliver the fact that a player has reconnected
     *
     * @param nickname the nickname of the player that reconnected
     * @throws RemoteException if something goes wrong with the network
     */
    @Override
    public void receiveNotificationReconnectedPlayer(String nickname) throws RemoteException {
        generatorOfThread.submit(() -> getControllerMain().receivedNotificationReconnectedPlayer(nickname));
    }

    /**
     * this method is called by the room to deliver the fact that a player has disconnected
     *
     * @param nickname the nickname of the player that disconnected
     * @throws RemoteException if something goes wrong with the network
     */
   public void deliverDisconnectionPlayer(String nickname) throws RemoteException {
       generatorOfThread.submit(() -> getControllerMain().receivedPlayerDisconnected(nickname));
   }

}
