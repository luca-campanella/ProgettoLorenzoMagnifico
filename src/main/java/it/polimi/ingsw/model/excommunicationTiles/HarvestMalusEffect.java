package it.polimi.ingsw.model.excommunicationTiles;

/**
 * Whenever you harvest with some diceValue, you harvest with numbeOfDice - malus
 */
public class HarvestMalusEffect extends AbstractExcommunicationTileEffect{
    private int malusOnDice;

    /**
     * this method
     * @return the malus on the dice
     */
    public int harvestMalusEffect()
    {
        return malusOnDice;
    }

    public String getShortEffectDescription(){
        return "-"+ malusOnDice + " On Harvest";
    }
}
