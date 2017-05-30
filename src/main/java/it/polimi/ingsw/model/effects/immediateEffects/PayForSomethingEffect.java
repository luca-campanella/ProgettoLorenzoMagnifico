package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * Pay some resources to get some resources. An example is yellow card
 */
public class PayForSomethingEffect implements ImmediateEffectInterface {
    ArrayList<Resource> toPay;
    ArrayList<Resource> toGain;
    public PayForSomethingEffect( ArrayList<Resource> temp, ArrayList<Resource> temp2){
        toPay = temp;
        toGain = temp2;
    }
    public void applyToPlayer(Player player){
        ;
    }
    public String descriptionOfEffect()
    {
        return "Pay "+ toPay.toString() + " to gain some advantage " + toGain.toString();
    }
    public String descriptionShortOfEffect(){
        int i;
        String temp = new String();
        for(i=0; i<toGain.size(); i++)
            temp = "+"+this.toGain.get(i).getResourceShortDescript();
        for(int k = 0; k<toPay.size(); k++)
            temp = "-"+this.toPay.get(i).getResourceShortDescript();
        return temp;
    }
}
