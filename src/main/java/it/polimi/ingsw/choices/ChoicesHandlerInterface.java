package it.polimi.ingsw.choices;

import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;

import java.util.ArrayList;

/**
 * This interface is used for the callbacks from the model to the controller
 * It will be implemented by two possible objects, one that asks the choices to the user (client side)
 * and the other one that reads the choices from a data structure (server side and client when not in turn)
 *
 * choice code explanation:
 * <b>Yellow build card</b>
 * choiceCode == card name -> the value of the hashmap is the integer index of the arraylist of effects of the card
 * if choiceCode == -1 the choice resulted in not activating any effect (activate a {@link it.polimi.ingsw.model.effects.immediateEffects.NoEffect}
 * either because the user decided so or because he didn't have enough resources to activate the effect
 */
public interface ChoicesHandlerInterface {

    /**
     * Callback from model to controller
     * The model uses this method when encounters a council gift and should choose between the possible ones
     * @param choiceCode
     * @param numberDiffGifts the number of different council gifts to ask for
     * @return The arraylist of effect chosen
     */
    public ArrayList<GainResourceEffect> callbackOnCoucilGift(String choiceCode, int numberDiffGifts);

    /**
     * Callback from model to controller
     * The model uses this method when encounters a {@link it.polimi.ingsw.model.cards.BuildingCard} with more than one effects and wnats to make the user choose which one activate
     * @param cardNameChoiceCode in this case the choice code corresponds to the card name
     * @param possibleEffectChoices
     * @return
     */
    public ImmediateEffectInterface callbackOnYellowBuildingCardEffectChoice(String cardNameChoiceCode, ArrayList<ImmediateEffectInterface> possibleEffectChoices);
}
