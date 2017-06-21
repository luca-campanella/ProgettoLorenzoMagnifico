package it.polimi.ingsw.client.cli.notblockingmenus;

import it.polimi.ingsw.client.cli.CliOptionsHandler;
import it.polimi.ingsw.client.cli.CliPrinter;
import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * this class is the base menu, where you cann see all the possible option during your phase
 */
public class InitialActionMenu extends BasicCLIMenu {
    private ArrayList<FamilyMember> playableFMs;
    private ArrayList<LeaderCard> leaderCardsNotPlayed;
    private ArrayList<LeaderCard> playedLeaderCards;

    public InitialActionMenu(ViewControllerCallbackInterface controller, ArrayList<FamilyMember> playableFMs, Board board, boolean playedFamilMembery,
                             ArrayList<LeaderCard> leaderCardsNotPlayed, ArrayList<LeaderCard> playedLeaderCards) {
        super("it's your turn, please select the action you want to perform by typing the corresponding abbreviation", controller);
        this.playableFMs = playableFMs;
        this.leaderCardsNotPlayed = leaderCardsNotPlayed;
        this.playedLeaderCards = playedLeaderCards;
        addOption("BOARD", "Show me the board", () -> this.printBoard(board));
        if(!playedFamilMembery)
            addOption("FM", "Place a Family Member on an action space", this::placeFamilyMember);
        if(leaderCardsNotPlayed.size() !=0)
            addOption("DL", "Discard a Leader", this::discardLeader);
        addOption("PL", "Play a Leader card", this::playLeader);
        if(playedLeaderCards.size() != 0)
            addOption("AL", "Activate a Leader ability", this::activateLeaderAbility);
        addOption("END","Pass the turn", this::passTheTurn);
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

    /**
     * this method is used to discard a leader card and obtain resources
     */
    private void discardLeader() {

        LeaderDiscardMenu menu = new LeaderDiscardMenu(getController(),leaderCardsNotPlayed);
        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.submit(menu);
    }

    private void playLeader() {

    }

    private void activateLeaderAbility() {

    }
}
