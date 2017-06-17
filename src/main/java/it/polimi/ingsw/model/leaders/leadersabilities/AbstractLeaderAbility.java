package it.polimi.ingsw.model.leaders.leadersabilities;

import java.io.Serializable;

/**
 * this class just handles both immediate and permanent leader abilities
 */
public abstract class AbstractLeaderAbility implements Serializable{

    /*private LeaderAbilityTypeEnum abilityType;

    public AbstractLeaderAbility(LeaderAbilityTypeEnum abilityType) {
        this.abilityType = abilityType;
    }*/

    /*public LeaderAbilityTypeEnum getAbilityType() {
        return abilityType;
    }*/

    public AbstractLeaderAbility(){
        super();
    }

    public abstract LeaderAbilityTypeEnum getAbilityType();

    public abstract String getAbilityDescription();
}
