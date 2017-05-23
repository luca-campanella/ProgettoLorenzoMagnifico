package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

/**
 * Created by higla on 23/05/2017.
 */
public class TakeOrPaySomethingConditionedEffect extends TakeOrPaySomethingEffect {
    private Resource condition;
    public TakeOrPaySomethingConditionedEffect(Resource resource, Resource condition)
    {
        super(resource);
        this.condition = condition;
    }
    @Override
    public void applyToPlayer(Player player) {
        super.applyToPlayer(player);
    }
    public String descriptionOfEffect(){
        return "Non so cosa fa";
    }

    public String descriptionShortOfEffect(){
        return "Non so cosa fa";
    }

}
