package it.polimi.ingsw.model.leaders.leadersabilities;

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

}
