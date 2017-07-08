package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
/**
 * This effect discount something for the player. Example 51
 */
public class DiscountEffect implements ImmediateEffectInterface{
    //private CardColorEnum color;
    private ArrayList<Resource> resources;
    public DiscountEffect(ArrayList<Resource> resources)
    {
        this.resources = resources;
    }

    /**
     * @param player
     * @param choicesHandlerInterface
     */
    @Override
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface,String cardName) {
        player.addResources(resources);
    }
    @Override
    public String descriptionOfEffect() {
        return "This effect discounts something to player";
    }
    @Override
    public String descriptionShortOfEffect() {
        return "Disc";
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public void setResources(ArrayList<Resource> resources) {
        this.resources = resources;
    }
}
