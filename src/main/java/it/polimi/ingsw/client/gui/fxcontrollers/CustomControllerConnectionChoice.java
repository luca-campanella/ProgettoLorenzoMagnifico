package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.network.NetworkTypeEnum;
import it.polimi.ingsw.utils.Debug;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by campus on 22/06/2017.
 */
public class CustomControllerConnectionChoice extends CustomFxController {

    @FXML
    private TextField nickField;

    @FXML
    public void socketConnection(ActionEvent event) {
        Debug.printVerbose("socketconnection clieck");
        if(getController() == null)
            Debug.printVerbose("controller is null");
        Platform.runLater(() -> getController().callbackNetworkType(NetworkTypeEnum.SOCKET));
    }

    @FXML
    public void rmiConnection(ActionEvent event) {
        System.out.println("socketconnection clieck");
        Platform.runLater(() -> getController().callbackNetworkType(NetworkTypeEnum.RMI));
    }
}
