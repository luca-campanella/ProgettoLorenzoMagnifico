package it.polimi.ingsw.client.gui;

/**
 * Created by higla on 11/05/2017.
 */

import it.polimi.ingsw.client.controller.AbstractUIType;
import it.polimi.ingsw.client.controller.ClientMain;
import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.client.gui.fxcontrollers.CustomControllerLeaderChoices;
import it.polimi.ingsw.client.gui.fxcontrollers.CustomFxController;
import it.polimi.ingsw.model.board.AbstractActionSpace;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.cards.VentureCardMilitaryCost;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.PersonalTile;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.MarketWrapper;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.model.resource.TowerWrapper;
import it.polimi.ingsw.utils.Debug;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class GraphicalUI extends AbstractUIType {

    Stage mainStage;
    Stage currentStage;
    CustomFxController currentFXControl;
    /**
     * This is the constructor of the class
     * @param controller is used to make callbacks on the controller ({@link ClientMain}
     */
    public GraphicalUI(ViewControllerCallbackInterface controller)
    {
        super(controller);
        if(controller == null)
            Debug.printVerbose("controller is null in GraphicalUI");
        //guiApplication.launch();
    }

    /**
     * This is the constructor of the class
     * @param controller is used to make callbacks on the controller ({@link ClientMain}
     */
    public GraphicalUI(ViewControllerCallbackInterface controller, Stage mainStage)
    {
        super(controller);
        this.mainStage = mainStage;
        currentStage = new Stage();
    }

    public void selectFamilyMember()
    {
        int i;
        String familyColorID;
        System.out.print("Select a family member. You can choose " );
        /*for(i=0; i< familyMembers.size(); i++)
            System.out.print(familyMembers.... + " ");
        */
        System.out.print("Yellow, Red, Green, Neutral");
        while(true) {
            familyColorID = inputScanner.nextLine();
            if(existingColors(familyColorID))
                break;
        }
        //clientMain.callbackFamilyMemberSelected(familyColorID);
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

    }

    /**
     * this method prints all allowed actions for the user.
     * @param legalActionSpaces
     */
    public void askWhichActionSpace(List<AbstractActionSpace> legalActionSpaces){
        Debug.printDebug("Sono in CLI.printAllowedActions()");
        System.out.println("Stampo tutte le azioni disponibili dell'utente");
    }

    @Override
    public int askChoice(String nameCard, ArrayList<String> choices, HashMap<ResourceTypeEnum, Integer> resourcePlayer) {
        return 0;
    }

    /**
     * This method is called when the player has joined a room, but the game isn't started yet
     */
    @Override
    public void showWaitingForGameStart() {
        
    }

    /**
     * Used when it's the turn of the user and he has to choose which action he wants to perform
     * This method will trigger either
     * {@link ViewControllerCallbackInterface#callbackFamilyMemberSelected(FamilyMember)} (it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum, int)} or
     * //todo other methods triggered
     *
     * @param playableFMs the list of playable family members to make the user choose
     * @param board
     */
    @Override
    public void askInitialAction(ArrayList<FamilyMember> playableFMs, Board board, boolean playedFamilyMember, ArrayList<LeaderCard> leaderCardsNotPlayed,
                                 ArrayList<LeaderCard> playedLeaderCards, Player player) {

    }

    /**
     * This method is called when a choice on a council gift should be perfomed by the ui
     *
     * @param options
     * @return the index of the selected option, the choice the user made
     */
    @Override
    public int askCouncilGift(ArrayList<GainResourceEffect> options) {
        return 0;
    }

    /**
     * This method is called when a choice on which effect to activate in a yellow card should be perfomed by the ui
     *
     * @param possibleEffectChoices
     * @return the index of the chosen effect
     */
    @Override
    public int askYellowBuildingCardEffectChoice(ArrayList<ImmediateEffectInterface> possibleEffectChoices) {
        return 0;
    }

    /**
     * This method is called when a choice on which cost to pay in a purple card should be perfomed by the ui
     *
     * @param costChoiceResource the list of resources the player will pay if he chooses this option
     * @param costChoiceMilitary the cost he will pay on something conditioned
     * @return 0 if he chooses to pay with resources, 1 with military points
     */
    @Override
    public int askPurpleVentureCardCostChoice(List<Resource> costChoiceResource, VentureCardMilitaryCost costChoiceMilitary) {
        //todo
        return 0;
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
        leaderCards.forEach(leader -> ((CustomControllerLeaderChoices) (currentFXControl)).addLeader(leader));
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
        return 0;
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
        return 0;
    }

    @Override
    public int askAddingServants(int minimum, int maximum) {
        return 0;
    }

    /**
     * this method is called when a player pass the phase
     *
     * @param nickname the player that had pass the phase
     */
    @Override
    public void showEndOfPhaseOfPlayer(String nickname) {

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
        return null;
    }

    /**
     * this method just alerts user that there was an error somewhere. It doesn't handle the error
     *
     * @param error
     */
    @Override
    public void printError(String error) {
        //TODO implent
    }

    /**
     * This method is called by {@link ClientMain} to display an incoming chat message (Direction: {@link ClientMain} -> {@link AbstractUIType}; general direction: Server -> Client)
     *
     * @param senderNick
     * @param msg
     */
    @Override
    public void displayChatMsg(String senderNick, String msg) {

    }

    @Override
    public void askChatMsg() {

    }

    //@Override
    public int askChoice(String nameCard, ArrayList<String> choices) {
        return 0;
    }

    /**
     * this method helps selectFamilyMember()'s method return if the color user wrote is right or not
     * this method should also receive the familyMembers list to match the input.
     * @param familyColorID
     * @return
     */
    private boolean existingColors(String familyColorID){
        return (familyColorID.equalsIgnoreCase("yellow")||familyColorID.equalsIgnoreCase("red")||familyColorID.equalsIgnoreCase("green")||familyColorID.equalsIgnoreCase("neutral"));
    }


    String tmpInput;
    Scanner inputScanner = new Scanner(System.in);
    ClientMain clientMain;
    // UIControllerUserInterface UIController = new UIControllerUserInterface();
    public void loginFailure(String reasonFailure)
    {

        //System.out.println("Error: " + reasonFailure)
        ;
        //askLoginOrCreate()
    }

    /**
     * Chiede all'utente con quale connessione si vuole connettere
     */
    public void askNetworkType()
    {
        Debug.printDebug("Sono nella gui. Voglio chedere quale network usare.");

        Platform.runLater(() ->openNewWindow("ConnectionChooserV2.fxml", "Connection Type Choice", null));
    }

    /**
     * This is the method which starts asking the User inputs.
     */
    public void readAction(){
        Debug.printError("Sono nella gui.readAction");
        while(true)
        {
            System.out.println("Quale azione vuoi fare? Giocare un Leader, Scartare un Leader, Piazzare un Familiare ? Scrivi Gioca, Scarta, Piazza");
            tmpInput = inputScanner.nextLine();
            if(tmpInput.equalsIgnoreCase("gioca")){
                clientMain.callbackPlayLeader();
                break;
            }
            if(tmpInput.equalsIgnoreCase("scarta")){
                //clientMain.callbackDiscardLeader();
                break;
            }
            if(tmpInput.equalsIgnoreCase("Piazza")){
                clientMain.callbackPerformPlacement();
                break;
            }

        }
    }

    /**
     * This method asks to the user if he wants to connect with an existing account or to create one.
     */
    public void askLoginOrCreate()
    {
        Debug.printDebug("Sono nella gui. ask login or create.");

        Platform.runLater(() -> openNewWindow("Login.fxml", "Login or register", null));
    }

    //permette all'utente di create un nuovo account
    public void createNewAccount(){
        System.out.println("Creating new Account...");
    }
    //permette all'utente di Loggare
    public void askLogin(){
        System.out.println("Logging In...");
    }
    //aggiorna l'UI
    public void updateView()
    {
        System.out.println("Sono in gui");
    }

    /**
     * This method opens a new window and shows it. It also sets the controller for the callbacks inside the custom fx controller
     * This method shoudl be passed as a parameter to the runLater fx method
     * @param fxmlFileName the fxml to start from
     * @param title the title of the window
     */
    private void openNewWindow(String fxmlFileName, String title, Runnable runBeforeShow) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/"+fxmlFileName));

        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentFXControl = ((CustomFxController) fxmlLoader.getController());

        currentFXControl.setController(getController());

        currentStage.setTitle(title);
        currentStage.setScene(new Scene(root));

        if(runBeforeShow != null) //there is something to run
            runBeforeShow.run();

        currentStage.show();
    }

}

