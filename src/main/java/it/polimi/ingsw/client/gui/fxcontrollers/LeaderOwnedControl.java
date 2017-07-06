package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Debug;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//todo: leaders: add play / activate / discard effects
//todo: check bug (2x leaders)
//todo: check if right part is filled
//todo: hide / show the window properly
/**
 * This class controls the AnchorPane that a user sees when clicks "ShowLeader"
 */
public class LeaderOwnedControl extends CustomFxControl {
    private Player thisPlayer;
    private List<LeaderCard> leaderNotUsed;
    private List<LeaderCard> leaderActivated;
    private List<LeaderCard> leadersPlayable;
    private List<LeaderCard> leadersOPRNotActivated;
    private LeaderCard selectedLeader;
    private ExecutorService pool;
    @FXML
    private GridPane leadersNotPlayedGridPane;
    @FXML
    private GridPane leadersPlayedGridPane;
    @FXML
    private Button discardLeaderButton;
    @FXML
    private Button playLeaderButton;
    @FXML
    private Button activateLeaderButton;
    private ToggleGroup toggleGroupLeaders = new ToggleGroup();

    private ToggleButton lastLeaderButtonClicked;
    private HashMap<String, LeaderCard> buttonsInHandLeadersMap = new HashMap<>(2);
    private HashMap<String, LeaderCard> buttonsPlayedLeadersMap = new HashMap<>(2);
    private int gridPaneRightRows;
    private int gridPaneRightCols;
    private int iteratorGridPaneRight;
    private final String leadersPath = "/imgs/Leaders/";

    public void setLeaders(Player player, List<LeaderCard> leaderNotUsed, List<LeaderCard> leaderActivated, List<LeaderCard> leadersPlayable, List<LeaderCard> leadersOPRNotActivated) {
        pool = Executors.newCachedThreadPool();
        this.thisPlayer = player;
        this.leaderNotUsed = leaderNotUsed;
        this.leaderActivated = leaderActivated;
        this.leadersOPRNotActivated = leadersOPRNotActivated;
        this.leadersPlayable = leadersPlayable;
        this.gridPaneRightCols = 0;
        this.gridPaneRightRows = 0;
        int numberOfLeaderNotUsed = 0;
        int numberOfActivatedLeaders = 0;
        int numberOfNotActivateLeaders = 0;
        this.iteratorGridPaneRight = 0;
        //this first block prints the left part of the Leaders screen, where leader can be played
        //or discarded
        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 2; k++) {
                if (leaderNotUsed.size() > numberOfLeaderNotUsed) {
                    Debug.printVerbose("Hello, im setting The Leaders");
                    ToggleButton button = (ToggleButton) leadersNotPlayedGridPane.lookup("#leaderNotPlayed" + i + k);
                    button.setToggleGroup(toggleGroupLeaders);
                    /* setting images inside the button */
                    final Image leaderImage = new Image(getClass().getResourceAsStream(leadersPath + leaderNotUsed.get(numberOfLeaderNotUsed).getImgName()));
                    final ImageView toggleImage = new ImageView();
                    button.setGraphic(toggleImage);
                    toggleImage.imageProperty().bind(Bindings
                            .when(button.selectedProperty())
                            .then(leaderImage)
                            .otherwise(leaderImage) //this should be unselected
                    );
                    toggleImage.setFitHeight(320);
                    toggleImage.setPreserveRatio(true);
                    button.setDisable(false);
                    button.setOnAction(new EventHandler<ActionEvent>() {
                                           @Override
                                           public void handle(ActionEvent e) {
                                               lastLeaderButtonClicked = (ToggleButton) e.getSource();
                                               Debug.printVerbose(lastLeaderButtonClicked.getId());
                                               selectedLeader = buttonsInHandLeadersMap.get(lastLeaderButtonClicked.getId());
                                               discardLeaderButton.setDisable(false);
                                               try{
                                               if (selectedLeader.isPlayable(player)) {
                                                   playLeaderButton.setDisable(false);
                                               }
                                               else{
                                                   playLeaderButton.setDisable(true);
                                               }}
                                               catch(Exception exception)
                                               {
                                                   Debug.printVerbose("Avoided crush on leaders");
                                               }
                                           }
                                       }
                    );

                    buttonsInHandLeadersMap.put(button.getId(), leaderNotUsed.get(numberOfLeaderNotUsed));
                    Debug.printVerbose("leader " + leaderNotUsed.get(numberOfLeaderNotUsed).getName() + "added succesfully to the window");
                    numberOfLeaderNotUsed++;
                }
            }
        }
        for(int rows = 0; rows < 2; rows++)
            for(int cols = 0; cols < 2; cols++)
            {
                if (leadersOPRNotActivated.size() > numberOfNotActivateLeaders) {
                    Debug.printVerbose("Hello, im setting The Leaders played");
                    ToggleButton button = (ToggleButton) leadersPlayedGridPane.lookup("#leaderNotPlayed" + rows + cols);
                    button.setToggleGroup(toggleGroupLeaders);
                    /* setting images inside the button */
                    final Image leaderImage = new Image(getClass().getResourceAsStream(leadersPath + leaderNotUsed.get(numberOfLeaderNotUsed).getImgName()));
                    final ImageView toggleImage = new ImageView();
                    button.setGraphic(toggleImage);
                    toggleImage.imageProperty().bind(Bindings
                            .when(button.selectedProperty())
                            .then(leaderImage)
                            .otherwise(leaderImage) //this should be unselected
                    );
                    toggleImage.setFitHeight(320);
                    toggleImage.setPreserveRatio(true);
                    button.setDisable(false);
                    button.setOnAction(new EventHandler<ActionEvent>() {
                                           @Override
                                           public void handle(ActionEvent e) {
                                               lastLeaderButtonClicked = (ToggleButton) e.getSource();
                                               Debug.printVerbose(lastLeaderButtonClicked.getId());
                                               selectedLeader = buttonsInHandLeadersMap.get(lastLeaderButtonClicked.getId());
                                               try{
                                               discardLeaderButton.setDisable(true);
                                               playLeaderButton.setDisable(true);
                                               activateLeaderButton.setDisable(false);}
                                               catch(Exception exception)
                                               {
                                                   Debug.printVerbose("Avoided crush on leaders");
                                               }
                                           }
                                       }
                    );

                    buttonsPlayedLeadersMap.put(button.getId(), leaderNotUsed.get(numberOfNotActivateLeaders));
                    Debug.printVerbose("leader " + leaderNotUsed.get(numberOfNotActivateLeaders).getName() + "added succesfully to the window");
                    numberOfNotActivateLeaders++;
                }
            }
    }



    public void refreshLeadersCollections(Player player, List<LeaderCard> leaderNotUsed, List<LeaderCard> leaderActivated, List<LeaderCard> leadersPlayable, List<LeaderCard> leadersOPRNotActivated)
    {
        this.leaderNotUsed = leaderNotUsed;
        this.leaderActivated = leaderActivated;
        this.leadersOPRNotActivated = leadersOPRNotActivated;
        this.leadersPlayable = leadersPlayable;
        //todo: remove this
        for(LeaderCard iterator : leadersOPRNotActivated)
            Debug.printVerbose(iterator.getName());
        /*
        if(thisPlayer.equals(player))
            ;
            //refreshLeadersCurrentPlayerView();
        else
            refreshLeadersOtherPlayerView(player);*/
        refreshLeadersOtherPlayerView(player);
    }

    /**
     * this method just refreshes all the leaders buttons
     */
    private void refreshLeadersCurrentPlayerView()
    {
        //todo check if leaderNotUsed and leadersPlayable doesn't overlap and make errors....
        setGenericViewButtons(leaderNotUsed, leadersNotPlayedGridPane, false, false, true, true);
        setGenericViewButtons(leadersPlayable, leadersNotPlayedGridPane, false, false, false, true);
        setGenericViewButtons(leadersOPRNotActivated, leadersPlayedGridPane, false, true, true, false);
        setGenericViewButtons(leaderActivated, leadersPlayedGridPane, true, true, true, true);
        Debug.printVerbose("Refresh called");

    }
    private void refreshLeadersOtherPlayerView(Player player){
        for(LeaderCard iterator : leaderNotUsed)
            Debug.printVerbose(iterator.getName() + " ");
        for(LeaderCard iterator : leaderActivated)
            Debug.printVerbose(iterator.getName() + " " + iterator.getName());

        Debug.printVerbose("Ma ci entro qua?" + player.getNickname());

    }

    /**
     * this method is used to refresh a leader collection
     * @param leaderList is the list of leader to refresh
     * @param paneToLook is the grid where to find the buttons
     * @param card true if i want to set the card disable, false if i don't
     * @param discard true if i want to set the
     * @param play is true when playButton is disabled
     * @param activate is false when  activateButton is enabled
     */
    private void setGenericViewButtons(List<LeaderCard> leaderList,GridPane paneToLook, boolean card, boolean discard, boolean play, boolean activate)
    {

        for(LeaderCard iterator : leaderList) {
            String nameToLook = ("#" + iterator.getName());
            ToggleButton temp = (ToggleButton)(paneToLook.lookup( nameToLook));
            for(Node it : paneToLook.getChildren())
                Debug.printVerbose(it.getId() + " ");
            Debug.printVerbose(iterator.getName() + " " +  nameToLook + paneToLook.toString() + " " + paneToLook.getChildren().toString());
            temp.setDisable(card);
            discardLeaderButton.setDisable(discard);
            playLeaderButton.setDisable(play);
            activateLeaderButton.setDisable(activate);
        }

    }

    private void updateGridBounds()
    {
        iteratorGridPaneRight++;
        if(iteratorGridPaneRight == 1)
        {
            gridPaneRightRows = 1;
            gridPaneRightCols = 0;
        }else if(iteratorGridPaneRight == 2) {
            gridPaneRightCols = 1;
            gridPaneRightRows = 0;
        }
        else {
            gridPaneRightCols = 1;
            gridPaneRightRows = 1;
        }
    }

    private void addPlayedLeader(LeaderCard leaderCard){
        ToggleButton button = (ToggleButton)leadersPlayedGridPane.lookup("#leaderPlayed" + gridPaneRightRows + gridPaneRightCols);
        updateGridBounds();

        /* setting images inside the button */
        final Image leaderImage = new Image(getClass().getResourceAsStream(leadersPath + leaderCard.getImgName()));
        final ImageView toggleImage = new ImageView();
        button.setToggleGroup(toggleGroupLeaders);

        button.setGraphic(toggleImage);
        toggleImage.imageProperty().bind(Bindings
                .when(button.selectedProperty())
                .then(leaderImage)
                .otherwise(leaderImage) //this should be unselected
        );
        toggleImage.setFitHeight(320);
        toggleImage.setPreserveRatio(true);
        button.setDisable(false);
        button.setOnAction(new EventHandler<ActionEvent>() {
                               @Override
                               public void handle(ActionEvent e) {
                                   lastLeaderButtonClicked = (ToggleButton) e.getSource();
                                   selectedLeader = buttonsInHandLeadersMap.get(lastLeaderButtonClicked.getId());
                                    discardLeaderButton.setDisable(true);
                                   if (leadersOPRNotActivated.contains(leaderCard)) {
                                       activateLeaderButton.setDisable(false);
                                   }
                               }
                           }
        );

        buttonsInHandLeadersMap.put(button.getId(), leaderCard);
        Debug.printVerbose("leader " + leaderCard.getName() + "added succesfully to the window played Leaders");

    }

    private void loadImageLeader(int rows, int cols, GridPane gridPane, String imgName)
    {
        ToggleButton button = new ToggleButton();
        GridPane.setConstraints(button, rows, cols);
        gridPane.getChildren().add(button);
        final Image leaderImage = new Image(getClass().getResourceAsStream("/imgs/Leaders/" + imgName));
        final ImageView toggleImage = new ImageView();
        button.setGraphic(toggleImage);
        toggleImage.imageProperty().bind(Bindings
                .when(button.selectedProperty())
                .then(leaderImage)
                .otherwise(leaderImage) //this should be unselected
        );
        toggleImage.setFitHeight(320);
        toggleImage.setPreserveRatio(true);
    }


    @FXML
    public void discardLeaderClick(ActionEvent event) {

        //buttonsInHandLeadersMap.values().remove(selectedLeader);
        selectedLeader = buttonsInHandLeadersMap.get(lastLeaderButtonClicked.getId());
        buttonsInHandLeadersMap.remove(lastLeaderButtonClicked.getId());
        lastLeaderButtonClicked.setDisable(true);
        leaderNotUsed.remove(selectedLeader);

        pool.submit(()-> getController().callbackDiscardLeader(selectedLeader));

        discardLeaderButton.setDisable(true);

    }

    @FXML
    public void playLeaderClick(ActionEvent event) {
        selectedLeader = buttonsInHandLeadersMap.get(lastLeaderButtonClicked.getId());
        buttonsInHandLeadersMap.remove(lastLeaderButtonClicked.getId());
        lastLeaderButtonClicked.setVisible(false);
        addPlayedLeader(selectedLeader);

        //leaderNotUsed.remove(selectedLeader);
        //leadersOPRNotActivated.add(selectedLeader);

        pool.submit(()-> getController().callbackPlayLeader(selectedLeader));
        playLeaderButton.setDisable(true);
    }
    @FXML
    public void activateLeaderClick(ActionEvent event)
    {
        lastLeaderButtonClicked.setDisable(true);
        //i could updateLists here
        pool.submit(()-> getController().callbackPlayLeader(selectedLeader));
        activateLeaderButton.setDisable(true);
    }

}