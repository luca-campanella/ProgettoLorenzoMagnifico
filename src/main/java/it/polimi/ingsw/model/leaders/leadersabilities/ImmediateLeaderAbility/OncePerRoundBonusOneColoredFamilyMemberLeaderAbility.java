package it.polimi.ingsw.model.leaders.leadersabilities.ImmediateLeaderAbility;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;

/**
 * This leader ability gives you a bonus on one of your colored family members
 * This ability is usually implemented by "Federico da Montefeltro"
 */
public class OncePerRoundBonusOneColoredFamilyMemberLeaderAbility extends AbstractImmediateLeaderAbility {

    private int bonusValue;

    public OncePerRoundBonusOneColoredFamilyMemberLeaderAbility(int bonusValue){
        super();
        this.bonusValue = bonusValue;
    }

    public int getBonusValue() {
        return bonusValue;
    }

    @Override
    public String getAbilityDescription() {
        return "One of your colored Family Members has a value of " + bonusValue + ", regardless of its related dice.";
    }

    @Override
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface, String cardName) {
        //todo apply to player
    }
}