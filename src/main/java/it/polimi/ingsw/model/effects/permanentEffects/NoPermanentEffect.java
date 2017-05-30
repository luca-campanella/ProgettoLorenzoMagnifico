package it.polimi.ingsw.model.effects.permanentEffects;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.resource.Resource;

/**
 * This permanent class effect hasn't effects
 */
public class NoPermanentEffect extends AbstractPermanentEffect {

    public String getShortDescription(){
        return "NoEf";
    }
    public String getDescription(){
        return "NoEffect";
    }
}
