package it.polimi.ingsw.client.gui.blockingdialogs;

import it.polimi.ingsw.model.cards.VentureCardMilitaryCost;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.utils.Debug;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * This is a dialog class for choosing Military or Resource cost.
 * If player chooses military it returns 1, 0 if he chooses Resource.
 */
public class AskPurpleCardCostDialog implements Callable<Integer> {

    List<Resource> costChoiceResource;
    VentureCardMilitaryCost costChoiceMilitary;

    public AskPurpleCardCostDialog(List<Resource> costChoiceResource, VentureCardMilitaryCost costChoiceMilitary)
    {
        this.costChoiceResource = costChoiceResource;
        this.costChoiceMilitary = costChoiceMilitary;
    }

    /**
     *
     * @return this method returns 0 if players chooses resource, 1 if he chooses Military Point
     * @throws Exception if dialog isn't opened correctly.
     */
    @Override
    public Integer call() throws Exception {
        Debug.printVerbose("askPurpleCardCostDialogCalled");
        //If there's no military cost, i choose Resource cost
        if(costChoiceMilitary == null)
            return 0;
        //If there are no resource costs, i choose Military cost.
        if(costChoiceResource == null)
            return 1;
        //If i arrive here, it means there are both, so i need to ask which one players want select
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choice!");
        alert.setHeaderText("Chooce what resource you want to spend");
        ButtonType buttonTypeOne = new ButtonType("Resources Cost");
        ButtonType buttonTypeTwo = new ButtonType("Military Cost");
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeOne){
            return 0;
        } else
            return 1;
    }

}
