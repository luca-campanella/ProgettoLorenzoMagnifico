package it.polimi.ingsw.client.gui.blockingdialogs;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.TowerWrapper;
import it.polimi.ingsw.utils.Debug;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * doing Abbess Effect
 */
public class AskWhereToPlaceNoDiceFamilyMember implements Callable<Integer> {

    private List<TowerWrapper> towerWrapper;

    public AskWhereToPlaceNoDiceFamilyMember(List<TowerWrapper> towerWrapper)
    {
        this.towerWrapper = towerWrapper;
    }

    @Override
    public Integer call() throws Exception {
        Debug.printVerbose("Inside askWhereToPlaceNoFamilyMember");

        ArrayList<String> choicesToShow = new ArrayList<>();
        // i get the colors of all towers
        for(TowerWrapper iterator : towerWrapper)
            choicesToShow.add(CardColorEnum.values()[(iterator.getTowerIndex())].getCardColor().concat(Integer.toString(iterator.getTowerFloor())));

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choicesToShow.get(0), choicesToShow);

        dialog.setTitle("Placing a Dice");
        dialog.setHeaderText("Choose where to place  a family member");
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);

        Debug.printVerbose("Waiting for user to choose");
        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            Debug.printVerbose("Your choice: " + result.get());
            for(int i = 0; i< choicesToShow.size(); i++)
                if (choicesToShow.get(i).equals(result.get())){
                    Debug.printVerbose(Integer.toString(i));
                    return i;
            }
        }
        Debug.printVerbose("Something went wrong");
        return 0;
    }

}
