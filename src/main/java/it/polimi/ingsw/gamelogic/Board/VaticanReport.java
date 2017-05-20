package it.polimi.ingsw.gamelogic.Board;

/**
 * Created by higla on 20/05/2017.
 */
public class VaticanReport {
    final int NUMBER_OF_AGES = 3;
    final int WALK_OF_FAITH = 16;
    private int[] requiredFaithPoints = new int[NUMBER_OF_AGES];
    private int[] correspondingVictoryPoints = new int[WALK_OF_FAITH];





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
    public int getRequiredVictoryPointByIndex(int i)
    {
        return correspondingVictoryPoints[i];
    }

    public int[] getCorrespondingVictoryPoints() {
        return correspondingVictoryPoints;
    }
}
