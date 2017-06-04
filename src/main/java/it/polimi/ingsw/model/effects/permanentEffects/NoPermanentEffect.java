package it.polimi.ingsw.model.effects.permanentEffects;

/**
 * This permanent class effect hasn't effects, thus doesn't override anything
 */
public class NoPermanentEffect extends AbstractPermanentEffect {

    public String getShortDescription(){
        return "NoEf";
    }
    public String getDescription(){
        return "NoEffect";
    }
}
