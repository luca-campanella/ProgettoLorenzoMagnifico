package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.model.leaders.LeaderCard;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

/**
 * This class controls the AnchorPane that a user sees when clicks "ShowLeader"
 */
public class LeaderOwnedControl extends CustomFxControl{
    ArrayList<LeaderCard> leaderNotUsed;
    List<LeaderCard> leaderActivated;
    List<LeaderCard> leadersPlayable;
    List<LeaderCard> leadersOPRNotActivated;

    @FXML
    private GridPane leadersNotPlayedGridPane;
    @FXML
    private GridPane leadersPlayedGridPane;



    public void setLeaders(ArrayList<LeaderCard> leaderNotUsed, List<LeaderCard> leaderActivated, List<LeaderCard> leadersPlayable, List<LeaderCard> leadersOPRNotActivated) {
        this.leaderNotUsed = leaderNotUsed;
        this.leaderActivated = leaderActivated;
        this.leadersOPRNotActivated = leadersOPRNotActivated;
        this.leadersPlayable = leadersPlayable;
        int numberOfLeaderNotUsed = 0;
        int numberOfLeaderPlayable = 0;
        int numberOfActivatedLeaders = 0;
        int numberOfNotActivateLeaders = 0;
        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 2; k++) {
                if (leaderNotUsed.size() > numberOfLeaderNotUsed) {
                    ToggleButton button = new ToggleButton();
                    GridPane.setConstraints(button, k, i);
                    leadersNotPlayedGridPane.getChildren().add(button);
                    /* setting images inside the button */
                    final Image leaderImage  = new Image(getClass().getResourceAsStream("/imgs/Leaders/" + leaderNotUsed.get(numberOfLeaderNotUsed).getImgName()));
                    final ImageView toggleImage = new ImageView();
                    button.setGraphic(toggleImage);

                    if(!(leadersPlayable.contains(leaderNotUsed.get(numberOfLeaderNotUsed))))
                        button.setDisable(true);

                    numberOfLeaderNotUsed++;
                }

            }
        }
        /*
        numberOfActivatedLeaders = 0;
        numberOfNotActivateLeaders = 0;
        for(int i=0; i<2; i++)
            for(int k=0; k<2; k++)
            {
                if(leaderActivated.size()> numberOfActivatedLeaders)
                {
                    ToggleButton button = new ToggleButton();
                    GridPane.setConstraints(button, k,i);
                }
                else if()
            }

        */
        return;
    }

}
