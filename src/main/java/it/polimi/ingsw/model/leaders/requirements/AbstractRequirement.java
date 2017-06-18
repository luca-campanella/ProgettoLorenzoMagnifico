package it.polimi.ingsw.model.leaders.requirements;

import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;

/**
 * This class is used to encapsulate the requirement for playing a leader card
 */
public abstract class AbstractRequirement implements Serializable {

    public abstract String getDescription();

    /**
     * This method return true if the player meets the requirement
     * @param player the player to perform the check on
     * @return true if the requirement is met, false otherwise
     */
    public abstract boolean isMet(Player player);
}
