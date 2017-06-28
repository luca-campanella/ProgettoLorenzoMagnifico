package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.cli.CliPrinter;
import it.polimi.ingsw.client.gui.GraphicalUI;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.board.Tower;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.leaders.PermanentLeaderCardCollector;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.MarketWrapper;
import it.polimi.ingsw.model.resource.TowerWrapper;
import it.polimi.ingsw.utils.Debug;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by campus on 24/06/2017.
 */
public class MainBoardControl extends CustomFxControl {
    boolean[] isLeaderStageCreated = {false,false, false, false, false};
    Stage secondStage = new Stage();
    CustomFxControl currentFXControl;
    @FXML
    private AnchorPane towersCouncilFaith;

    @FXML
    private AnchorPane marketBuildHarvest;

    @FXML
    private Button blueCardsButton;

    @FXML
    private Button purpleCardsButton;

    @FXML
    private HBox familyMembersPanel;

    @FXML
    private TextArea currentGameStateTextArea;

    @FXML
    private TabPane playersTabPersonalBoard;

    @FXML
    private Tab thisPlayerTab;

    @FXML
    private Tab player1Tab;

    @FXML
    private Tab player2Tab;

    @FXML
    private Tab player3Tab;



    private Board board;

    private Player thisPlayer;

    private List<Player> otherPlayers;

    private List<Dice> dices;

    @FXML
    private ToggleGroup familyMembersToggleGroup = new ToggleGroup();

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

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setThisPlayer(Player thisPlayer) {
        this.thisPlayer = thisPlayer;
    }

    public void setOtherPlayers(List<Player> otherPlayers) {
        this.otherPlayers = otherPlayers;
    }

    public void setDices(List<Dice> dices) {
        this.dices = dices;
    }

    public void displayDices() {
        for(Dice diceIter : dices) {
            if(diceIter.getColor() != DiceAndFamilyMemberColorEnum.NEUTRAL) {
                Text diceText = ((Text) (marketBuildHarvest.lookup("#dice" + diceIter.getColor().getIntegerValue())));
                diceText.setText(String.valueOf(diceIter.getValue()));
            }
        }
        //todo remove, this is just for debug
        CliPrinter.printPersonalBoard(thisPlayer);
    }

    public void displayThisPlayerPersonalBoard() {
        PersonalBoard persBoard = thisPlayer.getPersonalBoard();

        //we enable or disable the buttons to see blue and purple cards if the player has or has not some of them
        purpleCardsButton.setDisable((persBoard.getNumberOfColoredCard(CardColorEnum.PURPLE) == 0));
        blueCardsButton.setDisable((persBoard.getNumberOfColoredCard(CardColorEnum.BLUE) == 0));
    }

    public void displayFamilyMembers(/*List<FamilyMember> availableFMs*/) {
        for(Dice diceIter : dices) {
            ToggleButton fm = ((ToggleButton) (familyMembersPanel.lookup("#FM" + diceIter.getColor().getIntegerValue())));
                fm.setText(String.valueOf(diceIter.getValue()));
                fm.setStyle("-fx-border-color: " + thisPlayer.getPlayerColor().getStringValue() + ";");
                fm.setToggleGroup(familyMembersToggleGroup);
        }
    }

    @FXML
    public void showPurpleCards() {
        showCards(thisPlayer.getPersonalBoard().getCardListByColor(CardColorEnum.PURPLE), "Purple cards");
    }

    @FXML
    public void showBlueCards() {
        showCards(thisPlayer.getPersonalBoard().getCardListByColor(CardColorEnum.BLUE), "Blue cards");
    }
    /**
     * owned cards leader
     */
    @FXML
    public void showLeaderCards()
    {
        if(!isLeaderStageCreated[0])
        Platform.runLater(() -> this.openNewWindow("LeaderPickerScene.fxml", "Choose a leader", () -> this.showLeaders(
                thisPlayer.getLeaderCardsNotUsed(), thisPlayer.getPlayedLeaders(), thisPlayer.getPlayableLeaders(),
                thisPlayer.getPlayedNotActivatedOncePerRoundLeaderCards())));


    }

    private void showLeaders(ArrayList<LeaderCard> leaderNotUsed, List<LeaderCard> leaderActivated, List<LeaderCard> leadersPlayable, List<LeaderCard> leadersOPRNotActivated) {
        LeaderOwnedControl leaderOwnedControl = ((LeaderOwnedControl) (currentFXControl));
        leaderOwnedControl.setLeaders(leaderNotUsed,leaderActivated,leadersPlayable,leadersOPRNotActivated);
        return;
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
            Debug.printError("Error in loading fxml", e);
        }

        currentFXControl = (fxmlLoader.getController());

        currentFXControl.setController(getController());

        secondStage.setTitle(title);
        secondStage.setScene(new Scene(root, -1, -1, true, SceneAntialiasing.BALANCED));

        if(runBeforeShow != null) //there is something to run
            runBeforeShow.run();

        secondStage.show();
    }


    @FXML
    public void familyMemberSelected(ActionEvent event) {
        ToggleButton buttonFM = ((ToggleButton) (event.getSource()));

        DiceAndFamilyMemberColorEnum colorEnum = DiceAndFamilyMemberColorEnum.valueOf(
                Character.getNumericValue(buttonFM.getId().charAt(2)));

        Platform.runLater(() -> getController().callbackFamilyMemberSelected(thisPlayer.getFamilyMemberByColor(colorEnum)));
    }

    @FXML
    private void towerFloorSelected(ActionEvent event) {
        Button actionSpace = ((Button) (event.getSource()));
        String id = actionSpace.getId();
        int towerIndex = Character.getNumericValue(id.charAt(7));
        int floorIndex = Character.getNumericValue(id.charAt(8));
        Platform.runLater(() -> getController().callbackPlacedFMOnTower(towerIndex, floorIndex));
    }

    /**
     * Shows a window with the list of cards passed as an argument
     * @param cards the cards to show to the user
     */
    private void showCards(List<? extends AbstractCard> cards, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        if(cards.isEmpty()) {
            alert.setHeaderText("No cards to show");
        } else {
            alert.setHeaderText(null);
            //alert.setContentText(errorDescription);

            HBox cardsContainer = new HBox();
            cardsContainer.setSpacing(5);
            cardsContainer.setAlignment(Pos.CENTER);

            for (AbstractCard cardIter : cards) {
                final Image cardImage = new Image(getClass().getResourceAsStream("/imgs/Cards/" + cardIter.getImgName()));
                final ImageView imgView = new ImageView();
                imgView.setImage(cardImage);
                imgView.setPreserveRatio(true);

                cardsContainer.getChildren().add(imgView);
                alert.setGraphic(cardsContainer);
            }
        }
        alert.initStyle(StageStyle.UTILITY);
        //alert.initOwner(currentStage);
        alert.show();
    }

    public void setActiveActionSpaces(Optional<Integer> servantsNeededHarvest,
                                      Optional<Integer> servantsNeededBuild,
                                      Optional<Integer> servantsNeededCouncil,
                                      List<MarketWrapper> activeMarketSpaces,
                                      List<TowerWrapper> activeTowerSpaces) {

        //we set all AS to disabled
        for(int col = 0; col < 4; col++) {
            for(int raw = 0; raw < 4; raw++) {
                Button activeASButton = (Button) (towersCouncilFaith.lookup(("#towerAS" + col) + raw));
                activeASButton.setDisable(true);
            }
        }

        //we reactivatre only the ones passed via parameters
        for(TowerWrapper towerWrapperIter : activeTowerSpaces) {
            Button activeASButton = (Button) (towersCouncilFaith.lookup(("#towerAS" + towerWrapperIter.getTowerIndex()) + towerWrapperIter.getTowerFloor()));
            activeASButton.setDisable(false);
        }
    }

    public void setOrderOfPlayers(List<Player> players) {
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

    public void setPlayersPersonalBoards() {
        ObservableList<Tab> tabs = playersTabPersonalBoard.getTabs();
        tabs.get(0).setText("You (" + thisPlayer.getPlayerColor().getStringValue() + ")");

        for(int i = 1; i <= otherPlayers.size(); i++) {
                tabs.get(i).setText(otherPlayers.get(i-1).getNickname() + " (" + otherPlayers.get(i-1).getPlayerColor().getStringValue() + ")");
        }
        tabs.remove(otherPlayers.size()+1, tabs.size());
    }
}
