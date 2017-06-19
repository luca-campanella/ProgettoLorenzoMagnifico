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
        Debug.instance(Debug.LEVEL_VERBOSE);
        StdinSingleton.instance();
        temp = new LauncherClientFake(this);
        userInterface = temp.welcome();
        userInterface.askNetworkType();
        //Questo non penso vada bene in quanto credo debba essere il metodo corrispondente ad istanziare la classe corrispondente --Arto

    }
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
        } catch (NetworkException e) {
            //TODO handle network problems
            e.printStackTrace();
        }
        catch (LoginException e) {
            //TODO handle login problems (call the UI again)
            Debug.printDebug("Login exception occurred", e);
            switch(e.getErrorType()) {
                case ALREADY_LOGGED_TO_ROOM :
                    userInterface.printError("Already logged to room");
                    break;
                case NOT_EXISTING_USERNAME:
                    userInterface.printError("The username you inserted doesn't exists");
                    userInterface.askLoginOrCreate();
                break;
                case WRONG_PASSWORD:
                    userInterface.printError("The password you inserted was wrong");
                    userInterface.askLoginOrCreate();
                    break;
                default:
                    userInterface.printError("Something went wrong.");
                    userInterface.askLoginOrCreate();
                    break;

            }

        }
        this.nickname = userID;
        userInterface.showWaitingForGameStart();

        Debug.printVerbose("Im going to call askChatMsg");
        //userInterface.askChatMsg(); //TODO this is a method just for testing chat
        //userInterface.askInitialAction(); //TODO this is a method just for testing
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
        } catch (NetworkException e) {
            //TODO handle network problems
            e.printStackTrace();
        }
        catch(UsernameAlreadyInUseException e)
        {
            Debug.printDebug(e);
            userInterface.printError("The username you inserted is already in use, please insert a new one");
            userInterface.askLoginOrCreate();
        }
        this.nickname = userID;
        userInterface.showWaitingForGameStart();
    }

    /**
     * this method it's a callback method that is called from the {@link AbstractUIType} when i want to play a Leader
     */
    public void callbackPlayLeader(){
        initialActionsOnPlayerMove();
        Debug.printDebug("I'm in ClientMain.callbackPlayLeader");
    }

    /**
     * this method it's a callback method called from AbstractUIType when i want to discard a Leader.
     */
    public void callbackDiscardLeader(){
        Debug.printDebug("I'm in ClientMain.callbackDiscardLeader");
    }

    /**
     * this method is a callback method called from abstractUIType when a placement of a family member is performed
     */
    public void callbackPerformPlacement(){
        Debug.printDebug("I'm in ClientMain.callbackPerformPlacement");
        //get status... ricevo una lista di family member che posso usare
        userInterface.selectFamilyMember();
    }

    /**
     * this method is a callback method called from abstractUiType when a family member is selected
     * @param selectdFM the family member selected.
     */
    @Override
    public void callbackFamilyMemberSelected(FamilyMember selectdFM)
    {
        Debug.printDebug("Sono nel ClientMain.callbackFamilyMember: color = " + selectdFM.getColor());

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
     * this method will ask to the model Controller what action user can do
     */
    public void askAction(){
        userInterface.readAction();
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
        Debug.printVerbose("build succeded");

        /*LinkedList<BuildingCard> buildingCards = modelController.getYellowBuildingCards(familyMember.getPlayer());

        ArrayList<ImmediateEffectInterface> effects;

        for(BuildingCard cardIter : buildingCards) {
            effects = cardIter.getEffectsOnBuilding();
            if(effects.size() > 1) {

            }

        }*/
        //todo send the action to the server
    }

    /**
     * this method allows player to place a family member on a harvest action space
     * @param servantsUsed the number of servants the user decided to use
     */
    @Override
    public void callbackPlacedFMOnHarvest(int servantsUsed){
        modelController.harvest(familyMemberCurrentAction, servantsUsed);
    }

    /**
     * this method allows player to place a family member on a tower floor action space
     * @param towerIndex the identifier of the tower
     * @param floorIndex the identifier of the floor
     */
    @Override
    public void callbackPlacedFMOnTower(int towerIndex, int floorIndex){
        modelController.placeOnTower(familyMemberCurrentAction, towerIndex, floorIndex);
    }

    /**
     * this method allows player to place a family member on a market action space
     * @param marketASIndex the selected market AS
     */
    @Override
    public void callbackPlacedFMOnMarket(int marketASIndex){
        modelController.placeOnMarket(familyMemberCurrentAction, marketASIndex);
    }

    /**
     * this method allows player to place a family member in the council action space
     */
    @Override
    public void callbackPlacedFMOnCouncil(){
        modelController.placeOnCouncil(familyMemberCurrentAction);
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
    public void receivedStartGameBoard(Board board){
        Debug.printVerbose("receivedStartgameBoard called");

        modelController = new ModelController(players, board);

        //add the coins to the orderOfPlayers based on the order of turn
        modelController.addCoinsStartGame(players);

        //now that we have the board we can give the object the possibile options for a council gift
        otherPlayerChoicesHandler = new NetworkChoicesPacketHandler(modelController.getBoard().getCouncilAS().getCouncilGiftChoices());

        //todo this is just for testing
        //CliPrinter.printBoard(board);
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

        modelController.setFamilyMemberDices();

        ArrayList<FamilyMember> playableFMs = thisPlayer.getNotUsedFamilyMembers();
        for(FamilyMember fmIter : playableFMs) {
            Debug.printVerbose("PLAYABLE FM:" + "Family member of color " + fmIter.getColor() + "of value " + fmIter.getValue());
        }
    }

    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType}
     * to notify that the player is in turn and should move
     */
    public void receivedStartTurnNotification() {
        Debug.printVerbose("receivedStartTurnNotification called");
        ArrayList<FamilyMember> playableFMs = thisPlayer.getNotUsedFamilyMembers();
        for(FamilyMember fmIter : playableFMs) {
            Debug.printVerbose("PLAYABLE FM:" + "Family member of color " + fmIter.getColor() + "of value " + fmIter.getValue());
        }
        //it's this player's turn, he should answer callbacks from model
        modelController.setChoicesController(this);
        userInterface.askInitialAction(playableFMs, modelController.getBoard());
    }

    /**
     * this method is called by {@link it.polimi.ingsw.client.network.AbstractClientType}
     * to notify that the has to pick a leader card between the ones proposed
     * @param leaderCards options
     */
    @Override
    public void receivedLeaderCards(List<LeaderCard> leaderCards) {
        userInterface.askLeaderCards(leaderCards);
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
    }

    /**
     * this method is called by the view to communicate the leader choice
     * @param leaderCardChoice the choice made
     */
    @Override
    public void callbackOnLeaderCardChosen(LeaderCard leaderCardChoice) {
        Debug.printVerbose("callbackOnLeaderCardChosen Called");
        try {
            clientNetwork.deliverLeaderChose(leaderCardChoice);
        } catch (NetworkException e) {
            //todo handle better
            Debug.printError("Cannot send leader choice", e);
            userInterface.printError("Cannot contact the server, exiting the program");
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
        userInterface.askPersonalTiles(standardTile, specialTile);
    }


    /**
     * this method is called by the view to communicate the personal tile choice
     * @param tileType the choice made
     */
    @Override
    public void callbackOnPersonalTileChosen(PersonalTileEnum tileType) {
        /*try {
            //todo methods for network
            clientNetwork.deliverLeaderChose(leaderCardChoice);
        } catch (NetworkException e) {
            //todo handle better
            Debug.printError("Cannot send leader choice", e);
            userInterface.printError("Cannot contact the server, exiting the program");
            System.exit(0);
        }*/
    }

    @Override
    public void callBackPassTheTurn() {
        try{
            clientNetwork.endPhase();
        }
        catch (IOException e){
            Debug.printError("Can deliver the end of the phase",e);
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
        otherPlayerChoicesHandler.setChoicesMap(playerChoices);
        modelController.setChoicesController(otherPlayerChoicesHandler);
        modelController.placeOnTower(player.getFamilyMemberByColor(familyMemberColor), towerIndex, floorIndex);
        //todo show something in the view
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
        modelController.placeOnMarket(player.getFamilyMemberByColor(familyMemberColor), marketIndex);
        //todo show something in the view
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
        otherPlayerChoicesHandler.setChoicesMap(playerChoices);
        modelController.setChoicesController(otherPlayerChoicesHandler);
        modelController.harvest(player.getFamilyMemberByColor(familyMemberColor), servantsUsed);
        //todo show something in the view
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
        otherPlayerChoicesHandler.setChoicesMap(playerChoices);
        modelController.setChoicesController(otherPlayerChoicesHandler);
        modelController.build(player.getFamilyMemberByColor(familyMemberColor), servantsUsed);
        //todo show something in the view
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
        otherPlayerChoicesHandler.setChoicesMap(playerChoices);
        modelController.setChoicesController(otherPlayerChoicesHandler);
        modelController.placeOnCouncil(player.getFamilyMemberByColor(familyMemberColor));
        //todo show something in the view
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

        int choice = userInterface.askWhichLeaderabilityToCopy(possibleLeaders);

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

        if (choice == 0)
            return true;
        return false;
    }

    /**
     * this method is used to receive the end phase of other players
     * @param nickname the player that had pass
     */
    @Override
    public void receiveEndPhase(String nickname) {

        userInterface.showEndOfPhaseOfPlayer(nickname);

    }
}



