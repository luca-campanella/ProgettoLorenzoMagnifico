package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Debug;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

/**
 * This class represents the controller with all the graphical informations related to a tab of a certain player
 */
public class PlayerTabSubControl extends Tab {

    Player player;

    @FXML
    ImageView thisPlayerPersonalTile;

    public PlayerTabSubControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "PlayerTabSubScene.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Sets the instance of the player related to this tab
     * @param player
     */
    public void setRelatedPlayer(Player player) {
        this.player = player;
    }

    /**
     * Sets the personal tile image of the related player
     */
    public void setPersonalTile() {
        Debug.printVerbose("setUpPersonalTIles called");
        Debug.printVerbose(player.getPersonalBoard().getPersonalTile().getImgName());
        Image tileImg  = new Image(getClass().getResourceAsStream("/imgs/PersonalBonusTiles/Long/" +
                        player.getPersonalBoard().getPersonalTile().getImgName()));
        thisPlayerPersonalTile.setImage(tileImg);
        thisPlayerPersonalTile.setPreserveRatio(true);
    }

}
