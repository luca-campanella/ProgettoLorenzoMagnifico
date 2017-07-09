package it.polimi.ingsw.client.gui.blockingdialogs;

import it.polimi.ingsw.model.cards.VentureCardMilitaryCost;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.FamilyMember;
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
 * Created by higla on 05/07/2017.
 */
public class AskWhichFMBonusDialog implements Callable<DiceAndFamilyMemberColorEnum> {

    List<FamilyMember> availableFamilyMembers;


    public AskWhichFMBonusDialog(List<FamilyMember> availableFamilyMembers)
    {
        this.availableFamilyMembers = availableFamilyMembers;
    }

    /**
     *
     * @return this method returns 0 if players chooses resource, 1 if he chooses Military Point
     * @throws Exception if dialog isn't opened correctly.
     */
    @Override
    public DiceAndFamilyMemberColorEnum call() throws Exception {
        Debug.printVerbose("AskWhichFMBonusDialog");

        List<String> optionsString = new ArrayList<>();
        for (FamilyMember iterator : availableFamilyMembers)
            optionsString.add(iterator.getColor().toString());

        ChoiceDialog<String> dialog = new ChoiceDialog<>(optionsString.get(0), optionsString);

        dialog.setTitle("Choice!");
        dialog.setHeaderText("Make a choice!");
        dialog.setContentText("Choose your family member color to boost");
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent())
            for (FamilyMember iterator : availableFamilyMembers)
                if (iterator.getColor().toString().equals(result.get()))
                    return iterator.getColor();

        //todo debug here. If cancel, we need to handle that situation
        return null;


    }

}
