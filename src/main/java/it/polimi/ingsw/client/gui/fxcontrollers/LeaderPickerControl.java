package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.utils.Debug;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.HashMap;

/**
 * This fx control handles event in the personal tiles choosing window and helps creating it dynamically
 */
public class LeaderPickerControl extends CustomFxControl {

    @FXML
    private HBox leaderContainer;

    @FXML
    private Button chooseButton;

    private HashMap<String, LeaderCard> buttonsLeadersMap = new HashMap<String, LeaderCard>(2);
    private ToggleGroup toggleGroup = new ToggleGroup();
    private LeaderCard selectedLeader;

    /**
     * Add a single leader card to the window, should be called with all leader cards before showing the window
     * @param leader the leader card to add
     */
    public void addLeader(LeaderCard leader) {

        final ToggleButton toggle = new ToggleButton();
        toggle.setToggleGroup(toggleGroup);


        final Image leaderImage  = new Image(getClass().getResourceAsStream("/imgs/Leaders/" + leader.getImgName()));
        final ImageView toggleImage = new ImageView();
        toggle.setGraphic(toggleImage);
        toggleImage.imageProperty().bind(Bindings
                .when(toggle.selectedProperty())
                .then(leaderImage)
                .otherwise(leaderImage) //this should be unselected
        );
        toggleImage.setFitHeight(420);
        /*toggleImage.prefHeight(320);
        toggleImage.maxHeight(320);
        //toggleImage.setFitHeight(320);*/
        toggleImage.setPreserveRatio(true);


        toggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                chooseButton.setDisable(false);
                Object source = e.getSource();
                if (source instanceof ToggleButton) { //should always be true
                    ToggleButton clickedBtn = (ToggleButton) source; // that's the button that was clicked
                    System.out.println(clickedBtn.getId()); // prints the id of the button
                    selectedLeader = buttonsLeadersMap.get(clickedBtn.getId());
                }
            }
        });

        leaderContainer.getChildren().add(toggle);
        leaderContainer.setAlignment(Pos.CENTER);
        toggle.setId(leader.getName());
        buttonsLeadersMap.put(toggle.getId(), leader);
        Debug.printVerbose("leader " + leader.getName() + "added scuccesfully to the window");
    }

    /**
     * Handles the pressing of the button, which corresponds with a choice
     * Calls back the controller with the performed choice
     * @param event the fx event
     */
    @FXML
    public void buttonChooseClicked(ActionEvent event) {
        Platform.runLater(()-> getController().callbackOnLeaderCardChosen(selectedLeader));
    }



}
