package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceCollector;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * Those are the yellow cards.
 */
public class BuildingCard extends AbstractCard{

    /**
     * The array list of the cost to pay when taking the card
     */
    private ArrayList<Resource> cost;
    private static CardColorEnum cardColor = CardColorEnum.YELLOW;

    /**
     * the array list of effects called when a Building action is perfomed.
     * This arraylist will usually be filled with {@link it.polimi.ingsw.model.effects.immediateEffects.PayForSomethingEffect}
     */
    private ArrayList<ImmediateEffectInterface> effectsOnBuilding;

    /**
     * this parameter indicates minimum dice's value to attivate card's build effect
     */
    private int buildEffectValue;

    /**
     * This method should be called by {@link it.polimi.ingsw.model.controller.ModelController#build(FamilyMember, int)}
     * It activates the cards only if the card dice requirement is higher than {@param realDiceValue} (the {@link FamilyMember} + servants value)
     * In contrast with {@link TerritoryCard#applyEffectsFromHarvestToPlayer(Player, int, ChoicesHandlerInterface)} this method doesn't apply
     * all the effects inside the card because they are a choice, just one effect can be chosen and activated
     * @param player the player to apply the effects to
     * @param realDiceValue the real value when performing the action (the {@link FamilyMember} + servants)
     * @param choicesController the controller that handles the choices on the effects, either by asking the user or the hashmap of choices inside the network package
     */
    public void applyEffectsFromBuildToPlayer(Player player, int realDiceValue, ChoicesHandlerInterface choicesController){

        if(realDiceValue < buildEffectValue) {
            //No effect should be activated
            Debug.printVerbose("No effect activated on card " + getName() + "because realDiceValue < buildEffectValue (" + realDiceValue + " < " + buildEffectValue +")");
            return;
        }

        //we should ask the user or the network package which effect he wants to activate
        ImmediateEffectInterface choice = choicesController.callbackOnYellowBuildingCardEffectChoice(getName(), effectsOnBuilding);

        Debug.printVerbose("In yellow build card " + getName() + "got this choice " + choice.descriptionOfEffect());

        //if the choice contains a council gift we should also ask the user what gift he desires
        //This can now be done in the card and not here because the card can call the callback, since the name of the card is passed as a parameter
        /*if(choice instanceof PayForCouncilGiftEffect) {
            ArrayList<GainResourceEffect> councilGiftChoice = choicesController.callbackOnCouncilGift(getName() + ":councilGift", 1);
            councilGiftChoice.get(0).applyToPlayer(player, choicesController, getName());
        }*/

        choice.applyToPlayer(player, choicesController, getName());
    }

    public ArrayList<Resource> getCost() {
        return cost;
    }

    @Override
    public List<Resource> getCostAskChoice(ChoicesHandlerInterface choicesController) {
        return cost;
    }

    //todo: cancel
    public void setCost(ArrayList<Resource> cost) {
        this.cost = cost;
    }

    public int getBuildEffectValue() {
        return buildEffectValue;
    }
    //todo: cancel
    public void setBuildEffectValue(int buildEffectValue) {
        this.buildEffectValue = buildEffectValue;
    }

    public ArrayList<ImmediateEffectInterface> getEffectsOnBuilding() {
        return effectsOnBuilding;
    }
    //todo: cancel
    public void setEffectsOnBuilding(ArrayList<ImmediateEffectInterface> effectsOnBuilding) {
        this.effectsOnBuilding = effectsOnBuilding;
    }

    /**
     * this method is called from the printer and helps it to print all effectsOnBuilding.
     * todo: I dind't want to put this method in the CLI because all second effects are different. -- Arto
     * @return the string with all effects.
     */
    public String secondEffect(){
        String temp = new String();
        for(int i=0; i<effectsOnBuilding.size();i++)
            temp += effectsOnBuilding.get(i).descriptionShortOfEffect();
        return temp;
    }
    public CardColorEnum getColor(){
        return cardColor;
    }

    /**
     * this method is used to control if the player can buy the following cards with the available resources
     * @param resource are the resources of the player
     * @return true if the player can buy it , false otherwise
     */
    @Override
    public boolean canBuy(ResourceCollector resource) {
        if(resource.checkIfContainable(cost))
            return true;
        return false;
    }
}
