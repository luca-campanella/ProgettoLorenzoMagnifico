package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.model.player.PersonalTile;
import it.polimi.ingsw.model.player.PersonalTileEnum;
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

/**
 * Created by campus on 23/06/2017.
 */
public class PersonalTilesPickerControl extends CustomFxControl {

    @FXML
    HBox tilesContainer;

    @FXML
    Button chooseButton;

    ToggleGroup toggleGroup = new ToggleGroup();
    PersonalTile selectedTile;
    PersonalTile standardTile, specialTile;

    public void addTiles(PersonalTile standardTile, PersonalTile specialTile) {
        this.standardTile = standardTile;
        this.specialTile = specialTile;
        addSingleTileToScene(standardTile);
        addSingleTileToScene(specialTile);
    }

    private void addSingleTileToScene(PersonalTile tile) {

        final ToggleButton toggle = new ToggleButton();
        toggle.setToggleGroup(toggleGroup);

        final Image leaderImage  = new Image(getClass().getResourceAsStream("/imgs/PersonalBonusTiles/" + tile.getImgName()));
        final ImageView toggleImage = new ImageView();
        toggle.setGraphic(toggleImage);
        toggleImage.imageProperty().bind(Bindings
                .when(toggle.selectedProperty())
                .then(leaderImage)
                .otherwise(leaderImage) //this shoulb be unselected
        );
        toggleImage.setFitHeight(220);
        toggleImage.setPreserveRatio(true);

        toggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                chooseButton.setDisable(false);
                Object source = e.getSource();
                if (source instanceof ToggleButton) { //should always be true
                    ToggleButton clickedBtn = (ToggleButton) source; // that's the button that was clicked
                    System.out.println(clickedBtn.getId()); // prints the id of the button
                    if(PersonalTileEnum.STANDARD.name().equals(clickedBtn.getId()))
                        selectedTile = standardTile;
                    else
                        selectedTile = specialTile;
                }
            }
        });

        tilesContainer.getChildren().add(toggle);
        toggle.setId(tile.getPersonalTileEnum().name());
        Debug.printVerbose("tile " + tile.getPersonalTileEnum().name() + "added scuccesfully to the window");
    }

    @FXML
    public void buttonChooseClicked(ActionEvent event) {
        Platform.runLater(()-> getController().callbackOnTileChosen(selectedTile));
    }



}
