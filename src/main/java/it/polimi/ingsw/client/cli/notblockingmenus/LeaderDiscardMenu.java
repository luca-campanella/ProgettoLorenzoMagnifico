package it.polimi.ingsw.client.cli.notblockingmenus;

import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.model.leaders.LeaderCard;

import java.util.List;

/**
 * this class is used to show the leader cards the player can discard
 */
public class LeaderDiscardMenu extends BasicCLIMenu{

    public LeaderDiscardMenu(ViewControllerCallbackInterface controller, List<LeaderCard> options) {
        super("Please select one of the leaders below by typing his name", controller);
        for(LeaderCard leaderIter : options) {
            addOption(leaderIter.getName().substring(0,4).toUpperCase(), "Discard the leader " + leaderIter.getName()
                            + " who has ability: " + leaderIter.getAbility().getAbilityDescription(),
                    () -> controller.callbackDiscardLeader(leaderIter));
        }
    }
}
