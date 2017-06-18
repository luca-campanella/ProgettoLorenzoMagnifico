package it.polimi.ingsw.model.leaders.leadersabilities;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;

import java.io.Serializable;

/**
 * this class just handles both immediate and permanent leader abilities
 */
public abstract class AbstractLeaderAbility implements Serializable{

    public AbstractLeaderAbility(){
        super();
    }

    public abstract LeaderAbilityTypeEnum getAbilityType();

    public abstract String getAbilityDescription();

    /**
     * Default method to ask the user stuff when he decides to play (but not activate yet) the leader
     * This method will be overridden by "Lorenzo de' Medici" leader ability to ask the user what he wants to copy
     * @param choicesHandler the handler of choices
     */
    @Deprecated
    public void askChoicesAtPlayMoment(ChoicesHandlerInterface choicesHandler) {
        return;
    }
}
