package it.polimi.ingsw.choices;

import it.polimi.ingsw.model.cards.BuildingCard;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used to read choices arrived via network and to read them both in the server and in the client when it's not his turn
 */
public class NetworkChoicesPacketHandler implements ChoicesHandlerInterface {

    /**
     * this hashmap is filled with all the choices the user made regarding the move
     */
    HashMap<String, Integer> choicesMap;

    ArrayList<GainResourceEffect> possibleCouncilGifts;

    public NetworkChoicesPacketHandler(HashMap<String, Integer> choicesMap, ArrayList<GainResourceEffect> possibleCouncilGifts) {
        this.choicesMap = choicesMap;
        this.possibleCouncilGifts = possibleCouncilGifts;
    }

    /**
     * Callback from model to controller
     * The model uses this method when encounters a council gift and should choose between the possible ones
     *
     * @param choiceCode
     * @param numberDiffGifts the number of different council gifts to ask for
     * @return The arraylist of effect chosen
     */
    @Override
    public ArrayList<GainResourceEffect> callbackOnCoucilGift(String choiceCode, int numberDiffGifts) {
        ArrayList<GainResourceEffect> choices = new ArrayList<>(numberDiffGifts);
        int choice;
        GainResourceEffect effect;
        for(int i = 0; i < numberDiffGifts; i++) {
            choice = choicesMap.get(choiceCode + i);
            effect = possibleCouncilGifts.get(choice);
            Debug.printVerbose("Callback on yellowBuldingCardCalled (gift n " + i + ", choice = " + choice + " corrisponding con to effect: " + effect.descriptionOfEffect());
            choices.add(effect);
        }
        return choices;
    }

    /**
     * Callback from model to controller
     * The model uses this method when encounters a {@link BuildingCard} with more than one effects and wnats to make the user choose which one activate
     *
     * @param choiceCode
     * @param possibleEffectChoices
     * @return
     */
    @Override
    public ImmediateEffectInterface callbackOnYellowBuildingCardEffectChoice(String choiceCode, ArrayList<ImmediateEffectInterface> possibleEffectChoices) {
        int choice = choicesMap.get(choiceCode);
        ImmediateEffectInterface effect = possibleEffectChoices.get(choice);
        Debug.printVerbose("Callback on yellowBuldingCardCalled, choice = " + choice + " corrisponding con to effect: " + effect.descriptionOfEffect());
        return effect;
    }
}
