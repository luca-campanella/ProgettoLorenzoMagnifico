package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.network.NetworkTypeEnum;
import it.polimi.ingsw.utils.Debug;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Created by campus on 22/06/2017.
 */
public class LoginRegisterController extends CustomFxController {

    @FXML
    private TextField nickField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void loginHandler(ActionEvent event) {
        Debug.printVerbose(nickField.getText() + passwordField.getText());

        //Platform.runLater(() -> getController().callbackNetworkType(NetworkTypeEnum.SOCKET));
    }

    @FXML
    public void registerHandler(ActionEvent event) {

        Platform.runLater(() -> getController().callbackNetworkType(NetworkTypeEnum.RMI));
    }
}
