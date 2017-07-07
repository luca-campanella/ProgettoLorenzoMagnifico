package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.choices.NetworkChoicesPacketHandler;
import it.polimi.ingsw.client.cli.StdinSingleton;
import it.polimi.ingsw.client.exceptions.ClientConnectionException;
import it.polimi.ingsw.client.exceptions.LoginException;
import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.client.exceptions.UsernameAlreadyInUseException;
import it.polimi.ingsw.client.network.AbstractClientType;
import it.polimi.ingsw.client.network.NetworkTypeEnum;
import it.polimi.ingsw.client.network.rmi.RMIClient;
import it.polimi.ingsw.client.network.socket.SocketClient;
import it.polimi.ingsw.client.network.socket.packet.PlayerPositionEndGamePacket;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.cards.BuildingCard;
import it.polimi.ingsw.model.cards.VentureCard;
import it.polimi.ingsw.model.cards.VentureCardMilitaryCost;
import it.polimi.ingsw.model.controller.ModelController;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.NoEffect;
import it.polimi.ingsw.model.effects.immediateEffects.PayForSomethingEffect;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.leaders.leadersabilities.AbstractLeaderAbility;
import it.polimi.ingsw.model.leaders.leadersabilities.EmptyLeaderAbility;
import it.polimi.ingsw.model.player.*;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceCollector;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.utils.Debug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * TODO: implement launcher
 */
public class ClientMain implements NetworkControllerClientInterface, ViewControllerCallbackInterface, ChoicesHandlerInterface {
    private LauncherClientFake temp;
    private AbstractUIType userInterface;
    private AbstractClientType clientNetwork;
    private ModelController modelController;
    private boolean playedFamilyMember = false;

    /**
     * this object is used to handle the choices made by another player that need to reply to the callback from model
     */
    private NetworkChoicesPacketHandler otherPlayerChoicesHandler;

    /**
     * The list of players in the room
     */
    private ArrayList<Player> players;

    /**
     * The player connected with this client, useful to perform the actions the user chooses
     */
    private Player thisPlayer;

    /**
     * true if the player is currently suspended from the game due to timeout
     */
    private boolean isThisPlayerSuspended = false;

    /**
     * this hashmap is filled with all the choices the user made regarding the move he's currently performed
     * it is filled by all the callback methods
     * it should be re-instantiated every time a new action is performed
     */
    private HashMap<String, Integer> choicesOnCurrentAction;

    /**
     * this hashmap is filled with all the choices the user made regarding the move he's currently performed
     * it is filled by all the callback methods
     * it should be re-instantiated every time a new action is performed
     * This second hashmap is used when order is not sure and a more reliable way of passing information is useful
     */
    private HashMap<String, String> choicesOnCurrentActionString;

    /**
     * the player of this controller
     */
    private String nickname;

    /**
     * this resource collector is used to check that the user has sufficient resources to make a build choice
     * it is initialized with the resources of the player at the beginning of the build action
     */
    private ResourceCollector resourcesCheckMap;

    private FamilyMember familyMemberCurrentAction;

    /**
    this is Class Constructor
     */
    public ClientMain()
    {
        startUp();
    }

    public void startUp() {
        Debug.instance(Debug.LEVEL_VERBOSE);
        StdinSingleton.instance();
        temp = new LauncherClientFake(this);
        userInterface = temp.welcome();
        userInterface.askNetworkType();
    }

    @Deprecated
    public static void main(String args[]) {
        new ClientMain();
    }

    /**
     * This method returns Client's userInterface
     * @return Client's userInterface
     */
    public AbstractUIType getUserInterface() {
        return userInterface;
    }

    /**
     * This method is used to make a callback from view to model when the user chooses the network type
     * @param networkChoice the network type chosen by the user
     */
    @Override
    public void callbackNetworkType(NetworkTypeEnum networkChoice){
        Debug.printDebug("I'm in ClientMain.callbackNetworkType, choice = " + networkChoice);
        if(networkChoice == NetworkTypeEnum.RMI) {
            clientNetwork = new RMIClient(this, "127.0.0.1", 3034);
            try {
                clientNetwork.connect();
            } catch (ClientConnectionException e) {
                e.printStackTrace();
                //TODO: handling no Connection
            }
        }
        else {//Here enters if network type is a socket
            clientNetwork = new SocketClient(this, "127.0.0.1", 3035);
            try {
                clientNetwork.connect();
            } catch (ClientConnectionException e) {
                e.printStackTrace();
                //TODO: handling no Connection
            }
        }
        userInterface.askLoginOrCreate();
    }

    /**
     * this method is called when a user is trying to login.
     * @param userID the username
     * @param userPW the password
     */
    @Override
    public void callbackLogin(String userID, String userPW){
        Debug.printDebug("Sono nel ClientMain.callbackLogin.");
        try {
            clientNetwork.loginPlayer(userID, userPW);

            this.nickname = userID;
            userInterface.showWaitingForGameStart();
            Debug.printVerbose("Login succesfully");

        } catch (NetworkException e) {
            //TODO handle network problems
            e.printStackTrace();
        }
        catch (LoginException e) {
            //TODO handle login problems (call the UI again)
            Debug.printDebug("Login exception occurred", e);
            switch(e.getErrorType()) {
                case ALREADY_LOGGED_TO_ROOM :
                    userInterface.displayError("Already logged", "You are already logged to " +
                            "the current room, please use another account or wait for the game to start");
                    userInterface.askLoginOrCreate();
                    break;
                case NOT_EXISTING_USERNAME:
                    userInterface.displayError("Not existing username", "The username you inserted " +
                            "doesn't exists, please use an existing one or register a new account");
                    userInterface.askLoginOrCreate();
                break;
                case WRONG_PASSWORD:
                    userInterface.displayError("Wrong password", "The password you inserted was wrong");
                    userInterface.askLoginOrCreate();
                    break;
                default:
                    userInterface.displayError("Some problem", "Something went wrong.");
                    userInterface.askLoginOrCreate();
                    break;

            }

        }
        //those 2 methods are for testing: they can be deleted:
        //userInterface.askChatMsg();
        //userInterface.askInitialAction();
    }

    /**
     * Called by the UI when the user wants to create a new account to connect to the server
     * @param userID
     * @param userPW
     */
    @Override
    public void callbackCreateAccount(String userID, String userPW){
        Debug.printDebug("I'm in ClientMain.callbackCreateAccount");
        try {
            clientNetwork.registerPlayer(userID, userPW);
            this.nickname = userID;
            userInterface.showWaitingForGameStart();
        } catch (NetworkException e) {
            //TODO handle network problems
            e.printStackTrace();
        }
        catch(UsernameAlreadyInUseException e)
        {
            Debug.printDebug(e);
            userInterface.displayError("Username already in use", "The username you inserted is already in use, please insert a new one");
            userInterface.askLoginOrCreate();
        }
    }

    /**
     * this method it's a callback method called from AbstractUIType when i want to discard a Leader.
     */
    public void callbackDiscardLeader(LeaderCard leaderCard){

        Debug.printDebug("I'm in ClientMain.callbackDiscardLeader");
        modelController.setChoicesController(this);
        modelController.discardLeaderCard(thisPlayer.getNickname(),leaderCard.getName());
        try{
            clientNetwork.discardLeaderCard(leaderCard.getName(),choicesOnCurrentAction);
            clientChoices();
        }
        catch (NetworkException e){
            Debug.printError("the client cannot deliver the leader card to discard");
        }
    }

    /**
     * this method is a callback method called from abstractUiType when a family member is selected
     * @param selectdFM the family member selected.
     */
    @Override
    public void callbackFamilyMemberSelected(FamilyMember selectdFM)
    {
        Debug.printDebug("Sono nel ClientMain.callbackFamilyMember: color = " + selectdFM.getColor() + " value: " + selectdFM.getValue());

        familyMemberCurrentAction = selectdFM;

        userInterface.askWhichActionSpace(modelController.spaceHarvestAvailable(familyMemberCurrentAction),
                modelController.spaceBuildAvailable(familyMemberCurrentAction),
                modelController.spaceCouncilAvailable(familyMemberCurrentAction),
                modelController.spaceMarketAvailable(familyMemberCurrentAction),
                modelController.spaceTowerAvailable(familyMemberCurrentAction),
                familyMemberCurrentAction.getPlayer().getResource(ResourceTypeEnum.SERVANT));
        Debug.printDebug("Chiamata ritorna a callbackFM");

    }

    /**
     * this method should be called every time a new action / move is perfomed by the user
     * It initialises the data structures used afterwards
     */
    private void initialActionsOnPlayerMove() {
        choicesOnCurrentAction = new HashMap<>();
        choicesOnCurrentActionString = new HashMap<>();

    }

    /**
     * This method is called by {@link AbstractClientType} to display an incoming chat message (Direction: AbstractClientType -> ClientMain; general direction: Server -> Client)
     * @param senderNick
     * @param msg
     */
    @Override
    public void receiveChatMsg(String senderNick, String msg) {
        userInterface.displayChatMsg(senderNick, msg);
    }


    /**
     * this is the call back method to send a message to all other players in the room (Direction: {@link AbstractUIType} -> {@link ClientMain}; general direction: Client -> server)
     * @param msg
     * @throws NetworkException
     */
    @Override
    public void callbackSendChatMsg(String msg) throws NetworkException {
        clientNetwork.sendChatMsg(msg);
    }

    /**
     * this method allows player to place a family member on a build action space
     * @param servantsUsed the number of servants the user decided to use
     */
    @Override
    public void callbackPlacedFMOnBuild(int servantsUsed) {
        /*We make a copy of the hashmap because we have to perform some checks on it and this checks should not affect
        the hashmap of the player. Even tough making a copy using the constructor makes just a shallow copy, this is sufficient
        since Integer types are immutable. This copy will be modified inside the callbacks
         */

        Debug.printVerbose("before calling build");
        resourcesCheckMap = new ResourceCollector(familyMemberCurrentAction.getPlayer().getResourcesCollector());
        modelController.build(familyMemberCurrentAction, servantsUsed);
        Debug.printVerbose("build succeeded");

        try{
            clientNetwork.build(familyMemberCurrentAction, servantsUsed, choicesOnCurrentAction);
            Debug.printVerbose("delivered build to server");
            playedFamilyMember = true;
            clientChoices();
        }
        catch (NetworkException e){
            Debug.printError("cannot deliver the move build to the server");
        }
    }

    /**
     * this method allows player to place a family member on a harvest action space
     * @param servantsUsed the number of servants the user decided to use
     */
    @Override
    public void callbackPlacedFMOnHarvest(int servantsUsed){

        Debug.printVerbose("before calling harvest");
        modelController.harvest(familyMemberCurrentAction, servantsUsed);
        Debug.printVerbose("harvest succeeded");

        try{
            clientNetwork.harvest(familyMemberCurrentAction, servantsUsed, choicesOnCurrentAction);
            Debug.printVerbose("delivered harvest to server");
            playedFamilyMember = true;
            clientChoices();
        }
        catch (NetworkException e){
            Debug.printError("cannot deliver the move harvest to the server");
        }
    }

    /**
     * this method allows player to place a family member on a tower floor action space
     * @param towerIndex the identifier of the tower
     * @param floorIndex the identifier of the floor
     */
    @Override
    public void callbackPlacedFMOnTower(int towerIndex, int floorIndex){

        Debug.printVerbose("Callback: placed fm on tower " + towerIndex + " on floor " + floorIndex);

        modelController.placeOnTower(familyMemberCurrentAction, towerIndex, floorIndex);

        try{
            clientNetwork.placeOnTower(familyMemberCurrentAction, towerIndex, floorIndex, choicesOnCurrentAction);
            Debug.printVerbose("delivered tower move to server");
            playedFamilyMember = true;
            clientChoices();
        }
        catch (IOException e){
            Debug.printError("cannot deliver the move on tower to the server");
        }
    }

    /**
     * this method allows player to place a family member on a market action space
     * @param marketASIndex the selected market AS
     */
    @Override
    public void callbackPlacedFMOnMarket(int marketASIndex){

        Debug.printVerbose("Callback on market");
        modelController.placeOnMarket(familyMemberCurrentAction, marketASIndex);
        try{
            clientNetwork.placeOnMarket(familyMemberCurrentAction, marketASIndex, choicesOnCurrentAction);
            Debug.printVerbose("delivered market move to server");
            playedFamilyMember = true;
            clientChoices();
        }
        catch (IOException e){
            Debug.printError("cannot deliver the move on market to the server");
        }

    }

    /**
     * this method allows player to place a family member in the council action space
     */
    @Override
    public void callbackPlacedFMOnCouncil(){

        Debug.printVerbose("before calling council");
        modelController.placeOnCouncil(familyMemberCurrentAction);
        Debug.printVerbose("place on council succeeded");
        try{
            clientNetwork.placeOnCouncil(familyMemberCurrentAction, choicesOnCurrentAction);
            Debug.printVerbose("delivered place on council to server");
            playedFamilyMember = true;
            clientChoices();
        }
        catch (NetworkException e){
            Debug.printError("cannot deliver the move on council to the server");
        }
    }

    /**
     * Callback from model to controller
     * The model uses this method when encounters a council gift and should choose between the possible ones
     * The method also performs checks that all the chosen council gifts are different one another
     * This implementation calls the view and asks what the user wants to choose
     * The UI should perform a <b>blocking</b> question to the user and return directly to this method
     * @param choiceCode
     * @param numberDiffGifts the number of different council gifts to ask for
     * @return The arraylist of effect chosen
     */
    @Override
    public ArrayList<GainResourceEffect> callbackOnCouncilGift(String choiceCode, int numberDiffGifts) {
        ArrayList<GainResourceEffect> options = modelController.getBoard().getCouncilAS().getCouncilGiftChoices();
        ArrayList<GainResourceEffect> choices = new ArrayList<>(numberDiffGifts);
        int choice;

        for(int i = 0; i < numberDiffGifts; i++) {
            choice = userInterface.askCouncilGift(options);
            Debug.printVerbose("after taken choice");
            choices.add(options.get(choice));
            options.remove(choice);
            choicesOnCurrentAction.put(choiceCode + i, choice);
        }

        return choices;
    }

    /**
     * Callback from model to controller
     * The model uses this method when encounters a {@link BuildingCard} with more than one effects and wants to make the user choose which one activate
     * This callback should be called even if the user has no choices, because it also performs resource checks
     * If in these checks it understands no effect can be chosen then it returns a {@link NoEffect} class and puts the choice in the hashmap to -1
     * This implementation calls the view and asks what the user wants to choose
     * The UI should perform a <b>blocking</b> question to the user and return directly to this method
     * @param cardNameChoiceCode
     * @param possibleEffectChoices
     * @return
     */
    @Override
    public ImmediateEffectInterface callbackOnYellowBuildingCardEffectChoice(String cardNameChoiceCode, List<ImmediateEffectInterface> possibleEffectChoices) {

        //We will make a copy of the arraylist beacuse we have to remove some objects that cannot be chosen from the user
        ArrayList<ImmediateEffectInterface> realPossibleEffectChoices = new ArrayList<>(possibleEffectChoices.size());
        ImmediateEffectInterface effectIter;
        //we check if the user has left sufficient resources to perform this effect
        for(int i = 0; i < possibleEffectChoices.size(); i++) {
            effectIter = possibleEffectChoices.get(i);
            if(effectIter instanceof PayForSomethingEffect) {
                if(!resourcesCheckMap.checkIfContainable(((PayForSomethingEffect) effectIter).getToPay()))
                    continue; //we should not add the option because the player doesn't have enough resources
            }
            realPossibleEffectChoices.add(effectIter);
        }

        int choice;
        ImmediateEffectInterface effectChosen;

        //if there's no possibility left or one possibility the choice is already made, no need to ask the user
        if(possibleEffectChoices.isEmpty())
        {
            choice = -1;
            effectChosen = new NoEffect();
        }
        else if(possibleEffectChoices.size() == 1) {
            effectChosen = realPossibleEffectChoices.get(1);
            choice = possibleEffectChoices.indexOf(effectChosen);
        }
        else { //there are possible choices: let's ask the UI what to chose
            int tmpChoice = userInterface.askYellowBuildingCardEffectChoice(realPossibleEffectChoices);
            if(tmpChoice == -1) { // the player decided not to activate any effect
                effectChosen = new NoEffect();
                choice = -1;
            }
             else { //he chose an effect, let's return the correct one
                effectChosen = realPossibleEffectChoices.get(tmpChoice);
                choice = possibleEffectChoices.indexOf(effectChosen);
            }
        }

        //we need to subtract the resources he payed from the copy of the hashmap in order to be sure next checks are correct
        if(effectChosen instanceof PayForSomethingEffect) {
            resourcesCheckMap.addResource(((PayForSomethingEffect) effectChosen).getToGain());
        }

        choicesOnCurrentAction.put(cardNameChoiceCode, choice);
        return effectChosen;
    }

    /**
     * Callback from model to controller
     * The model uses this method inside {@link VentureCard#getCostAskChoice(ChoicesHandlerInterface)} to understand what cos he should subtract
     *
     * @param choiceCode
     * @param costChoiceResource the list of resources the player will pay if he chooses this option
     * @param costChoiceMilitary the cost he will pay on something conditioned
     * @return The list of resources the model has to take away from the player
     */
    @Override
    public List<Resource> callbackOnVentureCardCost(String choiceCode, List<Resource> costChoiceResource, VentureCardMilitaryCost costChoiceMilitary) {
        Player player = familyMemberCurrentAction.getPlayer();

        //first we check if the player has enough resources (military points) to cover the requirement, if he doesn't the choice is made and goes for paying with resources
        if(costChoiceMilitary.getResourceRequirement().getValue() > player.getResource(costChoiceMilitary.getResourceRequirement().getType()))
            return costChoiceResource;

        int choice = userInterface.askPurpleVentureCardCostChoice(costChoiceResource, costChoiceMilitary);

        choicesOnCurrentAction.put(choiceCode, choice);
        if(choice==1) {
            ArrayList<Resource> res = new ArrayList<>(1);
            res.add(costChoiceMilitary.getResourceCost());
            return res;
        }
        return costChoiceResource;
    }

    @Override
    @Deprecated
    public void setNickname(String nickname){

        this.nickname = nickname;

    }

    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType} to signal that the game has started
     * and to pass to every client the order of players
     * Here we create the localArrayList of Players
     * @param orderPlayers the nicknames of the players, in order
     */
    @Override
    public void receivedOrderPlayers(ArrayList<String> orderPlayers) {
        players = new ArrayList<Player>(orderPlayers.size());
        Debug.printVerbose("receivedOrderPlayers called, nickname = " + nickname);

        Player playerTmp;
        for(String nicknameIter : orderPlayers) {
            playerTmp = new Player(nicknameIter);
            if(nicknameIter.equals(nickname))
                thisPlayer = playerTmp;
            playerTmp.setPlayerColor(PlayerColorEnum.valueOf(players.size()));
            players.add(playerTmp);
            Debug.printVerbose("Created new player " + nicknameIter);
        }
        Debug.printVerbose("thisPlayerInitialized with value = " + thisPlayer.getNickname());
        //thisPlayer = players.get(players.indexOf(nickname));
    }


    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType} to signal that the game has started
     * and to pass the board the server has created
     * @param board the board from the server
     */
    @Override
    public void receivedStartGameBoard(Board board) {

        Debug.printVerbose("receivedStartgameBoard called");

        modelController = new ModelController(players, board);

        modelController.setFamilyMemberDices();

        //add the coins to the orderOfPlayers based on the order of turn
        modelController.addCoinsStartGame(players);

        //now that we have the board we can give the object the possibile options for a council gift
        otherPlayerChoicesHandler = new NetworkChoicesPacketHandler(modelController.getBoard().getCouncilAS().getCouncilGiftChoices());
    }

    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType} to signal that the game has started
     * and the dices already thrown
     * @param dices the board from the server
     */
    public void receivedDices(ArrayList<Dice> dices) {
        Debug.printVerbose("receivedDices called");

        dices.forEach(dice -> Debug.printVerbose("Dice " + dice.getValue() + " " + dice.getColor() ));
        modelController.setDice(dices);

        modelController.reloadFamilyMember();

        ArrayList<FamilyMember> playableFMs = thisPlayer.getNotUsedFamilyMembers();
        for(FamilyMember fmIter : playableFMs) {
            Debug.printVerbose("PLAYABLE FM:" + "Family member of color " + fmIter.getColor() + "of value " + fmIter.getValue());
        }
        userInterface.waitMenu();
    }

    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType}
     * to notify that the player is in turn and should move
     */
    public void receivedStartTurnNotification() {
        Debug.printVerbose("receivedStartTurnNotification called");
        playedFamilyMember = false;
        clientChoices();
    }

    /**
     * this method is called to show to the client the different choices he can do
     */
    public void clientChoices(){

        //it's this player's turn, he should answer callbacks from model
        modelController.setChoicesController(this);
        initialActionsOnPlayerMove();
        //List<LeaderCard> notPlayedLeaderCards = //modelController.getLeaderCardsNotPlayed(thisPlayer.getNickname());
        //List<LeaderCard> playeableLeaderCards = //findPlayableLeader(notPlayedLeaderCards);
        userInterface.askInitialAction(playedFamilyMember);

    }


    /**
     * This method returns to the view a true if the player was suspended
     *
     * @return true if the player was suspended
     */
    @Override
    public boolean callbackObtainIsThisPlayerSuspended() {
        return isThisPlayerSuspended;
    }

    /**
     * This method returns to the view a reference to the player the client represents
     * this method is usually called to show the personal board of the player
     *
     * @return the player the clietn represents
     */
    @Override
    public Player callbackObtainPlayer() {

        return thisPlayer;
        //userInterface.printPersonalBoard(thisPlayer);

    }

    /**
     * This method returns to the view true if this turn the palyer already played a family member
     *
     * @return true if this turn the palyer already played a family member
     */
    @Override
    public FamilyMember callbackObtainSelectedFamilyMember() {
        return familyMemberCurrentAction;
    }

    /**
     * this method returns to the client the response of the excommunication choice
     * @param response the excommunication response: "yes" if the player wants to avoid the excommunication
     *                                               "no" if the player wants to be excommunicate without losing his faith points
     */
    @Override
    public void callbackExcommunicationChoice(String response, int numTile) {

        manageExcommunicationChoice(thisPlayer.getNickname(), response, numTile);
        try{
            clientNetwork.excommunicationChoice(response);
            Debug.printVerbose("delivered excommunication choice to server");
        }
        catch (NetworkException e){
            Debug.printError("cannot deliver the excommunication choice to the server");
        }

    }

    /**
     * This method is called by the view in order to reconnect a player that was suspended
     */
    @Override
    public void callbackConnectPlayerAgain() {
        isThisPlayerSuspended = false;
        try {
            clientNetwork.reconnectPlayer();
        } catch (NetworkException e) {
            userInterface.displayErrorAndExit("Network problem", e.getMessage());
        }
        userInterface.waitMenu();
    }

    /**
     * this method is called by the menu to ask the leader cards not played
     */
    @Override
    public ArrayList<LeaderCard> callbackObtainLeaderCardsNotPlayed() {
        return thisPlayer.getLeaderCardsNotUsed();
    }

    /**
     * this method is used to menage the choices done on the excommunication tile
     * @param nickname the nickname of the player that had done the choice
     * @param response the response of the player
     * @param numTile the number of the tile that the player takes if he is excommunicated
     */
    public void manageExcommunicationChoice(String nickname, String response, int numTile) {

        if(response.equals("YES"))
            modelController.avoidExcommunicationPlayer(nickname);
        else
            modelController.excommunicatePlayer(nickname, numTile);
    }

    /**
     * this method is called by the network to deliver the fact that a player has disconnected due to the timeout
     *
     * @param nickname the nickname of the player that disconnected
     */
    @Override
    public void receivedNotificationSuspendedPlayer(String nickname) {
        Debug.printVerbose("*** the player " + nickname + " has been suspended");
        if(thisPlayer.getNickname().equals(nickname)){
            isThisPlayerSuspended = true;
            userInterface.notifyThisPlayerSuspended();
        } else {
            userInterface.notifyAnotherPlayerSuspended(nickname);
        }
    }

    /**
     * this method is called by the network to deliver the fact that a player has reconnected
     *
     * @param nickname the nickname of the player that reconnected
     */
    @Override
    public void receivedNotificationReconnectedPlayer(String nickname) {
        userInterface.notifyPlayerReconnected(nickname);
    }

    /**
     * This method returns to the view a reference to the board
     * this method is called to obtain the board of the game inside the view
     *
     * @return the current board
     */
    @Override
    public Board callbackObtainBoard() {
        return modelController.getBoard();
    }

    /**
     * This method returns to the view a list of other players
     * this method is usually called by the view to show the personal boards of the other players
     *
     * @return the list of other playes
     */
    @Override
    public List<Player> callbackObtainOtherPlayers() {

        ArrayList<Player> otherPlayers = new ArrayList<>(players.size()-1);
        for(Player player : players){
            if(!player.getNickname().equals(thisPlayer.getNickname()))
                otherPlayers.add(player);
        }
        //userInterface.printPersonalBoardOtherPlayers(otherPlayers);
        return otherPlayers;
    }

    /**
     * This method returns to the view a list of all the players in order
     * this method is usually called by the view to show the correct order of players in the board
     *
     * @return the list of all players in order
     */
    @Override
    public List<Player> callbackObtainPlayersInOrder() {
        return players;
    }

    /**
     *
     * @param leaderCard the leader card that activates the effect
     */
    @Override
    public void callbackActivateLeader(LeaderCard leaderCard) {

        Debug.printDebug("I'm in ClientMain.callbackActivateLeader");
        modelController.setChoicesController(this);
        modelController.activateLeaderCard( thisPlayer.getNickname(), leaderCard.getName());
        try{
            clientNetwork.activateLeaderCard(leaderCard.getName(),choicesOnCurrentAction);
        }
        catch (NetworkException e){
            Debug.printError("the client cannot deliver the leader card to activate");
        }
        clientChoices();
    }

    /**
     * This method returns to the view the list of dices
     * this method is usually called by the view to show the dices value
     * @return the list of the dices
     */
    @Override
    public List<Dice> callbackObtainDices() {
        return modelController.getDices();
    }

    /**
     * this method is called to play a leader card on the hand of the player
     * @param leaderCard the leader card played by the player
     */
    @Override
    public void callbackPlayLeader(LeaderCard leaderCard) {

        Debug.printDebug("I'm in ClientMain.callbackPlayLeader");
        modelController.setChoicesController(this);
        modelController.playLeaderCard(leaderCard.getName(), thisPlayer);
        try{
            clientNetwork.playLeaderCard(leaderCard.getName(),choicesOnCurrentActionString, choicesOnCurrentAction); //todo send choicesOnCurrentAction
        }
        catch (NetworkException e){
            Debug.printError("the client cannot deliver the leader card to discard");
        }
        clientChoices();
    }

    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType}
     * to notify that the has to pick a leader card between the ones proposed
     * @param leaderCards options
     */
    @Override
    public void receivedLeaderCards(List<LeaderCard> leaderCards) {
        Debug.printDebug("Automatically chose leader " + leaderCards.get(0).getName());
        callbackOnLeaderCardChosen(leaderCards.get(0)); //todo remove these two statements
        //todo commented to skip leader chosing phase, not to lose time
        //userInterface.askLeaderCards(leaderCards);
    }

    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType}
     * @param cards the cards the client had to place
     */
    @Override
    public void receiveCardsToPlace(ArrayList<AbstractCard> cards) {
        Debug.printDebug("Cards received, placing on board");
        cards.forEach((card) -> Debug.printVerbose(card.getName()));
        modelController.placeCardOnBoard(cards);
        if(!players.get(0).getNickname().equals(thisPlayer.getNickname()))
            userInterface.waitMenu();
    }

    /**
     * this method is called by the view to communicate the leader choice
     * @param leaderCardChoice the choice made
     */
    @Override
    public void callbackOnLeaderCardChosen(LeaderCard leaderCardChoice) {
        Debug.printVerbose("callbackOnLeaderCardChosen Called");
        modelController.addLeaderCardToPlayer(leaderCardChoice, thisPlayer);
        //todo do this only if some other player is missing
        userInterface.showWaitingForLeaderChoices();
        try {
            clientNetwork.deliverLeaderChose(leaderCardChoice);
        } catch (NetworkException e) {
            //todo handle better
            Debug.printError("Cannot send leader choice", e);
            userInterface.displayError("Connection problem", "Cannot contact the server, exiting the program");
            System.exit(0);
        }
    }

    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType}
     * to notify that the has to pick a personal tile between the ones proposed
     * @param standardTile option1
     * @param specialTile option2
     */
    @Override
    public void receivedPersonalTiles(PersonalTile standardTile, PersonalTile specialTile) {
        //userInterface.askPersonalTiles(standardTile, specialTile);
        callbackOnTileChosen(standardTile);
    }

    /**
     * this method is used to deliver to the server that thi player had ended the phase
     */
    @Override
    public void callBackPassTheTurn() {
        try{
            clientNetwork.endPhase();
            userInterface.waitMenu();

        }
        catch (NetworkException e){
            Debug.printError("Cannot deliver the end of the phase",e);
            clientChoices();
        }
    }

    /**
     * this method is used to deliver to the connection the personal tile chosen by the client at the start of the game
     * @param tileChosen the personal tile chosen by the player to use in this game
     */
    @Override
    public void callbackOnTileChosen(PersonalTile tileChosen) {
        userInterface.showWaitingForTilesChoices();
        modelController.setPersonalTile(tileChosen, thisPlayer.getNickname());
        try{
            clientNetwork.deliverTileChosen(tileChosen);
        }
        catch (NetworkException e){
            Debug.printError("Cannot deliver the personal tile chosen",e);
        }
    }

    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType}
     * to notify that another player has moved on a tower
     *
     * @param nickname          the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param towerIndex        the index of the tower he placed the family member in
     * @param floorIndex        the index of the floor he placed the family member in
     * @param playerChoices     the hashmao with his choices correlated with this action
     */
    @Override
    public void receivedPlaceOnTower(String nickname, DiceAndFamilyMemberColorEnum familyMemberColor, int towerIndex, int floorIndex, HashMap<String, Integer> playerChoices) {
        Player player = modelController.getPlayerByNickname(nickname);
        Debug.printVerbose("the player " + nickname + " has place the family member on tower.");
        otherPlayerChoicesHandler.setChoicesMap(playerChoices);
        modelController.setChoicesController(otherPlayerChoicesHandler);
        FamilyMember fm = player.getFamilyMemberByColor(familyMemberColor);
        modelController.placeOnTower(fm, towerIndex, floorIndex);
        //we notify the user the other player has done this move
        userInterface.notifyPlaceOnTower(fm, towerIndex, floorIndex);
    }

    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType}
     * to notify that another player has moved inside the market
     *
     * @param nickname          the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param marketIndex       the index of the market action space he placed the family member in
     * @param playerChoices     the hashmao with his choices correlated with this action
     */
    @Override
    public void receivedPlaceOnMarket(String nickname, DiceAndFamilyMemberColorEnum familyMemberColor, int marketIndex, HashMap<String, Integer> playerChoices) {
        Player player = modelController.getPlayerByNickname(nickname);
        otherPlayerChoicesHandler.setChoicesMap(playerChoices);
        modelController.setChoicesController(otherPlayerChoicesHandler);
        FamilyMember fm = player.getFamilyMemberByColor(familyMemberColor);
        modelController.placeOnMarket(fm, marketIndex);
        //we notify the user the other player has done this move
        userInterface.notifyPlaceOnMarket(fm, marketIndex);
    }

    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType}
     * to notify that another player has moved inside the harvest action space
     *
     * @param nickname          the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param servantsUsed the number of the servants used to perform this action
     * @param playerChoices     the hashmap with his choices correlated with this action
     */
    @Override
    public void receivedHarvest(String nickname, DiceAndFamilyMemberColorEnum familyMemberColor, int servantsUsed, HashMap<String, Integer> playerChoices) {
        Player player = modelController.getPlayerByNickname(nickname);
        Debug.printVerbose("the player " + nickname + " has harvested.");
        otherPlayerChoicesHandler.setChoicesMap(playerChoices);
        modelController.setChoicesController(otherPlayerChoicesHandler);
        FamilyMember fm = player.getFamilyMemberByColor(familyMemberColor);
        modelController.harvest(fm, servantsUsed);
        //we notify the user the other player has done this move
        userInterface.notifyPlaceOnHarvest(fm, servantsUsed);
    }

    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType}
     * to notify that another player has moved inside the build action space
     *
     * @param nickname          the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param servantsUsed the number of the servants used to perform this action
     * @param playerChoices     the hashmap with his choices correlated with this action
     */
    @Override
    public void receivedBuild(String nickname, DiceAndFamilyMemberColorEnum familyMemberColor, int servantsUsed, HashMap<String, Integer> playerChoices) {
        Player player = modelController.getPlayerByNickname(nickname);
        Debug.printVerbose("the player " + nickname + " has build.");
        otherPlayerChoicesHandler.setChoicesMap(playerChoices);
        modelController.setChoicesController(otherPlayerChoicesHandler);
        FamilyMember fm = player.getFamilyMemberByColor(familyMemberColor);
        modelController.build(fm, servantsUsed);
        //we notify the user the other player has done this move
        userInterface.notifyPlaceOnBuild(fm, servantsUsed);
    }

    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType}
     * to notify that another player has moved inside the council
     *
     * @param nickname          the nickname of the player performing the action
     * @param familyMemberColor the color of the family member he performed the action with
     * @param playerChoices     the hashmap with his choices correlated with this action
     */
    @Override
    public void receivedPlaceOnCouncil(String nickname, DiceAndFamilyMemberColorEnum familyMemberColor, HashMap<String, Integer> playerChoices) {
        Player player = modelController.getPlayerByNickname(nickname);
        Debug.printVerbose("received place on council");
        otherPlayerChoicesHandler.setChoicesMap(playerChoices);
        modelController.setChoicesController(otherPlayerChoicesHandler);
        FamilyMember fm = player.getFamilyMemberByColor(familyMemberColor);
        modelController.placeOnCouncil(fm);
        //we notify the user the other player has done this move
        userInterface.notifyPlaceOnCouncil(fm);
    }

    /**
     * Callback from model to controller
     * the model uses this method when it is playing a leader who has a COPY ability, such as "Lorenzo de' Medici"
     * to ask which choice the user wants or has done
     * @param possibleLeaders possible leader abilities to choose from
     * @return the chosen leader ability
     */
    public AbstractLeaderAbility callbackOnWhichLeaderAbilityToCopy(List<LeaderCard> possibleLeaders) {
        if(possibleLeaders.isEmpty())
            return new EmptyLeaderAbility();

        int choice = userInterface.askWhichLeaderAbilityToCopy(possibleLeaders);

        LeaderCard chosenLeader = possibleLeaders.get(choice);
        choicesOnCurrentActionString.put("COPY_ABILITY", chosenLeader.getName());

        return chosenLeader.getAbility();
    }

    /**
     * Callback from model to controller
     * the model uses this method when it is playing a leader who has a ONCE_PER_ROUND ability
     * to ask the user if he also wants to activate the ability
     * @return true if he also wants to activate, false otherwise
     */
    public boolean callbackOnAlsoActivateLeaderCard() {
        int choice = userInterface.askAlsoActivateLeaderCard();

        choicesOnCurrentAction.put("AlsoActivateLeader", choice);
        //it is true if choice is 0, false if it is = 1
        return(choice == 0);
    }

    /**
     * this method is used to receive the end phase of other players
     * @param nickname the player that had pass
     */
    @Override
    public void receiveEndPhase(String nickname) {
        userInterface.notifyEndOfPhaseOfPlayer(nickname);
    }

    /**
     * this method is used to receive the personal tile deliver by other players
     * @param nickname the nickname of the player that had chosen the tile
     * @param personalTile the personal tile chosen
     */
    @Override
    public void receiveFloodPersonalTile(String nickname, PersonalTile personalTile) {
        modelController.setPersonalTile(personalTile, nickname);
    }

    /**
     * this method is called by the connectioon to deliver the discard leader card of a player
     * @param nickname the nickname of the player that discarded the leader card
     * @param nameCard the name of the leader card discarded
     * @param resourceGet the resource obtained
     */
    @Override
    public void receivedDiscardLeaderCard(String nickname, String nameCard, HashMap<String, Integer> resourceGet) {

        otherPlayerChoicesHandler.setChoicesMap(resourceGet);
        modelController.setChoicesController(otherPlayerChoicesHandler);
        modelController.discardLeaderCard(nickname, nameCard);
        Debug.printVerbose("The player "+ nickname + " has discarded " + nameCard);
        userInterface.notifyDiscardLeaderCard(nickname, nameCard);
    }

    /**
     * this method is called by the server to inform the client that a player had left the game
     * @param nicknamePlayerDisconnected the nickname of the player that had left the game
     */
    @Override
    public void receiveDisconnection(String nicknamePlayerDisconnected) {

        for(Player player : players){
            if(player.getNickname().equals(nicknamePlayerDisconnected)){
                players.remove(player);
                modelController.removePlayer(player);
                break;
            }
        }
        userInterface.displayError(nicknamePlayerDisconnected + "Has disconnected", "DiconnectionOfAPlayer");
    }

    /**
     * this method is used to receive the leader cards that had been chosen by the other players
     * @param nickname the nickname of the player that had chosen the card
     * @param leaderCard the chosen leader card
     */
    @Override
    public void receiveChosenLeader(String nickname, LeaderCard leaderCard) {

        modelController.addLeaderCardToPlayer(leaderCard, modelController.getPlayerByNickname(nickname));

    }

    /**
     * this method is called by RMI client to receive the leaderc card played bu another player
     * @param nameCard the name of the leader card played
     * @param choicesOnCurrentActionString the choices done while playing the leader card
     * @param nickname the nickname of the player
     * @param choicesOnCurrentAction
     */
    @Override
    public void receivePlayLeaderCard(String nameCard, HashMap<String, String> choicesOnCurrentActionString, String nickname, HashMap<String, Integer> choicesOnCurrentAction) {

        otherPlayerChoicesHandler.setChoicesMapString(choicesOnCurrentActionString);
        otherPlayerChoicesHandler.setChoicesMap(choicesOnCurrentAction);
        modelController.setChoicesController(otherPlayerChoicesHandler);
        modelController.playLeaderCard(nameCard, modelController.getPlayerByNickname(nickname));
        userInterface.notifyPlayLeaderCard(nickname, nameCard);
    }

    /**
     * this method is called by the network to receive the end game results
     * @param playerPositionEndGamePacket this packet contains all the players with the final victory points and position
     */
    @Override
    public void receiveEndGame(ArrayList<PlayerPositionEndGamePacket> playerPositionEndGamePacket) {

        userInterface.showEndOfGame(playerPositionEndGamePacket);
    }

    /**
     * this method is called by the network to deliver the leader card activated by anothe player
     * @param nickname the nickname of the player that had activated the leader card abilty
     * @param nameCard the name of the leader card activated
     * @param resourceGet the resources gotten from the leader ability
     */
    @Override
    public void receiveActivateLeaderCard(String nickname, String nameCard, HashMap<String, Integer> resourceGet) {

        otherPlayerChoicesHandler.setChoicesMap(resourceGet);
        modelController.setChoicesController(otherPlayerChoicesHandler);
        modelController.activateLeaderCard(nickname, nameCard);
        Debug.printVerbose("The player "+ nickname + " has activated " + nameCard);
        userInterface.notifyActivateLeaderCard(nickname, nameCard);
    }

    /**
     * this method is called by the server to deliver the players excommunicated
     * @param playersExcommunicated the nicknames of the players excommunicated
     */
    @Override
    public void receiveExcommunicatedPlayers(ArrayList<String> playersExcommunicated, int numTile) {

        modelController.excommunicatePlayer(playersExcommunicated, numTile);
        userInterface.displayExcommunicationPlayers(playersExcommunicated);
        if(!playersExcommunicated.contains(thisPlayer.getNickname()) && !isThisPlayerSuspended){
            userInterface.askExcommunicationChoice(numTile);
        }
    }

    /**
     * Callback from model to controller
     * the model uses this method when the player performs an action but from the model we have to ask
     * how many servants he wants to add
     * @param minimum the minimum number of servants he shuold at least add (typically 0)
     * @param maximum the maximum number of servants he can add (typically the ones the player has)
     * @return the number of servants the player wants to add to the action
     */
    @Override
    public int callbackOnAddingServants(String choiceCode, int minimum, int maximum) {
        if(minimum == maximum)
            return maximum;

        int choice = userInterface.askAddingServants(minimum, maximum);

        choicesOnCurrentAction.put(choiceCode+":servantsAdded", choice);
        return choice;
    }

    /**
     * Callback from model to controller
     * the model uses this method when the player activate a leader with a once per round ability that modifies
     * the value of one of his colored family members, he has to choose which one
     * @param choiceCode the code of the choice, to be put inside hashmap
     * @param availableFamilyMembers the list of available family member, it's useless to modify
     *                               the value of a family member already played
     * @throws IllegalArgumentException if the list is empty
     * @return the color of the family member he chose
     */
    @Override
    public DiceAndFamilyMemberColorEnum callbackOnFamilyMemberBonus(String choiceCode, List<FamilyMember> availableFamilyMembers) throws IllegalArgumentException {
        DiceAndFamilyMemberColorEnum choice;

        try {
            choice = userInterface.askWhichFamilyMemberBonus(availableFamilyMembers);
        } catch (IllegalArgumentException e) {
            choicesOnCurrentAction.put(choiceCode, -1); //means no family member
            throw new IllegalArgumentException(e);
        }

        choicesOnCurrentAction.put(choiceCode, choice.getIntegerValue());
        return choice;
    }


}



