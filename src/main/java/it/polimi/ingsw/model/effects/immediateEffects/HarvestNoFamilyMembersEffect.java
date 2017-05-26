package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.model.player.Player;

/**
 * Created by higla on 23/05/2017.
 */
public class HarvestNoFamilyMembersEffect extends AbstractPerformActionEffect {

    public HarvestNoFamilyMembersEffect(int harvestValue)
    {
        this.diceValue = harvestValue;
    }
    @Override
    public void applyToPlayer(Player player){
        ;
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
