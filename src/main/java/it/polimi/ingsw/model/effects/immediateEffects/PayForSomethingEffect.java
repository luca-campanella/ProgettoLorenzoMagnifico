package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * Pay some resources to get some resources. An example is yellow card 30
 */
public class PayForSomethingEffect implements ImmediateEffectInterface {
    // toPay value is < 0 by default
    ArrayList<Resource> toPay;
    //toPay value is > 0 by default
    ArrayList<Resource> toGain;
    public PayForSomethingEffect( ArrayList<Resource> toPay, ArrayList<Resource> toGain){
        this.toPay = toPay;
        this.toGain = toGain;
    }

    /**
     * just subtracts the resources to pay from the player and adds the resources to gain
     * Performs the exchange
     * @param player the player to apply to
     * @param choicesHandlerInterface not used in this case, no choice inside the effect
     */
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface,String cardName){
        //to pay is by default < 0
        player.addResources(toPay);
        //to gain is by default > 0
        player.addResources(toGain);
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
