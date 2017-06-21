package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.client.network.NetworkTypeEnum;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by campus on 21/06/2017.
 */
public class MainGUIApplication extends Application {

    ViewControllerCallbackInterface controller;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ConnectionChooser.fxml"));

        stage.setTitle("Connection Type Choice");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void socketConnection(ActionEvent event) {
        System.out.println("socketconnection clieck");
        Platform.runLater(() -> controller.callbackNetworkType(NetworkTypeEnum.SOCKET));
    }

    @FXML
    public void rmiConnection(ActionEvent event) {
        System.out.println("socketconnection clieck");
        Platform.runLater(() -> controller.callbackNetworkType(NetworkTypeEnum.RMI));
    }

    public void setController(ViewControllerCallbackInterface controller) {
        this.controller = controller;
    }
}
