package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.controller.ControllerCallbackInterface;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;

/**
 * Created by campus on 11/06/2017.
 */
public class InitialActionMenu extends BasicCLIMenu {
    ArrayList<FamilyMember> playableFMs;

    public InitialActionMenu(ControllerCallbackInterface controller, ArrayList<FamilyMember> playableFMs) {
        super("it's your turn, please select the action you want to perform by tiping the corresponding abbreviation", controller);
        this.playableFMs = playableFMs;
        addOption("FM", "Place a Family Member on an action space", () -> this.placeFamilyMember());
        addOption("DL", "Discard a Leader", () -> this.discardLeader());
        addOption("PL", "Play a Leader card", () -> this.playLeader());
        addOption("AL", "Activate a Leader ability", () -> this.activateLeaderAbility());
    }

    private void placeFamilyMember() {
        Debug.printVerbose("placeFamilyMember called");
        CliOptionsHandler leaderChooser = new CliOptionsHandler(playableFMs.size());

        for(FamilyMember fmIter : playableFMs) {
            Debug.printVerbose("Adding option" + "Family member of color " + fmIter.getColor() + "of value " + fmIter.getValue());
            leaderChooser.addOption("Family member of color " + fmIter.getColor() + "of value " + fmIter.getValue());
        }

        //Todo coherently with leaders available

        getController().callbackFamilyMemberAndServantsSelected(playableFMs.get(leaderChooser.askUserChoice()));
    }

    private void discardLeader() {

    }

    private void playLeader() {

    }

    private void activateLeaderAbility() {

    }
}
