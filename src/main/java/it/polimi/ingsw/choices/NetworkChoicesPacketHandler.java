package it.polimi.ingsw.choices;

import it.polimi.ingsw.model.cards.BuildingCard;
import it.polimi.ingsw.model.cards.VentureCard;
import it.polimi.ingsw.model.cards.VentureCardMilitaryCost;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.NoEffect;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public NetworkChoicesPacketHandler(ArrayList<GainResourceEffect> possibleCouncilGifts) {
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
    public ArrayList<GainResourceEffect> callbackOnCouncilGift(String choiceCode, int numberDiffGifts) {
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
     * @param cardNameChoiceCode
     * @param possibleEffectChoices
     * @return
     */
    //todo: check if List<..> is right --Arto
    @Override
    public ImmediateEffectInterface callbackOnYellowBuildingCardEffectChoice(String cardNameChoiceCode, List<ImmediateEffectInterface> possibleEffectChoices) {
        ImmediateEffectInterface effect;
        int choice = choicesMap.get(cardNameChoiceCode);
        if(choice == -1)
            effect = new NoEffect();
        else
            effect = possibleEffectChoices.get(choice);

        Debug.printVerbose("Callback on yellowBuldingCardCalled, choice = " + choice + " corrisponding con to effect: " + effect.descriptionOfEffect());
        return effect;
    }

    /**
     * Callback from model to controller
     * The model uses this method inside {@link VentureCard#getCostAskChoice(ChoicesHandlerInterface)} to understand what cos he should subtract
     *
     * @param choiceCode
     * @param costChoiceResource the list of resources the player will pay if he chooses this option
     * @param costChoiceMilitary the cost he will pay on something conditioned
     * @return The arraylist of resources the model has to take away from the player
     */
    @Override
    public List<Resource> callbackOnVentureCardCost(String choiceCode, List<Resource> costChoiceResource, VentureCardMilitaryCost costChoiceMilitary) {
        int choice = choicesMap.get(choiceCode);
        if(choice==1) {
            ArrayList<Resource> res = new ArrayList<>(1);
            res.add(costChoiceMilitary.getResourceCost());
            return res;
        }
        return costChoiceResource;
    }

    public void setChoicesMap(HashMap<String, Integer> choicesMap) {
        this.choicesMap = choicesMap;
    }
}
