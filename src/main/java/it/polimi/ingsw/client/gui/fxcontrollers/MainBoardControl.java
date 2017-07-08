package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.cli.CliPrinter;
import it.polimi.ingsw.client.gui.blockingdialogs.AskMoreServantsDialog;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.excommunicationTiles.ExcommunicationTile;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.MarketWrapper;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.model.resource.TowerWrapper;
import it.polimi.ingsw.utils.Debug;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This object is the fx controller of the main board scene
 * Method name syntax:
 * set -- sets an attribute of the control that is needed to show something else or to perform queries
 * setUp -- does something that should be done only the first time the window is opened
 * display -- displays or refreshes something, may be called more than once during the game
 * notify -- makes the changes on the ui needed in order to show the move made by another player
 */
public class MainBoardControl extends CustomFxControl {

    private ToggleButton currentFamilyMemberSelected;
    @FXML
    private AnchorPane towersCouncilFaith;

    @FXML
    private AnchorPane marketPane;

    @FXML
    private AnchorPane buildHarvestPane;

    @FXML
    private HBox familyMembersPanel;

    @FXML
    private TextArea currentGameStateTextArea;

    @FXML
    private TabPane playersTabPersonalBoard;

    @FXML
    private PlayerTabSubControl thisPlayerTab;

    @FXML
    private PlayerTabSubControl player1Tab;

    @FXML
    private PlayerTabSubControl player2Tab;

    @FXML
    private PlayerTabSubControl player3Tab;

    @FXML
    private ToggleGroup familyMembersToggleGroup = new ToggleGroup();

    /**
     * This coordinates represent the center of each space for faith points in the board
     */
    private final double faithTrackYCoord = 877;
    private final double faithTrackXCoords[] = {25, 63, 101, 150, 212, 276, 323, 362, 399, 438, 475, 515, 552, 589, 627, 665};

    /**
     * This map relates the nickname of a player to his cylinder on the faith track
     */
    HashMap<String, FaithTrackPlaceHolderCollector> faithTrackCylinderMap;

    /**
     * This is the pool to run tasks in background
     */
    private ExecutorService pool;

    private int minServantsBuild = 0;
    private int minServantsHarvest = 0;

    /**
     * This hashmap is used to obtain the tab related to the player
     */
    HashMap<String, PlayerTabSubControl> playersTabMap;

    /**
     * This list contains all the family members represented as a button added during this turn
     * Should be cleaned at the end of the turn
     */
    List<ToggleButton> addedFamilyMembersButtons;


    private Board board;

    private Player thisPlayer;

    private List<Player> otherPlayers;

    private List<Dice> dices;



    /**
     * Contructor, called when opened fxml
     */
    public MainBoardControl() {
        playersTabMap = new HashMap<String, PlayerTabSubControl>(3);
        faithTrackCylinderMap = new HashMap<String, FaithTrackPlaceHolderCollector>(3);
        pool = Executors.newFixedThreadPool(2);
        addedFamilyMembersButtons = new ArrayList<ToggleButton>(16);
    }

    /**
     * This method sets the board object so that the controller can perform queries on it
     * @param board the current board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * This method sets the list of other players so that the controller can perform queries on it
     * @param otherPlayers the list of players not related to this client
     */
    public void setOtherPlayers(List<Player> otherPlayers) {
        this.otherPlayers = otherPlayers;
    }

    /**
     * This method sets the player related to this client so that the controller can perform queries on it
     * @param thisPlayer the player not related to this client
     */
    public void setThisPlayer(Player thisPlayer) {
        this.thisPlayer = thisPlayer;
    }

    /**
     * This method sets the list of dices so that the controller can perform queries on it
     * @param dices the list of dices
     */
    public void setDices(List<Dice> dices) {
        this.dices = dices;
    }

    /**
     * This method displays the excommunication tiles
     * Should be called only at the beginning of the game
     */
    public void setUpExcommTiles() {
        List<ExcommunicationTile> tiles = board.getExcommunicationTiles();

        for(int i = 0; i < tiles.size(); i++) {
            ImageView imgView = ((ImageView) (towersCouncilFaith.lookup("#excomm" + i)));
            Image tileImg  = new Image(getClass().getResourceAsStream("/imgs/ExcommunicationTiles/" +
                    tiles.get(i).getImgName()));
            imgView.setImage(tileImg);
            imgView.setPreserveRatio(true);
        }
    }

    /**
     * This method is used ad startup when we have to setup all the personal boards of the players
     */
    public void setUpPlayersPersonalBoards() {
        ObservableList<Tab> tabs = playersTabPersonalBoard.getTabs();
        Tab currentTab = tabs.get(0); //this player tab
        currentTab.setText("You (" + thisPlayer.getPlayerColor().getStringValue() + ")");

        thisPlayerTab.setUpTab(getController(), thisPlayer, true);
        playersTabMap.put(thisPlayer.getNickname(), thisPlayerTab);

        for(int i = 1; i <= otherPlayers.size(); i++) {
            currentTab = tabs.get(i);
            currentTab.setText(otherPlayers.get(i-1).getNickname() + " (" + otherPlayers.get(i-1).getPlayerColor().getStringValue() + ")");
        }
        tabs.remove(otherPlayers.size()+1, tabs.size());

        if(otherPlayers.size() >= 1) {
            player1Tab.setUpTab(getController(), otherPlayers.get(0), false);
            playersTabMap.put(otherPlayers.get(0).getNickname(), player1Tab);
            if(otherPlayers.size() >= 2) {
                player2Tab.setUpTab(getController(), otherPlayers.get(1), false);
                playersTabMap.put(otherPlayers.get(1).getNickname(), player2Tab);
                if(otherPlayers.size() >= 3) {
                    player3Tab.setUpTab(getController(), otherPlayers.get(2), false);
                    playersTabMap.put(otherPlayers.get(2).getNickname(), player3Tab);
                }
            }
        }
    }

    /**
     * This method is used ad startup when we have to setup the faith track placeholders
     */
    public void setUpFaithCylinders(List<Player> players) {
        Cylinder cylinder;

        for(int i = 0; i < players.size(); i++) {
            cylinder = (Cylinder) (towersCouncilFaith.lookup(("#faithCylinder" + i)));
            cylinder.setVisible(true);
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(Color.valueOf(players.get(i).getPlayerColor().getStringValue()));
            cylinder.setMaterial(material);

            faithTrackCylinderMap.put(players.get(i).getNickname(), new FaithTrackPlaceHolderCollector(players.get(i), cylinder));
        }
    }

    /**
     * This displays (or refreshes) the order of the player in the personal board cylinders
     * @param players the ordered list of players
     */
    public void displayOrderOfPlayers(List<Player> players) {
        Cylinder cylinder;

        for(int i = 0; i < players.size(); i++) {
            cylinder = (Cylinder) (towersCouncilFaith.lookup(("#orderCylinder" + i)));
            cylinder.setVisible(true);
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(Color.valueOf(players.get(i).getPlayerColor().getStringValue()));
            //material.setSpecularColor(Color.RED);
            cylinder.setMaterial(material);
        }
    }

    /**
     * This method displays (or refreshes) the cards on the board
     */
    public void displayCards() {
        Tower[] towers = board.getTowers();
        final String placeHoldername = "#card";
        for(int col = 0; col < towers.length; col++) {
            for(int raw = 0; raw < 4; raw++) {
                ImageView imgView = ((ImageView) (towersCouncilFaith.lookup(placeHoldername +col+raw)));
                Image cardImg = null;
                try {
                    cardImg = new Image(getClass().getResourceAsStream("/imgs/Cards/" +
                            towers[col].getFloorByIndex(raw).getCard().getImgName()));
                } catch (NullPointerException e) {
                    Debug.printError("CARD Something went wrong loading the card, look afterwards", e);
                    Debug.printError("towers[col]" + towers[col].toString());
                    Debug.printError("towers[col].getFloorByIndex(raw)" + towers[col].getFloorByIndex(raw).toString());
                    Debug.printError("towers[col].getFloorByIndex(raw).getCard()" + towers[col].getFloorByIndex(raw).getCard().toString());
                    Debug.printError("towers[col].getFloorByIndex(raw).getCard().getImgName()" + towers[col].getFloorByIndex(raw).getCard().getImgName());
                }
                Debug.printVerbose(cardImg.toString());
                Debug.printVerbose(imgView.toString());
                imgView.setImage(cardImg);
                imgView.setPreserveRatio(true);
            }
        }
        //todo remove, this is just for debug
        CliPrinter.printBoard(board);
    }

    /**
     * This method displays (or refreshes) the dices on the board
     */
    public void displayDices() {
        for(Dice diceIter : dices) {
            if(diceIter.getColor() != DiceAndFamilyMemberColorEnum.NEUTRAL) {
                Text diceText = ((Text) (marketPane.lookup("#dice" + diceIter.getColor().getIntegerValue())));
                diceText.setText(String.valueOf(diceIter.getValue()));
            }
        }
        //todo remove, this is just for debug
        CliPrinter.printPersonalBoard(thisPlayer);
    }

    /**
     * This method displays (or refreshes) the family members of this player
     */
    public void displayFamilyMembers(/*List<FamilyMember> availableFMs*/) {

        for(FamilyMember familyMember : thisPlayer.getNotUsedFamilyMembers()) {
            ToggleButton fm = ((ToggleButton) (familyMembersPanel.lookup("#FM" + familyMember.getColor().getIntegerValue())));
                fm.setText(String.valueOf(familyMember.getValue()));
                fm.setStyle("-fx-border-color: " + thisPlayer.getPlayerColor().getStringValue() + ";");
                fm.setToggleGroup(familyMembersToggleGroup);
                fm.setVisible(true);
        }
    }

    /**
     * This method is used to refresh the personal board of a player
     * @param playerNickname the nickname of the player to refresh the tab of
     */
    public void refreshPersonalBoardOfPlayer(String playerNickname) {
        PlayerTabSubControl playerTab = playersTabMap.get(playerNickname);
        playerTab.refreshResourcesAndCards();
        refreshFaithTrackOfPlayer(playerNickname);
    }

    /**
     * Refreshes the cylinders of the faith track
     * @param playerNickname the player to refresh the cylinder of
     */
    private void refreshFaithTrackOfPlayer(String playerNickname) {
        FaithTrackPlaceHolderCollector collector = faithTrackCylinderMap.get(playerNickname);
        Player player = collector.getPlayer();
        final int updatedFaithPoints = player.getResource(ResourceTypeEnum.FAITH_POINT);

        if(updatedFaithPoints == collector.getCurrentFaithPoints())
            return; //nothing to update

        Cylinder cylinder = collector.getCylinder();
        List<Player> allPlayers = getController().callbackObtainPlayersInOrder();
        int numberOfPlayersSameFaithPoints = 0;

        //we calculate how many players are already on the space we have to place our cylinder on
        for(Player playerIter : allPlayers) {
            if(playerIter != player && playerIter.getResource(ResourceTypeEnum.FAITH_POINT) == updatedFaithPoints)
                numberOfPlayersSameFaithPoints += 1;
        }

        //we calculate the coordinates
        double y = faithTrackYCoord - ((cylinder.getHeight()+cylinder.getRadius())/2) - (cylinder.getHeight()*numberOfPlayersSameFaithPoints) + 8;
        double x = faithTrackXCoords[updatedFaithPoints] - (cylinder.getRadius()/2) + 5;

        cylinder.setLayoutX(x);
        cylinder.setLayoutY(y);
    }

    /**
     * This method is called to perform all the actions needed to prepare the gui for a new round
     */
    public void prepareForNewRound() {
        //we removed all added family members from the board, wherever they might be
        for(ToggleButton fmButtonIter : addedFamilyMembersButtons) {
            if(towersCouncilFaith.getChildren().remove(fmButtonIter))
                continue;
            if(buildHarvestPane.getChildren().remove(fmButtonIter))
                continue;
            marketPane.getChildren().remove(fmButtonIter);
        }
    }

    /*
    public void updateFamilyMembers() {
        ArrayList<Player> allPlayers = new ArrayList<>(5);
        allPlayers.add(thisPlayer);
        allPlayers.addAll(otherPlayers);
        for(Player player : allPlayers)
        {
            //this method shows other players family member inside towers
            for(int towerIndex = 0; towerIndex < board.getTowers().length; towerIndex++) {
                Tower tower = board.getTowers()[towerIndex];
                for (int floorIndex = 0; floorIndex < tower.getFloors().length; floorIndex++) {
                    TowerFloorAS floor = tower.getFloors()[floorIndex];

                    for (FamilyMember familyMember : floor.getFamilyMembers()) {

                        Button fm = ((Button) (towersCouncilFaith.lookup("#towerAS" + towerIndex + floorIndex)));
                        fm.setText(String.valueOf(familyMember.getValue()));
                        fm.setStyle("-fx-border-color: " + player.getPlayerColor().getStringValue() + ";");
                        fm.getStyleClass().add("familyMemberButton");
                    }
                }
            }
            //this method shows other family members inside market
            for(int marketIndex = 0; marketIndex < board.getMarket().size(); marketIndex++)
            {
                for (FamilyMember familyMember : board.getMarket().get(marketIndex).getFamilyMembers()) {
                    Button fm = ((Button) (marketPane.lookup("#marketAS" + marketIndex)));
                    fm.setText(String.valueOf(familyMember.getValue()));
                    fm.setStyle("-fx-border-color: " + player.getPlayerColor().getStringValue() + ";");
                    fm.getStyleClass().add("familyMemberButton");
                }
            }
        }
    }*/

    public void displayExcommTiles() {
        List<ExcommunicationTile> tiles = board.getExcommunicationTiles();

        for(int i = 0; i < tiles.size(); i++) {
            ImageView imgView = ((ImageView) (towersCouncilFaith.lookup("#excomm" + i)));
            Image tileImg  = new Image(getClass().getResourceAsStream("/imgs/ExcommunicationTiles/" +
                    tiles.get(i).getImgName()));
            imgView.setImage(tileImg);
            imgView.setPreserveRatio(true);
        }
    }

    /**
     * Appends a message on the text area that displays the current state of the game
     * @param toAppend text to append
     */
    public void appendMessageOnStateTextArea(String toAppend) {
        currentGameStateTextArea.appendText("\n" + "--> " + toAppend);
        currentGameStateTextArea.setScrollTop(Double.MAX_VALUE);
    }

    /**
     * This method sets al family members to enabled/disabled depending on the parameter
     * @param disabled true if you want to disable
     */
    public void setFamilyMemberDisable(boolean disabled) {
        for(DiceAndFamilyMemberColorEnum coloEnumIter : DiceAndFamilyMemberColorEnum.values()) {
            ToggleButton fm = ((ToggleButton) (familyMembersPanel.lookup("#FM" + coloEnumIter.getIntegerValue())));
            fm.setDisable(disabled);
        }
    }

    /**
     * This method disables or enables all actions for the user
     * Should be called with true when it's not his turn
     * with false otherwire
     * @param disable true if actions should be disabled
     */
    public void disableAllActionsNotHisTurn(boolean disable) {
        if(disable)
            this.disableActionSpaces();
        this.setFamilyMemberDisable(disable);
        PlayerTabSubControl thisPlayerTab = playersTabMap.get(thisPlayer.getNickname());
        thisPlayerTab.setLeadersActionsDisable(disable);
        thisPlayerTab.setEndTurnButtonDisable(disable);
    }

    /**
     * This method disables all the button that let the user click on an action space
     */
    private void disableActionSpaces() {
        for(int col = 0; col < 4; col++) {
            for(int raw = 0; raw < 4; raw++) {
                Button activeTowersASButton = (Button) (towersCouncilFaith.lookup(("#towerAS" + col) + raw));
                activeTowersASButton.setDisable(true);
            }
        }


        for(int iterator = 0; iterator < 4; iterator++) {
            Button marketASButton = (Button) (marketPane.lookup("#marketAS" + iterator));
            marketASButton.setDisable(true);
        }

        Button activeCouncilASButton = (Button) (towersCouncilFaith.lookup("#councilGiftButton"));
        activeCouncilASButton.setDisable(true);
        //setting build and harvest enabled
        Button harvestSmallASButton = (Button) (buildHarvestPane.lookup("#harvestSmallActionSpace"));
        harvestSmallASButton.setDisable(true);
        Button harvestBigASButton = (Button) (buildHarvestPane.lookup("#harvestBigActionSpace"));
        harvestBigASButton.setDisable(true);
        Button buildSmallASButton = (Button) (buildHarvestPane.lookup("#buildSmallActionSpace"));
        buildSmallASButton.setDisable(true);
        Button buildBigASButton = (Button) (buildHarvestPane.lookup("#buildBigActionSpace"));
        buildBigASButton.setDisable(true);
    }

    /**
     * This method displays (or refreshes) only the action spaces that are available with the selected family member
     * @param servantsNeededHarvest The servants needed by the user to harvest, Optional.empty() if the action is not valid
     * @param servantsNeededBuild   The servants needed by the user to build, Optional.empty() if the action is not valid
     * @param servantsNeededCouncil The servants needed by the user to place on cuincil, Optional.empty() if the action is not valid
     * @param activeMarketSpaces    The list of legal action spaces in the market
     * @param activeTowerSpaces     the list of legal action spaces on the towers
     */
    public void displayActiveActionSpaces(Optional<Integer> servantsNeededHarvest,
                                          Optional<Integer> servantsNeededBuild,
                                          Optional<Integer> servantsNeededCouncil,
                                          List<MarketWrapper> activeMarketSpaces,
                                          List<TowerWrapper> activeTowerSpaces) {

        towersCouncilFaith.setMouseTransparent(false);
        Debug.printVerbose("displayActiveActionSpaces called");

        //we set all AS to disabled
        disableActionSpaces();

        //we reactivate only the ones passed via parameters
        if(servantsNeededBuild.isPresent()){
            if(board.getBuild().checkIfFirst()){
                Button activeBuildButton = (Button) (buildHarvestPane.lookup("#buildSmallActionSpace"));
                activeBuildButton.setDisable(false);
                minServantsBuild = servantsNeededBuild.get();
            }
            else if(!board.getBuild().isTwoPlayersOneSpace()){
                Button activeBuildButton = (Button) (buildHarvestPane.lookup("#buildBigActionSpace"));
                activeBuildButton.setDisable(false);
            }
        }

        if(servantsNeededHarvest.isPresent()){
            if(board.getHarvest().checkIfFirst()){
                Button activeHarvestButton = (Button) (buildHarvestPane.lookup("#harvestSmallActionSpace"));
                activeHarvestButton.setDisable(false);
            }
            else if(!board.getBuild().isTwoPlayersOneSpace()){
                Button activeHarvestButton = (Button) (buildHarvestPane.lookup("#harvestBigActionSpace"));
                activeHarvestButton.setDisable(false);
                minServantsBuild = servantsNeededHarvest.get();
            }
        }

        if(servantsNeededCouncil.isPresent()){
            Button activeCouncilButton = (Button) (towersCouncilFaith.lookup("#councilGiftButton"));
            activeCouncilButton.setDisable(false);
        }


        if(servantsNeededBuild.isPresent()) {
            BuildAS build = board.getBuild();
            if(build.isTwoPlayersOneSpace() || build.checkIfFirst() ||
                    thisPlayer.getPermanentLeaderCardCollector().canPlaceFamilyMemberInOccupiedActionSpace()) {
                Button activeButton = (Button) (buildHarvestPane.lookup("#buildSmallActionSpace"));
                activeButton.setDisable(false);
            } else if(!build.isTwoPlayersOneSpace()) {
                Button activeButton = (Button) (buildHarvestPane.lookup("#buildBigActionSpace"));
                activeButton.setDisable(false);
            }
        }

        if(servantsNeededHarvest.isPresent()) {
            HarvestAS harvest = board.getHarvest();
            if(harvest.isTwoPlayersOneSpace() || harvest.checkIfFirst() ||
                    thisPlayer.getPermanentLeaderCardCollector().canPlaceFamilyMemberInOccupiedActionSpace()) {
                Button activeButton = (Button) (buildHarvestPane.lookup("#harvestSmallActionSpace"));
                activeButton.setDisable(false);
            } else if(!harvest.isTwoPlayersOneSpace()) {
                Button activeButton = (Button) (buildHarvestPane.lookup("#harvestBigActionSpace"));
                activeButton.setDisable(false);
            }
        }

        if(servantsNeededCouncil.isPresent()) {
            Button activeButton = (Button) (towersCouncilFaith.lookup("#councilGiftButton"));
            activeButton.setDisable(false);
        }

        for(MarketWrapper marketIterator : activeMarketSpaces)
        {
            Button marketASButton = (Button) (marketPane.lookup("#marketAS" + marketIterator.getMarketIndex()));
            Debug.printVerbose("iterator on wrapper: " + marketIterator.getMarketIndex());
            marketASButton.setDisable(false);
        }

        for(TowerWrapper towerWrapperIter : activeTowerSpaces) {
            Button activeTowersASButton = (Button) (towersCouncilFaith.lookup(("#towerAS" + towerWrapperIter.getTowerIndex()) + towerWrapperIter.getTowerFloor()));
            activeTowersASButton.setDisable(false);
        }

       /* Button button = new Button("click me");
        button.setLayoutX(400+new Random().nextInt(20));
        button.setLayoutY(400);
        button.toFront();
        todo remove

        towersCouncilFaith.getChildren().add(button);*/


        //we reactivate only the AS passed via parameters -> problem here. Wrapper is not used correctly

        for(MarketWrapper marketIterator : activeMarketSpaces)
        {
            Button marketASButton = (Button) (marketPane.lookup("#marketAS" + marketIterator.getMarketIndex()));
            Debug.printVerbose("iterator on wrapper: " + marketIterator.getMarketIndex());
            marketASButton.setDisable(false);
        }

    }

    /**
     * Method called by fx when a family member is clicked
     * @param event the fx event
     */
    @FXML
    public void familyMemberSelected(ActionEvent event) {
        ToggleButton buttonFM = ((ToggleButton) (event.getSource()));

        DiceAndFamilyMemberColorEnum colorEnum = DiceAndFamilyMemberColorEnum.valueOf(
                Character.getNumericValue(buttonFM.getId().charAt(2)));
        currentFamilyMemberSelected = buttonFM;
        pool.submit(() -> getController().callbackFamilyMemberSelected(thisPlayer.getFamilyMemberByColor(colorEnum)));
    }

    /**
     * Method called by fx when a harvest as is clicked
     * @param event the fx event
     */
    //todo check this method
    @FXML
    private void harvestSelected(ActionEvent event)
    {

        Button actionSpace = ((Button) (event.getSource()));
        Debug.printVerbose("Hello1");
        //FutureTask<Integer> futureTask = new FutureTask(new AskMoreServantsDialog(minServantsHarvest, thisPlayer.getResource(ResourceTypeEnum.SERVANT)));
        //Platform.runLater(futureTask);
        AskMoreServantsDialog askMoreServantsDialog = new AskMoreServantsDialog(minServantsHarvest, thisPlayer.getResource(ResourceTypeEnum.SERVANT));
        int userAnswer = minServantsHarvest;
        try {
            userAnswer = askMoreServantsDialog.call();
            Debug.printVerbose("Numbero scelto dall'utente" + userAnswer);
        }catch (Exception e) {
        e.printStackTrace();
        Debug.printVerbose("Error opening dialogue, selecting the minServe number");
        }

        final int temp = userAnswer;

        pool.submit(()->getController().callbackPlacedFMOnHarvest(temp));

        //place the family member in the correct place
        if(actionSpace.getId().equals("harvestSmallActionSpace"))
            placeFamilyMemberForThisPlayer(buildHarvestPane,
                    new Coordinates(actionSpace.getLayoutX(), actionSpace.getLayoutY()));
        else {
            //we have to subtract one because one has already been placed to the small action space
            int occupyingFMs = board.getHarvest().getOccupyingFamilyMemberNumber()-1;
            Coordinates coord = calculateCoordinatesBigActionSpace(actionSpace, occupyingFMs);
            placeFamilyMemberForThisPlayer(towersCouncilFaith, coord);
        }

        Debug.printVerbose("Added FM to build");
        //updateFamilyMembers();

    }

    /**
     * Method called by fx when a build as is clicked
     * @param event the fx event
     */
    @FXML
    private void buildSelected(ActionEvent event)
    {
        Button actionSpace = ((Button) (event.getSource()));

        //FutureTask<Integer> futureTask = new FutureTask(new AskMoreServantsDialog(minServantsHarvest, thisPlayer.getResource(ResourceTypeEnum.SERVANT)));
        //Platform.runLater(futureTask);
        AskMoreServantsDialog askMoreServantsDialog = new AskMoreServantsDialog(minServantsBuild, thisPlayer.getResource(ResourceTypeEnum.SERVANT));
        int userAnswer = minServantsBuild;
        try {
            userAnswer = askMoreServantsDialog.call();
            Debug.printVerbose("Number chosen" + userAnswer);
        }catch (Exception e) {
            e.printStackTrace();
            Debug.printVerbose("Error opening dialogue, selecting the minServe number");
        }

        final int temp = userAnswer;

        Debug.printVerbose("Added FM to build");

        pool.submit(()->getController().callbackPlacedFMOnBuild(temp));

        //place the family member in the correct place
        if(actionSpace.getId().equals("buildSmallActionSpace"))
            placeFamilyMemberForThisPlayer(buildHarvestPane,
                    new Coordinates(actionSpace.getLayoutX(), actionSpace.getLayoutY()));
        else {
            //we have to subtract one because one has already been placed to the small action space
            int occupyingFMs = board.getBuild().getOccupyingFamilyMemberNumber()-1;
            Coordinates coord = calculateCoordinatesBigActionSpace(actionSpace, occupyingFMs);
            placeFamilyMemberForThisPlayer(towersCouncilFaith, coord);
        }


        //updateFamilyMembers();

    }

    /**
     * Method called by fx when a market as is clicked
     * @param event the fx event
     */
    @FXML
    private void marketSelected(ActionEvent event)
    {
        Button actionSpace = ((Button) (event.getSource()));
        String id = actionSpace.getId();
        int marketIndex = Character.getNumericValue(id.charAt(8));
        Debug.printVerbose("Market placed" + marketIndex);

        //place the family member
        placeFamilyMemberForThisPlayer(marketPane, new Coordinates(actionSpace.getLayoutX(), actionSpace.getLayoutY()));

        pool.submit(()->getController().callbackPlacedFMOnMarket(marketIndex));
        //CliPrinter.printBoard(board);
        //updateFamilyMembers();
    }

    /**
     * Method called by fx when the council is clicked
     * @param event the fx event
     */
    @FXML
    private void councilGiftSelected(ActionEvent event)
    {
        Button actionSpace = ((Button) (event.getSource()));

        //place the family member
        Coordinates coord = calculateCoordinatesBigActionSpace(actionSpace, board.getCouncilAS().getOccupyingFamilyMemberNumber());
        placeFamilyMemberForThisPlayer(towersCouncilFaith, coord);

        pool.submit(() -> getController().callbackPlacedFMOnCouncil());

        Debug.printVerbose("Added FM to council");
        //updateFamilyMembers();
    }

    public void setUpNumberOfPlayers(int numberOfPlayers)
    {
        if (numberOfPlayers == 4){
            return;
        }
        disableMarket();
        if(numberOfPlayers == 3)
            return;
        disableBuildHarvest();

    }
    private void disableMarket() {
        ImageView imgView = ((ImageView) (marketPane.lookup("#marketBlock2")));
        Image blockImg  = new Image(getClass().getResourceAsStream("/imgs/marketBlock.png"));
        imgView.setImage(blockImg);
        imgView.setPreserveRatio(true);
        ImageView secondImage = ((ImageView) (marketPane.lookup("#marketBlock3")));
        secondImage.setImage(blockImg);
        secondImage.setPreserveRatio(true);

    }
    private void disableBuildHarvest(){
        ImageView imgView = ((ImageView) (buildHarvestPane.lookup("#buildBlockActionSpace")));
        Image blockImg  = new Image(getClass().getResourceAsStream("/imgs/productionBlock.png"));
        imgView.setImage(blockImg);
        imgView.setPreserveRatio(true);
        ImageView secondImage = ((ImageView) (buildHarvestPane.lookup("#harvestBlockActionSpace")));
        secondImage.setImage(blockImg);
        secondImage.setPreserveRatio(true);
        }
    /**
     * Method called by fx when a tower as as is clicked
     * @param event the fx event
     */
    @FXML
    private void towerFloorSelected(ActionEvent event) {
        Debug.printVerbose("towerFloorSelected called");
        Button actionSpace = ((Button) (event.getSource()));
        String id = actionSpace.getId();
        int towerIndex = Character.getNumericValue(id.charAt(7));
        int floorIndex = Character.getNumericValue(id.charAt(8));

        //remove the corresponding card
        ImageView imgView = ((ImageView) (towersCouncilFaith.lookup("#card"+towerIndex+floorIndex)));
        imgView.setImage(null);

        //place the family member
        placeFamilyMemberForThisPlayer(towersCouncilFaith, new Coordinates(actionSpace.getLayoutX(), actionSpace.getLayoutY()));

        pool.submit(() -> getController().callbackPlacedFMOnTower(towerIndex, floorIndex));
    }

    /**
     * This method performs actions needed when a family member of this player is placed on an action space
     * @param paneToPlaceTo the pane to which the family member will be added
     * @param coord the coordinates to place the family member to
     */
    private void placeFamilyMemberForThisPlayer(Pane paneToPlaceTo, Coordinates coord) {
        //he cannot place a family member anymore
        disableActionSpaces();
        setFamilyMemberDisable(true);

        ToggleButton fmButton = createFamilyMemberButtonPlaceHolder(
                getController().callbackObtainSelectedFamilyMember(),
                coord);

        currentFamilyMemberSelected.setVisible(false);
        paneToPlaceTo.getChildren().add(fmButton);
    }

    /**
     * Creates a toggle button with the right style as a family member
     * @param familyMember the family member to style the button
     * @param coord the coordinates to give the button to
     * @return the toggle button representing the family member
     */
    private ToggleButton createFamilyMemberButtonPlaceHolder(FamilyMember familyMember, Coordinates coord) {

        ToggleButton fmButton = new ToggleButton(String.valueOf(familyMember.getValue()));
        fmButton.getStyleClass().add("FM" + familyMember.getColor().getIntegerValue() + "Class");
        fmButton.getStyleClass().add("familyMemberButton");
        //fmButton.getStyleClass().remove("familyMemberButton");
        fmButton.getStyleClass().add("familyMemberPlaceHolder");
        fmButton.setStyle("-fx-border-color: " + familyMember.getPlayer().getPlayerColor().getStringValue() + ";");
        fmButton.setLayoutX(coord.getX());
        fmButton.setLayoutY(coord.getY());
        fmButton.setDisable(true);
        fmButton.toFront();
        addedFamilyMembersButtons.add(fmButton); //in order to remove it afterwards

        return fmButton;
    }

    /**
     * Private immutable class for storing coordinates
     */
    private final class Coordinates {
        double x, y;
        public Coordinates(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public final double getX() {
            return x;
        }

        public final double getY() {
            return y;
        }
    }

    /**
     * Calculates where to palce a family member on an big AS
     * @param actionSpaceButton the big action space
     * @param numberFMAlreadyPlaced the number of family members already placed in that space
     * @return the coordinates to place to
     */
    private Coordinates calculateCoordinatesBigActionSpace(Button actionSpaceButton, int numberFMAlreadyPlaced) {
        double x = actionSpaceButton.getLayoutX();
        double y = actionSpaceButton.getLayoutY();

        //this is just to get the Height and the width of a family member
        ToggleButton familyMemberInfo = ((ToggleButton) (familyMembersToggleGroup.getToggles().get(0)));

        y += actionSpaceButton.getHeight() / 2; //we want to set it in the middle
        y -= familyMemberInfo.getHeight() / 2;

        x += familyMemberInfo.getWidth() * numberFMAlreadyPlaced;

        return new Coordinates(x, y);
    }

    private void displayFaithPoints()
    {

    }

    /**
     * This method is used by the controller when it receives a place on tower from another player and wants
     * to notify the user that such a move has happened
     *
     * @param fm         the family member used for the move
     * @param towerIndex the index of the tower
     * @param floorIndex the index of the floor AS
     */
    public void notifyPlaceOnTower(FamilyMember fm, int towerIndex, int floorIndex) {
        notifyMoveOnGameStateTextArea(fm, "in a tower action space of coordinates [" + towerIndex + ";" +
                floorIndex + "], look at his tab for updates on resources and cards");


        //remove the corresponding card
        ImageView imgView = ((ImageView) (towersCouncilFaith.lookup("#card"+towerIndex+floorIndex)));
        imgView.setImage(null);

        //place the new family member
        Button asTowerButton = ((Button) (towersCouncilFaith.lookup("#towerAS"+towerIndex+floorIndex)));
        ToggleButton fmButton = createFamilyMemberButtonPlaceHolder(fm,
                new Coordinates(asTowerButton.getLayoutX(), asTowerButton.getLayoutY()));
        towersCouncilFaith.getChildren().add(fmButton);
        refreshPersonalBoardOfPlayer(fm.getPlayer().getNickname());
    }

    /**
     * This method is used by the controller when it receives a place on market from another player and wants
     * to notify the user that such a move has happened
     * @param fm the family member used for the move
     * @param marketIndex the index of the market as
     */
    public void notifyPlaceOnMarket(FamilyMember fm, int marketIndex) {
        notifyMoveOnGameStateTextArea(fm, "in a market action space of index " + marketIndex);

        //place the new family member
        Button asButton = ((Button) (marketPane.lookup("#marketAS"+marketIndex)));
        ToggleButton fmButton = createFamilyMemberButtonPlaceHolder(fm,
                new Coordinates(asButton.getLayoutX(), asButton.getLayoutY()));
        marketPane.getChildren().add(fmButton);
        refreshPersonalBoardOfPlayer(fm.getPlayer().getNickname());
    }

    /**
     * This method is used by the controller when it receives a place on harvest from another player and wants
     * to notify the user that such a move has happened
     * @param fm the family member used for the move
     * @param servantsUsed the number of servants used for the action
     */
    public  void notifyPlaceOnHarvest(FamilyMember fm, int servantsUsed) {
        notifyMoveOnGameStateTextArea(fm, "in the harvest action space with " + servantsUsed + " servants");

        Button activeButton;
        Coordinates coord;
        int occupyingFMs = board.getHarvest().getOccupyingFamilyMemberNumber();
        //place the family member in the correct place
        if(occupyingFMs <= 1) { //just the one he already placed, here the action is already in the model
            activeButton = (Button) (buildHarvestPane.lookup("#harvestSmallActionSpace"));
            coord = new Coordinates(activeButton.getLayoutX(), activeButton.getLayoutY());
        }
        else {
            activeButton = (Button) (buildHarvestPane.lookup("#harvestBigActionSpace"));
            coord = calculateCoordinatesBigActionSpace(activeButton, occupyingFMs-2);
        }
        ToggleButton fmButton = createFamilyMemberButtonPlaceHolder(fm, coord);
        buildHarvestPane.getChildren().add(fmButton);

        refreshPersonalBoardOfPlayer(fm.getPlayer().getNickname());
    }

    /**
     * This method is used by the controller when it receives a place on build from another player and wants
     * to notify the user that such a move has happened
     * @param fm the family member used for the move
     * @param servantsUsed the number of servants used for the action
     */
    public  void notifyPlaceOnBuild(FamilyMember fm, int servantsUsed) {
        notifyMoveOnGameStateTextArea(fm, "in the build action space with " + servantsUsed + " servants");

        Button activeButton;
        Coordinates coord;
        int occupyingFMs = board.getBuild().getOccupyingFamilyMemberNumber();
        //place the family member in the correct place
        if(occupyingFMs <= 1) { //just the one he already placed, here the action is already in the model
            activeButton = (Button) (buildHarvestPane.lookup("#buildSmallActionSpace"));
            coord = new Coordinates(activeButton.getLayoutX(), activeButton.getLayoutY());
        }
        else {
            activeButton = (Button) (buildHarvestPane.lookup("#buildBigActionSpace"));
            coord = calculateCoordinatesBigActionSpace(activeButton, occupyingFMs-2);
        }
        ToggleButton fmButton = createFamilyMemberButtonPlaceHolder(fm, coord);
        buildHarvestPane.getChildren().add(fmButton);

        refreshPersonalBoardOfPlayer(fm.getPlayer().getNickname());
    }

    /**
     * This method is used by the controller when it receives a place on the council from another player and wants
     * to notify the user that such a move has happened
     * @param fm the family member used for the move
     */
    public void notifyPlaceOnCouncil(FamilyMember fm) {
        notifyMoveOnGameStateTextArea(fm, "in the council action space");

        Button activeButton = (Button) (towersCouncilFaith.lookup("#councilGiftButton"));
        Coordinates coord = calculateCoordinatesBigActionSpace(activeButton, //the model already updated hence -1
                board.getCouncilAS().getOccupyingFamilyMemberNumber()-1);
        ToggleButton fmButton = createFamilyMemberButtonPlaceHolder(fm, coord);
        towersCouncilFaith.getChildren().add(fmButton);

        refreshPersonalBoardOfPlayer(fm.getPlayer().getNickname());
    }

    /**
     * this method is called when a player pass the phase
     *
     * @param nickname the player that had pass the phase
     */
    public void notifyEndOfPhaseOfPlayer(String nickname) {
        appendMessageOnStateTextArea("["+nickname + "] --> " + nickname + " has passed the turn.");
    }

    /**
     * This method is used by the controller when it receives a discard leader action from another player and wants
     * to notify the user that such a move has happened
     * @param nickname the name of the palyer making the move
     * @param nameCard the name of the leader card involved in the action
     */
    public void notifyDiscardLeaderCard(String nickname, String nameCard) {
        notifyLeaderAction(nickname, nameCard, "discarded");
    }

    /**
     * This method is used by the controller when it receives a play leader action from another player and wants
     * to notify the user that such a move has happened
     * @param nickname the name of the palyer making the move
     * @param nameCard the name of the leader card involved in the action
     */
    public void notifyPlayLeaderCard(String nickname, String nameCard) {
        notifyLeaderAction(nickname, nameCard, "played");
    }

    /**
     * This method is used by the controller when it receives a activate leader action from another player and wants
     * to notify the user that such a move has happened
     * @param nickname the name of the palyer making the move
     * @param nameCard the name of the leader card involved in the action
     */
    public void notifyActivateLeaderCard(String nickname, String nameCard) {
        notifyLeaderAction(nickname, nameCard, "activated");
    }

    private void notifyLeaderAction(String nickname, String nameCard, String nameAction) {
        appendMessageOnStateTextArea("["+nickname + "] --> " + nickname + " has " + nameAction +" a leader card named "
                + nameCard + ".");

        PlayerTabSubControl tabSubControl = playersTabMap.get(nickname);
//        tabSubControl.refreshLeaderCards();
        tabSubControl.refreshResourcesAndCards();
    }


    private void notifyMoveOnGameStateTextArea(FamilyMember familyMember, String text) {
        appendMessageOnStateTextArea("["+familyMember.getPlayer().getNickname() + "] --> " + familyMember.getPlayer().getNickname() +
                " has placed his " + familyMember.getColor() + " family member of value " + familyMember.getValue() +
                " " + text);
    }

    /**
     * this method is called by the client to ask the client if he wants to be excommunicated on the gui
     */
    public void askExcommunicationChoice(int numTile) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Excommunication?");
            alert.setHeaderText("Do you want to be excommunicated?");
            alert.setContentText("The time for the Vatican report has come, you have the faith points not to be " +
                    "excommunicated, do you want to be excommunicated anyway?");

            ButtonType excomYes = new ButtonType("Yes, excommunicate me");
            ButtonType excomNo = new ButtonType("No, don't excommunicate me");
            alert.getButtonTypes().setAll(excomNo, excomYes);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == excomYes)
                pool.execute(() -> getController().callbackExcommunicationChoice("YES", numTile));
            else
                pool.execute(() -> getController().callbackExcommunicationChoice("NO", numTile));
        });
    }

    /**
     * This is a private class that lets us keep track of all the informations needed to update the faith track
     */
    private class FaithTrackPlaceHolderCollector {
        Player player;
        Cylinder cylinder;
        int currentFaithPoints;

        public FaithTrackPlaceHolderCollector(Player player, Cylinder cylinder) {
            this.player = player;
            this.cylinder = cylinder;
            currentFaithPoints = 0;
        }

        public Player getPlayer() {
            return player;
        }

        public Cylinder getCylinder() {
            return cylinder;
        }

        public int getCurrentFaithPoints() {
            return currentFaithPoints;
        }

        public void setCurrentFaithPoints(int currentFaithPoints) {
            this.currentFaithPoints = currentFaithPoints;
        }
    }
}
