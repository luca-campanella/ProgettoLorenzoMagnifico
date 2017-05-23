package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

/**
 * Created by higla on 23/05/2017.
 */
public class PayForSomethingEffect implements ImmediateEffectInterface {
    Resource toPay;
    Resource toGain;
    public PayForSomethingEffect(Resource toPay, Resource toGain){
        this.toPay = toPay;
        this.toGain = toGain;
    }
    public void applyToPlayer(Player player){
        ;
    }
    public String descriptionOfEffect()
    {
        return "Pay "+ toPay.toString() + " to gain some advantage " + toGain.toString();
    }
    public String descriptionShortOfEffect(){
        return "PFSE";
    }
}
