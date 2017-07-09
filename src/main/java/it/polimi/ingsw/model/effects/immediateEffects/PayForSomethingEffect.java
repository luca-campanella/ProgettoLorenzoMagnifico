package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Pay some resources to get some resources. An example is yellow card 30
 */
public class PayForSomethingEffect implements ImmediateEffectInterface {
    // toPay value is > 0 by default
    private List<Resource> toPay;
    //toPay value is > 0 by default
    private List<Resource> toGain;
    public PayForSomethingEffect(List<Resource> toPay, List<Resource> toGain){
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
        //to pay is by default > 0
        player.subResources(toPay);
        //to gain is by default > 0
        player.addResources(toGain);
    }

    /**
     * this method is used to describe with words what the card do
     */
    public String descriptionOfEffect()
    {
        return "Pay "+ toPay.toString() + " to gain some advantage " + toGain.toString();
    }

    /**
     * prints short description of the effect
     * @return
     */
    public String descriptionShortOfEffect(){

        StringBuilder temp = new StringBuilder();
        for(Resource iterator : toGain) {
            temp.append("+");
            temp.append(iterator.getResourceShortDescript());
        }
        for(Resource iterator : toPay) {
            temp.append("-");
            temp.append(iterator.getResourceShortDescript());
        }
        return temp.toString();
    }

    public List<Resource> getToPay() {
        return toPay;
    }

    public List<Resource> getToGain() {
        return toGain;
    }

}
