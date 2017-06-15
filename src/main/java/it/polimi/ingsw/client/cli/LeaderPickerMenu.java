package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.controller.ControllerCallbackInterface;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.FamilyMember;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by campus on 11/06/2017.
 */
public class LeaderPickerMenu extends BasicCLIMenu {
    ArrayList<FamilyMember> playableFMs;

    public LeaderPickerMenu(ControllerCallbackInterface controller, List<LeaderCard> options) {
        super("it's your turn, please select the action you want to perform by typing the corresponding abbreviation", controller);
        this.playableFMs = playableFMs;
        for(LeaderCard leaderIter : options) {
            addOption(leaderIter.getName(), "Take the leader " + leaderIter.getName() + " who has ability: " + leaderIter.getAbility().getAbilityDescription(), () -> controller.callbackOnLeaderCardChosen(leaderIter));
        }
    }
}
