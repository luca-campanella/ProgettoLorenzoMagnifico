package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

/**
 * This effect gives a council gift to the player. example: 52
 */
public class GiveCouncilGiftEffect implements ImmediateEffectInterface {
    private int numberOfCouncilGift;

    public GiveCouncilGiftEffect(int numberOfCouncilGift) {
        this.numberOfCouncilGift = numberOfCouncilGift;
    }

    /**
     * this method uses callback to determine which give give the user
     * The it applies the gift (a {@link GainResourceEffect}) to the player
     * @param player
     * @param choicesHandlerInterface
     */
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface,String cardName)
    {
        List<GainResourceEffect> choices = choicesHandlerInterface.callbackOnCouncilGift(cardName + ":councilGift", numberOfCouncilGift);

        //we apply all the choices made by the user to the player
        for(GainResourceEffect effectIterator : choices)
            effectIterator.applyToPlayer(player, choicesHandlerInterface, cardName);
    }
    public String descriptionOfEffect(){
        return "Gives a Council Gift to a player.";
    }
    public String descriptionShortOfEffect(){return "Gift " + numberOfCouncilGift;}
    public int getNumberOfCouncilGift() {
        return numberOfCouncilGift;
    }

    public void setNumberOfCouncilGift(int numberOfCouncilGift) {
        this.numberOfCouncilGift = numberOfCouncilGift;
    }
}
