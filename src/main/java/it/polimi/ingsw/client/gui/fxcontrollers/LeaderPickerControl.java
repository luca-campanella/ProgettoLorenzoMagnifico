package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.utils.Debug;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.HashMap;

/**
 * Created by campus on 23/06/2017.
 */
public class LeaderPickerControl extends CustomFxControl {

    @FXML
    HBox leaderContainer;

    @FXML
    Button chooseButton;

    HashMap<String, LeaderCard> buttonsLeadersMap = new HashMap<String, LeaderCard>(2);
    ToggleGroup toggleGroup = new ToggleGroup();
    LeaderCard selectedLeader;

    public void addLeader(LeaderCard leader) {

        final ToggleButton toggle = new ToggleButton();
        toggle.setToggleGroup(toggleGroup);
       /* System.out.println(getClass());
        System.out.println(getClass().getResource("/LorenzoRitratto.jpg"));
        System.out.println(getClass().getResource("/Leaders/leaders_f_c_01"));*/
        final Image leaderImage  = new Image(getClass().getResourceAsStream("/imgs/Leaders/" + leader.getImgName()));
        final ImageView toggleImage = new ImageView();
        toggle.setGraphic(toggleImage);
        toggleImage.imageProperty().bind(Bindings
                .when(toggle.selectedProperty())
                .then(leaderImage)
                .otherwise(leaderImage) //this shoulb be unselected
        );
        toggleImage.setFitHeight(320);
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
        toggle.setId(leader.getName());
        buttonsLeadersMap.put(toggle.getId(), leader);
        Debug.printVerbose("leader " + leader.getName() + "added scuccesfully to the window");
    }

    @FXML
    public void buttonChooseClicked(ActionEvent event) {
        Platform.runLater(()-> getController().callbackOnLeaderCardChosen(selectedLeader));
    }



}
