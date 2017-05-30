package it.polimi.ingsw.client;

import com.sun.javafx.sg.prism.EffectFilter;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.TakeOrPaySomethingConditionedEffect;
import it.polimi.ingsw.model.effects.immediateEffects.TakeOrPaySomethingEffect;
import it.polimi.ingsw.model.effects.permanentEffects.AbstractPermanentEffect;

import java.util.ArrayList;

/**
 * Created by higla on 20/05/2017.
 */
public class CliPrinter {
    static final int MAX_LENGHT_CARD_NAME = 30;
    static final int sceneLenght = 39;
    static final int TOWER_LENGHT = 34;
    static final int SAMETYPE_CARDS_NUMBER = 24;

    /**
     * this method prints the board
     * @param board
     */
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

    /**
     * this method prints all the towers with a lot of overhead
     * @param board
     */
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

    /**
     * print towers with no overhead
     * @param board
     */
    public void printTowers(Board board){
        int i;
        int k;
        TowerFloorAS[] temp; // = new TowerFloorAS[board.getNUMBER_OF_TOWERS()];
        for(k=0; k<board.getNUMBER_OF_TOWERS(); k++) {
            for(i=0; i<10; i++)
                System.out.print(" ");
            printColorTower(board.getTowerColor(board.getTower(k)).toString());
            for(i=0; i<18; i++)
                System.out.print(" ");
        }
        printTowerCeiling(board, "_");

        for(i=0; i< board.getNUMBER_OF_FLOORS(); i++)
        {
            //printTowerCeiling(board, "_");
            System.out.println();
            temp = board.getFloorLevel(board.getNUMBER_OF_FLOORS()-1-i);
            printPillar();
            System.out.println();
            printPillar();
            System.out.println();
            printCardNameActionSpace(board, temp);
            System.out.println();
            printPillar();
            System.out.println();
            printPillar();
            printTowerCeiling(board, "|");
            //System.out.println("");

        }
        //printTowerCeiling(board, "|");
        System.out.println("");
    }

    /**
     * this method prints the card name action space
     * @param board
     * @param temp
     */
    private void printCardNameActionSpace(Board board,TowerFloorAS[] temp){
        for(int k=0; k<board.getNUMBER_OF_TOWERS(); k++) {
            String name = "cardName";
            System.out.print("|");
            for(int i = 0; i< (TOWER_LENGHT)/2 - name.length(); i++)
                System.out.print(" ");
            System.out.print(name);
            for(int i = 0; i< (TOWER_LENGHT)/2 - name.length()-1; i++)
                System.out.print(" ");
            System.out.print("|");

            String auxiliaryPrinterString = new String();
            auxiliaryPrinterString = "cardName";
            auxiliaryPrinterString = " *" + temp[k].getDiceValue() + "* " + temp[k].getEffectShortDescription();
            System.out.print(auxiliaryPrinterString);
            System.out.print(" ");
            //System.out.print("Lunghezza " + auxiliaryPrinterString.length());
        }
    }
    private void printPillar()
    {
        final int totalScene = 40;
        for(int k=0; k<4; k++) {
            System.out.print("|");
            for (int i = 0; i < 25; i++)
                System.out.print(" ");
            System.out.print("|");
            for (int j = 0; j < 11; j++) {
                System.out.print(" ");
            }
        }

    }
    private void printTowerCeiling(Board board, String c)
    {
        int i, k, j;
        final int totalScene = 38;
        int numberOfUnderscore = 24;
        System.out.println();
        //total sum must be 38
        for(k=0; k< board.getNUMBER_OF_TOWERS(); k++) {
            System.out.print(""+ c);
            for (i = 0; i < numberOfUnderscore; i++)
                System.out.print("_");
            if(c.equalsIgnoreCase(("|"))) {
                numberOfUnderscore++;
                System.out.print("_");
            }
            System.out.print(c);
            for(j=0; j< totalScene - numberOfUnderscore -2; j++)
                System.out.print(" ");
            if(c.equalsIgnoreCase(("|")))
                numberOfUnderscore--;
        }
    }
    public void printColorTower(String printed){
        int i;
        int temp = 10 - printed.length();
        if(printed.length()>0)
            for(i=0; i<temp; i++)
                printed += " ";
        System.out.print(printed);
    }

    public void printDeck(Deck deck){
        int i;
        int temp;
        for(i = 0; i<SAMETYPE_CARDS_NUMBER; i++) {
            System.out.print("Numero " + i + " ");
            printTerritoryCard(deck.getTerritoryCards().get(i));
        }
        temp = i-1;
        for(i=0; i<SAMETYPE_CARDS_NUMBER; i++){
            temp++;
            System.out.print("Numero " + temp + " ");
            printBuildingCards(deck.getBuildingCards().get(i));
        }
        for(i = 0; i<SAMETYPE_CARDS_NUMBER; i++) {
            temp ++;
            System.out.print("Numero " + temp + " ");
            printCharacterCards(deck.getCharacterCards().get(i));
        }


        for(i = 0; i<SAMETYPE_CARDS_NUMBER; i++) {
            temp ++;
            System.out.print("Numero " + temp + " ");
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
        String temp = new String();
        temp = "Name " + card.getName();
        System.out.print(temp);
        for(int i= 0; i<MAX_LENGHT_CARD_NAME-temp.length();i++)
            System.out.print(" ");
        System.out.print("Period " + card.getPeriod());
        if(card.getCostChoiceMilitary() != null) {
            System.out.print(" Military cost: ");
            printCostsConditioned(card.getCostChoiceMilitary());
        }
        if(card.getCostChoiceResource() != null) {
            System.out.print(" Resource cost: ");
            printCosts(card.getCostChoiceResource());
        }
        System.out.print(". Immediate Effect :");
        printImmediateEffects(card.getImmediateEffect());
        System.out.print(". Number of victory points " + card.getVictoryEndPoints());
        System.out.println("");
        //printPermanentEffects(card.getPermanentEffect());
    }
    public void printCostsConditioned(ArrayList<TakeOrPaySomethingConditionedEffect> effects)
    {
        for(int i=0; i< effects.size(); i++)
        System.out.print(effects.get(i).descriptionShortOfEffect());
    }
    public void printCharacterCards(CharacterCard card){
        String temp = new String();
        int i, lenght;
        temp = "Name " + card.getName() +".";
        lenght = MAX_LENGHT_CARD_NAME - temp.length();
        for(i=0; i< lenght; i++)
            temp += " ";
        System.out.print(temp + "Period " + card.getPeriod() + " costs ");
        printCosts(card.getCost());
        System.out.print(". Immediate Effect: ");
        printImmediateShortEffects(card.getImmediateEffect());
        System.out.print("Permanent Effect: ");
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
        System.out.print(effect.descriptionShortOfEffect());
    }
    public void printTerritoryCardVerbose(TerritoryCard card){
        System.out.print("Name " + card.getName()+", period " + card.getPeriod() + ", value " + card.getHarvestEffectValue());
        System.out.print(". Immediate Effect :");
        printImmediateEffects(card.getImmediateEffect());
        System.out.print("Harvest Effect: ");
        printImmediateEffects(card.getEffectsOnHarvest());
        System.out.println("");
    }
    public void printTerritoryCard(TerritoryCard card){
        String temp = new String();
        int i, lenght;
        temp = "Name " + card.getName() +".";
        lenght = MAX_LENGHT_CARD_NAME - temp.length();
        for(i=0; i< lenght; i++)
            temp += " ";
        temp += " Period " + card.getPeriod() + ". Value " + card.getHarvestEffectValue();
        System.out.print(temp);
        System.out.print(". Immediate Effect: ");
        printImmediateShortEffects(card.getImmediateEffect());
        System.out.print("Harvest Effect: ");
        printImmediateShortEffects(card.getEffectsOnHarvest());
        System.out.println("");
    }

    public void printImmediateShortEffects(ArrayList<ImmediateEffectInterface> effect) {
        int i, lenght;
        String temp = new String();
        final int MAX_LENGHT_SHORT_EFFECTS = 12;
        for (i = 0; i < effect.size(); i++) {
            temp += (effect.get(i).descriptionShortOfEffect() + " ");
        }
        lenght = MAX_LENGHT_SHORT_EFFECTS - temp.length();
        //temp += ".";
        for(i=0; i<lenght; i++)
            temp += " ";
        System.out.print(temp);
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
        for(i=0; i< board.getMarket().size(); i++)
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
        for(i=0; i< board.getMarket().size(); i++)
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
        System.out.print("This is Council Hall: ");
        System.out.println("Effect " + board.getCouncil().getSpaceDescription());

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
