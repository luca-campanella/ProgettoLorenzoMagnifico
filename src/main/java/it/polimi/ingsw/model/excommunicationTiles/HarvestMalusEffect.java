package it.polimi.ingsw.model.excommunicationTiles;

/**
 * Whenever you harvest with some diceValue, you harvest with numbeOfDice - malus
 */
public class HarvestMalusEffect extends AbstractExcommunicationTileEffect{
    private int malusOnDice;

    /**
     * this method returns the malus on the dice value when the user havests
     * @return the malus on the dice
     */
    public int harvestDiceMalusEffect()
    {
        return malusOnDice;
    }

    public String getShortEffectDescription(){
        return "-"+ malusOnDice + " On Harvest";
    }
}
