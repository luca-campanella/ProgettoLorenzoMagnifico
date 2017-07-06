package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.cards.TerritoryCard;
import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.utils.Debug;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

/**
 * This class represents the controller with all the graphical informations related to a tab of a certain player
 */
public class PlayerTabSubControl extends CustomFxControl {

    private Player player;
    private PersonalBoard personalBoard;
    /**
     * true if the tab is linked with the player which controls the client
     */
    private boolean isThisPlayer = false;

    /**
     * true if the stage for the leaders is already created, false otherwise
     */
    private boolean isLeaderStageCreated;

    /**
     * this stage represents the stage of the windows that displays leaders
     */
    private Stage leadersStage;

    /**
     * this control is the fx control of the leaders window
     * //todo change controller name if it's the same for both scenes, or make different instances
     */
    private LeaderOwnedControl leadersControl;

    @FXML
    private ImageView thisPlayerPersonalTile;

    @FXML
    private ImageView victoryPointImage;

    @FXML
    private HBox personalBoardPane;



    @FXML
    private Button blueCardsButton;

    @FXML
    private Button purpleCardsButton;

    @FXML
    private Button passTurnButton;

    @FXML
    private ToolBar buttonsToolBar;


    public PlayerTabSubControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PlayerTabSubScene.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());



        try {
            Debug.printVerbose("before running load");
            fxmlLoader.load();
            Debug.printVerbose("after running load");
        } catch (IOException exception) {
            Debug.printError("Exception caught");
            Debug.printError(exception);
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }

    /**
     * This method is used at the beginning of the game when we have to setup a tab for each player with his information
     * @param controller the controller for callbacks
     * @param relatedPlayer the player related to the tab
     * @param isThisPlayer true if the tab is linked with the player which controls the client
     */
    public void setUpTab(ViewControllerCallbackInterface controller, Player relatedPlayer, boolean isThisPlayer) {
        Debug.printVerbose("setUpTab called");
        this.isThisPlayer = isThisPlayer;
        setController(controller);
        setRelatedPlayer(relatedPlayer);
        personalBoard = player.getPersonalBoard();
        //setFixedImages();
        setPersonalTile();
        refreshResourcesAndCards();
        if(!isThisPlayer) {
            buttonsToolBar.getItems().remove(passTurnButton);
        }
    }

    /**
     * This method is used to refresh the tab after the player performed an action
     */
    public void refreshResourcesAndCards(){
        displayCards(personalBoard.getYellowBuildingCards());
        displayCards(personalBoard.getTerritoryCards());
        displayResources();
        //we enable or disable the buttons to see blue and purple cards if the player has or has not some of them
        purpleCardsButton.setDisable((personalBoard.getNumberOfColoredCard(CardColorEnum.PURPLE) == 0));
        blueCardsButton.setDisable((personalBoard.getNumberOfColoredCard(CardColorEnum.BLUE) == 0));
    }

    /**
     * This method it is called to refresh the leader window
     */
    public void refreshLeaderCards() {
        //todo check if this method is right
        leadersControl.refreshLeadersCollections(
                player.getLeaderCardsNotUsed(),
                player.getPlayedLeaders(),
                player.getPlayableLeaders(),
                player.getPlayedNotActivatedOncePerRoundLeaderCards());
    }

    private void displayCards(List<? extends AbstractCard> cards){
        for(int iterator = 0; iterator < cards.size(); iterator++) {
            StringBuilder imageViewId = new StringBuilder();
            if(cards.get(iterator) instanceof TerritoryCard){
                imageViewId.append("#territoryCard");
            }
            else {
                imageViewId.append("#buildingCard");
            }
            ImageView imgView = ((ImageView) (personalBoardPane.lookup(imageViewId.toString() + String.valueOf(iterator))));
            Image cardImg = new Image(getClass().getResourceAsStream(
                    "/imgs/Cards/" + cards.get(iterator).getImgName()));
            Debug.printVerbose(cardImg.toString());
            Debug.printVerbose(imgView.toString());
            imgView.setImage(cardImg);
            imgView.setPreserveRatio(true);
        }

    }
    private void displayResources(){
        //todo: change style of the resources

        Debug.printVerbose("Hello, i'm here" + player.toString());

        ((Label)personalBoardPane.lookup("#coinLabel")).setText(String.valueOf(player.getResource(ResourceTypeEnum.COIN)));
        ((Label)personalBoardPane.lookup("#woodLabel")).setText(String.valueOf(player.getResource(ResourceTypeEnum.WOOD)));
        ((Label)personalBoardPane.lookup("#stoneLabel")).setText(String.valueOf(player.getResource(ResourceTypeEnum.STONE)));
        ((Label)personalBoardPane.lookup("#servantsLabel")).setText(String.valueOf(player.getResource(ResourceTypeEnum.SERVANT)));
        ((Label)personalBoardPane.lookup("#militaryLabel")).setText(String.valueOf(player.getResource(ResourceTypeEnum.MILITARY_POINT)));
        ((Label)personalBoardPane.lookup("#victoryLabel")).setText(String.valueOf(player.getResource(ResourceTypeEnum.VICTORY_POINT)));

    }
    /**
     * Sets the instance of the player related to this tab
     * @param player
     */
    private void setRelatedPlayer(Player player) {
        this.player = player;
    }
        //todo delete
    /*
    private void setFixedImages(){
        Debug.printVerbose("setUpFixedImage called");

        Image tileImg  = new Image(getClass().getResourceAsStream("/imgs/victory_point.png"));
        victoryPointImage.setImage(tileImg);
        victoryPointImage.setPreserveRatio(false);
    }*/
    /**
     * Sets the personal tile image of the related player
     */
    private void setPersonalTile() {
        Debug.printVerbose("setUpPersonalTIles called");
        Debug.printVerbose(player.getPersonalBoard().getPersonalTile().getImgName());
        Image tileImg  = new Image(getClass().getResourceAsStream("/imgs/PersonalBonusTiles/Long/" +
                player.getPersonalBoard().getPersonalTile().getImgName()));
        thisPlayerPersonalTile.setImage(tileImg);
        thisPlayerPersonalTile.setPreserveRatio(true);
    }

    public void setEndTurnButtonDisable(boolean disabled) {
        passTurnButton.setDisable(disabled);
    }



    @FXML
    public void showPurpleCards() {
        showCards(player.getPersonalBoard().getCardListByColor(CardColorEnum.PURPLE), "Purple cards");
    }

    @FXML
    public void showBlueCards() {
        showCards(player.getPersonalBoard().getCardListByColor(CardColorEnum.BLUE), "Blue cards");
    }

    @FXML
    public void passTurn()
    {
        Platform.runLater(()-> getController().callBackPassTheTurn());
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

    /**
     * This method opens the leader window shows it. It also sets the controller for the callbacks inside the custom fx controller
     * This method should be passed as a parameter to the runLater fx method
     * @param fxmlFileName the fxml to start from
     * @param title the title of the window
     */
    private void openLeadersWindow(String fxmlFileName, String title, Runnable runBeforeShow) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/"+fxmlFileName));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            Debug.printError("Error in loading fxml", e);
        }
        leadersStage = new Stage();
        leadersControl = (LeaderOwnedControl) (fxmlLoader.getController());

        leadersControl.setController(getController());

        leadersStage.setTitle(title);
        leadersStage.setScene(new Scene(root, -1, -1, true, SceneAntialiasing.BALANCED));

        if(runBeforeShow != null) //there is something to run
            runBeforeShow.run();

        leadersStage.show();
    }


    public void setLeadersActionsDisable(boolean disabled) {
        //todo disable the possibility to play leaders, but not the button to show leaders
    }
    /**
     * This method responds to the pressing of the leader button by creating and showing the leader window
     */
    @FXML
    private void showLeaderCards()
    {
        if(!isLeaderStageCreated) {
            final String fxmlFileName;
            if(isThisPlayer)
                fxmlFileName = "LeaderOwnedScene.fxml";
            else
                fxmlFileName = "LeaderOtherPlayersScene.fxml";

            Platform.runLater(() -> this.openLeadersWindow(fxmlFileName, "Leaders",
                    () -> leadersControl.setLeaders(player,
                            player.getLeaderCardsNotUsed(),
                            player.getPlayedLeaders(),
                            player.getPlayableLeaders(),
                            player.getPlayedNotActivatedOncePerRoundLeaderCards())));
            Debug.printVerbose("runLater loaded");
            isLeaderStageCreated = true;
        }
        else {
            leadersControl.refreshLeadersCollections(
                    player.getLeaderCardsNotUsed(),
                    player.getPlayedLeaders(),
                    player.getPlayableLeaders(),
                    player.getPlayedNotActivatedOncePerRoundLeaderCards());
            leadersStage.show();
        }
    }


}
