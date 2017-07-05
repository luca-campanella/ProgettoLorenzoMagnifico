package it.polimi.ingsw.client.gui.blockingdialogs;

import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.utils.Debug;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * this class represents the future task needed to implement the menu on asking which council gif the user wants
 */
public class AskCouncilGiftDialog implements Callable<Integer> {

    List<GainResourceEffect> options;

    public AskCouncilGiftDialog(List<GainResourceEffect> options) {
        this.options = options;
    }

    @Override
    public Integer call() throws Exception {

        Debug.printVerbose("Im inside displayCouncilOption");

        List<String> optionsString = new ArrayList<>();
        for (GainResourceEffect iterator : options)
            optionsString.add(iterator.descriptionOfEffect());

        Debug.printVerbose("Im inside displayCouncilOption1");

        ChoiceDialog<String> dialog = new ChoiceDialog<>(optionsString.get(0), optionsString);
        Debug.printVerbose("Im inside displayCouncilOption2");

        dialog.setTitle("Information Harvest");
        dialog.setHeaderText("Look, a Choiche Dialog");
        dialog.setContentText("Choose your councilGift!");

        Debug.printVerbose("Im inside displayCouncilOption3");

        Optional<String> result = dialog.showAndWait();

        Debug.printVerbose("Im inside displayCouncilOption4");

        for (int index = 0; index < optionsString.size(); index++)
            if (optionsString.get(index).equals(result))
                return index;
        //todo debug, always return 0
        return 0;
    }
}

