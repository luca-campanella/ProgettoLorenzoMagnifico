package it.polimi.ingsw.model.resource;

/**
 *  this is the class used to deliver the index of the market spaces needed on the classes and the servant needed
 */
public class MarketWrapper {

    /**
     * the index of the market
     */
    private int marketIndex;

    /**
     * the number of servant needed to place se family member on the tower
     */
    private int servantNeeded;

    public MarketWrapper(int marketIndex, int servantNeeded){

        this.marketIndex = marketIndex;
        this.servantNeeded = servantNeeded;
    }

    public int getServantNeeded() {
        return servantNeeded;
    }

    public int getMarketIndex() {
        return marketIndex;
    }
}
