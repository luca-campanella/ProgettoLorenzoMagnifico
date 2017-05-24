package it.polimi.ingsw.client;

import com.sun.javafx.sg.prism.EffectFilter;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.MarketAS;
import it.polimi.ingsw.model.board.Tower;
import it.polimi.ingsw.model.board.TowerFloorAS;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.TakeOrPaySomethingEffect;
import it.polimi.ingsw.model.effects.permanentEffects.AbstractPermanentEffect;

import java.util.ArrayList;

/**
 * Created by higla on 20/05/2017.
 */
public class CliPrinter {
    public void printBoard(Board board){
        //printing the towers
        printTowers(board);
        //printTowersVerbose(board);
        //printing the market
        printLine(199);
        printMarket(board);
        //printMarketVerbose(board)
        printLine(199);
        //printing build
        printBuildAS(board);
        printLine(199);
        //printing harvest
        printHarvestAS(board);
        printLine(199);
        //printing council
        printCouncil(board);
        //printing vaticanReport
        printLine(199);
        printVaticanReport(board);
    }

    public void printTowersVerbose(Board board){
        int i;
        int k;
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

    }
    public void printTowers(Board board){
        int i;
        int k;
        TowerFloorAS[] temp; // = new TowerFloorAS[board.getNUMBER_OF_TOWERS()];
        for(k=0; k<board.getNUMBER_OF_TOWERS(); k++)
            System.out.print("      " + board.getTowerColor(board.getTower(k)) + "            ");
        System.out.println();
        for(i=0; i< board.getNUMBER_OF_FLOORS(); i++)
        {
            temp = board.getFloorLevel(board.getNUMBER_OF_FLOORS()-1-i);
           /* for(k=0; k<board.getNUMBER_OF_TOWERS(); k++)
                System.out.print(" _____ ");*/

            for(k=0; k<board.getNUMBER_OF_TOWERS(); k++)
                System.out.print("||   " + "cardName" + "  *" + temp[k].getDiceValue() + "* " + temp[k].getEffectShortDescription());
            System.out.println("||");

        }

    }
    public void printDeck(Deck deck){
        int i;
        for(i = 0; i<1; i++) {
            printTerritoryCard(deck.getTerritoryCards().get(i));
            printCharacterCards(deck.getCharacterCards().get(i));
            printBuildingCards(deck.getBuildingCards().get(i));
            printVentureCards(deck.getVentureCards().get(i));
        }
    }
    public void printBuildingCards(BuildingCard card){
        System.out.print("Name " + card.getName()+ ", period " + card.getPeriod() + ", costs ");
        printCosts(card.getCost());
        System.out.print(". Immediate Effect :");
        printImmediateEffects(card.getImmediateEffect());
        System.out.print(". Permanent Effect: ");
        printImmediateEffects(card.getEffectsOnBuilding());
        System.out.println("");
        //printPermanentEffects(card.getPermanentEffect());
    }
    public void printVentureCards(VentureCard card){
        System.out.print("Name " + card.getName()+ ", period " + card.getPeriod() + ", costs Military ");
        printCosts(card.getCostChoiceMilitary());
        System.out.print(" Alternative " );
        printCosts(card.getCostChoiceResource());
        System.out.print(". Immediate Effect :");
        printImmediateEffects(card.getImmediateEffect());
        System.out.print(". Number of victory points " + card.getVictoryEndPoints());
        System.out.println("");
        //printPermanentEffects(card.getPermanentEffect());
    }

    public void printCharacterCards(CharacterCard card){
        System.out.print("Name " + card.getName()+ ", period " + card.getPeriod() + ", costs ");
        printCosts(card.getCost());
        System.out.print(". Immediate Effect :");
        printImmediateEffects(card.getImmediateEffect());
        System.out.print(". Permanent Effect: ");
        printPermanentEffects(card.getPermanentEffect());
        System.out.println("");
        //printPermanentEffects(card.getPermanentEffect());
    }
    public void printCosts(ArrayList<TakeOrPaySomethingEffect> effect)
    {
        int i=0;
        //for(i=0; i< effect.size(); i++)
            printCost(effect.get(i));
    }
    public void printCost(TakeOrPaySomethingEffect effect)
    {
        System.out.print(effect.descriptionOfEffect());
    }
    public void printTerritoryCard(TerritoryCard card){
        System.out.print("Name " + card.getName()+", period " + card.getPeriod() + ", value " + card.getHarvestEffectValue());
        System.out.print(". Immediate Effect :");
        printImmediateEffects(card.getImmediateEffect());
        System.out.print(". Harvest Effect: ");
        printImmediateEffects(card.getEffectsOnHarvest());
        System.out.println("");


    }

    public void printImmediateEffects(ArrayList<ImmediateEffectInterface> effect)
    {
        int i;
        for(i = 0; i< effect.size(); i++){
            System.out.print(effect.get(i).descriptionOfEffect() + " ");
        }
        //System.out.println("");
    }
    public void printPermanentEffects(ArrayList<AbstractPermanentEffect> effect)
    {
        int i;
        for(i = 0; i< effect.size(); i++){
            System.out.print(" " + effect.get(i).toString() + " ");
        }
    }

    public String getEffectShortDescription(TowerFloorAS floor){

        return "ciao";
    }
    public void printMarket(Board board)
    {
        int i;
        System.out.println("This is the market: ");
        for(i=0; i< board.getMarket().length; i++)
        {
            MarketAS market = board.getMarketSpaceByIndex(i);
            System.out.print("|" + i + "| *" + market.getDiceValue() + "* " + market.getEffectShortDescription() + " ");
        }
        System.out.println(" ");
    }
    public void printMarketVerbose(Board board)
    {
        int i;
        System.out.println("This is the market: ");
        for(i=0; i< board.getMarket().length; i++)
        {
            MarketAS market = board.getMarketSpaceByIndex(i);
            System.out.println("Space " + i + " Dice " + market.getDiceValue() + " " + market.getEffectDescription());
        }
    }
    public void printBuildAS(Board board)
    {
            System.out.println("This is Build Action Space: ");
            System.out.println("Standard "+ board.getBuild().getValueStandard() + ". Malus " + board.getBuild().getValueMalus() );
    }
    public void printHarvestAS(Board board)
    {
        System.out.println("This is Harvest Action Space: ");
        System.out.println("Standard "+ board.getHarvest().getValueStandard() + ". Malus " + board.getHarvest().getValueMalus() );
    }
    public void printCouncil(Board board)
    {
        System.out.println("This is Council Hall: ");
        //System.out.println("Effect " + board.getCouncilShortEffect());

    }
    public void printVaticanReport(Board board)
    {
        int i;
        // System.out.println("Those are the Faith points you need to have to avoid scomunica ");
        System.out.print(" | ");
        for(i = 0; i< board.getVaticanFaithAge().length; i++) {
            System.out.print(board.getVaticanFaithAgeIndex(i));
            System.out.print(" | ");
        }
        System.out.println();
        //System.out.println("Those are the Victory points you get from vaticanReport ");
        printLine(199);
        System.out.print(" | ");
        for(i = 0; i< board.getVaticanVictoryPoints().length; i++) {
            System.out.print(board.getVictoryPointsByIndex(i));
            System.out.print(" | ");
        }
    }
    public void printBoardDetailed(Board board){
        System.out.println("");
        System.out.println("This is a detailed version of the board ");

        //printing the towers
        printTowersVerbose(board);
        //printTowersVerbose(board);
        //printing the market
        printLine(199);
        printMarketVerbose(board);
        //printMarketVerbose(board)
        printLine(199);
        //printing build
        printBuildAS(board);
        printLine(199);
        printHarvestAS(board);
        System.out.println("This is harvestActionSpace " + board.getHarvest().toString() + ". No effect yet available ");
        printLine(199);
        //printing council
        System.out.println("This is councilActionSpace " + board.getCouncil().toString() + ". Nothing to show yet ");
        //priting vaticanReport
        printLine(199);
        printVaticanReport(board);
    }
    public void printLine(int lineLenght){
        int i;
        for(i = 0; i < lineLenght; i++)
            System.out.print("-");
        System.out.println("");
    }
}
