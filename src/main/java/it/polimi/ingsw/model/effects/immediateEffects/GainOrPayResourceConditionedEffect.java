package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

/**
 * This effect allows you to take something if you have some resources
 */
public class GainOrPayResourceConditionedEffect extends GainSomethingEffect {
    //this condition is positive. But also the effective cost of the card is positive
    //this is coherent with all costs management.
    //Costs is something you need to pay in order to get the card.
    private Resource condition;
    public GainOrPayResourceConditionedEffect(Resource resource, Resource condition)
    {
        super(resource);
        this.condition = condition;
    }

    /**
     * For Each condition give Resources to the Player
     */
    @Override
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface) {
        int conditionPlayer = player.getResource(condition.getType());
        int conditionValueCard = condition.getValue();
        //If condition is OK i give resources to the player
        if(conditionValueCard <= conditionPlayer){
            super.applyToPlayer(player, choicesHandlerInterface);
            return;
        }
        //else either i use a invalid action or i return simply and controller recognize something went wrong
        //posso chiamare il suo super?
    }
    public String descriptionOfEffect(){
        return "You can take " + super.resource.toString() + " if you have " + this.condition.toString();
    }

    public String descriptionShortOfEffect(){
        return "-"+super.resource.getResourceShortDescript()+"IF "+this.condition.getResourceShortDescript();
    }

}
