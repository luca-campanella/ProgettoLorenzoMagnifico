package it.polimi.ingsw.server;

/**
 * This class initializes values for timeout and time for the turn.
 */
public class RoomConfigurator {
    //when room has the minimum number of player a timeout starts with this countdown.
    private int timeoutSec;
    //time for a turn
    private int timeToPass;

    public int getTimeoutSec() {
        return timeoutSec;
    }

    public int getTimeToPass() {
        return timeToPass;
    }
}
