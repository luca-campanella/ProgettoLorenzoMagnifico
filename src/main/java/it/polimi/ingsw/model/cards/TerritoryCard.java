package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;

/**
 * This is the green card.
 */
public class TerritoryCard extends AbstractCard{
    //green card don't have a cost (rules)
    /*
  this parameter indicates minimum dice's value to attivate card's build effect
   */
    private int harvestEffectValue;
    ArrayList<ImmediateEffectInterface> effectsOnHarvest;
    private static final CardColorEnum cardColor = CardColorEnum.GREEN;

    /**
     * This method should be called by {@link it.polimi.ingsw.model.controller.ModelController#harvest(FamilyMember, int)}
     * It activates the cards only if the card dice requirement is higher than {@param realDiceValue} (the {@link FamilyMember} + servants value)
     * In contrast with {@link BuildingCard#applyEffectsFromBuildToPlayer(Player, int, ChoicesHandlerInterface)} this method applies all the effects inside teh card,
     * because they are not a choice
     * @param player the player to apply the effects to
     * @param realDiceValue the real value when performing the action (the {@link FamilyMember} + servants)
     * @param choicesController the controller that handles the choices on the effects, in this case just a coincil gift choice,
     *                         either by asking the user or the hashmap of choices inside the network package
     */
    public void applyEffectsFromHarvestToPlayer(Player player, int realDiceValue, ChoicesHandlerInterface choicesController) {

        if (realDiceValue < harvestEffectValue) {
            //No effect should be activated
            Debug.printVerbose("No effect activated on card " + getName() + "because realDiceValue < harvestEffectValue (" + realDiceValue + " < " + harvestEffectValue + ")");
            return;
        }

        //Here we should activate all effects, differently from the Building card
        //TODO give the method the card name
        effectsOnHarvest.forEach(effect -> effect.applyToPlayer(player, choicesController,getName()));
    }

    public void setHarvestEffectValue(int value){ this.harvestEffectValue = value; }

    public int getHarvestEffectValue() {
        return harvestEffectValue;
    }

    public ArrayList<ImmediateEffectInterface> getEffectsOnHarvest() {
        return effectsOnHarvest;
    }

    public void setEffectsOnHarvest(ArrayList<ImmediateEffectInterface> effectsOnHarvest) {
        this.effectsOnHarvest = effectsOnHarvest;
    }

    /**
     * this method force to have no costs.
     * @return
     */
    public ArrayList<Resource> getCost() {
        return new ArrayList<>(0) ;
    }

    @Override
    public ArrayList<Resource> getCostAskChoice(ChoicesHandlerInterface choicesController) {
        return new ArrayList<>(0);
    }

    /**
     * this method returns a string with all harvest effects
     * @return
     */
    public String secondEffect(){
        String temp = new String();
        temp = "H.Value " + harvestEffectValue + "/";
        for(int i=0; i<effectsOnHarvest.size(); i++)
            temp += effectsOnHarvest.get(i).descriptionShortOfEffect();
        return temp;
    }
    public CardColorEnum getColor(){
        return cardColor;
    }
}
