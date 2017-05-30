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

    /**
     * For Each condition give Resources to the Player
     */
    @Override
    public void applyToPlayer(Player player) {
        int i = player.getResource(condition.getType());
        int temp = condition.getValue();
        for(; temp>0;){
            super.applyToPlayer(player);
            temp -= condition.getValue();
        }
    }
    public String descriptionOfEffect(){
        return "Puoi prendere " + super.resource.toString() + " se hai " + this.condition.toString();
    }

    public String descriptionShortOfEffect(){
        return "+"+super.resource.getResourceShortDescript()+"IF"+this.condition.getResourceShortDescript();
    }

}
