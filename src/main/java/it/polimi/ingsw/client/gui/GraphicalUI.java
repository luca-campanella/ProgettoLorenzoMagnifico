package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.controller.AbstractUIType;
import it.polimi.ingsw.client.controller.ClientMain;
import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.client.gui.blockingdialogs.*;
import it.polimi.ingsw.client.gui.fxcontrollers.*;
import it.polimi.ingsw.client.network.socket.packet.PlayerPositionEndGamePacket;
import it.polimi.ingsw.model.cards.VentureCardMilitaryCost;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.PersonalTile;
import it.polimi.ingsw.model.resource.MarketWrapper;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.TowerWrapper;
import it.polimi.ingsw.utils.Debug;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This object represents the implementation of the user interface via graphical user interface
 * Uses java fx
 */
public class GraphicalUI extends AbstractUIType {

    private Stage currentStage;
    private CustomFxControl currentFXControl;
    private volatile SceneEnum currentSceneType;

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final String WAITING_SCENE_FXML = "WaitingScene.fxml";

    private static final String ERROR_IN_OPENING_DIALOG_MESSAGE = "Error in opening dialogue, default value instead";

    /**
     * This is the constructor of the class
     * @param controller is used to make callbacks on the controller ({@link ClientMain}
     */
    public GraphicalUI(ViewControllerCallbackInterface controller)
    {
        super(controller);
        currentStage = new Stage();
    }

    /**
     * This method asks the user to pick one of the action spaces to put his family member in
     * Direction: {@link ClientMain} -> {@link AbstractUIType}
     *
     * @param servantsNeededHarvest The servants needed by the user to harvest, Optional.empty() if the action is not valid
     * @param servantsNeededBuild   The servants needed by the user to build, Optional.empty() if the action is not valid
     * @param servantsNeededCouncil The servants needed by the user to place on cuincil, Optional.empty() if the action is not valid
     * @param activeMarketSpaces    The list of legal action spaces in the market
     * @param activeTowerSpaces     the list of legal action spaces on the towers
     * @param availableServants     the number of servants the user can spend to perform the action
     */
    @Override
    public void askWhichActionSpace(Optional<Integer> servantsNeededHarvest, Optional<Integer> servantsNeededBuild, Optional<Integer> servantsNeededCouncil, List<MarketWrapper> activeMarketSpaces, List<TowerWrapper> activeTowerSpaces, int availableServants) {
        Debug.printVerbose("askWhichActionSpace called");
        MainBoardControl control = (MainBoardControl) (currentFXControl);
        Platform.runLater(() -> control.displayActiveActionSpaces(servantsNeededHarvest, servantsNeededBuild, servantsNeededCouncil, activeMarketSpaces, activeTowerSpaces));
    }



    /**
     * This method is called when the player has joined a room, but the game isn't started yet
     */
    @Override
    public void showWaitingForGameStart() {
        currentSceneType = SceneEnum.WAITING_SCENE;
        Debug.printDebug("GUI: whow waiting for game start");

        Platform.runLater(() -> this.openNewWindow(WAITING_SCENE_FXML, "Waiting for game to start",
                () -> this.prepareWaitingScene("Room joined succesfully, waiting for other players to join or for timeout...")));

    }

    /**
     * This method is called by the controller after a leader is selected and the player has to wait for enemies to choose their
     */
    @Override
    public void showWaitingForLeaderChoices() {
        currentSceneType = SceneEnum.WAITING_SCENE;
        Debug.printDebug("GUI: whow waiting for another leader choice");

        Platform.runLater(() -> openNewWindow(WAITING_SCENE_FXML, "Waiting for leader choices",
                () -> this.prepareWaitingScene("Leader chose, waiting for other players to choose...")));
    }

    /**
     * This method is called by the controller after a personal tile is selected and the player
     * has to wait for enemies to choose their
     */
    @Override
    public synchronized void showWaitingForTilesChoices() {
        //sometimes happens the other thread arrives first, no need to show anything then
        if(currentSceneType != SceneEnum.MAIN_BOARD) {
            currentSceneType = SceneEnum.WAITING_SCENE;
            Debug.printDebug("GUI: show waiting for tile choice");

            Platform.runLater(() -> openNewWindow(WAITING_SCENE_FXML, "Waiting for tiles choices",
                    () -> this.prepareWaitingScene("Personal tile chose, waiting for other players to choose...")));
        }
    }

    /**
     * Used when it's the turn of the user and he has to choose which action he wants to perform
     * This method will trigger either
     * {@link ViewControllerCallbackInterface#callbackFamilyMemberSelected(FamilyMember)} (it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum, int)} or
     *
     */
    @Override
    public synchronized void askInitialAction(boolean playedFamilyMember) {
        Debug.printVerbose("askInitialAction called with currentSceneType = " + currentSceneType);
        StringBuilder textToDisplay = new StringBuilder("It's your turn, ");

        if(!playedFamilyMember) {
            textToDisplay.append("please select a family member and then an action space or ");
        }
        else {
            textToDisplay.append("you already played your family member, please ");
        }
        textToDisplay.append("make a leader action or pass the turn");

        if(currentSceneType != SceneEnum.MAIN_BOARD) {
            Platform.runLater(() -> openNewWindow("MainBoardScene.fxml", "Main game",
                    () -> setUpMainBoardControl(textToDisplay.toString(), true)));
            currentSceneType = SceneEnum.MAIN_BOARD;
            getController().setBoardNeedsToBeRefreshed(false);
        } else {
            Platform.runLater(() -> {
            if(getController().callbackObtainBoardNeedsToBeRefreshed()) {
                updateViewForNewRound();
                getController().setBoardNeedsToBeRefreshed(false);
            }

                MainBoardControl control = (MainBoardControl) (currentFXControl);
                control.appendMessageOnStateTextArea(textToDisplay.toString());
                control.disableAllActionsNotHisTurn(false);
                control.setFamilyMemberDisable(playedFamilyMember);
                control.refreshPersonalBoardOfPlayer(getController().callbackObtainPlayer().getNickname());
            });
        }
    }

    /**
     * Update all the view for the new round
     */
    private void updateViewForNewRound() {
        MainBoardControl control = (MainBoardControl) (currentFXControl);
            control.displayDices();
            control.displayCards();
            control.displayFamilyMembers();
            control.refreshFaithTrack();
            control.prepareForNewRound();
            control.displayOrderOfPlayers(getController().callbackObtainPlayersInOrder());
            control.appendMessageOnStateTextArea("**NEW ROUND**");
    }

    /**
     * performs all the action on the {@link MainBoardControl} in order to display the initial board
     * @param message the initial message to show the user
     * @param isHisTurn true if it's player's turn, false if it is not
     */
    private void setUpMainBoardControl(String message, boolean isHisTurn) {
        MainBoardControl control = (MainBoardControl) (currentFXControl);

        control.setBoard(getController().callbackObtainBoard());
        control.displayCards();
        control.setUpExcommTiles();
        control.setThisPlayer(getController().callbackObtainPlayer());
        control.setOtherPlayers(getController().callbackObtainOtherPlayers());
        control.displayOrderOfPlayers(getController().callbackObtainPlayersInOrder());
        control.setUpFaithCylinders(getController().callbackObtainPlayersInOrder());
        control.setDices(getController().callbackObtainDices());
        control.displayDices();
        control.displayFamilyMembers();
        control.setUpPlayersPersonalBoards();
        control.setUpNumberOfPlayers(1+ getController().callbackObtainOtherPlayers().size());
        control.appendMessageOnStateTextArea(message);
        if(!isHisTurn)
            control.disableAllActionsNotHisTurn(true);

        currentFXControl = control;
    }


    /**
     * This method is called when a choice on a council gift should be perfomed by the ui
     *
     * @param options is a list of all options
     * @return the index of the selected option, the choice the user made
     */
    @Override
    public int askCouncilGift(List<GainResourceEffect> options) {
        Debug.printVerbose("Im in askCouncilGiftGUI0");
        FutureTask<Integer> futureTask = new FutureTask(new AskChoiceOnEffectDialog(options, "council gift"));
        Platform.runLater(futureTask);
        Debug.printVerbose("Im in askCouncilGiftGUI1");
        int choice = 0;
        try {
            Debug.printVerbose("Im in askCouncilGiftGUIInside try");
            choice = futureTask.get();
            Debug.printVerbose("Got council choice from GUI: " + choice);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.SEVERE, "Error in opening dialogue", e);
            this.displayError("Error in opening dialogue, default value", e.getMessage());
        }
        Debug.printVerbose("Im in askCouncilGiftGUIEnd");
        return choice;
    }

    /**
     * This method is called when a choice on which effect to activate in a yellow card should be perfomed by the ui
     *
     * @param possibleEffectChoices is a list of all options available
     * @return the index of the chosen effect
     */
    @Override
    public int askYellowBuildingCardEffectChoice(List<ImmediateEffectInterface> possibleEffectChoices) {
        FutureTask<Integer> futureTask = new FutureTask(new AskChoiceOnEffectDialog(possibleEffectChoices, " building "));
        Platform.runLater(futureTask);
        Debug.printVerbose("Building... askYellowBuildingCardEffectChoice");
        int choice = 0;
        try {
            choice = futureTask.get();
            Debug.printVerbose("Got building choice from GUI: " + choice);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.SEVERE, ERROR_IN_OPENING_DIALOG_MESSAGE, e);
            this.displayError(ERROR_IN_OPENING_DIALOG_MESSAGE, e.getMessage());
        }
        return choice;

    }

    /**
     * This method is called when a choice on which cost to pay in a purple card should be perfomed by the ui
     * Tested and currently working
     * @param costChoiceResource the list of resources the player will pay if he chooses this option
     * @param costChoiceMilitary the cost he will pay on something conditioned
     * @return 0 if he chooses to pay with resources, 1 with military points
     */
    @Override
    public int askPurpleVentureCardCostChoice(List<Resource> costChoiceResource, VentureCardMilitaryCost costChoiceMilitary) {
        FutureTask<Integer> futureTask = new FutureTask(new AskPurpleCardCostDialog(costChoiceResource, costChoiceMilitary));
        Platform.runLater(futureTask);

        int choice = 0;
        try {
            choice = futureTask.get();
            Debug.printVerbose("Got purpleCost choice from GUI: " + choice);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.SEVERE, ERROR_IN_OPENING_DIALOG_MESSAGE + " askPurpleVentureCardCostChoice", e);
            this.displayError(ERROR_IN_OPENING_DIALOG_MESSAGE, e.getMessage());
        }
        return choice;
    }

    /**
     * Asks what card to take when he has a free action due to a card that has such effect
     * @param towerWrapper the list of available spaces
     * @return the index of the choice
     */
    @Override
    public int askWhereToPlaceNoDiceFamilyMember(List<TowerWrapper> towerWrapper){
        FutureTask<Integer> futureTask = new FutureTask(new AskWhereToPlaceNoDiceFamilyMember(towerWrapper));
        Platform.runLater(futureTask);
        Debug.printVerbose("Inside ask...");

       int choice = 0;
       try{
           choice = futureTask.get();
           Debug.printVerbose("Got whereToPlaceDiceNoFamilyMember " + choice);
       } catch (InterruptedException | ExecutionException e) {
           LOGGER.log(Level.SEVERE, ERROR_IN_OPENING_DIALOG_MESSAGE + " askWhereToPlaceNoDiceFamilyMember", e);
           this.displayError(ERROR_IN_OPENING_DIALOG_MESSAGE, e.getMessage());
       }
       Debug.printVerbose("number of " + choice);
        ((MainBoardControl)(currentFXControl)).removeCardFromView(towerWrapper.get(choice).getTowerIndex(), towerWrapper.get(choice).getTowerFloor());
       return choice;
    }

    /**
     * This method is called at the beginning of the game to choose one leader card
     * This method should be non-blocking
     *
     * @param leaderCards the list of resources the player will pay if he chooses this option
     */
    @Override
    public void askLeaderCards(List<LeaderCard> leaderCards) {

        Debug.printDebug("GUI: ask leader cards");

        Platform.runLater(() -> openNewWindow("LeaderPickerScene.fxml", "Choose a leader", () -> this.setLeadersToWindow(leaderCards)));

    }

    private void setLeadersToWindow(List<LeaderCard> leaderCards) {
        leaderCards.forEach(leader -> ((LeaderPickerControl) (currentFXControl)).addLeader(leader));
    }


    /**
     * This method is called at the beginning of the game to choose one personal tile
     * This method should be non-blocking
     *
     * @param standardTile option1
     * @param specialTile  option2
     */
    @Override
    public void askPersonalTiles(PersonalTile standardTile, PersonalTile specialTile) {
        Debug.printDebug("GUI: ask personal tiles");

        Platform.runLater(() -> openNewWindow("PersonalTilesPickerScene.fxml", "Choose a personal tile",
                () -> ((PersonalTilesPickerControl) (currentFXControl)).addTiles(standardTile, specialTile)));

    }

    /**
     * This method is called when a player chooses to play a ledear with a COPY ability and he should be asked to choose
     * This method should be blocking
     *
     * @param possibleLeaders the possibilites to choose from
     * @return the index of the choice
     */
    @Override
    public int askWhichLeaderAbilityToCopy(List<LeaderCard> possibleLeaders) {

        FutureTask<Integer> futureTask = new FutureTask(new AskWhichLeaderAbilityToCopyDialog(possibleLeaders, currentStage));
        Platform.runLater(futureTask);
        Debug.printVerbose("Hello, someone activated Lorenzo Il Magnifico and im inside the callBack");
        int choice = 0;

        try {
            choice = futureTask.get();
            Debug.printVerbose("Copying a leader choice from GUI: " + choice);
        } catch (InterruptedException | ExecutionException e) {
            String displayThis = "Error in opening dialogue, default value instead, called from askAddingServants";
            this.displayError(displayThis, e.getMessage());
            LOGGER.log(Level.SEVERE, "Error in opening dialogue askWhichLeaderAbilityToCopy", e);

        }

        return choice;
    }

    /**
     * This method is called when the player it is playing a leader who has a ONCE_PER_ROUND ability
     * to ask the user if he also wants to activate the ability
     * This method should be blocking
     *
     * @return true if he also wants to activate, false otherwise
     */
    @Override
    public int askAlsoActivateLeaderCard() {

        FutureTask<Integer> futureTask = new FutureTask(new AskAlsoActivateLeaderCardDialog());
        Platform.runLater(futureTask);

        int choice = 0;

        try {
            choice = futureTask.get();
            Debug.printVerbose("Activating or not effect choice from GUI: " + choice);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.SEVERE, "Error in opening dialogue askAlsoActivateLeaderCard", e);
            this.displayError("Error in opening dialogue, default value instead, called from askAddingServants", e.getMessage());
        }

        return choice;
    }

    @Override
    public int askAddingServants(int minimum, int maximum) {
        Debug.printVerbose("I'm in askAddingServants");
        FutureTask<Integer> futureTask = new FutureTask(new AskMoreServantsDialog(minimum, maximum));
        Platform.runLater(futureTask);
        Debug.printVerbose("I'm in askAddingServants2");
        int choice = 0;
        try {
            choice = futureTask.get();
            Debug.printVerbose("Got more servants choice from GUI: " + choice);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.SEVERE, "Error in opening dialogue askAddingServants", e);
            this.displayError("Error in opening dialogue, default value instead, called from askAddingServants, perchè sei qui?", e.getMessage());
        }
        return choice;

    }

    /**
     * This method is called when the player activate a leader with a once per round ability that modifies
     * the value of one of his colored family members, he has to choose which one
     *
     * @param availableFamilyMembers the list of available family member, it's useless to modify
     *                               the value of a family member already played
     * @return the color of the family member he chose
     * @throws IllegalArgumentException if the list is empty
     */
    @Override
    public DiceAndFamilyMemberColorEnum askWhichFamilyMemberBonus(List<FamilyMember> availableFamilyMembers) throws IllegalArgumentException {
        if(availableFamilyMembers.isEmpty()) {
            throw new IllegalArgumentException("the list is empty");
        }
        FutureTask<DiceAndFamilyMemberColorEnum> futureTask = new FutureTask(new AskWhichFMBonusDialog(availableFamilyMembers));
        Platform.runLater(futureTask);

        DiceAndFamilyMemberColorEnum choice = null;
        try {
            choice = futureTask.get();
            Debug.printVerbose("Got DiceAndFamilyMember choice from GUI: " + choice);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.SEVERE, ERROR_IN_OPENING_DIALOG_MESSAGE + " askWhichFamilyMemberBonus", e);

            this.displayError(ERROR_IN_OPENING_DIALOG_MESSAGE, e.getMessage());
        }
        return choice;
    }

    /**
     * Shows the main board, but it's not his turn
     */
    @Override
    public void waitMenu() {
        Debug.printVerbose("waitMenu called with currentSceneType = " + currentSceneType);
        String message = "Opponents are currently playing, please wait your turn";
        if(currentSceneType != SceneEnum.MAIN_BOARD) {
            Platform.runLater(() -> openNewWindow("MainBoardScene.fxml", "Main game",
                    () -> setUpMainBoardControl(message, false)));
            currentSceneType = SceneEnum.MAIN_BOARD;
            getController().setBoardNeedsToBeRefreshed(false);
        } else {
            Platform.runLater(() -> {
                MainBoardControl control = (MainBoardControl) (currentFXControl);
            if(getController().callbackObtainBoardNeedsToBeRefreshed()) {
                updateViewForNewRound();
                control.refreshPersonalBoardOfPlayer(getController().callbackObtainPlayer().getNickname());
                getController().setBoardNeedsToBeRefreshed(false);
            }
                control.appendMessageOnStateTextArea(message);
                control.disableAllActionsNotHisTurn(true);
            });
        }
    }

    /**
     * this method is called when the game is ended
     * @param playerPositionEndGamePacket the packet with all the information of the end of the game
     */
    @Override
    public void showEndOfGame(List<PlayerPositionEndGamePacket> playerPositionEndGamePacket) {
        Platform.runLater(() -> showEndOfGameDialog(playerPositionEndGamePacket));
    }

    private void showEndOfGameDialog(List<PlayerPositionEndGamePacket> playerPositionEndGamePacket) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("End game");
        alert.setHeaderText("The game has ended");

        StringBuilder stringBuilder = new StringBuilder();
        Debug.printVerbose("Show end game");

            if(playerPositionEndGamePacket.get(0).getNickname().equals(getController().callbackObtainPlayer().getNickname()))
                stringBuilder.append("***YOU WON*** :D");
            else
                stringBuilder.append("*YOU LOST* :(");
        stringBuilder.append("Here are the final standings");

        for(int i = 1 ; i <= playerPositionEndGamePacket.size(); i++){
            for(PlayerPositionEndGamePacket playerIter : playerPositionEndGamePacket){
                if(playerIter.getPosition() == i) {
                    stringBuilder.append(playerIter.getPosition() + " position: " + playerIter.getNickname()
                            + " " + playerIter.getVictoryPoints() + " Victory Points");
                }
            }
        }

        alert.setContentText(stringBuilder.toString());
        alert.initOwner(currentStage);
        alert.show();
    }


    /**
     * this method just alerts user that there was an error somewhere. It doesn't handle the error
     * @param title the title of the error
     * @param errorDescription the description of the error
     */
    @Override
    public void displayError(String title, String errorDescription) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(title);
            alert.setContentText(errorDescription);
            alert.initOwner(currentStage);
            alert.show();
        });
    }

    /**
     * this method alerts user that there was an error somewhere. It doesn't handle the error
     * The error is a fatal one and after the user clicked ok the program terminates     *
     * @param title the title of the error
     * @param errorDescription the description of the error
     */
    @Override
    public void displayErrorAndExit(String title, String errorDescription) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(title);
            alert.setContentText(errorDescription);
            alert.initOwner(currentStage);
            alert.showAndWait();
            System.exit(0);
        });
    }

    /**
     * This method is called by {@link ClientMain} to display an incoming chat message (Direction: {@link ClientMain} -> {@link AbstractUIType}; general direction: Server -> Client)
     *
     * @param senderNick is the nick of the person who sent the msg
     * @param msg is the txt of the mesessage
     */
    @Override
    public void displayChatMsg(String senderNick, String msg) {
        /*
        for future implementation
         */
    }


    /**
     * Asks the user which connection mode he wants to use
     */
    @Override
    public void askNetworkType()
    {

        currentSceneType = SceneEnum.CONNECTION_CHOICE;

        Platform.runLater(() ->openNewWindow("ConnectionChooserV2.fxml", "Connection Type Choice", null));
    }

    /**
     * This method asks to the user if he wants to connect with an existing account or to create one.
     */
    @Override
    public void askLoginOrCreate()
    {
        Debug.printDebug("Sono nella gui. ask login or create.");
        if(currentSceneType != SceneEnum.LOGIN_REGISTER) {
            Platform.runLater(() -> openNewWindow("LoginRegisterScene.fxml", "Login or register",
                    () -> ((LoginRegisterControl) (currentFXControl)).setStage(currentStage)));
            currentSceneType = SceneEnum.LOGIN_REGISTER;
        }
    }

    /**
     * This method opens a new window and shows it. It also sets the controller for the callbacks inside the custom fx controller
     * This method shoudl be passed as a parameter to the runLater fx method
     * @param fxmlFileName the fxml to start from
     * @param title the title of the window
     */
    public synchronized void openNewWindow(String fxmlFileName, String title, Runnable runBeforeShow) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/"+fxmlFileName));
        Debug.printVerbose(getClass().getResource("/"+fxmlFileName).toString());
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            Debug.printError("Error in loading fxml", e);
            LOGGER.log(Level.SEVERE, "Error in opening dialogue openNewWindow", e);

            displayErrorAndExit("Fatal error", "Error message: " + e.getMessage());
        }

        currentFXControl = (CustomFxControl) fxmlLoader.getController();

        currentFXControl.setController(getController());
        if(runBeforeShow != null) //there is something to run
            runBeforeShow.run();

        currentStage.setTitle(title);
        //currentStage.setScene(new Scene(root, -1, -1, true, SceneAntialiasing.BALANCED)); antialiasing was bugged
        currentStage.setScene(new Scene(root));
        currentStage.setResizable(false);

        currentStage.show();
    }

    private void prepareWaitingScene(String message) {
        ((WaitingSceneControl) (currentFXControl)).setMessage(message);
    }


    /**
     * This method is used by the controller when it receives a place on tower from another player and wants
     * to notify the user that such a move has happened
     * @param fm         the family member used for the move
     * @param towerIndex the index of the tower
     * @param floorIndex the index of the floor AS
     */
    @Override
    public void notifyPlaceOnTower(FamilyMember fm, int towerIndex, int floorIndex) {
        Platform.runLater( () ->
        ((MainBoardControl) (currentFXControl)).notifyPlaceOnTower(fm, towerIndex, floorIndex));
    }

    /**
     * Tells the view to remove a card
     * @param towerWrapper contains the coordinates of the card
     */
    @Override
    public void removeCard(TowerWrapper towerWrapper) {
        Platform.runLater( () -> ((MainBoardControl)(currentFXControl))
                .removeCardFromView(towerWrapper.getTowerFloor(),
                        towerWrapper.getTowerIndex()));
    }

    /**
     * This method is used by the controller when it receives a place on market from another player and wants
     * to notify the user that such a move has happened
     * @param fm the family member used for the move
     * @param marketIndex the index of the market as
     */
    @Override
    public void notifyPlaceOnMarket(FamilyMember fm, int marketIndex) {
        Platform.runLater( () ->
                ((MainBoardControl) (currentFXControl)).notifyPlaceOnMarket(fm, marketIndex));
    }

    /**
     * This method is used by the controller when it receives a place on harvest from another player and wants
     * to notify the user that such a move has happened
     * @param fm the family member used for the move
     * @param servantsUsed the number of servants used for the action
     */
    @Override
    public  void notifyPlaceOnHarvest(FamilyMember fm, int servantsUsed) {
        Platform.runLater( () ->
                ((MainBoardControl) (currentFXControl)).notifyPlaceOnHarvest(fm, servantsUsed));
    }

    /**
     * This method is used by the controller when it receives a place on build from another player and wants
     * to notify the user that such a move has happened
     * @param fm the family member used for the move
     * @param servantsUsed the number of servants used for the action
     */
    @Override
    public  void notifyPlaceOnBuild(FamilyMember fm, int servantsUsed) {
        Platform.runLater( () ->
                ((MainBoardControl) (currentFXControl)).notifyPlaceOnBuild(fm, servantsUsed));
    }

    /**
     * This method is used by the controller when it receives a place on the council from another player and wants
     * to notify the user that such a move has happened
     * @param fm the family member used for the move
     */
    @Override
    public void notifyPlaceOnCouncil(FamilyMember fm) {
        Platform.runLater( () ->
                ((MainBoardControl) (currentFXControl)).notifyPlaceOnCouncil(fm));
    }

    /**
     * this method is called when a player pass the phase
     *
     * @param nickname the player that had pass the phase
     */
    @Override
    public void notifyEndOfPhaseOfPlayer(String nickname) {
        Platform.runLater(() ->
                ((MainBoardControl) (currentFXControl)).notifyEndOfPhaseOfPlayer(nickname));
    }

    /**
     * This method is used by the controller when it receives a discard leader action from another player and wants
     * to notify the user that such a move has happened
     * @param nickname the name of the palyer making the move
     * @param nameCard the name of the leader card involved in the action
     */
    @Override
    public void notifyDiscardLeaderCard(String nickname, String nameCard) {
        Platform.runLater(() ->
                ((MainBoardControl) (currentFXControl)).notifyDiscardLeaderCard(nickname, nameCard));
    }

    /**
     * This method is used by the controller when it receives a play leader action from another player and wants
     * to notify the user that such a move has happened
     * @param nickname the name of the palyer making the move
     * @param nameCard the name of the leader card involved in the action
     */
    @Override
    public void notifyPlayLeaderCard(String nickname, String nameCard) {
        Platform.runLater(() ->
                ((MainBoardControl) (currentFXControl)).notifyPlayLeaderCard(nickname, nameCard));
    }

    /**
     * This method is used by the controller when it receives a activate leader action from another player and wants
     * to notify the user that such a move has happened
     * @param nickname the name of the palyer making the move
     * @param nameCard the name of the leader card involved in the action
     */
    @Override
    public void notifyActivateLeaderCard(String nickname, String nameCard) {
        Platform.runLater(() ->
                ((MainBoardControl) (currentFXControl)).notifyActivateLeaderCard(nickname, nameCard));
    }

    /**
     * this method is called by the client to show the player excommunicated on the gui
     * @param playersExcommunicated the nickname of the players excommunicated
     */
    @Override
    public void displayExcommunicationPlayers(List<String> playersExcommunicated) {
        Platform.runLater(() ->
                ((MainBoardControl) (currentFXControl)).displayExcommunicationPlayers(playersExcommunicated));
    }

    /**
     * this method is called by the client to ask the client if he wants to be excommunicated on the gui
     */
    @Override
    public void askExcommunicationChoice(int numTile) {
         Platform.runLater(() ->
                ((MainBoardControl) (currentFXControl)).askExcommunicationChoice(numTile));
    }


    /**
     * This method is called by controller to signal that another player was suspende due to timeout
     * @param nickname the nick of the player suspended
     */
    @Override
    public void notifyAnotherPlayerSuspended(String nickname) {
        Platform.runLater(() ->
                ((MainBoardControl) (currentFXControl)).appendMessageOnStateTextArea(
                        "[" + nickname + "] --> has been suspended from the game, the server will " +
                                "automatically pass for him until he reconnects"));
    }

    /**
     * This method is called by controller to signal that this player was suspended due to timeout
     */
    @Override
    public void notifyThisPlayerSuspended() {

        Platform.runLater(() -> {
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10));

            Text text = new Text("You have been suspended from the game, the server will " +
                    "automatically pass for you until you reconnect. To do so, please click ok");
            grid.add(text, 0, 0);

            Button bnOK = new Button("OK");
            grid.add(bnOK, 2, 2);

            Scene dialog = new Scene(grid);

            Stage stage = new Stage();
            stage.setScene(dialog);

            bnOK.setOnAction(e -> {
                stage.close();
                new Thread(() -> getController().callbackConnectPlayerAgain()).start();
            });

            stage.show();
            stage.toFront();
        });
    }

    /**
     * This method is called by controller to signal that another player was disconnected due to network problems
     * @param nickname the nick of the player disconnected
     */
    @Override
    public void notifyAnotherPlayerDisconnected(String nickname) {
        Platform.runLater(() ->
                ((MainBoardControl) (currentFXControl)).appendMessageOnStateTextArea(
                        "[" + nickname + "] --> has been removed from the game due to a netowrk problem," +
                                " the server will " +
                                "automatically pass for him until the end of the game"));
    }

    /**
     * This method is called by controller to signal that another player has reconnected after suspension
     * @param nickname the nick of the player reconnected
     */
    @Override
    public void notifyPlayerReconnected(String nickname) {
        Platform.runLater(() ->
                ((MainBoardControl) (currentFXControl)).appendMessageOnStateTextArea(
                        "[" + nickname + "] --> was suspended, now he reconnected"));
    }
}

