package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;

import java.util.ArrayList;

/**
 * This object just contains the code of the choice and the options, it is used to communicate between {@link ClientMainController} and the model via {@link it.polimi.ingsw.model.controller.ModelController}
 * The choice is still to make
 */
public class ChoiceContainer {
    String kewCode;
    ArrayList<ImmediateEffectInterface> options;

    public ChoiceContainer(String kewCode, ArrayList<ImmediateEffectInterface> options) {
        this.kewCode = kewCode;
        this.options = options;
    }

    public String getKewCode() {
        return kewCode;
    }

    public ArrayList<ImmediateEffectInterface> getOptions() {
        return options;
    }
}
