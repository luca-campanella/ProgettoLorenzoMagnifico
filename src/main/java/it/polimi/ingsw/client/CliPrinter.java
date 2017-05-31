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
    //lenght of tower + action space next to that tower
    static final int sceneLenght = 39 +10;
    static final int TOWER_LENGHT = 34 ;
    static final int SAMETYPE_CARDS_NUMBER = 24 ;
    //lenght of tower
    static final int INSIDE_TOWER_LENGHT = 25 +10;

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
            for(i=0; i<15; i++)
                System.out.print(" ");
            printColorTower(board.getTowerColor(board.getTower(k)).toString());
            for(i=0; i<23; i++)
                System.out.print(" ");
        }
        printTowerCeiling(board, "_");

        for(i=0; i< board.getNUMBER_OF_FLOORS(); i++)
        {
            //printTowerCeiling(board, "_");
            System.out.println();
            temp = board.getFloorLevel(board.getNUMBER_OF_FLOORS()-1-i);
            printCostCards(temp);
            System.out.println();
            printPillar();
            System.out.println();
            printCardNameActionSpace(board, temp);
            System.out.println();
            printImmediateEffectPillar(temp);
            System.out.println();
            printSecondEffectPillar(temp);
            printTowerCeiling(board, "|");
            //System.out.println("");

        }
        //printTowerCeiling(board, "|");
        System.out.println("");
    }
    private void printImmediateEffectPillar(TowerFloorAS[] floors){
        for(int i= 0; i< floors.length; i++)
        printCardImmediateEffectOnFloor(floors[i]);
    }

    /**
     * this method prints the second  effect pillar
     * @param floors
     */
    private void printSecondEffectPillar(TowerFloorAS[] floors){
        for(int i= 0; i< floors.length; i++)
            printCardSecondEffectOnFloor(floors[i]);
    }

    /**
     * this method prints all immediate effect
     * @param floor
     */
    private void printCardImmediateEffectOnFloor(TowerFloorAS floor)
    {
        ArrayList<? extends ImmediateEffectInterface> costs;
        String tempCostsScene = "| Instantly: ";
        costs = floor.getCard().getImmediateEffect();
        //first i print the costs
        for(int i = 0; i< costs.size(); i++)
            tempCostsScene += costs.get(i).descriptionShortOfEffect() + " ";
        //then i print the remaining space from cost to .. |
        while(tempCostsScene.length()< INSIDE_TOWER_LENGHT+1)
            tempCostsScene += " ";
        tempCostsScene += "|";
        //then i prepare the scene for the next pillar. Middle tower lenght is 26 not 29
        for(int i = 0; i< sceneLenght-INSIDE_TOWER_LENGHT -3; i++)
            tempCostsScene += " ";

        System.out.print(tempCostsScene);
    }

    /**
     * this method prints all second effects permanent / harvests / build and purple
     * @param floor
     */
    private void printCardSecondEffectOnFloor(TowerFloorAS floor)
    {
        String tempCostsScene = "| Second: ";
        tempCostsScene += floor.getCard().secondEffect();

        //Here i fit my string to the scene.. |
        while(tempCostsScene.length()< INSIDE_TOWER_LENGHT+1)
            tempCostsScene += " ";
        tempCostsScene += "|";
        //then i prepare the scene for the next pillar. Middle tower lenght is 26 not 29
        for(int i = 0; i< sceneLenght-INSIDE_TOWER_LENGHT -3; i++)
            tempCostsScene += " ";

        System.out.print(tempCostsScene);
    }
    /**
     * this method prints the card name action space
     * @param board
     * @param temp
     */
    private void printCardNameActionSpace(Board board,TowerFloorAS[] temp){
        for(int k=0; k<board.getNUMBER_OF_TOWERS(); k++) {
            //temp printing, waiting for filling all the cards
            String name = temp[k].getCard().getName();

            System.out.print("|");
            int tempLength = ((INSIDE_TOWER_LENGHT) - name.length())/2;
            for(int i = 0; i< tempLength; i++)
                System.out.print(" ");
            System.out.print(name);
            tempLength += name.length();
            for(int i = 0; i< (INSIDE_TOWER_LENGHT) - tempLength; i++)
                System.out.print(" ");
            System.out.print("|");

            String auxiliaryPrinterString = new String();
            auxiliaryPrinterString = " *" + temp[k].getDiceValue() + "* " + temp[k].getEffectShortDescription();
            System.out.print(auxiliaryPrinterString);
            System.out.print(" ");
            //System.out.print("Lunghezza " + auxiliaryPrinterString.length());
        }
    }

    /**
     * this class prints all costs cards in a level
     */
    private void printCostCards(TowerFloorAS[] floors){
        printOneEmptyPillar();
        printGenericCostPillar(floors[1]);
        printGenericCostPillar(floors[2]);
        printGenericCostPillar(floors[3]);
    }
    //printVenutreCostPillar
    /** this method prints pillars for just one tower
     *
     */
    private void printOneEmptyPillar()
    {
        System.out.print("|");
            for (int i = 0; i < INSIDE_TOWER_LENGHT; i++)
                System.out.print(" ");
            System.out.print("|");
            for (int j = 0; j < sceneLenght - INSIDE_TOWER_LENGHT - 3; j++) {
                System.out.print(" ");
            }
    }
    /**
     * This method prints a generic card cost pillar
     */
    public void printGenericCostPillar(TowerFloorAS floor){
        ArrayList<? extends ImmediateEffectInterface> costs;
        String tempCostsScene = "|Cost: ";
        String subCostScene = " ";
        costs = floor.getCard().getCost();
        //first i print the costs
        for(int i = 0; i< costs.size(); i++)
            tempCostsScene += costs.get(i).descriptionShortOfEffect() + " ";
        //then i print the remaining space from cost to .. |
        while(tempCostsScene.length()< INSIDE_TOWER_LENGHT+1)
            tempCostsScene += " ";
        tempCostsScene += "|";
        //then i prepare the scene for the next pillar. Middle tower lenght is 26 not 29
        for(int i = 0; i< sceneLenght-INSIDE_TOWER_LENGHT -3; i++)
        tempCostsScene += " ";

        System.out.print(tempCostsScene);
    }


    /**
     * this method print the cost of a card. It doesn't print the green tower
     * @param effect it's the effect printed
     */
    public void printCosts(ArrayList<? extends ImmediateEffectInterface> effect)
    {
        int i=0;
        for(i=0; i< effect.size(); i++)
            printCost(effect.get(i));
    }
    public void printCost(ImmediateEffectInterface effect)
    {
        System.out.print(effect.descriptionShortOfEffect());
    }


    /**
     * this class prints a pillar
     */
    private void printPillar()
    {

        for(int k=0; k< 4; k++) {
            System.out.print("|");
            for (int i = 0; i < INSIDE_TOWER_LENGHT; i++)
                System.out.print(" ");
            System.out.print("|");
            for (int j = 0; j < sceneLenght - INSIDE_TOWER_LENGHT - 3; j++) {
                System.out.print(" ");
            }
        }

    }

    /**
     * this method prints the tower ceiling
     * @param board
     * @param c it's a character that will be printed if we're printing a roof or a ceiling
     */
    private void printTowerCeiling(Board board, String c)
    {
        int i, k, j;
        int numberOfUnderscore = 34;
        System.out.println();
        for(k=0; k< board.getNUMBER_OF_TOWERS(); k++) {
            System.out.print(""+ c);
            for (i = 0; i < numberOfUnderscore; i++)
                System.out.print("_");
            if(c.equalsIgnoreCase(("|"))) {
                numberOfUnderscore++;
                System.out.print("_");
            }
            System.out.print(c);
            for(j=0; j< sceneLenght - numberOfUnderscore - 3; j++)
                System.out.print(" ");
            if(c.equalsIgnoreCase(("|")))
                numberOfUnderscore--;
        }
    }

    /**
     * this method prints the color on the top of the towers
     * @param printed
     */
    public void printColorTower(String printed){
        int i;
        int temp = 10 - printed.length();
        if(printed.length()>0)
            for(i=0; i<temp; i++)
                printed += " ";
        System.out.print(printed);
    }

    /**
     * this method prints the short immediate effect
     * @param effect
     */
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


    /**
     * this method prints the permanente effect
     * @param effect are the effects that will be printed
     */
    public void printPermanentEffects(ArrayList<AbstractPermanentEffect> effect)
    {
        int i;
        for(i = 0; i< effect.size(); i++){
            System.out.print(effect.get(i).getShortDescription());
        }
    }

    /**
     * this method prints the market
     * @param board it's the game board
     */
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

    /**
     * this method prints the market verbose
     * @param board
     */
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

    /**
     * this method prints the build action space
     * @param board
     */
    public void printBuildAS(Board board)
    {
            System.out.println("This is Build Action Space: ");
            System.out.println("Standard "+ board.getBuild().getValueStandard() + ". Malus " + board.getBuild().getValueMalus() );
    }

    /**
     * this method print the Harvest action space on board
     * @param board
     */
    public void printHarvestAS(Board board)
    {
        System.out.println("This is Harvest Action Space: ");
        System.out.println("Standard "+ board.getHarvest().getValueStandard() + ". Malus " + board.getHarvest().getValueMalus() );
    }

    /**
     * this method prints out the council hall
     * @param board
     */
    public void printCouncil(Board board)
    {
        System.out.print("This is Council Hall: ");
        System.out.println("Effect " + board.getCouncil().getSpaceDescription());

    }

    /**
     * this method prints the VaticanReport on board
     * @param board
     */
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

    /**
     * this method prints the board detailed
     * @param board
     */
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

    /**
     * this method prints a line
     * @param lineLenght
     */
    public void printLine(int lineLenght){
        int i;
        for(i = 0; i < lineLenght; i++)
            System.out.print("-");
        System.out.println("");
    }
}
