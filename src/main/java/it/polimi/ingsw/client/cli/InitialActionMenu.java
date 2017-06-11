package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.controller.ControllerCallbackInterface;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;

/**
 * Created by campus on 11/06/2017.
 */
public class InitialActionMenu extends BasicCLIMenu {

    public InitialActionMenu(ControllerCallbackInterface controller) {
        super("it's your turn, please select the action you want to perform by tiping the corresponding abbreviation", controller);
        addOption("FM", "Place a Family Member on an action space", () -> this.placeFamilyMember());
        addOption("DL", "Discard a Leader", () -> this.discardLeader());
        addOption("PL", "Play a Leader card", () -> this.playLeader());

    }

    private void placeFamilyMember() {
        CliOptionsHandler leaderChooser = new CliOptionsHandler(4);
        //Todo coherently with leaders available

        getController().callbackFamilyMemberAndServantsSelected(DiceAndFamilyMemberColorEnum.BLACK, 2);
    }

    private void discardLeader() {

    }

    private void playLeader() {

    }
}
