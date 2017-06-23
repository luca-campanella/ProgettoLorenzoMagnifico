package it.polimi.ingsw.client.gui.fxcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * This control performs no callbacks, just shows a window that tells the user the game is waiting for something
 */
public class WaitingSceneControl extends CustomFxController {

    @FXML
    Label messageLabel;

    public void setMessage(String message) {
        messageLabel.setText(message);
    }
}
