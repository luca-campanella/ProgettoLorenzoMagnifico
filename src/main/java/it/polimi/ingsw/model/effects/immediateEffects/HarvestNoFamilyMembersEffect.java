package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;

/**
 * This effect allows player to harvest without placing a family member on HarvestAS ex: 69
 */
public class HarvestNoFamilyMembersEffect extends AbstractPerformActionEffect {

    public HarvestNoFamilyMembersEffect(int harvestValue)
    {
        this.diceValue = harvestValue;
    }
    @Override
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface,String cardName){
        int servantsToAdd = choicesHandlerInterface.callbackOnAddingServants(cardName,0, player.getResource(ResourceTypeEnum.SERVANT));
        player.harvest(diceValue + servantsToAdd, choicesHandlerInterface);
    }

    @Override
    public String descriptionOfEffect() {
        return "This method allows player to harvest";
    }
    @Override
    public String descriptionShortOfEffect() {
        return "Harv";
    }
}
