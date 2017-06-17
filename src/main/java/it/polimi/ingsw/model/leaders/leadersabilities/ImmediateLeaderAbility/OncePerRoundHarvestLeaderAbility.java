package it.polimi.ingsw.model.leaders.leadersabilities.ImmediateLeaderAbility;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.leaders.leadersabilities.LeaderAbilityTypeEnum;
import it.polimi.ingsw.model.player.Player;

/**
 * This ability gives the possibility to harvest once per round with a certain dice value,
 * This ability will usually be used by "Francesco Sforza"
 */
public class OncePerRoundHarvestLeaderAbility extends AbstractImmediateLeaderAbility {

    private int diceValue = 0;

    public OncePerRoundHarvestLeaderAbility(LeaderAbilityTypeEnum leaderAbilityType, int diceValue) {
        super(leaderAbilityType);
        this.diceValue = diceValue;
    }

    /**
     * this method allows player to build.
     * @param player is the instance of the player that is activating the cardLeader
     * @param choicesHandlerInterface it's the interface that asks the choice
     * @param cardName it's the name of the card that calls the effect.
     */
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface, String cardName)
    {
        //todo: implement applyToPlayer;
    }

    public String getAbilityDescription() {
        return "Perform a Harvest action at value " + diceValue + ". (You can increase this action value only by " +
                "spending servants; you can’t increase it with Farmer or Peasant Development Cards.)";
    }

    public int getDiceValue() {
        return diceValue;
    }
}
