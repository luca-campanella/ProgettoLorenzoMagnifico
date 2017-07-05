package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Debug;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import javax.swing.*;
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
    ArrayList<LeaderCard> leaderNotUsed;
    List<LeaderCard> leaderActivated;
    List<LeaderCard> leadersPlayable;
    List<LeaderCard> leadersOPRNotActivated;
    LeaderCard selectedLeader;
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
    private Button activateLeader;
    private ToggleButton lastLeaderButtonClicked;
    private HashMap<String, LeaderCard> buttonsInHandLeadersMap = new HashMap<String, LeaderCard>(2);
    private HashMap<String, LeaderCard> buttonsPlayedLeadersMap = new HashMap<String, LeaderCard>(2);
    private int gridPaneRightRows;
    private int gridPaneRightCols;
    private int iterator;

    public void setLeaders(Player player, ArrayList<LeaderCard> leaderNotUsed, List<LeaderCard> leaderActivated, List<LeaderCard> leadersPlayable, List<LeaderCard> leadersOPRNotActivated) {
        pool = Executors.newCachedThreadPool();
        this.leaderNotUsed = leaderNotUsed;
        this.leaderActivated = leaderActivated;
        this.leadersOPRNotActivated = leadersOPRNotActivated;
        this.leadersPlayable = leadersPlayable;
        this.gridPaneRightCols = 0;
        this.gridPaneRightRows = 0;
        int numberOfLeaderNotUsed = 0;
        int numberOfActivatedLeaders = 0;
        int numberOfNotActivateLeaders = 0;
        this.iterator = 0;
        //this first block prints the left part of the Leaders screen, where leader can be played
        //or discarded
        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 2; k++) {
                if (leaderNotUsed.size() > numberOfLeaderNotUsed) {
                    ToggleButton button = new ToggleButton();
                    GridPane.setConstraints(button, k, i);
                    leadersNotPlayedGridPane.getChildren().add(button);
                    /* setting images inside the button */
                    final Image leaderImage = new Image(getClass().getResourceAsStream("/imgs/Leaders/" + leaderNotUsed.get(numberOfLeaderNotUsed).getImgName()));
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
                    button.setId(leaderNotUsed.get(numberOfLeaderNotUsed).getName());
                    button.setOnAction(new EventHandler<ActionEvent>() {
                                           @Override
                                           public void handle(ActionEvent e) {
                                               lastLeaderButtonClicked = (ToggleButton) e.getSource();
                                               selectedLeader = buttonsInHandLeadersMap.get(lastLeaderButtonClicked.getId());
                                               discardLeaderButton.setDisable(false);
                                               Debug.printVerbose("Hello i'm outside THE if" + player.getNickname());
                                               if (selectedLeader.isPlayable(player)) {
                                                   playLeaderButton.setDisable(false);
                                                   Debug.printVerbose("Hello i'm inside THE if");
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
    }

    public void refreshLeaders(ArrayList<LeaderCard> leaderNotUsed, List<LeaderCard> leaderActivated, List<LeaderCard> leadersPlayable, List<LeaderCard> leadersOPRNotActivated)
    {
        this.leaderNotUsed = leaderNotUsed;
        this.leaderActivated = leaderActivated;
        this.leadersOPRNotActivated = leadersOPRNotActivated;
        this.leadersPlayable = leadersPlayable;
        refreshLeadersView();
    }
    private void refreshLeadersView()
    {
        return;
    }
    private void updateGridBounds()
    {
        iterator ++;
        if(iterator == 1)
        {
            gridPaneRightRows = 1;
            gridPaneRightCols = 0;
        }else if(iterator == 2) {
            gridPaneRightCols = 1;
            gridPaneRightRows = 0;
        }
        else {
            gridPaneRightCols = 1;
            gridPaneRightRows = 1;
        }
    }
    private void addPlayedLeader(LeaderCard leaderCard){
        ToggleButton button = new ToggleButton();
        GridPane.setConstraints(button, gridPaneRightRows, gridPaneRightCols);
        updateGridBounds();
        leadersPlayedGridPane.getChildren().add(button);
                    /* setting images inside the button */
        final Image leaderImage = new Image(getClass().getResourceAsStream("/imgs/Leaders/" + leaderCard.getImgName()));
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
        button.setId(leaderCard.getName());
        button.setOnAction(new EventHandler<ActionEvent>() {
                               @Override
                               public void handle(ActionEvent e) {
                                   lastLeaderButtonClicked = (ToggleButton) e.getSource();
                                   selectedLeader = buttonsInHandLeadersMap.get(lastLeaderButtonClicked.getId());

                                   if (leadersOPRNotActivated.contains(leaderCard)) {
                                       activateLeader.setDisable(false);
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

        pool.submit(()-> getController().callbackDiscardLeader(selectedLeader));

        discardLeaderButton.setDisable(true);

    }

    @FXML
    public void playLeaderClick(ActionEvent event) {
        selectedLeader = buttonsInHandLeadersMap.get(lastLeaderButtonClicked.getId());
        buttonsInHandLeadersMap.remove(lastLeaderButtonClicked.getId());
        lastLeaderButtonClicked.setVisible(false);
        addPlayedLeader(selectedLeader);

        pool.submit(()-> getController().callbackPlayLeader(selectedLeader));
        playLeaderButton.setDisable(true);
    }

}