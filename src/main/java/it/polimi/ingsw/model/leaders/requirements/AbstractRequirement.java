package it.polimi.ingsw.model.leaders.requirements;

import java.io.Serializable;

/**
 * This class is used to encapsulate the requirement for playing a leader card
 */
public abstract class AbstractRequirement implements Serializable {

    public abstract String getDescription();
}
