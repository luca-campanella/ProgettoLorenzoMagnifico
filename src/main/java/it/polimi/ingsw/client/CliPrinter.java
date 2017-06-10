package it.polimi.ingsw.client;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.permanentEffects.AbstractPermanentEffect;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * This class prints on command line the board
 */
public class CliPrinter {
    //all the next attributes are used to pretty print the board
    //length of tower + action space next to that tower -> total sceneLength
    static final int sceneLenght = 49;
    //length of a tower
    static final int INSIDE_TOWER_LENGHT = 35;
    static final int LINELENGTH = 200;
    static final int MAX_LENGHT_SHORT_EFFECTS = 12;
    /**
     * this method prints the board
     * @param board
     */
    public void printBoard(Board board){
        //printing the towers
        printTowers(board);
        //printing the market
        printLine(LINELENGTH);
        printMarket(board);
        //printMarketVerbose(board)
        printLine(LINELENGTH);
        //printing build
        printBuildAS(board);
        printLine(LINELENGTH);
        //printing harvest
        printHarvestAS(board);
        printLine(LINELENGTH);
        //printing council
        printCouncil(board);
        //printing vaticanReport
        printLine(LINELENGTH);
        printVaticanReport(board);
        System.out.println();
    }

    /**
     * print towers with no overhead
     * @param board
     */
    public void printTowers(Board board){
        int i;
        TowerFloorAS[] floorOfAllTowers; // = new TowerFloorAS[board.getNUMBER_OF_TOWERS()];
        printTowersName(board);
        printTowerCeiling(board, "_");
        for(i=0; i< board.getNUMBER_OF_FLOORS(); i++)
        {
            floorOfAllTowers = board.getFloorLevel(board.getNUMBER_OF_FLOORS()-1-i);
            printTower(board, floorOfAllTowers);
        }
        System.out.println("");
    }

    /**
     * this method prints a single tower floor level (ex. territory lvl 1, character lvl 1.....)
     * @param board
     * @param floorOfAllTowers
     */
    private void printTower(Board board, TowerFloorAS[] floorOfAllTowers)
    {
        System.out.println();
        printCostCards(floorOfAllTowers);
        System.out.println();
        printPillar();
        System.out.println();
        printCardNameActionSpace(board, floorOfAllTowers);
        System.out.println();
        printImmediateEffectPillar(floorOfAllTowers);
        System.out.println();
        printSecondEffectPillar(floorOfAllTowers);
        printTowerCeiling(board, "|");
    }

    /**
     * this method prints the tower name at the top of the towers.
     * @param board
     */
    private void printTowersName(Board board)
    {
        int k;
        for(k=0; k<board.getNUMBER_OF_TOWERS(); k++) {
            printStringOnPillar(board.getTowerColor(board.getTower(k)).toString());
        }
    }

    /**
     * this method prints an immediate effect pillar floor
     * @param floors
     */
    private void printImmediateEffectPillar(TowerFloorAS[] floors){
        for(int i= 0; i< floors.length; i++)
        printCardImmediateEffectOnFloor(floors[i]);
    }

    /**
     * this method prints the second effect of a card, as a pillar
     * @param floors
     */
    private void printSecondEffectPillar(TowerFloorAS[] floors){
        for(int i= 0; i< floors.length; i++)
            printCardSecondEffectOnFloor(floors[i]);
    }

    /**
     * this method prints a string inside a scene (consideringa scene a tower | to the start of the other |
     * @param toPrint
     */
    private void printStringOnPillar(String toPrint)
    {
        int i;
        int tempLength;
        tempLength = (INSIDE_TOWER_LENGHT - toPrint.length())/2 -1;
        for(i=0; i<tempLength; i++)
            System.out.print(" ");
        printColorTower(toPrint);
        for(i=0; i<tempLength; i++)
            System.out.print(" ");
        for(i=0; i< sceneLenght - INSIDE_TOWER_LENGHT - 3; i++)
            System.out.print(" ");
    }
    /**
     * this method prints all immediate effect
     * @param floor
     */
    private void printCardImmediateEffectOnFloor(TowerFloorAS floor)
    {
        ArrayList<? extends ImmediateEffectInterface> costs;
        String immediateEffectsString = "| Instantly: ";
        costs = floor.getCard().getImmediateEffect();
        //first i print the costs
        for(int i = 0; i< costs.size(); i++)
            immediateEffectsString += costs.get(i).descriptionShortOfEffect() + " ";
        //then i print the remaining space from cost to .. |
        printScene(immediateEffectsString);
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

            String auxiliaryPrinterString;
            auxiliaryPrinterString = " *" + temp[k].getDiceValue() + "* " + temp[k].getEffectShortDescription();
            System.out.print(auxiliaryPrinterString);
            System.out.print(" ");
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
        int i = 0;
        ArrayList<Resource> costs;
        String tempCostsScene = "|Cost: ";
        costs = floor.getCard().getCost();
        //first i print the costs
        try {
            for (i = 0; i < costs.size(); i++)
                tempCostsScene += costs.get(i).getResourceShortDescript() + " ";
        }
        catch(NullPointerException e){
            System.out.print(costs.get(i).toString());
        }
        printScene(tempCostsScene);
    }
    private void printScene(String toPrint)
    {
        int i;
        //then i print the remaining space from cost to .. |
        while(toPrint.length()< INSIDE_TOWER_LENGHT+1)
            toPrint += " ";
        toPrint += "|";
        //then i prepare the scene for the next pillar. Middle tower lenght is 26 not 29
        for(i = 0; i< sceneLenght-INSIDE_TOWER_LENGHT -3; i++)
            toPrint += " ";
        System.out.print(toPrint);
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
     * this method prints the permanent effect
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
     * this method prints the build action space
     * @param board
     */
    public void printBuildAS(Board board)
    {
            System.out.println("This is Build Action Space: ");
            System.out.println("Standard "+ board.getBuild().getDiceValue() + ". Malus " + board.getBuild().getValueMalus() );
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
        printLine(LINELENGTH);
        System.out.print(" | ");
        for(i = 0; i< board.getVaticanVictoryPoints().length; i++) {
            System.out.print(board.getVictoryPointsByIndex(i));
            System.out.print(" | ");
        }
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
