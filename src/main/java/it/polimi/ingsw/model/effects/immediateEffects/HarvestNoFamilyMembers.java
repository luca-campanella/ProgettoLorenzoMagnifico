package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.model.player.Player;

/**
 * Created by higla on 23/05/2017.
 */
public class HarvestNoFamilyMembers extends AbstractPerformActionEffect {

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
        return "Harvest";
    }
}
