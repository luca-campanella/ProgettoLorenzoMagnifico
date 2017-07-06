package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * This effect allows player to get a council gift, if they pay some resources.
 * example: 32
 */
public class PayForCouncilGiftEffect extends AbstractPerformActionEffect {
    //toPay value is > 0 by default.
    private List<Resource> toPay;
    public PayForCouncilGiftEffect(List<Resource> temp){
        toPay = temp;
    }


    /**
     * just subtracts the resources to pay from the player and adds the resources to gain
     * Performs the exchange
     * @param player the player to apply to
     * @param choicesHandlerInterface not used in this case, no choice inside the effect
     */
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface,String cardName){
        List<GainResourceEffect> choice = choicesHandlerInterface.callbackOnCouncilGift(cardName + ":councilGift", 1);
        player.subResources(toPay);
        choice.get(0).applyToPlayer(player, choicesHandlerInterface, cardName);
    }

    public String descriptionOfEffect()
    {
        return "Pay "+ toPay.toString() + " to have a gift";
    }

    /**
     * prints short description of the effect
     * @return a short description of the effect
     */
    public String descriptionShortOfEffect(){

        StringBuilder temp = new StringBuilder("Gift");

        for(Resource iterator : toPay) {
            temp.append("-");
            temp.append(iterator.getResourceShortDescript());
        }
        return temp.toString();
    }

}
