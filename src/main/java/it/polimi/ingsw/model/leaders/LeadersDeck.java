package it.polimi.ingsw.model.leaders;

import java.util.ArrayList;

/**
 * This class contains all the instances of the LeadersCard read from file
 */
public class LeadersDeck {
    ArrayList<LeaderCard> leaders;

    public LeadersDeck(ArrayList<LeaderCard> leaders) {
        this.leaders = leaders;
    }

    public ArrayList<LeaderCard> getLeaders() {
        return leaders;
    }
}
