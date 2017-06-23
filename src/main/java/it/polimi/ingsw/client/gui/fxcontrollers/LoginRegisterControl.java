package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.UsernamePasswordValidator;
import it.polimi.ingsw.utils.Debug;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * this is the custom controller for the login or register window, it validates passoword and username and makes
 * corresponding callbacks
 */
public class LoginRegisterControl extends CustomFxControl {

    @FXML
    private TextField nickField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void loginHandler(ActionEvent event) {
        String nickname = nickField.getText();
        String password = passwordField.getText();

        Debug.printVerbose("nickname: " + nickname + "password: " + password);

        if(validateOrShowDialog(nickname, password)) {
            Platform.runLater(() -> getController().callbackLogin(nickname, password));
        }
    }

    @FXML
    public void registerHandler(ActionEvent event) {
        String nickname = nickField.getText();
        String password = passwordField.getText();

        Debug.printVerbose("nickname: " + nickname + "password: " + password);

        if(validateOrShowDialog(nickname, password)) {
            Platform.runLater(() -> getController().callbackCreateAccount(nickname, password));
        }
    }

    private static boolean validateOrShowDialog(String nickname, String password) {
        if(!UsernamePasswordValidator.validateUsername(nickname)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Wrong username");
            alert.setHeaderText("You inserted a semantically invalid username");
            alert.setContentText("Please insert a username with no spaces and with only letters or numbers");

            alert.showAndWait();
            return false;
        }
        if(!UsernamePasswordValidator.validatePassword(nickname)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Wrong password");
            alert.setHeaderText("You inserted a semantically invalid password");
            alert.setContentText("Please insert a password with no spaces");

            alert.showAndWait();
            return false;
        }

        return true;
    }
}
