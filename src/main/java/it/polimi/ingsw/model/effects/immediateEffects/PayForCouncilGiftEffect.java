package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * This effect allows player to get a council gift, if they pay some resources.
 * example: 32
 */
public class PayForCouncilGiftEffect extends AbstractPerformActionEffect {
    //toPay value is < 0 by default.
    ArrayList<Resource> toPay;
    public PayForCouncilGiftEffect(ArrayList<Resource> temp){
        toPay = temp;
    }


    /**
     * just subtracts the resources to pay from the player and adds the resources to gain
     * Performs the exchange
     * @param player the player to apply to
     * @param choicesHandlerInterface not used in this case, no choice inside the effect
     */
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface){
        //ArrayList<TakeSomethingEffect> choice = choicesHandlerInterface.callbackOnCoucilGift("cg", 1);
        player.addResource(toPay);
        //choice.get(0).applyToPlayer(player, choicesHandlerInterface); this is now done in the card and not here
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
