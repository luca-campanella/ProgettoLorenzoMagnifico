package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.model.leaders.LeaderCard;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
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
        int numberOfActivatedLeaders = 0;
        int numberOfNotActivateLeaders = 0;
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
                    if (!(leadersPlayable.contains(leaderNotUsed.get(numberOfLeaderNotUsed))))
                        button.setDisable(true);

                    numberOfLeaderNotUsed++;
                }

            }
        }

        numberOfActivatedLeaders = 0;
        numberOfNotActivateLeaders = 0;

        for (int rows = 0; rows < 2; rows++) {
            for (int cols = 0; cols < 2; cols++) {
                if (leadersOPRNotActivated.size() > numberOfNotActivateLeaders) {
                    /* setting images inside the button */
                    loadImageLeader(rows, cols,leadersPlayedGridPane , leadersOPRNotActivated.get(numberOfLeaderNotUsed).getImgName());
                    numberOfNotActivateLeaders++;
                } else if (leaderActivated.size() > numberOfActivatedLeaders) {
                    /* setting images inside the button */
                    loadImageLeader(rows,cols, leadersPlayedGridPane, leaderActivated.get(numberOfLeaderNotUsed).getImgName());
                    numberOfActivatedLeaders++;
                }
                //todo: ask campanella if leaderActivated have the same logic

            }

        }



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

}