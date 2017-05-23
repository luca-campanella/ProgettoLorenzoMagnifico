package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

/**
 * Created by higla on 23/05/2017.
 */
public class DiscountEffect implements ImmediateEffectInterface{
    private CardColorEnum color;
    private Resource[] resources = new Resource[2];
    //todo
    @Override
    public void applyToPlayer(Player player) {
        ;
    }
    @Override
    public String descriptionOfEffect() {
        return "This effect discounts something to player";
    }
    @Override
    public String descriptionShortOfEffect() {
        return "Discount";
    }
}
