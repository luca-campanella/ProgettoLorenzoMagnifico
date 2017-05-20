package it.polimi.ingsw.gamelogic.Board;

/**
 * Created by higla on 16/05/2017.
 */
public class Board {
    final int NUMBER_OF_TOWERS = 4;
    final int NUMBER_OF_MARKETS = 4;

    Tower[] towers = new Tower[NUMBER_OF_TOWERS];
    MarketAS[] market = new MarketAS[NUMBER_OF_MARKETS];
    public void viewBoard()
    {
        int i;
        for(i=0; i<NUMBER_OF_TOWERS; i++) {
            System.out.println(i + " Torre: ");
            towers[i].printTower();
        }
    }
    public void addTowersToBoard(Tower[] towers)
    {
        this.towers = towers;
    }
    public void addMarketToBoard(MarketAS[] market)
    {
        this.market = market;
    }

}


//