package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;

/**
 * This effect gives a gift to the player. example: 52
 */
public class GiveCouncilGiftEffect implements ImmediateEffectInterface {
    int numberOfCouncilGift;

    /**
     * todo: ask choices to player
     * @param player
     * @param choicesHandlerInterface
     */
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface,String cardName)
    {
        //choicesHandlerInterface.callbackOnCouncilGift();
    }
    public String descriptionOfEffect(){
        return "Gives a Council Gift to a player.";
    }
    public String descriptionShortOfEffect(){return "Gift " + numberOfCouncilGift;}
}
