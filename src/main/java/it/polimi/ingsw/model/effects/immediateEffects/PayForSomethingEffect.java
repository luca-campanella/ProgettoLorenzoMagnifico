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
    public PayForSomethingEffect( ArrayList<Resource> toPay, ArrayList<Resource> toGain){
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

    /**
     * prints short description of the effect
     * @return
     */
    public String descriptionShortOfEffect(){
        int i;
        String temp = new String();
        for(i=0; i<toGain.size(); i++)
            temp = "+"+this.toGain.get(i).getResourceShortDescript();
        for(int k = 0; k<toPay.size(); k++)
            temp += "-"+this.toPay.get(k).getResourceShortDescript();
        return temp;
    }

    public ArrayList<Resource> getToPay() {
        return toPay;
    }

    public ArrayList<Resource> getToGain() {
        return toGain;
    }

}
