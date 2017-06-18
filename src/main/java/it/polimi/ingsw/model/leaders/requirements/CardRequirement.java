package it.polimi.ingsw.model.leaders.requirements;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.player.Player;

/**
 * This is a requirement for a leader, the player is required to have at least numReq cards of a certain type
 */
public class CardRequirement extends AbstractRequirement {

    /**
     * number of cards required
     */
    int numReq = 0;

    /**
     * color / type of cards required
     */
    CardColorEnum reqCardColor;

    /**
     * COntructor to create the object
     * @param numReq number of cards required
     * @param reqCardColor color / type of cards required
     */
    public CardRequirement(int numReq, CardColorEnum reqCardColor) {
        this.numReq = numReq;
        this.reqCardColor = reqCardColor;
    }

    public int getNumReq() {
        return numReq;
    }

    public CardColorEnum getReqCardColor() {
        return reqCardColor;
    }

    @Override
    public String getDescription() {
        return numReq + " " + reqCardColor.getFullDescription() + "s";
    }

    /**
     * This method return true if the player meets the requirement
     * @param player the player to perform the check on
     * @return true if the requirement is met, false otherwise
     */
    @Override
    public boolean isMet(Player player) {
        if(player.getPersonalBoard().getNumberOfColoredCard(reqCardColor) >= numReq)
            return true;

        return false;
    }
}

