package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.cli.CliPrinter;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.board.Tower;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.excommunicationTiles.ExcommunicationTile;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.MarketWrapper;
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
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * This object is the fx controller of the main board scene
 * Method name syntax:
 * set -- sets an attribute of the control that is needed to show something else or to perform queries
 * setUp -- does something that should be done only the first time the window is opened
 * display -- displays or refreshes something, may be called more than once during the game
 */
public class MainBoardControl extends CustomFxControl {
    ToggleButton currentFamilyMemberSelected;
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
     * This hashmap is used to obtain the tab related to the player
     */
    HashMap<String, PlayerTabSubControl> playersTabMap;


    private Board board;

    private Player thisPlayer;

    private List<Player> otherPlayers;

    private List<Dice> dices;

    /**
     * Contructor, called when opened fxml
     */
    public MainBoardControl() {
        playersTabMap = new HashMap<String, PlayerTabSubControl>(3);
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
     * This displays (or refreshes) the order of the player in the personal board cylinders
     * @param players the ordered list of players
     */
    public void displayOrderOfPlayers(List<Player> players) {
        //todo set circles
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

        for(int col = 0; col < towers.length; col++) {
            for(int raw = 0; raw < 4; raw++) {
                ImageView imgView = ((ImageView) (towersCouncilFaith.lookup("#card"+col+raw)));
                Image cardImg  = new Image(getClass().getResourceAsStream("/imgs/Cards/" +
                        towers[col].getFloorByIndex(raw).getCard().getImgName()));
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
        }


    }

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

                        ToggleButton fm = ((ToggleButton) (towersCouncilFaith.lookup("#towerAS" + towerIndex + floorIndex)));
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
    }

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
        String currentText = currentGameStateTextArea.getText();
        currentGameStateTextArea.setText(currentText + "\n" + "--> " + toAppend);
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

        //we set all AS to disabled
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

        //todo: disable build and harvest
        //first we need to disable build
        //we need to disable also harvest
        //servamts needed to build / harvest ?
        //todo: ci sono volte in cui non sempre è possibile piazzare un family member.. Com'è stato gestito? --Arto
        //setting council enabled
        Button activeCouncilASButton = (Button) (towersCouncilFaith.lookup("#councilGiftButton"));
        activeCouncilASButton.setDisable(false);
        //setting build and harvest enabled
        Button harvestSmallASButton = (Button) (buildHarvestPane.lookup("#harvestSmallActionSpace"));
        harvestSmallASButton.setDisable(false);
        Button harvestBigASButton = (Button) (buildHarvestPane.lookup("#harvestBigActionSpace"));
        harvestBigASButton.setDisable(false);
        Button buildSmallASButton = (Button) (buildHarvestPane.lookup("#buildSmallActionSpace"));
        buildSmallASButton.setDisable(false);
        Button buildBigASButton = (Button) (buildHarvestPane.lookup("#buildBigActionSpace"));
        buildBigASButton.setDisable(false);

        //setting harvest AS enable

        //we reactivate only the ones passed via parameters
        for(TowerWrapper towerWrapperIter : activeTowerSpaces) {
            Button activeTowersASButton = (Button) (towersCouncilFaith.lookup(("#towerAS" + towerWrapperIter.getTowerIndex()) + towerWrapperIter.getTowerFloor()));
            activeTowersASButton.setDisable(false);
        }
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
        Platform.runLater(() -> getController().callbackFamilyMemberSelected(thisPlayer.getFamilyMemberByColor(colorEnum)));

    }

    /**
     * Method called by fx when a harvest as is clicked
     * @param event the fx event
     */
    //todo check this method
    @FXML
    private void harvestSelected(ActionEvent event)
    {
        Button buttonHarvest = ((Button) (event.getSource()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Harvest");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("I have a great message for you!");
        alert.showAndWait();
        //todo make the alert ask the user
        Platform.runLater(()->getController().callbackPlacedFMOnHarvest(5));

        currentFamilyMemberSelected.setVisible(false);
        ToggleButton familyMemberPlaced = new ToggleButton();
        familyMemberPlaced.setLayoutX(buttonHarvest.getLayoutX());
        familyMemberPlaced.setLayoutY(buttonHarvest.getLayoutY());
        familyMemberPlaced.getStyleClass().add("familyMemberButton");
        buildHarvestPane.getChildren().add(familyMemberPlaced);
        Debug.printVerbose("Added FM to build");
        updateFamilyMembers();

    }

    /**
     * Method called by fx when a build as is clicked
     * @param event the fx event
     */
    @FXML
    private void buildSelected(ActionEvent event)
    {
        Button buttonBuild = ((Button) (event.getSource()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Build");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("I have a great message for you!");
        alert.showAndWait();

        Platform.runLater(()->getController().callbackPlacedFMOnBuild(5));

        currentFamilyMemberSelected.setVisible(false);
        ToggleButton familyMemberPlaced = new ToggleButton();
        familyMemberPlaced.setLayoutX(buttonBuild.getLayoutX());
        familyMemberPlaced.setLayoutY(buttonBuild.getLayoutY());
        familyMemberPlaced.getStyleClass().add("familyMemberButton");
        buildHarvestPane.getChildren().add(familyMemberPlaced);
        Debug.printVerbose("Added FM to build");
        updateFamilyMembers();

    }

    /**
     * Method called by fx when a market as is clicked
     * @param event the fx event
     */
    @FXML
    private void marketSelected(ActionEvent event)
    {
        Button buttonMarket = ((Button) (event.getSource()));

        Button actionSpace = ((Button) (event.getSource()));
        String id = actionSpace.getId();
        int marketIndex = Character.getNumericValue(id.charAt(8));
        Debug.printVerbose("Market placed" + marketIndex);
        Platform.runLater(()->getController().callbackPlacedFMOnMarket(marketIndex));

        currentFamilyMemberSelected.setVisible(false);
        double height = buttonMarket.getPrefHeight();
        double width = buttonMarket.getWidth();
        ToggleButton familyMemberPlaced = new ToggleButton();
        /*familyMemberPlaced.setMinHeight(height);
        familyMemberPlaced.setMaxWidth(width);*/

        familyMemberPlaced.setLayoutX(buttonMarket.getLayoutX());
        familyMemberPlaced.setLayoutY(buttonMarket.getLayoutY());
        familyMemberPlaced.getStyleClass().add("familyMemberButton");
        marketPane.getChildren().add(familyMemberPlaced);

        //CliPrinter.printBoard(board);
        updateFamilyMembers();
    }

    /**
     * Method called by fx when the council is clicked
     * @param event the fx event
     */
    @FXML
    private void councilGiftSelected(ActionEvent event)
    {
        ToggleButton buttonCouncil = ((ToggleButton) (event.getSource()));

        Button actionSpace = ((Button) (event.getSource()));
        //String id = actionSpace.getId();
        Platform.runLater(() -> getController().callbackPlacedFMOnCouncil());
        currentFamilyMemberSelected.setVisible(false);
        double height = buttonCouncil.getPrefHeight();
        double width = buttonCouncil.getWidth();
        ToggleButton familyMemberPlaced = new ToggleButton();
        /*familyMemberPlaced.setMinHeight(height);
        familyMemberPlaced.setMaxWidth(width);*/

        familyMemberPlaced.setLayoutX(buttonCouncil.getLayoutX());
        familyMemberPlaced.setLayoutY(buttonCouncil.getLayoutY());
        familyMemberPlaced.getStyleClass().add("familyMemberButton");
        towersCouncilFaith.getChildren().add(familyMemberPlaced);
        Debug.printVerbose("Added FM to council");
        updateFamilyMembers();

    }

    /**
     * Method called by fx when a tower as as is clicked
     * @param event the fx event
     */
    @FXML
    private void towerFloorSelected(ActionEvent event) {
        Button actionSpace = ((Button) (event.getSource()));
        String id = actionSpace.getId();
        int towerIndex = Character.getNumericValue(id.charAt(7));
        int floorIndex = Character.getNumericValue(id.charAt(8));
        Platform.runLater(() -> getController().callbackPlacedFMOnTower(towerIndex, floorIndex));
    }

}
