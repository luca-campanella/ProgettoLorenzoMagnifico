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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class LeaderOtherControl extends CustomFxControl {
    private Player tabPlayer;
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

        showCardsOnGridPane(leadersPlayedGridPane, leadersRightPane, player, "#leaderPlayed");

    }


    private void showCardsOnGridPane(GridPane gridPane, List<LeaderCard> leaders, Player player, String leaderSituation)
    {
        int numberOfLeaderNotUsed = 0;
        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 2; k++) {
                if (leaders.size() > numberOfLeaderNotUsed) {
                        Debug.printVerbose("Hello, im setting The Leaders");
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
                                                   ;
                                                   }}
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
        if(tabPlayer.equals(player))
            ;
            //refreshLeadersCurrentPlayerView();
        else
            refreshLeadersOtherPlayerView(player);*/
        //refreshLeadersOtherPlayerView(player);
    }


    /**
     * this method just refreshes all the leaders buttons
     */
   /* private void refreshLeadersCurrentPlayerView()
    {
        //todo check if leaderNotUsed and leadersPlayable doesn't overlap and make errors....
        setGenericViewButtons(leaderNotUsed, leadersNotPlayedGridPane, false, false, true, true);
        setGenericViewButtons(leadersPlayable, leadersNotPlayedGridPane, false, false, false, true);
        setGenericViewButtons(leadersOPRNotActivated, leadersPlayedGridPane, false, true, true, false);
        setGenericViewButtons(leaderActivated, leadersPlayedGridPane, true, true, true, true);
        Debug.printVerbose("Refresh called");
    }
*/
    private void refreshLeadersOtherPlayerView(Player player){
        for(LeaderCard iterator : leaderNotUsed)
            Debug.printVerbose(iterator.getName() + " ");
        for(LeaderCard iterator : leaderActivated)
            Debug.printVerbose(iterator.getName() + " " + iterator.getName());

        Debug.printVerbose("Ma ci entro qua?" + player.getNickname());

    }



}
