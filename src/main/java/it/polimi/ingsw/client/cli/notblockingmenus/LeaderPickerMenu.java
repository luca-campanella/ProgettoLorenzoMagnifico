package it.polimi.ingsw.client.cli.notblockingmenus;

import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.model.leaders.LeaderCard;

import java.util.List;

/**
 * this is the class used to create and menage the leader cards delivered by the server
 */
public class LeaderPickerMenu extends BasicCLIMenu {

    public LeaderPickerMenu(ViewControllerCallbackInterface controller, List<LeaderCard> options) {
        super("Please select one of the leaders below by typing his name", controller);
        for(LeaderCard leaderIter : options) {
            addOption(leaderIter.getName().substring(0,4).toUpperCase(), "Take the leader " + leaderIter.getName()
                    + " who has ability: " + leaderIter.getAbility().getAbilityDescription(),
                    () -> controller.callbackOnLeaderCardChosen(leaderIter));
        }
    }
}
