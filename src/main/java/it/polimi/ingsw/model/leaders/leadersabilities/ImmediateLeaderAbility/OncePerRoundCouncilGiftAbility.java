package it.polimi.ingsw.model.leaders.leadersabilities.ImmediateLeaderAbility;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

/**
 * This class handles the leader's ability that gives the player the possibility to use
 * a council gift each round.
 */
public class OncePerRoundCouncilGiftAbility extends AbstractImmediateLeaderAbility {
    int numberOfDifferentCouncilGift;

    public OncePerRoundCouncilGiftAbility(int numberOfDifferentCouncilGift){
        super();
        this.numberOfDifferentCouncilGift = numberOfDifferentCouncilGift;
    }

    @Override
    public String getAbilityDescription() {
        return null;
    }

    @Override
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface, String cardName) {
        List<GainResourceEffect> choices = choicesHandlerInterface.callbackOnCouncilGift(cardName, numberOfDifferentCouncilGift);
        choices.forEach(choice -> choice.applyToPlayer(player, choicesHandlerInterface, cardName+"innerChoice"));
    }
}
