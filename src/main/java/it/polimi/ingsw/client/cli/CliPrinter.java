package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.permanentEffects.AbstractPermanentEffect;
import it.polimi.ingsw.model.excommunicationTiles.ExcommunicationTile;
import it.polimi.ingsw.model.leaders.LeadersDeck;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class prints on command line the board
 */
public class CliPrinter {
    //all the next attributes are used to pretty print the board
    //length of tower + action space next to that tower -> total sceneLength
    static final int SCENE_LENGHT = 49;
    //length of a tower
    private static final int INSIDE_TOWER_LENGHT = 35;
    private static final int LINELENGTH = 200;
    private static final int MAX_LENGHT_SHORT_EFFECTS = 12;

    /**
     * this method prints the board
     *
     * @param board is the gameBoard
     */
    public static void printBoard(Board board) {
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
     *
     * @param board
     */
    public static void printTowers(Board board) {
        int i;
        TowerFloorAS[] floorOfAllTowers;
        printTowersName(board);
        printTowerCeiling(board, "_");
        for (i = 0; i < board.getNUMBER_OF_FLOORS(); i++) {
            floorOfAllTowers = board.getFloorLevel(board.getNUMBER_OF_FLOORS() - 1 - i);
            printTower(board, floorOfAllTowers);
        }
        System.out.println("");
    }

    /**
     * this method prints a single tower floor level (ex. territory lvl 1, character lvl 1.....)
     *
     * @param board is the game board
     * @param floorOfAllTowers is a single floor
     */
    private static void printTower(Board board, TowerFloorAS[] floorOfAllTowers) {
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
     *
     * @param board is the gameBoard
     */
    private static void printTowersName(Board board) {
        int k;
        for (k = 0; k < board.getNUMBER_OF_TOWERS(); k++) {
            printStringOnPillar(board.getTowerColor(board.getTower(k)).toString());
        }
    }

    /**
     * this method prints an immediate effect pillar floor
     *
     * @param floors
     */
    private static void printImmediateEffectPillar(TowerFloorAS[] floors) {
        for (int i = 0; i < floors.length; i++)
            printCardImmediateEffectOnFloor(floors[i]);
    }

    /**
     * this method prints the second effect of a card, as a pillar
     *
     * @param floors
     */
    private static void printSecondEffectPillar(TowerFloorAS[] floors) {
        for(int i = 0; i < floors.length; i++)
            printCardSecondEffectOnFloor(floors[i]);
    }

    /**
     * this method prints a string inside a scene (consideringa scene a tower | to the start of the other |
     *
     * @param toPrint is the string to print
     */
    private static void printStringOnPillar(String toPrint) {
        int i;
        int tempLength;
        tempLength = (INSIDE_TOWER_LENGHT - toPrint.length()) / 2 - 1;
        for (i = 0; i < tempLength; i++)
            System.out.print(" ");
        printColorTower(toPrint);
        for (i = 0; i < tempLength; i++)
            System.out.print(" ");
        for (i = 0; i < SCENE_LENGHT - INSIDE_TOWER_LENGHT - 3; i++)
            System.out.print(" ");
    }

    /**
     * this method prints all immediate effect
     *
     * @param floor
     */
    private static void printCardImmediateEffectOnFloor(TowerFloorAS floor) {
        ArrayList<? extends ImmediateEffectInterface> costs;
        StringBuilder immediateEffects = new StringBuilder("| Instantly: ");

        costs = floor.getCard().getImmediateEffect();
        //first i print the costs
        for (int i = 0; i < costs.size(); i++) {
            immediateEffects.append(costs.get(i).descriptionShortOfEffect());
            immediateEffects.append(" ");
        }
        //then i print the remaining space from cost to .. |
        printScene(immediateEffects.toString());
    }

    /**
     * this method prints all second effects permanent / harvests / build and purple
     *
     * @param floor
     */
    private static void printCardSecondEffectOnFloor(TowerFloorAS floor) {
        StringBuilder tempCostsScene = new StringBuilder("| Second: ");
        tempCostsScene.append(floor.getCard().secondEffect());
        //Here i fit my string to the scene.. |
        while (tempCostsScene.length() < INSIDE_TOWER_LENGHT + 1)
            tempCostsScene.append(" ");
        tempCostsScene.append("|");
        //then i prepare the scene for the next pillar. Middle tower lenght is 26 not 29
        for (int i = 0; i < SCENE_LENGHT - INSIDE_TOWER_LENGHT - 3; i++)
            tempCostsScene.append(" ");

        System.out.print(tempCostsScene.toString());
    }

    /**
     * this method prints the card name action space
     *
     * @param board
     * @param temp
     */
    private static void printCardNameActionSpace(Board board, TowerFloorAS[] temp) {
        int tempLength = 0;
        String name;
        for (int k = 0; k < board.getNUMBER_OF_TOWERS(); k++) {
            //temp printing, waiting for filling all the cards
            name = temp[k].getCard().getName();

            System.out.print("|");
            tempLength = ((INSIDE_TOWER_LENGHT) - name.length()) / 2;
            for (int i = 0; i < tempLength; i++)
                System.out.print(" ");
            System.out.print(name);
            tempLength += name.length();
            for (int i = 0; i < (INSIDE_TOWER_LENGHT) - tempLength; i++)
                System.out.print(" ");
            System.out.print("| *" + temp[k].getDiceRequirement() + "* " + temp[k].getEffectShortDescription() + " ");
        }
    }

    /**
     * this class prints all costs cards in a level
     */
    private static void printCostCards(TowerFloorAS[] floors) {
        printOneEmptyPillar();
        printGenericCostPillar(floors[1]);
        printGenericCostPillar(floors[2]);
        printGenericCostPillar(floors[3]);
    }

    /**
     * this method prints pillars for just one tower
     */
    private static void printOneEmptyPillar() {
        System.out.print("|");
        for (int i = 0; i < INSIDE_TOWER_LENGHT; i++)
            System.out.print(" ");
        System.out.print("|");
        for (int j = 0; j < SCENE_LENGHT - INSIDE_TOWER_LENGHT - 3; j++) {
            System.out.print(" ");
        }
    }

    /**
     * This method prints a generic card cost pillar
     */
    public static void printGenericCostPillar(TowerFloorAS floor) {
        int i = 0;
        ArrayList<Resource> costs;
        StringBuilder tempCostsScene = new StringBuilder("|Cost: ");
        costs = floor.getCard().getCost();
        //first i print the costs
        try {
            for (i = 0; i < costs.size(); i++) {
                tempCostsScene.append(costs.get(i).getResourceShortDescript());
                tempCostsScene.append(" ");
            }
        } catch (NullPointerException e) {
            printScene("|La risorsa è vuota " + costs.size() + " " + costs.get(0).getValue());
            return;
        }
        printScene(tempCostsScene.toString());
    }
    
    private static void printScene(String toPrintString) {
        int i;
        StringBuilder toPrint = new StringBuilder(toPrintString);
        //then i print the remaining space from cost to .. |
        while (toPrint.length() < INSIDE_TOWER_LENGHT + 1)
            toPrint.append(" ");
        toPrint.append("|");
        //then i prepare the scene for the next pillar. Middle tower lenght is 26 not 29
        for (i = 0; i < SCENE_LENGHT - INSIDE_TOWER_LENGHT - 3; i++)
            toPrint.append(" ");
        System.out.print(toPrint.toString());
    }

    /**
     * this class prints a pillar
     */
    private static void printPillar() {

        for (int k = 0; k < 4; k++) {
            System.out.print("|");
            for (int i = 0; i < INSIDE_TOWER_LENGHT; i++)
                System.out.print(" ");
            System.out.print("|");
            for (int j = 0; j < SCENE_LENGHT - INSIDE_TOWER_LENGHT - 3; j++) {
                System.out.print(" ");
            }
        }
    }

    /**
     * this method prints the tower ceiling
     *
     * @param board
     * @param c     it's a character that will be printed if we're printing a roof or a ceiling
     */
    private static void printTowerCeiling(Board board, String c) {
        int i, k, j;
        int numberOfUnderscore = 34;
        System.out.println();
        for (k = 0; k < board.getNUMBER_OF_TOWERS(); k++) {
            System.out.print("" + c);
            for (i = 0; i < numberOfUnderscore; i++)
                System.out.print("_");
            if (c.equalsIgnoreCase(("|"))) {
                numberOfUnderscore++;
                System.out.print("_");
            }
            System.out.print(c);
            for (j = 0; j < SCENE_LENGHT - numberOfUnderscore - 3; j++)
                System.out.print(" ");
            if (c.equalsIgnoreCase(("|")))
                numberOfUnderscore--;
        }
    }

    /**
     * this method prints the color on the top of the towers
     *
     * @param printed
     */
    public static void printColorTower(String printed) {
        int i;
        int temp = 10 - printed.length();
        StringBuilder toPrint = new StringBuilder(printed);
        if (printed.length() > 0)
            for (i = 0; i < temp; i++)
                toPrint.append(" ");
        System.out.print(toPrint.toString());
    }

    /**
     * this method prints the short immediate effect
     *
     * @param effect is the effect we are
     */
    public static void printImmediateShortEffects(ArrayList<ImmediateEffectInterface> effect) {
        int i, lenght;
        StringBuilder temp = new StringBuilder();

        for (i = 0; i < effect.size(); i++) {
            temp.append(effect.get(i).descriptionShortOfEffect());
            temp.append(" ");
        }
        lenght = MAX_LENGHT_SHORT_EFFECTS - temp.length();
        for (i = 0; i < lenght; i++)
            temp.append(" ");
        System.out.print(temp.toString());
    }


    /**
     * this method prints the permanent effect
     *
     * @param effect are the effects that will be printed
     */
    public static void printPermanentEffects(List<AbstractPermanentEffect> effect) {
        int i;
        for (i = 0; i < effect.size(); i++) {
            System.out.print(effect.get(i).getShortDescription());
        }
    }

    /**
     * this method prints the market
     *
     * @param board it's the game board
     */
    private static void printMarket(Board board) {
        int i;
        System.out.println("This is the market: ");
        for (i = 0; i < board.getMarket().size(); i++) {
            MarketAS market = board.getMarketSpaceByIndex(i);
            System.out.print("|" + i + "| *" + market.getDiceRequirement() + "* " + market.getEffectShortDescription() + " ");
        }
        System.out.println(" ");
    }


    /**
     * this method prints the build action space
     *
     * @param board of the gameBoard
     */
    private static void printBuildAS(Board board) {
        System.out.println("This is Build Action Space: ");
        System.out.println("Standard " + board.getBuild().getDiceRequirement() + ". Malus " + board.getBuild().getValueMalus());
    }

    /**
     * this method print the Harvest action space on board
     *
     * @param board of the gameBoard
     */
    private static void printHarvestAS(Board board) {
        System.out.println("This is Harvest Action Space: ");
        System.out.println("Standard " + board.getHarvest().getValueStandard() + ". Malus " + board.getHarvest().getValueMalus());
    }

    /**
     * this method prints out the council hall
     *
     * @param board is the gameBoard
     */
    private static void printCouncil(Board board) {
        System.out.print("This is Council Hall: ");
        System.out.println("Effect " + board.getCouncil().getSpaceDescription());

    }

    /**
     * this method prints the VaticanReport on board
     *
     * @param board is game Board
     */
    private static void printVaticanReport(Board board) {
        int i;
        printExcommunicationCards(board);
        System.out.print(" | ");
        for (i = 0; i < board.getVaticanFaithAge().length; i++) {
            System.out.print(board.getVaticanFaithAgeIndex(i));
            System.out.print(" | ");
        }
        System.out.println();
        printLine(LINELENGTH);
        System.out.print(" | ");
        for (i = 0; i < board.getVaticanVictoryPoints().length; i++) {
            System.out.print(board.getVictoryPointsByIndex(i));
            System.out.print(" | ");
        }
    }
    private static void printExcommunicationCards(Board board)
    {
        ArrayList<ExcommunicationTile> excommunicationTiles = board.getExcommunicationTiles();
        System.out.println("Entro qui");
        for(ExcommunicationTile iterator : excommunicationTiles)
            System.out.println("After " + iterator.getPeriod() + " you need to have n faith points to avoid " + iterator.getEffect().getShortEffectDescription());
        return;
    }
    /**
     * this method prints a line
     *
     * @param lineLenght is the lenght of the line
     */
    private static void printLine(int lineLenght) {
        int i;
        for (i = 0; i < lineLenght; i++)
            System.out.print("-");
        System.out.println("");
    }

    public static void printLeadersDeck(LeadersDeck leadersDeck){
        leadersDeck.getLeaders().forEach(leader ->System.out.println("**"+leader.getName()+"**"+"\n"
                        +leader.getDescription()+"\n"
                        +"Requirement: "+leader.getRequirements().stream().map(req ->req.getDescription()). collect(Collectors.joining())+"\n"
                        +"Ability: "+leader.getAbility().getAbilityDescription() +"\n"));
    }
}
