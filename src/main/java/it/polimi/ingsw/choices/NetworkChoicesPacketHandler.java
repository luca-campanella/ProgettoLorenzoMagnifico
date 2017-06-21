package it.polimi.ingsw.choices;

import it.polimi.ingsw.model.cards.BuildingCard;
import it.polimi.ingsw.model.cards.VentureCard;
import it.polimi.ingsw.model.cards.VentureCardMilitaryCost;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.NoEffect;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.leaders.leadersabilities.AbstractLeaderAbility;
import it.polimi.ingsw.model.leaders.leadersabilities.EmptyLeaderAbility;
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

    /**
     * This map should only be set when a choice involving it is performed:
     * - leaders
     */
    HashMap<String, String> choicesMapString;

    ArrayList<GainResourceEffect> possibleCouncilGifts;

    public NetworkChoicesPacketHandler(HashMap<String, Integer> choicesMap, HashMap<String, String> choicesMapString, ArrayList<GainResourceEffect> possibleCouncilGifts) {
        this.choicesMap = choicesMap;
        this.possibleCouncilGifts = possibleCouncilGifts;
        this.choicesMapString = choicesMapString;
    }

    public NetworkChoicesPacketHandler(ArrayList<GainResourceEffect> possibleCouncilGifts) {
        this.possibleCouncilGifts = possibleCouncilGifts;
        choicesMap = new HashMap<>();
        choicesMapString = new HashMap<>();
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
     * @param cardNameChoiceCode it's the string of the card
     * @param possibleEffectChoices are all effect available
     * @return all the immedate effects available from yellow cards where player needs to make a decision
     */
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
     * @param choiceCode it's the choice descrpipted in {}@link ChoichesHandlerInterface}
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

    public void setChoicesMapString(HashMap<String, String> choicesMapString) {
        this.choicesMapString = choicesMapString;
    }

    /**
     * Callback from model to controller
     * the model uses this method when it is playing a leader who has a COPY ability, such as "Lorenzo de' Medici"
     * to ask which choice the user wants or has done
     * @param possibleLeaders possible leader abilities to choose from
     * @return the chosen leader ability
     */
    public AbstractLeaderAbility callbackOnWhichLeaderAbilityToCopy(List<LeaderCard> possibleLeaders) {
        if(possibleLeaders.isEmpty())
            return new EmptyLeaderAbility();

        String leaderName = choicesMapString.get("COPY_ABILITY");
        if(leaderName == null) //nothing found inside the map
            return new EmptyLeaderAbility();

        LeaderCard chosenLeader = null;

        for(LeaderCard leaderIter : possibleLeaders) {
            if (leaderIter.getName().equals(leaderName)) {
                chosenLeader = leaderIter;
                break;
            }
        }

        if(chosenLeader == null)
            return new EmptyLeaderAbility();

        return chosenLeader.getAbility();
    }

    /**
     * Callback from model to controller
     * the model uses this method when it is playing a leader who has a ONCE_PER_ROUND ability
     * to ask the user if he also wants to activate the ability
     * @return true if he also wants to activate, false otherwise
     */
    public boolean callbackOnAlsoActivateLeaderCard() {

        int choice = choicesMap.get("AlsoActivateLeader");

        if(choice == 0)
            return true;
        return false;
    }

    /**
     * Callback from model to controller
     * the model uses this method when the player performs an action but from the model we have to ask
     * how many servants he wants to add
     * @param minimum the minimum number of servants he shuold at least add (typically 0)
     * @param maximum the maximum number of servants he can add (typically the ones the player has)
     * @return the number of servants the player wants to add to the action
     */
    @Override
    public int callbackOnAddingServants(String choiceCode, int minimum, int maximum) {
        if(minimum == maximum)
            return maximum;

        return choicesMap.get(choiceCode+":servantsAdded");
    }
}
