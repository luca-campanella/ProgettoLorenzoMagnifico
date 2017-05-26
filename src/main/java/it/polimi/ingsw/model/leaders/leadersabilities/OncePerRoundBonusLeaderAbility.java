package it.polimi.ingsw.model.leaders.leadersabilities;

import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * this ability will be implemented by all Leaders having a once per round bonus which is a resource
 */
public class OncePerRoundBonusLeaderAbility extends AbstractLeaderAbility {
    private ArrayList<Resource> bonuses;

    public OncePerRoundBonusLeaderAbility(ArrayList<Resource> bonuses) {
        super(null);
        String tmpDescipt = new String();
        for(Resource i : bonuses)
            tmpDescipt += "Receive " + i.getValue() + " " + i.getResourceAbbreviation() + "\n";
        setAbilityDescription(tmpDescipt);
        this.bonuses = bonuses;
    }

    /**
     * this method will bew overridden by all Leaders having a once per round bonus which is a resource, if no leader overrides this method by default it returns null
     * @return null
     */
    @Override
    public ArrayList<Resource> getOncePerRoundBonus() {
        return bonuses;
    }

}
