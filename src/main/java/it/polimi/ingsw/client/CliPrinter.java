package it.polimi.ingsw.client;

import it.polimi.ingsw.gamelogic.Board.Board;
import it.polimi.ingsw.gamelogic.Board.MarketAS;
import it.polimi.ingsw.gamelogic.Board.Tower;
import it.polimi.ingsw.gamelogic.Board.TowerFloorAS;

/**
 * Created by higla on 20/05/2017.
 */
public class CliPrinter {


    public void printBoard(Board board){
        int i;
        int k;
        //printing the towers
        for(i=0; i< board.getTowers().length; i++)
        {
            Tower tower = board.getTower(i);
            System.out.println("This is the " + board.getTowerColor(tower) + " tower:");
            for(k=0; k < tower.getFloors().length; k++)
            {
                TowerFloorAS floor = tower.getFloorByIndex(k);
                System.out.println("Floor " + k + " has this dice value " + floor.getDiceValue() + " and " + floor.getEffectDescription());
            }
        }
        //printing the market
        System.out.println("This is the market: ");
        for(i=0; i< board.getMarket().length; i++)
        {
            MarketAS market = board.getMarketSpaceByIndex(i);
            System.out.println("Market space " + i + " has this dice value " + market.getDiceValue() + " and " + market.getEffectDescription());
        }
        //printing build
        System.out.println("This is buildActionSpace " + board.getBuild().toString() + ". No effect yet available ");
        //printing harvest
        System.out.println("This is harvestActionSpace " + board.getHarvest().toString() + ". No effect yet available ");
        //printing council
        System.out.println("This is councilActionSpace " + board.getCouncil().toString() + ". Nothing to show yet ");
        //priting vaticanReport
       System.out.println("Those are the Faith points you need to have to avoid scomunica ");
        for(i = 0; i< board.getVaticanFaithAge().length; i++) {
            System.out.print(board.getVaticanFaithAgeIndex(i));
            System.out.print(" ");
        }
        System.out.println();

        System.out.println("Those are the Victory points you get from vaticanReport ");
        for(i = 0; i< board.getVaticanVictoryPoints().length; i++) {
            System.out.print(board.getVictoryPointsByIndex(i));
            System.out.print(" ");
        }



    }
}
