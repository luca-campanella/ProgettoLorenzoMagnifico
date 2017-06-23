package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.network.NetworkTypeEnum;
import it.polimi.ingsw.utils.Debug;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Custom fx control to control the connection choice window
 */
public class ConnectionChoiceControl extends CustomFxControl {

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
