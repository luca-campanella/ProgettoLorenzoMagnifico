package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.controller.network.socket.protocol.FunctionResponse;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColor;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.controller.BoardConfigurator;
import it.polimi.ingsw.controller.Room;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This is the controller of one game
 */
public class GameController {

    /**
     * the room that this controller manages
     */
    private Room room;

    /**
     * the board of this game
     */
    private Board gameBoard;

    /**
     * the players that play in this game
     */
    private ArrayList<Player> players;

    private ArrayList<Dice> dices;

    private HashMap<Integer, FunctionResponse> initializeGame;

    private int round;

    private int period;

    public GameController(ArrayList<Player> players, Room room)
    {

        this.players.addAll(players);
        this.room = room;
        initializeGame = new HashMap<>(5);
        dices = new ArrayList<>(4);
        loadDices();
        loadInfoInitialization();
        doMethod(players.size());
        round=1;
        period=1;
        prepareForNewRound();

    }

    private void loadDices(){

        dices.add(new Dice(DiceAndFamilyMemberColor.BLACK));
        dices.add(new Dice(DiceAndFamilyMemberColor.NEUTRAL));
        dices.add(new Dice(DiceAndFamilyMemberColor.ORANGE));
        dices.add(new Dice(DiceAndFamilyMemberColor.WHITE));

    }
    private void loadInfoInitialization(){

        initializeGame.put(2, this::gameFor2);
        initializeGame.put(3, this::gameFor3);
        initializeGame.put(4, this::gameFor4);
        initializeGame.put(5, this::gameFor5);

    }

    public void doMethod(int numberOfPlayers){

        FunctionResponse initialization=initializeGame.get(numberOfPlayers);
        initialization.chooseMethod();

    }

    private void gameFor5(){

        //TODO method
        gameFor4();

    }

    private void gameFor4(){

        //TODO method
        gameFor3();

    }

    private void gameFor3(){

        //TODO method
        gameFor2();

    }

    private void gameFor2(){

        for(Player i : players){
            //i.load
        }

    }

    private void prepareForNewRound(){

        players.forEach(Player::resetFamilyMember);

        //TODO clean and load the cards on board
        //TODO change order players

        if(round%2==0)
            //TODO METHOD to call the excommunication
        round = round + 1;
    }

    public void prepareForNewPeriod(){

        period = period + 1;
    }

    private void boardConfiguration(int numberOfPlayers)
    {
        BoardConfigurator boardConfigurator = new BoardConfigurator();
        gameBoard = boardConfigurator.createBoard(numberOfPlayers);
    }

    public void placeOnTower(FamilyMember familyMember, int towerIndex, int floorIndex){

    }

    public void harvest(FamilyMember familyMember, int servant){

        //control on the input, if the player reall has that resources
        if(!familyMember.getPlayer().getFamilyMembers().contains(familyMember)
                || familyMember.getPlayer().getResource(ResourceTypeEnum.SERVANT)<servant)
            //this means that the player doesn't has the resources that claimed to have, this is cheating
            return;//TODO cheating or refresh board
        HarvestAS harvestPlace = gameBoard.getHarvest();
        //control on the action space, if the player already has a family member
        if(findFamilyMember(familyMember.getPlayer(), harvestPlace.getFamilyMembers())
                && familyMember.getColor()!=DiceAndFamilyMemberColor.NEUTRAL)
            //this means that the player has already placed a family member on that action space
            return;
        //TODO control of the blue effect
        //control if the family member has a right value to harvest
        if(servant+familyMember.getValue() < harvestPlace.getValueNeeded())
            //cannot place this familymembers because the value is too low
            return;
        gameBoard.harvest(familyMember);
        familyMember.getPlayer().harvest(servant+familyMember.getValue());

    }

    private boolean findFamilyMember(Player player, ArrayList<FamilyMember> familyMembers){
        for(FamilyMember i : familyMembers){
            if(i.getPlayer() == player && i.getColor()!=DiceAndFamilyMemberColor.NEUTRAL)
               return true;
        }
        return false;
    }

    public void build(FamilyMember familyMember, int servant){

        //control on the input, if the player reall has that resources
        if(!familyMember.getPlayer().getFamilyMembers().contains(familyMember)
                || familyMember.getPlayer().getResource(ResourceTypeEnum.SERVANT)<servant)
            //this means that the player doesn't has the resources that claimed to have, this is cheating
            return;//TODO cheating or refresh board
        BuildAS buildPlace = gameBoard.getBuild();
        //control on the action space, if the player already has a family member
        if(findFamilyMember(familyMember.getPlayer(), buildPlace.getFamilyMembers())
                && familyMember.getColor()!=DiceAndFamilyMemberColor.NEUTRAL)
            //this means that the player has already placed a family member on that action space
            return;
        //TODO control of the blue effect
        //control if the family member has a right value to build
        if(servant+familyMember.getValue() < buildPlace.getValueNeeded())
            //cannot place this familymembers because the value is too low
            return;
        gameBoard.build(familyMember);
        familyMember.getPlayer().build(familyMember.getValue());
        //TODO add control of the move and code on the board
    }

    public void placeOnMarket(FamilyMember familyMember, int marketSpaceIndex){

        MarketAS marketPlace = gameBoard.getMarketSpaceByIndex(marketSpaceIndex);
        if(!familyMember.getPlayer().getFamilyMembers().contains(familyMember)
                || familyMember.getPlayer().getResource(ResourceTypeEnum.SERVANT)<marketPlace.getValueStandard()-familyMember.getValue()
                || marketPlace == null)
            //this means that the player doesn't has the resources that claimed to have, this is cheating
            return;//TODO cheating or refresh board
        if(marketPlace.getFamilyMember() != null)
            // this means that the place on the market is not available
            return;
        marketPlace.performAction(familyMember);
    }

    public void discardCardLeader(Player player, int cardIndex){

        //player.discardLeaderCard();
    }

    public void activateCardLeader(Player player,int cardIndex){

        //player.activateLeaderCard();

    }
}


