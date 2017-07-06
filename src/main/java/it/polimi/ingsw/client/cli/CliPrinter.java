package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.cards.VentureCard;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.permanentEffects.AbstractPermanentEffect;
import it.polimi.ingsw.model.excommunicationTiles.ExcommunicationTile;
import it.polimi.ingsw.model.leaders.LeadersDeck;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.PersonalTile;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;

import java.util.ArrayList;
import java.util.LinkedList;
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
     * @param board this is the gameBoard
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
     * @param board            is the game board
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
     * this method gets a string of all the family members inside an
     * @param actionSpace which is where the family members are placed
     * @return a string with a list of all nickame + color family members
     */
    private static String familyMembersInActionSpace(AbstractActionSpace actionSpace)
    {
        StringBuilder familyMembers = new StringBuilder();
        for(FamilyMember iterator : actionSpace.getFamilyMembers()){
            familyMembers.append((iterator.getPlayer().getNickname()));
            familyMembers.append(" ");
            familyMembers.append(iterator.getColor().toString());}
        return familyMembers.toString();
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
     * this method prints an immediate effect pillar floorF
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
        for (int i = 0; i < floors.length; i++)
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
        StringBuilder immediateEffects = new StringBuilder("| ");
        if(floor.getFamilyMembers().isEmpty()) {
            immediateEffects.append("Instantly: ");
            costs = floor.getCard().getImmediateEffect();
            //first i print the costs
            for (int i = 0; i < costs.size(); i++) {
                immediateEffects.append(costs.get(i).descriptionShortOfEffect());
                immediateEffects.append(" ");
            }
        }
        else
            immediateEffects.append(familyMembersInActionSpace(floor));
        //then i print the remaining space from cost to .. |
        printScene(immediateEffects.toString());
        }


    /**
     * this method prints all second effects permanent / harvests / build and purple
     *
     * @param floor
     */
    private static void printCardSecondEffectOnFloor(TowerFloorAS floor) {
        StringBuilder tempCostsScene = new StringBuilder("|");
        if(floor.getFamilyMembers().isEmpty()){
            tempCostsScene.append("Second: ");

            tempCostsScene.append(floor.getCard().secondEffect());
        }
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
            if(temp[k].getFamilyMembers().isEmpty())
                name = temp[k].getCard().getName();
            else
                name = ("Card has been taken by player:");
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
        try{
        printGenericCostPillar(floors[1]);}
        catch (NullPointerException exception){
            printOneEmptyPillar();
        }
        try{
            printGenericCostPillar(floors[2]);}
        catch (NullPointerException exception){
            printOneEmptyPillar();
        }
        try{
            printGenericCostPillar(floors[3]);}
        catch (NullPointerException exception){
            printOneEmptyPillar();
        }

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
    private static void printGenericCostPillar(TowerFloorAS floor) {
        int i = 0;
        ArrayList<Resource> costs;
        StringBuilder tempCostsScene = new StringBuilder("|Cost: ");
        if(floor.getFamilyMembers().size()==0){
        if(floor.getCard() instanceof VentureCard) {
            try {
                tempCostsScene.append(((VentureCard) floor.getCard()).getCostChoiceMilitary().getDescription());
            } catch (NullPointerException noChoiceMilitary) {
                ;
            }
        }}
        if(floor.getFamilyMembers().size()==0){
        try {
            costs = floor.getCard().getCost();
            //first i print the costs
            for (i = 0; i < costs.size(); i++) {
                tempCostsScene.append(costs.get(i).getResourceShortDescript());
                tempCostsScene.append(" ");
            }
        } catch (NullPointerException e) {
            ;
        }}
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
        StringBuilder familyMemberOccupying = new StringBuilder();
        System.out.println("This is the market: ");
        for (i = 0; i < board.getMarket().size(); i++) {
            familyMemberOccupying.delete(0,familyMemberOccupying.length());
            MarketAS market = board.getMarketSpaceByIndex(i);
            if(!market.getFamilyMembers().isEmpty()){
                familyMemberOccupying.append(" FM here player: ");
                familyMemberOccupying.append(familyMembersInActionSpace(market));
            }
            System.out.print("|" + i + familyMemberOccupying.toString() + "| *" + market.getDiceRequirement() + "* " + market.getEffectShortDescription() + " ");

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
        if(!board.getBuild().getFamilyMembers().isEmpty())
            System.out.println("FM here player: " + familyMembersInActionSpace(board.getBuild()));
        else
            System.out.print("");
    }

    /**
     * this method print the Harvest action space on board
     *
     * @param board of the gameBoard
     */
    private static void printHarvestAS(Board board) {
        System.out.println("This is Harvest Action Space: ");
        System.out.println("Standard " + board.getHarvest().getValueStandard() + ". Malus " + board.getHarvest().getValueMalus());
        if(!board.getHarvest().getFamilyMembers().isEmpty())
            System.out.println("FM here: " + familyMembersInActionSpace(board.getHarvest()));
        else
            System.out.print("");
    }

    /**
     * this method prints out the council hall
     *
     * @param board is the gameBoard
     */
    private static void printCouncil(Board board) {
        System.out.print("This is Council Hall: ");
        System.out.println("Effect " + board.getCouncil().getSpaceDescription());
        if(!board.getCouncil().getFamilyMembers().isEmpty())
            System.out.println("FM here: " + familyMembersInActionSpace(board.getCouncilAS()));
        else
            System.out.print("");

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

    private static void printExcommunicationCards(Board board) {
        List<ExcommunicationTile> excommunicationTiles = board.getExcommunicationTiles();
        for (ExcommunicationTile iterator : excommunicationTiles)
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

    /**
     * this method simply cicles a list given from personal Board and calls a printGenericCard for all cards
     *
     * @param cardsToPrint is the List to print
     */
    private static void printGenericCards(LinkedList<? extends AbstractCard> cardsToPrint) {
        if ((cardsToPrint.isEmpty())) {
            System.out.println("NoCards");
            return;
        }
        for (AbstractCard cardIterator : cardsToPrint)
            printGenericCard(cardIterator);
    }

    /**
     * this method prints a generiCard for personalBoard
     *
     * @param card is the generic card we print
     */
    private static void printGenericCard(AbstractCard card) {
        StringBuilder stringToPrint = new StringBuilder();
        stringToPrint.append("Cardname: ");
        stringToPrint.append(card.getName());

        /*no need to print costs cause in personal board they won't be used
        stringToPrint.append("Cardcost: ");
        for(Resource iterator : card.getCost()){
            stringToPrint.append(iterator.getResourceShortDescript());
        }*/

        //i won't print immediate effect because in personal board they are no longer used
        stringToPrint.append(" second effect: ");
        stringToPrint.append(card.secondEffect());
        System.out.println(stringToPrint);
    }

    private static void printPersonalTile(PersonalTile personalTile) {
        System.out.println(personalTile.getDescription());
    }

    /**
     * this method prints the
     *
     * @param player's resources @ personalBoard
     */
    private static void printPersonalBoardResources(Player player) {
        StringBuilder resourcesStatus = new StringBuilder();
        resourcesStatus.append("PRINTING RESOURCES :");
        resourcesStatus.append("\n\tCoins : ");
        resourcesStatus.append(player.getResource(ResourceTypeEnum.COIN));
        resourcesStatus.append("\n\tWood : ");
        resourcesStatus.append(player.getResource(ResourceTypeEnum.WOOD));
        resourcesStatus.append("\n\tStone : ");
        resourcesStatus.append(player.getResource(ResourceTypeEnum.STONE));
        resourcesStatus.append("\n\tServant : ");
        resourcesStatus.append(player.getResource(ResourceTypeEnum.SERVANT));
        resourcesStatus.append("\n\tFaith Point : ");
        resourcesStatus.append(player.getResource(ResourceTypeEnum.FAITH_POINT));
        resourcesStatus.append("\n\tMilitary Point : ");
        resourcesStatus.append(player.getResource(ResourceTypeEnum.MILITARY_POINT));
        resourcesStatus.append("\n\tVictory Point : ");
        resourcesStatus.append(player.getResource(ResourceTypeEnum.VICTORY_POINT));
        System.out.println(resourcesStatus);
    }

    /**
     * this class prints the personalBoard
     *
     * @param player        is the user of the game
     */
    public static void printPersonalBoard(Player player) {
        System.out.println("Personal Board of the player : " + player.getNickname() + "\n");
        System.out.println("");
        System.out.print("Printing Territory Cards: ");
        printGenericCards(player.getPersonalBoard().getCardListByColor(CardColorEnum.GREEN));
        System.out.print("Printing Character Cards: ");
        printGenericCards(player.getPersonalBoard().getCardListByColor(CardColorEnum.BLUE));
        System.out.print("Printing Building Cards: ");
        printGenericCards(player.getPersonalBoard().getCardListByColor(CardColorEnum.YELLOW));
        System.out.print("Printing Venture Cards: ");
        printGenericCards(player.getPersonalBoard().getCardListByColor(CardColorEnum.PURPLE));
        /*printPersonalBoardTerritoryCards(personalBoard.getCardListByColor());
        printPersonalBoardCharacterCards(personalBoard.getCharacterCards());
        printPersonalBoardBuildingCards(personalBoard.getBuildingCards());
        printPersonalBoardVentureCards(personalBoard.getVentureCards());*/
        System.out.print("PRINTING PERSONAL TILE: ");
        printPersonalTile(player.getPersonalBoard().getPersonalTile());
        printPersonalBoardResources(player);

    }

    public static void printLeadersDeck(LeadersDeck leadersDeck) {
        leadersDeck.getLeaders().forEach(leader -> System.out.println("**" + leader.getName() + "**" + "\n"
                + leader.getDescription() + "\n"
                + "Requirement: " + leader.getRequirements().stream().map(req -> req.getDescription()).collect(Collectors.joining()) + "\n"
                + "Ability: " + leader.getAbility().getAbilityDescription() + "\n"));
    }

    /**
     * This method is called by {@link CommandLineUI} to notify the user of a move made with a family member
     * @param familyMember the family member moved
     * @param text the additional text to display
     */
    public static void printFMMoveNotification(FamilyMember familyMember, String text) {
        System.out.println("["+familyMember.getPlayer().getNickname() + "] --> " + familyMember.getPlayer().getNickname() +
        " has placed his " + familyMember.getColor() + " family member of value " + familyMember.getValue() +
        " " + text);
    }

    /**
     * This method is used to print some action of a player
     * @param nickname the player who made the move
     * @param text the text explaining the action
     */
    public static void printPlayerGeneralAction(String nickname, String text) {
        System.out.println("["+nickname + "] --> " + nickname + " " + text);
    }

    /**
     * this will be the standard output
     * @param string is the input string to print
     */
    public static void println(String string)
    {
        System.out.println(string);
    }

    /**
     * another method to print a string
     * @param string is the string to print
     */
    public static void print(String string)
    {
        System.out.print(string);
    }
}