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
    public int buildDiceMalusEffect()
    {
        return malusOnDice;
    }

    public String getShortEffectDescription(){
        return "-"+ malusOnDice + " On Build";
    }

    public void setMalusOnDice(int malusOnDice) {
        this.malusOnDice = malusOnDice;
    }
}
