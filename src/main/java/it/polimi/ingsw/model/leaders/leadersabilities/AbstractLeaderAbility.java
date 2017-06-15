package it.polimi.ingsw.model.leaders.leadersabilities;

import java.io.Serializable;

/**
 * this class just handles both immediate and permanent leader abilities
 */
public abstract class AbstractLeaderAbility implements Serializable{
    public abstract String getAbilityDescription();
}
