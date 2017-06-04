package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * This effect allows player to get a council gift, if they pay some resources.
 * example: 32
 */
public class PayForGiftEffect extends AbstractPerformActionEffect {
    ArrayList<Resource> toPay;
    public PayForGiftEffect( ArrayList<Resource> temp){
        toPay = temp;
    }
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface){
        ;
    }
    public String descriptionOfEffect()
    {
        return "Pay "+ toPay.toString() + " to have a gift";
    }

    /**
     * prints short description of the effect
     * @return
     */
    public String descriptionShortOfEffect(){
        int i;
        String temp = new String();
        temp = "Gift ";
        for(int k = 0; k<toPay.size(); k++)
            temp += "-"+this.toPay.get(k).getResourceShortDescript();
        return temp;
    }
}
