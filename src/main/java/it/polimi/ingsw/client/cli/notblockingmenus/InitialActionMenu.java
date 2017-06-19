package it.polimi.ingsw.client.cli.notblockingmenus;

import it.polimi.ingsw.client.cli.CliOptionsHandler;
import it.polimi.ingsw.client.cli.CliPrinter;
import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;

/**
 * Created by campus on 11/06/2017.
 */
public class InitialActionMenu extends BasicCLIMenu {
    ArrayList<FamilyMember> playableFMs;

    public InitialActionMenu(ViewControllerCallbackInterface controller, ArrayList<FamilyMember> playableFMs, Board board) {
        super("it's your turn, please select the action you want to perform by typing the corresponding abbreviation", controller);
        this.playableFMs = playableFMs;
        addOption("BOARD", "Show me the board", () -> this.printBoard(board));
        addOption("FM", "Place a Family Member on an action space", () -> this.placeFamilyMember());
        addOption("DL", "Discard a Leader", () -> this.discardLeader());
        addOption("PL", "Play a Leader card", () -> this.playLeader());
        addOption("AL", "Activate a Leader ability", () -> this.activateLeaderAbility());
        addOption("END","Pass the turn",() -> this.passTheTurn());
    }

    private void printBoard(Board board) {
        CliPrinter.printBoard(board);
        showMenuAndAsk();
    }

    private void passTheTurn(){

        getController().callBackPassTheTurn();
    }

    private void placeFamilyMember() {

        int indexRes = 0;
        if(playableFMs.size() == 1) {
            System.out.println("You can only place family member " + playableFMs.get(0).getColor() + " with value " + playableFMs.get(0).getValue());
            System.out.println("I'm placing this family member");
            indexRes = 0;
        } else {
            Debug.printVerbose("placeFamilyMember called");
            CliOptionsHandler familyMemberChooser = new CliOptionsHandler(playableFMs.size());

            for (FamilyMember fmIter : playableFMs) {
                familyMemberChooser.addOption("Family member of color " + fmIter.getColor() + "of value " + fmIter.getValue());
            }
            indexRes = familyMemberChooser.askUserChoice();
        }
        getController().callbackFamilyMemberSelected(playableFMs.get(indexRes));
    }

    private void discardLeader() {

    }

    private void playLeader() {

    }

    private void activateLeaderAbility() {

    }
}
