package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.server.ControllerGame;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColor;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.server.network.AbstractConnectionPlayer;

import java.util.ArrayList;

/**
 * This is the controller of one game
 */
public class ModelController {

    /**
     * the room that this controller manages
     */
    private ControllerGame controllerGame;

    /**
     * the board of this game
     */
    private Board gameBoard;

    /**
     * the players that play in this game
     */
    private ArrayList<Player> players;
    private ArrayList<Dice> dices;

    private int round;

    private int period;

    public ModelController(ArrayList<? extends AbstractConnectionPlayer> players, ControllerGame controllerGame, Board board)
    {

        this.players = new ArrayList<>(5);
        this.players.addAll(players);
        this.controllerGame = controllerGame;
        dices = new ArrayList<>(4);
        loadDices();
        round=1;
        period=1;

    }

    private void loadDices(){

        dices.add(new Dice(DiceAndFamilyMemberColor.BLACK));
        dices.add(new Dice(DiceAndFamilyMemberColor.NEUTRAL));
        dices.add(new Dice(DiceAndFamilyMemberColor.ORANGE));
        dices.add(new Dice(DiceAndFamilyMemberColor.WHITE));

    }

    /**
     * this method prepare all the resources to prepere for the game
     */
    public void startNewGame()
    {
        //initialize all the family member on the players
        for(Player i : players){
            i.setFamilyMembers(dices);
        }

    }


    /**
     * add the initial coins for every player
     */
    public void addCoinsStartGame(ArrayList<? extends Player> players){

        Resource resource = new Resource(ResourceTypeEnum.COIN, 5);

        for (Player player : players){

            player.addResource(resource);
            resource.setValue(resource.getValue()+1);

        }

    }

    /**
     * prepare the players for the new round
     */
    public void prepareForNewRound(){

        //reload the family member
        players.forEach(Player::reloadFamilyMember);

        gameBoard.clearBoard();

        //TODO clean and load the cards on board

        if(round%2==0){}
            //TODO METHOD to call the excommunication
        round = round + 1;
    }

    public ArrayList<FamilyMember> getFamilyMemberCouncil(){

        return gameBoard.getCouncil().getFamilyMembers();

    }

    public void prepareForNewPeriod(){
        period = period + 1;
    }

    public void placeOnTower(FamilyMember familyMember, int towerIndex, int floorIndex){

    }

    /**
     * this method is called to harvest on the board
     * @param familyMember the family member used to harvest
     * @param servant the number of servant used to increase the value of the family member
     */
    public void harvest(FamilyMember familyMember, int servant){

        //control on the input, if the player reall has that resources
        if(!familyMember.getPlayer().getNotUsedFamilyMembers().contains(familyMember)
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
        familyMember.getPlayer().harvest(servant+familyMember.getValue()+harvestPlace.getValueMalus());

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
        if(!familyMember.getPlayer().getNotUsedFamilyMembers().contains(familyMember)
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
        familyMember.getPlayer().build(servant+familyMember.getValue()+ buildPlace.getValueMalus());
        //TODO add control of the move and code on the board
    }

    public void placeOnMarket(FamilyMember familyMember, int marketSpaceIndex){

        MarketAS marketPlace = gameBoard.getMarketSpaceByIndex(marketSpaceIndex);
        if(!familyMember.getPlayer().getNotUsedFamilyMembers().contains(familyMember)
                || familyMember.getPlayer().getResource(ResourceTypeEnum.SERVANT)<marketPlace.getValueStandard()-familyMember.getValue()
                || marketPlace == null)
            //this means that the player doesn't has the resources that claimed to have, this is cheating
            return;//TODO cheating or refresh board
        if(marketPlace.getFamilyMember() != null)
            // this means that the place on the market is not available
            return;
        marketPlace.performAction(familyMember);
    }

    public void discardLeaderCard(Player player, String nameLeader){

        //player.discardLeaderCard();
    }

    public void activateLeaderCard(Player player, String nameLeader){

        //player.activateLeaderCard();

    }

    public void endGame(){

        players.forEach(Player::purplePoints);
    }
}


