package it.polimi.ingsw.client.GUITester;

import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.client.exceptions.NetworkException;
import it.polimi.ingsw.client.gui.GraphicalUI;
import it.polimi.ingsw.client.network.NetworkTypeEnum;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.leaders.leadersabilities.ImmediateLeaderAbility.OncePerRoundHarvestLeaderAbility;
import it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility.BonusNeutralFMLeaderAbility;
import it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility.CanPlaceFMInOccupiedASLeaderAbility;
import it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility.NotToSpendForOccupiedTowerLeaderAbility;
import it.polimi.ingsw.model.leaders.requirements.AbstractRequirement;
import it.polimi.ingsw.model.leaders.requirements.CardRequirement;
import it.polimi.ingsw.model.leaders.requirements.ResourceRequirement;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.PersonalTile;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.server.JSONLoader;
import it.polimi.ingsw.utils.Debug;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Just a class to tests some of the GUI windows
 */
public class GUIWindowsTester extends Application implements ViewControllerCallbackInterface {

    GraphicalUI gui;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Debug.instance(Debug.LEVEL_VERBOSE);

        gui = new GraphicalUI(this);

        testLeaders();
        //testWaitingMenu();
        //testCards();
        //testMainBoard();
    }

    public static void main(String args[]) {
        launch(args);
    }

    private void testMainBoard() {
        gui.askInitialAction(false);
    }

    private void testWaitingMenu() {
        gui.showWaitingForGameStart();
    }

    private void testCards() {
        List<AbstractCard> cards;
        Deck cardsDeck = null;
        try {
            cardsDeck = JSONLoader.createNewDeck();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cards = cardsDeck.getRandomCards(1).subList(0,4);
        gui.showCards(cards);
    }

    private static ArrayList<AbstractRequirement> createOneReqArray(AbstractRequirement req) {
        ArrayList<AbstractRequirement> reqs = new ArrayList<AbstractRequirement>(1);
        reqs.add(req);

        return reqs;
    }

    private static ArrayList<AbstractRequirement> createTwoReqArray(AbstractRequirement req1, AbstractRequirement req2) {
        ArrayList<AbstractRequirement> reqs = new ArrayList<AbstractRequirement>(2);
        reqs.add(req1);
        reqs.add(req2);

        return reqs;
    }

    private void testLeaders() {
        ArrayList<LeaderCard> leadersMock = new ArrayList<LeaderCard>(4);
       leadersMock.add(new LeaderCard(createOneReqArray(new CardRequirement(5, CardColorEnum.PURPLE)),
                "Francesco Sforza",
                "E per dirlo ad un tratto non ci fu guerra famosa nell’Italia, che Francesco Sforza non vi " +
                        "si trovasse, e le Repubbliche, Prencipi, Re e Papi andavano a gara per haverlo al suo sevigio.",
                new OncePerRoundHarvestLeaderAbility(1), "leaders_f_c_01.jpg"));

        leadersMock.add(new LeaderCard(createOneReqArray(new CardRequirement(5, CardColorEnum.BLUE)),
                "Ludovico Ariosto",
                "Io desidero intendere da voi Alessandro fratel, compar mio Bagno, S’in la Cort’è memoria " +
                        "più di noi; Se più il Signor m’accusa; se compagno Per me si lieva.",
                new CanPlaceFMInOccupiedASLeaderAbility(), "leaders_f_c_02.jpg"));

        leadersMock.add(new LeaderCard(createOneReqArray(new CardRequirement(5, CardColorEnum.YELLOW)),
                "Filippo Brunelleschi",
                "[...] sparuto de la persona [...], ma di ingegno tanto elevato che ben si può dire che e’" +
                        "ci fu donato dal cielo per dar nuova forma alla architettura.",
                new NotToSpendForOccupiedTowerLeaderAbility(), "leaders_f_c_03.jpg"));

        leadersMock.add(new LeaderCard(createTwoReqArray(
                new ResourceRequirement(new Resource(ResourceTypeEnum.MILITARY_POINT, 7)),
                new ResourceRequirement(new Resource(ResourceTypeEnum.FAITH_POINT,3))),
                "Sigismondo Malatesta",
                "Era a campo la maistà del re de Ragona. [...] el fé levare de campo cum la soe gente e" +
                        "cum lo altre di fiorentini, cum gram danno e poco onore del re.",
                new BonusNeutralFMLeaderAbility(3), "leaders_f_c_14.jpg"));

        gui.askLeaderCards(leadersMock);
    }

    /**
     * This method is used to make a callback from view to model when the user chooses the network type
     *
     * @param networkChoice the network type chosen by the user
     */
    @Override
    public void callbackNetworkType(NetworkTypeEnum networkChoice) {

    }

    /**
     * this method is called when a user is trying to login.
     *
     * @param userID the username
     * @param userPW the password
     */
    @Override
    public void callbackLogin(String userID, String userPW) {

    }

    /**
     * Called by the UI when the user wants to create a new account to connect to the server
     *
     * @param userID the username
     * @param userPW the password
     */
    @Override
    public void callbackCreateAccount(String userID, String userPW) {

    }

    /**
     * this method is a callback method called from abstractUiType when a family member is selected
     *
     * @param selectdFM the family member selected.
     */
    @Override
    public void callbackFamilyMemberSelected(FamilyMember selectdFM) {

    }

    /**
     * this method allows player to place a family member on a build action space
     *
     * @param servantsUsed the number of servants the user decided to use
     */
    @Override
    public void callbackPlacedFMOnBuild(int servantsUsed) {

    }

    /**
     * this method allows player to place a family member on a harvest action space
     *
     * @param servantsUsed the number of servants the user decided to use
     */
    @Override
    public void callbackPlacedFMOnHarvest(int servantsUsed) {

    }

    /**
     * this method allows player to place a family member on a tower floor action space
     *
     * @param towerIndex the identifier of the tower
     * @param floorIndex the identifier of the floor
     */
    @Override
    public void callbackPlacedFMOnTower(int towerIndex, int floorIndex) {

    }

    /**
     * this method allows player to place a family member on a market action space
     *
     * @param marketASIndex the selected market AS
     */
    @Override
    public void callbackPlacedFMOnMarket(int marketASIndex) {

    }

    /**
     * this method allows player to place a family member in the council action space
     */
    @Override
    public void callbackPlacedFMOnCouncil() {

    }

    /**
     * this is the call back method to send a message to all other players in the room (Direction: {@link it.polimi.ingsw.client.controller.AbstractUIType} -> {@link it.polimi.ingsw.client.controller.ClientMain}; general direction: Client -> server)
     *
     * @param msg
     * @throws NetworkException
     */
    @Override
    public void callbackSendChatMsg(String msg) throws NetworkException {

    }

    /**
     * This callback is used to call from view to controller to communicate the user choice
     *
     * @param leaderCardChoice the choice the user has made
     */
    @Override
    public void callbackOnLeaderCardChosen(LeaderCard leaderCardChoice) {
        Debug.printVerbose("Selected leader " + leaderCardChoice.getName());
    }

    /**
     * this method is called by the view and used to pass the turn
     */
    @Override
    public void callBackPassTheTurn() {

    }

    /**
     * this method is called by the view to deliver the special tile chosen to the server
     *
     * @param tileChosen
     */
    @Override
    public void callbackOnTileChosen(PersonalTile tileChosen) {

    }

    /**
     * this method is called by the view to discard a leader card
     *
     * @param leaderIter the leader card to discard
     */
    @Override
    public void callbackDiscardLeader(LeaderCard leaderIter) {

    }

    /**
     * this method is called to turn back at the start of the initial choices
     */
    @Override
    public void clientChoices() {

    }


    @Override
    public void callbackPlayLeader(LeaderCard leaderCard) {

    }

    /**
     * This method returns to the view a reference to the board
     * this method is called to obtain the board of the game inside the view
     *
     * @return the current board
     */
    @Override
    public Board callbackObtainBoard() {
        return null;
    }

    /**
     * This method returns to the view a reference to the player the client represents
     * this method is usually called to show the personal board of the player
     *
     * @return the player the clietn represents
     */
    @Override
    public Player callbackObtainPlayer() {
        return null;
    }

    /**
     * This method returns to the view a list of other players
     * this method is usually called by the view to show the personal boards of the other players
     *
     * @return the list of other playes
     */
    @Override
    public List<Player> callbackObtainOtherPlayers() {
        return null;
    }

    /**
     * This method returns to the view the list of dices
     * this method is usually called by the view to show the dices value
     *
     * @return the list of the dices
     */
    @Override
    public List<Dice> callbackObtainDices() {
        return null;
    }

    /**
     * This method returns to the view a list of all the players in order
     * this method is usually called by the view to show the correct order of players in the board
     *
     * @return the list of all players in order
     */
    @Override
    public List<Player> callbackObtainPlayersInOrder() {
        return null;
    }

    @Override
    public void callbackActivateLeader(LeaderCard leaderCard) {

    }
}
