package it.polimi.ingsw.model.leaders.leadersabilities.ImmediateLeaderAbility;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * this ability will be implemented by all Leaders having a once per round bonus which is a resource
 */

public class OncePerRoundResourceLeaderAbility extends AbstractImmediateLeaderAbility {
    private ArrayList<Resource> bonuses;

    public OncePerRoundResourceLeaderAbility(ArrayList<Resource> bonuses) {
        super();
        this.bonuses = bonuses;
    }

    /**
     * Override of the method to return the correct value of this particular ability
     * @return the array of bonuses
     */
    @Override
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface, String cardName)
    {
        player.addResourcesNoMalus(bonuses);
    }

    public String getAbilityDescription() {
        StringBuilder tmpDescipt = new StringBuilder();
        for(Resource i : bonuses)
            tmpDescipt.append("Receive " + i.getResourceFullDescript() + "\n");
        return tmpDescipt.toString();
    }


}
