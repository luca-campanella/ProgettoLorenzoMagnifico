package it.polimi.ingsw.model.excommunicationTiles;

/**
 * Whenever you build with some diceValue, you harvest with numbeOfDice - malus
 */
public class BuildMalusEffect extends AbstractExcommunicationTileEffect{
    private int malusOnDice;

    /**
     * this method
     * @return the malus on the dice
     */
    public int buildMalusEffect()
    {
        return malusOnDice;
    }


}
