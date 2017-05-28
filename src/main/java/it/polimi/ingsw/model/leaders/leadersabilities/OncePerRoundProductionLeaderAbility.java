package it.polimi.ingsw.model.leaders.leadersabilities;

import java.util.Optional;

/**
 * This ability gives the possibility to product once per round with a certain dice value,
 * This ability will usually be used by "Leonardo Da Vinci"
 */
public class OncePerRoundProductionLeaderAbility extends AbstractLeaderAbility {

    private int diceValue = 0;

    public OncePerRoundProductionLeaderAbility(int diceValue) {
        super();
        this.diceValue = diceValue;
    }

    /**
     * This method is the override, to return the correct value
     * @return on Optional with the dice value
     */
    @Override
    public Optional<Integer> getOncePerRoundProductionDiceValue() {
        return Optional.of(diceValue);
    }

    @Override
    public String getAbilityDescription() {
        return "Perform a Production action at value " + diceValue + ". (You can increase this action value only by spending servants; you can’t increase it with Farmer or Peasant Development Cards.)";
    }

    public int getDiceValue() {
        return diceValue;
    }
}
