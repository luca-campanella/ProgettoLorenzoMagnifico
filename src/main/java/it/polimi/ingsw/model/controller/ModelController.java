package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.cards.BuildingCard;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This is the controller of one game
 */
public class ModelController {

    /**
     * the board of this game
     */
    private Board gameBoard;

    /**
     * the players that play in this game
     */
    private ArrayList<Player> players;
    /**
     * the dices available to that game
     */
    private ArrayList<Dice> dices;


    /**
     * this is the object that lets the model make callbacks on the controller and get the answer either from the user or from the network package
     */
    private ChoicesHandlerInterface choicesController;

    private int round;

    private int period;

    public ModelController(ArrayList<? extends Player> players, Board board)
    {

        this.players = new ArrayList<>(5);
        this.players.addAll(players);
        dices = new ArrayList<>(4);
        this.gameBoard = board;
        loadDices();
        round=1;
        period=1;

    }

    private void loadDices(){
        dices.add(new Dice(DiceAndFamilyMemberColorEnum.BLACK));
        dices.add(new Dice(DiceAndFamilyMemberColorEnum.NEUTRAL));
        dices.add(new Dice(DiceAndFamilyMemberColorEnum.ORANGE));
        dices.add(new Dice(DiceAndFamilyMemberColorEnum.WHITE));
    }

    /**
     * this method prepare all the resources to prepare for the game
     */
    public void startNewGame()
    {
        //throws the dices to change the value
        dices.forEach(Dice::throwDice);

        //initialize all the family member on the players
        for(Player i : players){
            i.setFamilyMembers(dices);
        }
    }

    /**
     * This method should be called by the server at game startup setting the controller
     * from the client is should be called to swap from asking the user (his turn) to asking the package (not his turn) coming from the server for the choices
     * @param choicesController
     */
    public void setChoicesController(ChoicesHandlerInterface choicesController) {
        this.choicesController = choicesController;
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

        //throws the dices to change the value
        dices.forEach(Dice::throwDice);

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


    /**
     * This method performs the real action on the model when the player places a FM on a tower
     * This method goes down on the model to perform the action calling {@link Board}, {@link it.polimi.ingsw.model.board.Tower}, {@link it.polimi.ingsw.model.board.TowerFloorAS}
     * @param familyMember the family member to perform the action with
     * @param servants the number of servants to perform the action with
     * @param towerIndex the tower to place the family member to
     * @param floorIndex the floor to place the family member to
     */
    public void placeOnTower(FamilyMember familyMember, int servants, int towerIndex, int floorIndex){
        Player player = familyMember.getPlayer();

        //set the family member as used in the player
        player.playFamilyMember(familyMember);
        player.subResource(new Resource(ResourceTypeEnum.SERVANT, servants));
        //just adds the family member to the BuildAS
        gameBoard.placeOnTower(familyMember, towerIndex, floorIndex, choicesController);
    }

    /*
     * this method is called to harvest on the board
     * @param familyMember the family member used to harvest
     * @param servant the number of servant used to increase the value of the family member
     */
   /* public void isHarvestActionLegal(FamilyMember familyMember, int servant){

        //control on the input, if the player has that resources
        if(!familyMember.getPlayer().getNotUsedFamilyMembers().contains(familyMember)
                || familyMember.getPlayer().getResource(ResourceTypeEnum.SERVANT)<servant)
            //this means that the player doesn't has the resources that claimed to have, this is cheating
            return;//TODO cheating or refresh board
        HarvestAS harvestPlace = gameBoard.getHarvest();
        //control on the action space, if the player already has a family member
        if(findFamilyMember(familyMember.getPlayer(), harvestPlace.getFamilyMembers())
                && familyMember.getColor()!=DiceAndFamilyMemberColorEnum.NEUTRAL)
            //this means that the player has already placed a family member on that action space
            return;
        //TODO control of the blue effect
        //control if the family member has a right value to harvest
        if(servant+familyMember.getValue() < harvestPlace.getValueNeeded())
            //cannot place this familymembers because the value is too low
            return;
        gameBoard.harvest(familyMember);
        familyMember.getPlayer().harvest(servant+familyMember.getValue()+harvestPlace.getValueMalus());

    }*/

    private boolean findFamilyMember(Player player, ArrayList<FamilyMember> familyMembers){
        for(FamilyMember i : familyMembers){
            if(i.getPlayer() == player && i.getColor()!= DiceAndFamilyMemberColorEnum.NEUTRAL)
                return true;
        }
        return false;
    }

    /*public boolean isBuildLegalAction(FamilyMember familyMember, int servant){

        //control on the input, if the player reall has that resources
        if(!familyMember.getPlayer().getNotUsedFamilyMembers().contains(familyMember)
                || familyMember.getPlayer().getResource(ResourceTypeEnum.SERVANT)<servant)
            //this means that the player doesn't has the resources that claimed to have, this is cheating
            return false;//TODO cheating or refresh board
        BuildAS buildPlace = gameBoard.getBuild();
        //control on the action space, if the player already has a family member
        if(findFamilyMember(familyMember.getPlayer(), buildPlace.getFamilyMembers())
                && familyMember.getColor()!=DiceAndFamilyMemberColorEnum.NEUTRAL)
            //this means that the player has already placed a family member on that action space
            return false;
        //TODO control of the blue effect
        //control if the family member has a right value to build
        if(servant+familyMember.getValue() < buildPlace.getValueNeeded())
            //cannot place this familymembers because the value is too low
            return false;
        gameBoard.build(familyMember);
        familyMember.getPlayer().build(servant+familyMember.getValue()+ buildPlace.getValueMalus());
        //TODO add control of the move and code on the board

        return true;
    }*/

    /**
     * This methos is called when the player builds, it is the entry point to the model
     * @param familyMember the family member the player performed the action with
     * @param servants the number of servants the player performed the action with
     */
    public void build(FamilyMember familyMember, int servants) {

        Player player = familyMember.getPlayer();

        //set the family member as used in the player
        player.playFamilyMember(familyMember);
        player.subResource(new Resource(ResourceTypeEnum.SERVANT, servants));
        //just adds the family member to the BuildAS
        gameBoard.build(familyMember);

        player.build(familyMember.getValue() + servants, choicesController);
    }

    /**
     * This methos is called when the player harvests, it is the entry point to the model
     * @param familyMember the family member the player performed the action with
     * @param servants the number of servants the player performed the action with
     */
    public void harvest(FamilyMember familyMember, int servants) {

        Player player = familyMember.getPlayer();

        //set the family member as used in the player
        player.playFamilyMember(familyMember);
        player.subResource(new Resource(ResourceTypeEnum.SERVANT, servants));
        //just adds the family member to the BuildAS
        gameBoard.harvest(familyMember);

        player.harvest(familyMember.getValue() + servants, choicesController);
    }

    public void placeOnMarket(FamilyMember familyMember, int marketSpaceIndex){

        /*MarketAS marketPlace = gameBoard.getMarketSpaceByIndex(marketSpaceIndex);
        if(!familyMember.getPlayer().getNotUsedFamilyMembers().contains(familyMember)
                || familyMember.getPlayer().getResource(ResourceTypeEnum.SERVANT)<marketPlace.getValueStandard()-familyMember.getValue()
                || marketPlace == null)
            //this means that the player doesn't has the resources that claimed to have, this is cheating
            return;//TODO cheating or refresh board
        if(marketPlace.getFamilyMember() != null)
            // this means that the place on the market is not available
            return;
        marketPlace.performAction(familyMember);*/
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

    /**
     * this method returns the player object starting from his nickname
     * @param nickname
     * @return the corresponding Player
     * @throws IllegalArgumentException if not player with that nickname is found
     */
    public Player getPlayerByNickname(String nickname) throws IllegalArgumentException {
        for(Player i : players) {
            if(i.getNickname().equals(nickname))
                return i;
        }
        throw new IllegalArgumentException("No nickname matching " + nickname);
    }

    /*
     * This method return all the possible choices when a player decides to build
     * This method will be called by {@link it.polimi.ingsw.client.controller.ClientMain}, all on the client
     * @param familyMember the family member chosen to perform the cation, important to know the value of the action
     * @param servants number of servants added, still importants, some cards have higher values than others
     * @return an list of choices
     */
    /*public LinkedList<ChoiceContainer> getChoicesOnBuild(FamilyMember familyMember, Resource servants) {
        LinkedList<ChoiceContainer> choicesList = new LinkedList<ChoiceContainer>();
        LinkedList<BuildingCard> buildingCards = familyMember.getPlayer().getPersonalBoard().getYellowBuildingCards();

        int realDiceValue = familyMember.getValue() + servants.getValue();

        ArrayList<Resource> tempResources;

        for(BuildingCard cardIter : buildingCards) {
            if(cardIter.getBuildEffectValue() <= realDiceValue) {
                choicesList.add(new ChoiceContainer(cardIter.getName(), cardIter.getEffectsOnBuilding()));
            }
        }

        return choicesList;
    }*/

    /**
     * This method returns all the build yellow cards of a certain player
     * @param player the selected player
     * @return the list of cards
     */
    public LinkedList<BuildingCard> getYellowBuildingCards(Player player) {
        return player.getPersonalBoard().getYellowBuildingCards();
    }

    public ArrayList<Dice> getDices() {
        return dices;
    }

    public Board getBoard() {
        return gameBoard;
    }
}


