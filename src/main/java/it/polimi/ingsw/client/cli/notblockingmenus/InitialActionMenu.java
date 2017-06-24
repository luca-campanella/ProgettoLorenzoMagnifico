package it.polimi.ingsw.client.cli.notblockingmenus;

import it.polimi.ingsw.client.cli.CliOptionsHandler;
import it.polimi.ingsw.client.cli.CliPrinter;
import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * this class is the base menu, where you cann see all the possible option during your phase
 */
public class InitialActionMenu extends WaitBasicCliMenu {
    private ArrayList<FamilyMember> playableFMs;
    private List<LeaderCard> leaderCardsNotPlayed;
    private List<LeaderCard> playedLeaderCards;
    private List<LeaderCard> playableLeaderCards;

    public InitialActionMenu(ViewControllerCallbackInterface controller, ArrayList<FamilyMember> playableFMs, boolean playedFamilMembery,
                             List<LeaderCard> leaderCardsNotPlayed, List<LeaderCard> playedLeaderCards, List<LeaderCard> playableLeaderCards) {
        super("it's your turn, please select the action you want to perform by typing the corresponding abbreviation", controller);
        this.playableFMs = playableFMs;
        this.leaderCardsNotPlayed = leaderCardsNotPlayed;
        this.playedLeaderCards = playedLeaderCards;
        this.playableLeaderCards = playableLeaderCards;
        if(!playedFamilMembery)
            addOption("FM", "Place a Family Member on an action space", this::placeFamilyMember);
        if(leaderCardsNotPlayed.size() !=0)
            addOption("DL", "Discard a Leader", this::discardLeader);
        if(playableLeaderCards.size() !=0)
            addOption("PL", "Play a Leader card", this::playLeader);
        if(playedLeaderCards.size() != 0)
            addOption("AL", "Activate a Leader ability", this::activateLeaderAbility);
        addOption("END","Pass the turn", this::passTheTurn);
    }

    /**
     * this method is used to deliver to the servver the end of the phase of this player
     */
    private void passTheTurn(){

        getController().callBackPassTheTurn();
    }

    private void placeFamilyMember() {

        int indexRes = 0;
        if(playableFMs.size() == 1) {
            System.out.println("You can only place family member " + playableFMs.get(0).getColor() + " with value " + playableFMs.get(0).getValue());
            System.out.println("I'm placing this family member");
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

        int indexRes = 0;
        if(leaderCardsNotPlayed.size() == 1) {
            System.out.println("You can only discard Leader Card " + leaderCardsNotPlayed.get(0).getName());
            System.out.println("I'm discarding this leader card");
        }
        else {
            Debug.printVerbose("Discard leader card called");
            CliOptionsHandler discardLeaderCardChooser = new CliOptionsHandler(leaderCardsNotPlayed.size());

            for (LeaderCard lcIter : leaderCardsNotPlayed) {
                discardLeaderCardChooser.addOption("Discard " + lcIter.getName() + " of description " + lcIter.getAbility().getAbilityDescription());
            }
            indexRes = discardLeaderCardChooser.askUserChoice();
        }
        getController().callbackDiscardLeader(leaderCardsNotPlayed.get(indexRes));
    }

    /**
     * this method is used to play a leader card
     */
    private void playLeader() {

        int indexRes = 0;
        if(playableLeaderCards.size() == 1) {
            System.out.println("You can only play Leader Card " + playableLeaderCards.get(0).getName());
            System.out.println("I'm playing this leader card");
        }
        else {
            Debug.printVerbose("Play leader card called");
            CliOptionsHandler playLeaderCardChooser = new CliOptionsHandler(playableLeaderCards.size());

            for (LeaderCard lcIter : playableLeaderCards) {
                playLeaderCardChooser.addOption("play " + lcIter.getName() + " of description " + lcIter.getAbility().getAbilityDescription());
            }
            indexRes = playLeaderCardChooser.askUserChoice();
        }
        getController().callbackPlayLeader(playableLeaderCards.get(indexRes));
    }

    private void activateLeaderAbility() {

    }
}
