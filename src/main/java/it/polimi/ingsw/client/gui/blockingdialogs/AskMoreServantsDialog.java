package it.polimi.ingsw.client.gui.blockingdialogs;

import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * This dialog is opened up when a player wants to boost his action-power
 */
public class AskMoreServants implements Callable<Integer> {
    //todo: this class needs to be tested
    private int min;
    private int max;
    public AskMoreServants(int min, int max)
    {
        this.min = min;
        this.max = max;
    }

    @Override
    public Integer call() throws Exception {
        ArrayList<Integer> options = new ArrayList<>();
        for(int choice = min; choice < max; choice++)
            options.add(choice);

        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(min, options);

        dialog.setTitle("Information Harvest");
        dialog.setHeaderText("Look, a Choiche Dialog");
        dialog.setContentText("Choose your councilGift!");

        Optional<Integer> result = dialog.showAndWait();

        if(result.isPresent())
            for (int index = 0; index < options.size(); index++)
                if (options.get(index).equals(result.get()))
                    return index;

        //todo handle cancel action
        return min;
    }

}
