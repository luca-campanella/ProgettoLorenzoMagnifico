package it.polimi.ingsw.client;

        import it.polimi.ingsw.client.controller.ClientMain;
        import javafx.application.Application;
        import javafx.stage.Stage;

/**
 * this class
 */
public class ClientMainClass extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClientMain clientMain = new ClientMain();
        clientMain.startUp();
    }

    public static void main(String args[]) {
        launch(args);
    }
}
