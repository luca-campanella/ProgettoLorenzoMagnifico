package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.UsernamePasswordValidator;
import it.polimi.ingsw.utils.Debug;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * this is the custom controller for the login or register window, it validates passoword and username and makes
 * corresponding callbacks
 */
public class LoginRegisterControl extends CustomFxControl {

    @FXML
    private TextField nickField;

    @FXML
    private PasswordField passwordField;
    private Stage stage;

    /**
     * Handles the pressing of the login button
     * @param event the fx event
     */
    @FXML
    public void loginHandler(ActionEvent event) {
        String nickname = nickField.getText();
        String password = passwordField.getText();

        Debug.printVerbose("nickname: " + nickname + "password: " + password);

        if(validateOrShowDialog(nickname, password)) {
            Platform.runLater(() -> getController().callbackLogin(nickname, password));
        }
    }

    /**
     * Handles the pressing of the register button
     * @param event the fx event
     */
    @FXML
    public void registerHandler(ActionEvent event) {
        String nickname = nickField.getText();
        String password = passwordField.getText();

        Debug.printVerbose("nickname: " + nickname + "password: " + password);

        if(validateOrShowDialog(nickname, password)) {
            Platform.runLater(() -> getController().callbackCreateAccount(nickname, password));
        }
    }

    /**
     * This method validates the username and password with {@link UsernamePasswordValidator}
     * If they respect the parameters it returns true, otherwise false but also
     * shows a fx {@link Alert} dialog telling the user what went wrong
     * @param nickname the nickname / username
     * @param password the password
     * @return true if they are valid, false otherwise
     */
    private boolean validateOrShowDialog(String nickname, String password) {
        if(!UsernamePasswordValidator.validateUsername(nickname)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Wrong username");
            alert.setHeaderText("You inserted a semantically invalid username");
            alert.setContentText("Please insert a username with no spaces and with only letters or numbers");
            alert.initOwner(stage);
            alert.show();
            return false;
        }
        if(!UsernamePasswordValidator.validatePassword(password)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Wrong password");
            alert.setHeaderText("You inserted a semantically invalid password");
            alert.setContentText("Please insert a password with no spaces");
            alert.initOwner(stage);
            alert.show();
            return false;
        }

        return true;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
