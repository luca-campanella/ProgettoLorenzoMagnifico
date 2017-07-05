package it.polimi.ingsw.client.gui.blockingdialogs;

import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.utils.Debug;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * this class represents the future task needed to implement the menu on asking which council gif the user wants
 */
public class AskChoiceOnEffectDialog implements Callable<Integer> {

    List<? extends ImmediateEffectInterface> options;
    String description;
    public AskChoiceOnEffectDialog(List<? extends ImmediateEffectInterface> options, String description) {
        this.options = options;
        this.description = description;
    }

    @Override
    public Integer call() throws Exception {
        List<String> optionsString = new ArrayList<>();
        for (ImmediateEffectInterface iterator : options)
            optionsString.add(iterator.descriptionOfEffect());

        ChoiceDialog<String> dialog = new ChoiceDialog<>(optionsString.get(0), optionsString);

        dialog.setTitle("Information Harvest");
        dialog.setHeaderText("Look, a Choiche Dialog");
        dialog.setContentText("Choose your " + description + " effect");

        Optional<String> result = dialog.showAndWait();

        if(result.isPresent())
            for (int index = 0; index < optionsString.size(); index++)
                if (optionsString.get(index).equals(result.get()))
                    return index;
        //todo debug, if canceled returns 0
        return 0;
    }
}

