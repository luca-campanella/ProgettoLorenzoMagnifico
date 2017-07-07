package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.leaders.leadersabilities.CopyAnotherLeaderAbility;
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

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        int numberOfActivatedLeaders = 0;
        int numberOfNotActivateLeaders = 0;
        this.iteratorGridPaneRight = 0;
        //this first block prints the left part of the Leaders screen, where leader can be played
        //or discarded

        ArrayList<LeaderCard> leadersLeftPane = new ArrayList<>(4);
        ArrayList<LeaderCard> leadersRightPane = new ArrayList<>(4);
        Set<LeaderCard> temp = new HashSet<>(4);


        temp.addAll(leaderNotUsed);
        leadersLeftPane.addAll(temp);
        showCardsOnGridPane(leadersNotPlayedGridPane, leadersLeftPane, player, "#leaderNotPlayed");

        temp.clear();
        temp.addAll(leaderActivated);
        temp.addAll(leadersOPRNotActivated);
        leadersRightPane.addAll(temp);

        printList(leadersLeftPane);
        printList(leadersRightPane);

        showCardsOnGridPane(leadersPlayedGridPane, leadersRightPane, player, "#leaderPlayed");
        //disablePlayedLeaderCards(leadersPlayedGridPane, leaderActivated);

    }
    //todo: remove this
    //this is for Debug

    private void printList(ArrayList<LeaderCard> leaderCards)
    {
        Debug.printVerbose("Im inside printList and i'm going to print all the Leaders");
        for(LeaderCard iterator : leaderCards)
            Debug.printVerbose("First leader printed:" + iterator.getName() + " ");
    }

    private void showCardsOnGridPane(GridPane gridPane, List<LeaderCard> leaders, Player player, String leaderSituation)
    {
        int numberOfLeaderNotUsed = 0;
        Debug.printVerbose(leaders.size() + " Im starting to print stuff");
        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 2; k++) {
                if (leaders.size() > numberOfLeaderNotUsed) {

                    Debug.printVerbose("Im setting The Leaders" + leaders.get(numberOfLeaderNotUsed).getName() + i + k);
                    ToggleButton button = (ToggleButton) gridPane.lookup(leaderSituation + i + k);
                    button.setToggleGroup(toggleGroupLeaders);
                    /* setting images inside the button */
                    final Image leaderImage = new Image(getClass().getResourceAsStream(leadersPath + leaders.get(numberOfLeaderNotUsed).getImgName()));
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
                                               selectedLeader = buttonsInHandLeadersMap.get(lastLeaderButtonClicked.getId());
                                               if(gridPane.equals(leadersNotPlayedGridPane)) {
                                                   discardLeaderButton.setDisable(false);
                                                   playLeaderButton.setDisable(true);
                                                   if (selectedLeader.isPlayable(player))
                                                       playLeaderButton.setDisable(false);
                                                   activateLeaderButton.setDisable(true);
                                               }
                                               else
                                               {
                                                   discardLeaderButton.setDisable(true);
                                                   playLeaderButton.setDisable(true);
                                                   activateLeaderButton.setDisable(true);
                                                   if(leadersOPRNotActivated.contains(selectedLeader) && !(leaderActivated.contains(selectedLeader)))
                                                       activateLeaderButton.setDisable(false);
                                               }
                                               if((selectedLeader.getAbility() instanceof CopyAnotherLeaderAbility) && (gridPane.equals(leadersPlayedGridPane))){
                                                   activateLeaderButton.setDisable(false);
                                               }
                                           }
                                       }
                    );

                    buttonsInHandLeadersMap.put(button.getId(), leaders.get(numberOfLeaderNotUsed));
                    Debug.printVerbose("leader " + leaders.get(numberOfLeaderNotUsed).getName() + "added succesfully to the window");
                    numberOfLeaderNotUsed++;
                }
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
        //refreshLeadersOtherPlayerView(player);
    }


    /**
     * this method just refreshes all the leaders buttons
     */
    private void refreshLeadersCurrentPlayerView()
    {

        setGenericViewButtons(leaderNotUsed, leadersNotPlayedGridPane, false, false, true, true);
        setGenericViewButtons(leadersPlayable, leadersNotPlayedGridPane, false, false, false, true);
        setGenericViewButtons(leadersOPRNotActivated, leadersPlayedGridPane, false, true, true, false);
        setGenericViewButtons(leaderActivated, leadersPlayedGridPane, true, true, true, true);
        Debug.printVerbose("Refresh called");

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
        pool.submit(()-> getController().callbackPlayLeader(selectedLeader));
        playLeaderButton.setDisable(true);
        //todo eliminate this
        if((selectedLeader.getAbility() instanceof CopyAnotherLeaderAbility)){
            activateLeaderButton.setDisable(false);
        }
    }
    @FXML
    public void activateLeaderClick(ActionEvent event)
    {
        lastLeaderButtonClicked.setDisable(true);
        leadersOPRNotActivated.remove(selectedLeader);
        leaderActivated.add(selectedLeader);
        pool.submit(()-> getController().callbackPlayLeader(selectedLeader));
        activateLeaderButton.setDisable(true);
    }

}