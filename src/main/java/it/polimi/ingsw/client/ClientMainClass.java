package it.polimi.ingsw.client;

import it.polimi.ingsw.client.controller.ClientMain;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by campus on 22/06/2017.
 */
public class ClientMainClass extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new ClientMain();
    }

    public static void main(String args[]) {
        launch(args);
    }
}
