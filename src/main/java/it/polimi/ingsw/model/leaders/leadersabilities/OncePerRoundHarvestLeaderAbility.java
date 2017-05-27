package it.polimi.ingsw.model.leaders.leadersabilities;

import java.util.Optional;

/**
 * This ability gives the possibility to harvest once per round with a certain dice value,
 * This ability will usually be used by "Francesco Sforza"
 */
public class OncePerRoundHarvestLeaderAbility extends AbstractLeaderAbility {

    private int diceValue = 0;

    public OncePerRoundHarvestLeaderAbility(int diceValue) {
        super("Perform a Harvest action at value " + diceValue + ". (You can increase this action value only by spending servants; you can’t increase it with Farmer or Peasant Development Cards.)");
        this.diceValue = diceValue;
    }

    /**
     * This method is the override, to return the correct value
     * @return on Optional with the dice value
     */
    @Override
    public Optional<Integer> getOncePerRoundHarvestDiceValue() {
        return Optional.of(diceValue);
    }

    public int getDiceValue() {
        return diceValue;
    }
}
