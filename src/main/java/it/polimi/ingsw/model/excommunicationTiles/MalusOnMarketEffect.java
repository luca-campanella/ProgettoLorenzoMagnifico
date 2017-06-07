package it.polimi.ingsw.model.excommunicationTiles;

/**
 * This excommunication Tiles doesn't allow you to place a family member on the market.
 */
public class MalusOnMarketEffect extends AbstractExcommunicationTileEffect{
    private boolean maluseActivate = false;

    /**
     * this method doesn't allow a player to place an action player on market
     * @return true if you can't place it
     */
    public boolean marketNotAvailable()
    {
        return maluseActivate;
    }

    public String getShortEffectDescription(){
        return "Market not available";
    }
}
