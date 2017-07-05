package it.polimi.ingsw.client.gui.blockingdialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * Created by higla on 05/07/2017.
 */
public class AskAlsoActivateLeaderCardDialog implements Callable<Integer> {
    //todo: this class needs to be tested


    @Override
    public Integer call() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Leader activation");
        alert.setHeaderText("Do you want to also activate this Leader effect?");

        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeTwo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();
        //0 if it is true, 1 if it is false... Should be the opposite but here
        // @ClientMain callbackOnAlsoActivateLeaderCard it is 0 -> true, 1 -> false
        if (result.isPresent() && result.get() == buttonTypeOne){
            return 0;
        } else
            return 1;
    }

}

//todo check if returns a boolean or not
/*
public class AskAlsoActivateLeaderCardDialog implements Callable<Boolean> {


    @Override
    public Boolean call() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Leader activation");
        alert.setHeaderText("Do you want to also activate this Leader effect?");


        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeTwo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();
        return(result.isPresent() && result.get() == buttonTypeOne){

    }

}

 */
