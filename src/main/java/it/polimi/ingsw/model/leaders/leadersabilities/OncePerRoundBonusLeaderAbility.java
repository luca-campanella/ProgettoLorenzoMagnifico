package it.polimi.ingsw.model.leaders.leadersabilities;

import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * this ability will be implemented by all Leaders having a once per round bonus which is a resource
 */
public class OncePerRoundBonusLeaderAbility extends AbstractLeaderAbility {
    private ArrayList<Resource> bonuses;

    public OncePerRoundBonusLeaderAbility(ArrayList<Resource> bonuses) {
        super();
        this.bonuses = bonuses;
    }

    /**
     * Override of the method to return the correct value of this particular ability
     * @return the array of bonuses
     */
    @Override
    public ArrayList<Resource> getOncePerRoundBonus() {
        return bonuses;
    }

    @Override
    public String getAbilityDescription() {
        StringBuilder tmpDescipt = new StringBuilder();
        for(Resource i : bonuses)
            tmpDescipt.append("Receive " + i.getResourceFullDescript() + "\n");
        return tmpDescipt.toString();
    }


}
