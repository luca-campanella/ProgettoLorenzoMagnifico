package it.polimi.ingsw.client.gui.blockingdialogs;

import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.utils.Debug;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * This class is used to handle Lorenzo Il magnifico effect.
 */
public class AskWhichLeaderAbilityToCopyDialog implements Callable<Integer> {

    private ToggleGroup toggleGroup = new ToggleGroup();
    private List<ToggleButton> leadersButtons;
    private List<LeaderCard> possibleLeaders;
    private final String leadersPath = "/imgs/Leaders/";
    private LeaderCard selectedLeader;
    private HashMap<String, LeaderCard> leadersButtonsMap = new HashMap<>();
    private Stage stage;

    public AskWhichLeaderAbilityToCopyDialog(List<LeaderCard> possibleLeaders, Stage stage) {
        this.possibleLeaders = possibleLeaders;
        this.stage = stage;
    }

    private ButtonType buttonChoice;

    @Override
    public Integer call() throws Exception {

        Debug.printError("AskWhichLeader");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Lorenzo's effect");
        alert.setHeaderText("Select a Leader to Copy");
        alert.initOwner(stage);
        stage.setAlwaysOnTop(true);

        Debug.printError("AskWhichLeader");
        HBox cardsContainer = new HBox();
        cardsContainer.setSpacing(5);
        cardsContainer.setAlignment(Pos.CENTER);

        Debug.printError("AskWhichLeader");
        for (LeaderCard iterator : possibleLeaders)
            createButtonLeader(iterator);
        Debug.printError("AskWhichLeader");
        cardsContainer.getChildren().setAll(leadersButtons);
        buttonChoice = new ButtonType("SubmitChoice");
        alert.getButtonTypes().setAll(buttonChoice);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonChoice)
            if (selectedLeader != null)
                return (possibleLeaders.indexOf(selectedLeader));
            else {
                Debug.printError("User didn't select a Leader,ask he will receive a random LeaderEffect from the one possible");
            }
        Debug.printError("AskWhichLeader");
        return 0;
    }

    private void createButtonLeader(LeaderCard leaderCard) {

        Debug.printVerbose("Im setting The Leaders ask" + leaderCard.getName());
        ToggleButton button = new ToggleButton();
        button.setToggleGroup(toggleGroup);
                    /* setting images inside the button */
        final Image leaderImage = new Image(getClass().getResourceAsStream(leadersPath + leaderCard.getName()));
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
                                   ToggleButton button = (ToggleButton) e.getSource();
                                   selectedLeader = leadersButtonsMap.get(button.getId());
                               }
                           }
        );
        leadersButtonsMap.put(button.getId(), leaderCard);
        leadersButtons.add(button);
    }

}