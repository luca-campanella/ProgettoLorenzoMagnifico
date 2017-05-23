package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

/**
 * Created by higla on 23/05/2017.
 */
public class TowerOrPaySomethingConditionedOnCardEffect extends TakeOrPaySomethingEffect {
    CardColorEnum color;
    public TowerOrPaySomethingConditionedOnCardEffect(Resource resource, CardColorEnum color){
        super(resource);
        this.color = color;
    }

    @Override
    //todo: apply to player
    public void applyToPlayer(Player player) {
        ;
    }
}
