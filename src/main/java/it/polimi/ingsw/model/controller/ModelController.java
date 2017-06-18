package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.cards.BuildingCard;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.leaders.leadersabilities.AbstractLeaderAbility;
import it.polimi.ingsw.model.leaders.leadersabilities.LeaderAbilityTypeEnum;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.*;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
     * this method should be called only at the beginning of the game both by client and server
     */
    public void setFamilyMemberDices()
    {
        //initialize all the family member on the players
        for(Player i : players){
            i.setFamilyMembers(this.dices);
        }
    }

    public ArrayList<Dice> getDices() {
        return dices;
    }

    /**
     * this method is called by the client to set the value of the dice with the value of the dice delivered by the server
     * This method should be called every new round
     */
    public void setDice(ArrayList<Dice> dice){

        for(Dice dieToSet : this.dices){
            for(Dice die :dice){
                if(dieToSet.getColor() == die.getColor()){
                    dieToSet.setValue(die.getValue());
                    break;
                }
            }
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
        players.forEach(Player::prepareForNewRound);

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
     * this method is used to deliver all the tower spaces available to the player
     * @return the coordinates of the tower spaces available for this family member and the servant needed
     */
    public ArrayList<TowerWrapper> spaceTowerAvailable(FamilyMember familyMember){
        Debug.printDebug("spaceTowerCalled");

        ArrayList<TowerWrapper> towerWrappers = new ArrayList<>(16);
        for(int towerIndex = 0 ; towerIndex<4 ; towerIndex++) {

            Tower tower = gameBoard.getTowers()[towerIndex];
            ArrayList<FamilyMember> familyMembers = new ArrayList<>(4);
            for (TowerFloorAS towerFloorAS : tower.getFloors()) {

                familyMembers.addAll(towerFloorAS.getFamilyMembers());
            }

            for (int towerFloor = 0; towerFloor < 4; towerFloor++) {

                TowerFloorAS towerFloorAs = tower.getFloors()[towerFloor];
                if(isPlaceOnTowerFloorLegal(familyMember, familyMember.getPlayer().getResource(ResourceTypeEnum.SERVANT), towerFloorAs, familyMembers)){
                    int servantNeeded = towerFloorAs.getDiceRequirement() - familyMember.getValue() -
                            familyMember.getPlayer().getPersonalBoard().getCharacterCardsCollector().getBonusOnDice(towerFloorAs.getCard().getColor()) ;
                    if (servantNeeded < 0)
                        servantNeeded = 0;

                    towerWrappers.add(new TowerWrapper(towerIndex, towerFloor, servantNeeded));}
            }

            familyMembers.clear();

        }
        Debug.printDebug("spaceTowerRETURNED");

        return towerWrappers;
    }

    /**
     * this method is used to deliver all the market spaces available to the player
     * @return the index of the market spaces available for this family member and the servant needed
     */
    public List<MarketWrapper> spaceMarketAvailable(FamilyMember familyMember) {
        Debug.printDebug("spaceMarketAvailable");

        LinkedList<MarketWrapper> marketWrappers = new LinkedList<MarketWrapper>();
        for (int marketIndex = 0; marketIndex < gameBoard.getMarket().size(); marketIndex++) {
            MarketAS market = gameBoard.getMarket().get(marketIndex);
            if(isMarketActionLegal(familyMember, familyMember.getPlayer().getResource(ResourceTypeEnum.SERVANT), market)){
            int servantNeeded = market.getDiceRequirement() - familyMember.getValue() -
                    familyMember.getPlayer().getPersonalBoard().getCharacterCardsCollector().getBonusOnBuild();
            if (servantNeeded < 0)
                servantNeeded = 0;

            marketWrappers.add(new MarketWrapper(marketIndex, servantNeeded));
            }
        }
        Debug.printDebug("spaceMarketRETURNED");

        return marketWrappers;
    }

    /**
     * this method is used to control if the family member can be placed on the floor of a defined tower
     * @param familyMember the family member tht the player wants to place
     * @return true if the move is available, false otherwise
     */
    private boolean isPlaceOnTowerFloorLegal(FamilyMember familyMember, int servant, TowerFloorAS towerFloorAS,ArrayList<FamilyMember> familyMembersOnTheTower){

        //control on the input, if the player has that resources and if the place is not available
        if(!familyMember.getPlayer().getNotUsedFamilyMembers().contains(familyMember)
                || familyMember.getPlayer().getResource(ResourceTypeEnum.SERVANT)<servant)
            //this means that the player doesn't has the resources that claimed to have, this is cheating
            return false;
        //control on the action space, if the player already has a family member
        if(findFamilyMemberNotNeutral(familyMember.getPlayer(), familyMembersOnTheTower)
                && familyMember.getColor()!=DiceAndFamilyMemberColorEnum.NEUTRAL)
            //this means that the player has already placed a family member on that action space
            return false;
        //control if the family member has a right value to harvest
        if(servant+familyMember.getValue() +
                familyMember.getPlayer().getPersonalBoard().getCharacterCardsCollector().getBonusOnDice(towerFloorAS.getCard().getColor())< towerFloorAS.getDiceRequirement())
            //cannot place this family members because the value is too low
            return false;
        ResourceCollector resource = new ResourceCollector(familyMember.getPlayer().getResourcesCollector());
        resource.addResource(familyMember.getPlayer().getPersonalBoard().getCharacterCardsCollector().getDiscountOnTower(towerFloorAS.getCard().getColor()));
        resource.subResource(towerFloorAS.getCard().getCost());
        ArrayList<ImmediateEffectInterface>effectInterfaces = towerFloorAS.getEffects();
        for(ImmediateEffectInterface effectIter : effectInterfaces){
            if(effectIter instanceof GainResourceEffect){
                 resource.addResource(((GainResourceEffect) effectIter).getResource());
            }
        }

        //it asks the card if the player can buy it with this resources
        if(towerFloorAS.getCard().canBuy(resource))
            return true;
        return false;

    }

    /**
     * This method performs the real action on the model when the player places a FM on a tower
     * This method goes down on the model to perform the action calling {@link Board}, {@link it.polimi.ingsw.model.board.Tower}, {@link it.polimi.ingsw.model.board.TowerFloorAS}
     * @param familyMember the family member to perform the action with
     * @param towerIndex the tower to place the family member to
     * @param floorIndex the floor to place the family member to
     */
    public void placeOnTower(FamilyMember familyMember, int towerIndex, int floorIndex){
         gameBoard.placeOnTower(familyMember, towerIndex, floorIndex, choicesController);
    }

    /**
     * this method is used to control if the family member can move on the harvest and, if possible, the servant needed
     * @return the servant needed or empty if the move is not valid
     */
    public Optional<Integer> spaceHarvestAvailable(FamilyMember familyMember){
        Debug.printDebug("spaceHarvestAvailable");
        if(isHarvestActionLegal(familyMember,familyMember.getPlayer().getResource(ResourceTypeEnum.SERVANT))){
            HarvestAS harvestPlace = gameBoard.getHarvest();
            int servantNeeded = harvestPlace.getValueNeeded() - familyMember.getValue() -
                    familyMember.getPlayer().getPersonalBoard().getCharacterCardsCollector().getBonusOnHarvest();
            if(servantNeeded < 0)
                servantNeeded = 0;
            Debug.printDebug("spaceHarvestAvailableRETURNED");
            return Optional.of(servantNeeded);
        }

        Debug.printDebug("spaceHarvestAvailableRETURNED");
        return Optional.empty();

    }

    /*
     * this method is called to harvest on the board
     * @param familyMember the family member used to harvest
     * @param servant the number of servant used to increase the value of the family member
     */
   private boolean isHarvestActionLegal(FamilyMember familyMember, int servant){

        //control on the input, if the player has that resources
        if(!familyMember.getPlayer().getNotUsedFamilyMembers().contains(familyMember)
                || familyMember.getPlayer().getResource(ResourceTypeEnum.SERVANT)<servant)
            //this means that the player doesn't has the resources that claimed to have, this is cheating
            return false;
        HarvestAS harvestPlace = gameBoard.getHarvest();
        //control on the action space, if the player already has a family member
        if(findFamilyMemberNotNeutral(familyMember.getPlayer(), harvestPlace.getFamilyMembers())
                && familyMember.getColor()!=DiceAndFamilyMemberColorEnum.NEUTRAL)
            //this means that the player has already placed a family member on that action space
            return false;
        //control if the family member has a right value to harvest
        if(servant+familyMember.getValue() +
                familyMember.getPlayer().getPersonalBoard().getCharacterCardsCollector().getBonusOnHarvest() < harvestPlace.getValueNeeded())
            //cannot place this family members because the value is too low
            return false;
        return true;

    }

    /**
     * this method is used to coltrol if the player has another family member on the space, the neutral family member doesn't count
     */
    private boolean findFamilyMemberNotNeutral(Player player, ArrayList<FamilyMember> familyMembers){
        for(FamilyMember i : familyMembers){
            if(i.getPlayer() == player && i.getColor()!= DiceAndFamilyMemberColorEnum.NEUTRAL)
                return true;
        }
        return false;
    }

    /**
     * this method is used to coltrol if the player has another family member on the space
     */
    private boolean findFamilyMember(Player player, ArrayList<FamilyMember> familyMembers){
        for(FamilyMember i : familyMembers){
            if(i.getPlayer() == player)
                return true;
        }
        return false;
    }

    /**
     * this method is used to control if the family member can move on the build and, if possible, the servant needed
     * @return the servant needed or empty if the move is not valid
     */
    public Optional<Integer> spaceBuildAvailable(FamilyMember familyMember){
        Debug.printDebug("spaceBuildAvailable");

        if(isBuildActionLegal(familyMember,familyMember.getPlayer().getResource(ResourceTypeEnum.SERVANT))){
            BuildAS buildPlace = gameBoard.getBuild();
            int servantNeeded = buildPlace.getValueNeeded() - familyMember.getValue() -
                    familyMember.getPlayer().getPersonalBoard().getCharacterCardsCollector().getBonusOnBuild();
            if(servantNeeded < 0)
                servantNeeded = 0;
            Debug.printDebug("spaceBuildAvailableRETURNED");

            return Optional.of(servantNeeded);
        }

        Debug.printDebug("spaceBuildAvailableRETURNED");

        return Optional.empty();

    }

    /**
     * this method is used to control if the move on the build is possible
     * @param servant the servant the player owns
     * @return true if the move is possible, false otherwise
     */
    private boolean isBuildActionLegal(FamilyMember familyMember, int servant){

        //control on the input, if the player really has that resources
        if(!familyMember.getPlayer().getNotUsedFamilyMembers().contains(familyMember)
                || familyMember.getPlayer().getResource(ResourceTypeEnum.SERVANT)<servant)
            //this means that the player doesn't has the resources that claimed to have, this is cheating
            return false;
        BuildAS buildPlace = gameBoard.getBuild();
        //control on the action space, if the player already has a family member
        if(findFamilyMemberNotNeutral(familyMember.getPlayer(), buildPlace.getFamilyMembers())
                && familyMember.getColor()!=DiceAndFamilyMemberColorEnum.NEUTRAL)
            //this means that the player has already placed a family member on that action space
            return false;
        //control if the family member has a right value to build
        if(servant+familyMember.getValue() +
                familyMember.getPlayer().getPersonalBoard().getCharacterCardsCollector().getBonusOnBuild() < buildPlace.getValueNeeded())
            //cannot place this familymembers because the value is too low
            return false;

        return true;
    }

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
     * This method is called when the player harvests, it is the entry point to the model
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

    /**
     * this method is used to control if the family member can be placed on the space merket
     * @param servant number of servants the player owns
     * @return the index of the space, and the servant needed
     */
    private boolean isMarketActionLegal(FamilyMember familyMember, int servant, MarketAS spaceMarket){

        //control on the input, if the player really has that resources
        if(!familyMember.getPlayer().getNotUsedFamilyMembers().contains(familyMember)
                || familyMember.getPlayer().getResource(ResourceTypeEnum.SERVANT)<servant)
            //this means that the player doesn't has the resources that claimed to have, this is cheating
            return false;

        //control on the action space, if the player already has a family member
        if(findFamilyMemberNotNeutral(familyMember.getPlayer(), spaceMarket.getFamilyMembers())
                && familyMember.getColor()!=DiceAndFamilyMemberColorEnum.NEUTRAL)
            //this means that the player has already placed a family member on that action space
            return false;
        //control if the family member has a right value to build
        if(servant+familyMember.getValue() +
                familyMember.getPlayer().getPersonalBoard().getCharacterCardsCollector().getBonusOnBuild() < spaceMarket.getDiceRequirement())
            //cannot place this familymembers because the value is too low
            return false;
        return true;
    }
    /**
     * This method performs the real action on the model when the player places a FM on a market space
     * This method goes down on the model to perform the action calling {@link it.polimi.ingsw.model.board.Board}, {@link it.polimi.ingsw.model.board.MarketAS}
     * @param familyMember
     * @param marketSpaceIndex the selected market AS
     */
    public void placeOnMarket(FamilyMember familyMember, int marketSpaceIndex){

        /*MarketAS marketPlace = gameBoard.getMarketSpaceByIndex(marketSpaceIndex);
        if(!familyMember.getPlayer().getNotUsedFamilyMembers().contains(familyMember)
                || familyMember.getPlayer().getResource(ResourceTypeEnum.SERVANT)<marketPlace.getValueStandard()-familyMember.getValue())
            //this means that the player doesn't has the resources that claimed to have, this is cheating
            return;//TODO cheating or refresh board
        if(marketPlace.getFamilyMember() != null)
            // this means that the place on the market is not available
            return;
        marketPlace.performAction(familyMember);*/

       gameBoard.placeOnMarket(familyMember, marketSpaceIndex, choicesController);
    }

    /**
     * this method is used to control if the family member can move on the harvest and, if possible, the servant needed
     * @return the servant needed or empty if the move is not valid
     */
    public Optional<Integer> spaceCouncilAvailable(FamilyMember familyMember){
        Debug.printDebug("spaceCouncilAvailable");
        if(isCouncilActionLegal(familyMember,familyMember.getPlayer().getResource(ResourceTypeEnum.SERVANT))){
            CouncilAS councilPlace = gameBoard.getCouncil();
            int servantNeeded = councilPlace.getDiceRequirement()- familyMember.getValue();
            if(servantNeeded < 0)
                servantNeeded = 0;
            Debug.printDebug("spaceCouncilAvailableRETURNED");
            return Optional.of(servantNeeded);
        }
        Debug.printDebug("spaceCouncilAvailableRETURNED");
        return Optional.empty();

    }

    /**
     * this method is used to control if the move on the council can be done with the following famil member
     * @param familyMember that the player wants to place
     * @param servant the number of servant used to increase the value of the family member
     * @return false if the move is not legal, true otherwise
     */
    private boolean isCouncilActionLegal(FamilyMember familyMember, int servant){

        //control on the input, if the player really has that resources
        if(!familyMember.getPlayer().getNotUsedFamilyMembers().contains(familyMember)
                || familyMember.getPlayer().getResource(ResourceTypeEnum.SERVANT)<servant)
            //this means that the player doesn't has the resources that claimed to have, this is cheating
            return false;
        CouncilAS councilPlace = gameBoard.getCouncil();
        //control on the action space, if the player already has a family member
        if(findFamilyMember(familyMember.getPlayer(), councilPlace.getFamilyMembers()))
            //this means that the player has already placed a family member on that action space
            return false;
        //control if the family member has a right value to build
        if(servant+familyMember.getValue() < councilPlace.getDiceRequirement())
            //cannot place this family members because the value is too low
            return false;

        return true;
    }
    /**
     * This method performs the real action on the model when the player places a FM int he council
     * This method goes down on the model to perform the action calling {@link it.polimi.ingsw.model.board.Board}, {@link it.polimi.ingsw.model.board.CouncilAS}
     * @param familyMember
     */
    public void placeOnCouncil(FamilyMember familyMember) {
        Player player = familyMember.getPlayer();

        player.playFamilyMember(familyMember);

        gameBoard.placeOnCouncil(familyMember, choicesController);
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


    /**
     * this method is used by the client to understand the move available with a defined family member
     * @return the space where the family member can be placed
     */
    /*public ArrayList<AbstractActionSpace> getAvailableMove(Player player, FamilyMember familyMember){

    }*/

    public Board getBoard() {
        return gameBoard;
    }

    /**
     * this method is called by the controller when a player chooses a leader in the initial phase
     * @param leaderCard the card to add to the player
     * @param player the player tha had chosen the card
     */
    public void addLeaderCardToPlayer(LeaderCard leaderCard, Player player){

        player.addLeaderCard(leaderCard);

    }

    /**
     * this method is called by the controller when a player plays a leader
     * @param leaderCard the card that should be set as played inside the player
     * @param player the player that had chosen to play the card
     */
    public void playLeaderCard(LeaderCard leaderCard, Player player){
        //if the leader he's chosen has the ability to copy another leader ability we should ask which one he wants to copy
        if(leaderCard.getAbility().getAbilityType() == LeaderAbilityTypeEnum.COPY_ABILITY) {
            //Let's retreive all the played leaders of other players
            ArrayList<LeaderCard> playedLeaders = new ArrayList<LeaderCard>(2);
            for(Player playerIter : players) {
                if(playerIter != player) //only leaders from other players can be copied
                    playedLeaders.addAll(playerIter.getPlayedLeaders());
            }
            AbstractLeaderAbility choice = choicesController.callbackOnWhichLeaderAbilityToCopy(playedLeaders);
            leaderCard.setAbility(choice);
        }

        //we deldegate the rest of the action to the player itself
        player.playLeader(leaderCard, choicesController);
    }


    public void placeCardOnBoard(ArrayList<AbstractCard> cardsToPlace) {

        for(CardColorEnum colorEnum : CardColorEnum.values()) {
            int floor = 0;
            for (AbstractCard card : cardsToPlace) {
                if (card.getColor() == colorEnum) {
                    gameBoard.setCardsOnTower(card, colorEnum, floor++);
                    cardsToPlace.remove(card);
                }
            }
        }
    }
}


