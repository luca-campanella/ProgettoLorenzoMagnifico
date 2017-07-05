package it.polimi.ingsw.model.board;

import java.io.Serializable;

/**
 * This is the vatican walk of faith.
 * It also contains excommunication cards
 * todo: add excommunication cards
 */
public class VaticanReport implements Serializable{
    public static final int NUMBER_OF_AGES = 3;
    public static final int WALK_OF_FAITH = 16;
    private int[] requiredFaithPoints = new int[NUMBER_OF_AGES];
    private int[] correspondingVictoryPoints = new int[WALK_OF_FAITH];

    public VaticanReport(){
            super();
    }

    public VaticanReport(int[] requiredFaithPoints, int[] correspondingVictoryPoints) {
        this.requiredFaithPoints = requiredFaithPoints;
        this.correspondingVictoryPoints = correspondingVictoryPoints;
    }

    public void setRequiredFaithPoints(int[] faith) {
        this.requiredFaithPoints = faith;
    }

    public void setCorrespondingVictoryPoints(int[] victoryPointsCorresponding) {
        this.correspondingVictoryPoints = victoryPointsCorresponding;
    }

    public int[] getRequiredFaithPoints() {
        return requiredFaithPoints;
    }
    public int getRequiredFaithPointsByIndex(int i)
    {
        return requiredFaithPoints[i];
    }

    /**
     * this method is called by the model controller to add the victory points to the player based on the number of faith point
     * @param faithPointsPlayer the number of faith points of the leader
     * @return the number of victory points to add to the player
     */
    public int getRequiredVictoryPointByIndex(int faithPointsPlayer)
    {
        if(faithPointsPlayer >= WALK_OF_FAITH)
            return correspondingVictoryPoints[WALK_OF_FAITH-1];
        return correspondingVictoryPoints[faithPointsPlayer];
    }

    public int[] getCorrespondingVictoryPoints() {
        return correspondingVictoryPoints;
    }
}
