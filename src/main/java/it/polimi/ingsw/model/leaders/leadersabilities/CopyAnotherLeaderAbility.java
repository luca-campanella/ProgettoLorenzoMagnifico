package it.polimi.ingsw.model.leaders.leadersabilities;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;

/**
 *
 */
public class CopyAnotherLeaderAbility extends AbstractLeaderAbility {

    public CopyAnotherLeaderAbility() {
        super();
    }

    @Override
    public String getAbilityDescription() {
        return "Copy the ability of another Leader Card already played by another player. Once you " +
                "decide the ability to copy, it can’t be changed.";
    }

    @Override
    public LeaderAbilityTypeEnum getAbilityType() {
        return LeaderAbilityTypeEnum.COPY_ABILITY;
    }

    /**
     * Default method to ask the user stuff when he decides to play (but not activate yet) the leader
     * This method will be overridden by "Lorenzo de' Medici" leader ability to ask the user what he wants to copy
     * @param choicesHandler the handler of choices
     */
    @Override
    @Deprecated
    public void askChoicesAtPlayMoment(ChoicesHandlerInterface choicesHandler) {
        return;
    }

}
