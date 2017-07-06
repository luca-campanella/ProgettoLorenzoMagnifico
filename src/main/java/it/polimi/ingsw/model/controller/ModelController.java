package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.client.network.socket.packet.PlayerPositionEndGamePacket;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.cards.BuildingCard;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.excommunicationTiles.ExcommunicationTile;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.leaders.leadersabilities.AbstractLeaderAbility;
import it.polimi.ingsw.model.leaders.leadersabilities.LeaderAbilityTypeEnum;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.PersonalTile;
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
        round =1;
        period =1;

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

        Resource resource = new Resource(ResourceTypeEnum.COIN, 4);
        //todo: eliminate. I'm using this to test leaders and labels
        Resource resource1 = new Resource(ResourceTypeEnum.SERVANT, 15);
        Resource resource2 = new Resource(ResourceTypeEnum.MILITARY_POINT, 30);
        Resource resource3 = new Resource(ResourceTypeEnum.STONE, 15);
        Resource resource4 = new Resource(ResourceTypeEnum.WOOD, 15);

        for (Player player : players){
            resource.setValue(resource.getValue()+1);
            player.addResource(resource);
        }
        players.get(0).addResource(resource);
        players.get(0).addResource(resource);
        players.get(0).addResource(resource);
        players.get(0).addResource(resource1);
        players.get(0).addResource(resource2);
        players.get(0).addResource(resource3);
        players.get(0).addResource(resource4);


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
                            familyMember.getPlayer().getPersonalBoard().getCharacterCardsCollector().getBonusOnDice(towerFloorAs.getCard().getColor())
                            //malus coming from excommunication tiles
                            + familyMember.getPlayer().getExcommunicationTilesCollector().malusDiceOnTowerColor(towerFloorAs.getCard().getColor());

                    //malus coming from excommunication tile
                    servantNeeded *= familyMember.getPlayer().getExcommunicationTilesCollector().payMoreServant();

                    if (servantNeeded < 0)
                        servantNeeded = 0;

                    towerWrappers.add(new TowerWrapper(towerIndex, towerFloor, servantNeeded));
                    Debug.printVerbose("Added to wrapper: indice " + towerIndex + "piano " + towerFloor);}
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

                //malus coming from excommunication tile
                servantNeeded *= familyMember.getPlayer().getExcommunicationTilesCollector().payMoreServant();

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

        Player player = familyMember.getPlayer();

        //if it's a territory card we have to check if the requirement on military points is met
        if(towerFloorAS.getCard().getColor() == CardColorEnum.GREEN
                && !player.getPersonalBoard().
                canAddTerritoryCard(player.getResource(ResourceTypeEnum.COIN),
                        player.getPermanentLeaderCardCollector().noMilitaryPointsNeededForTerritoryCards()))
            return false;

        //control on the input, if the player has that resources and if the place is not available
        if(!player.getNotUsedFamilyMembers().contains(familyMember)
                || player.getResource(ResourceTypeEnum.SERVANT)<servant){
            //this means that the player doesn't has the resources that claimed to have, this is cheating
            Debug.printVerbose("the player doesn t have the family member");
            return false;
        }
        //control on the action space, if the player already has a family member
        if(findFamilyMemberNotNeutral(player, familyMembersOnTheTower)
                && familyMember.getColor()!=DiceAndFamilyMemberColorEnum.NEUTRAL){
            Debug.printVerbose("there are already another one");
            //this means that the player has already placed a family member on that action space
            return false;}
        //control if the family member has a right value to place here
        if((servant/player.getExcommunicationTilesCollector().payMoreServant())
                //we divide by the exchange rate given by the malus of excommunication tiles
                +familyMember.getValue() +
                //bonus from blue cards
                player.getPersonalBoard().getCharacterCardsCollector().getBonusOnDice(towerFloorAS.getCard().getColor())
                //malus from excomm tiles
                - player.getExcommunicationTilesCollector().malusDiceOnTowerColor(towerFloorAS.getCard().getColor())
                < towerFloorAS.getDiceRequirement()){
            Debug.printVerbose("doesnt have the family member value");
            //cannot place this family members because the value is too low
            return false;
        }
        //we check if the action space is not occupied and if the user has a leader that lets him place the fm there anyway
        if(towerFloorAS.getOccupyingFamilyMemberNumber() >= 1 && !player.getPermanentLeaderCardCollector().canPlaceFamilyMemberInOccupiedActionSpace())
            return false;

        ResourceCollector resource = new ResourceCollector(familyMember.getPlayer().getResourcesCollector());

        //if he's not the first to place a family member on the tower we subtract three coins
        if(!familyMembersOnTheTower.isEmpty() && !player.getPermanentLeaderCardCollector().hasNotToSpendForOccupiedTower()) {
            resource.subResource(new Resource(ResourceTypeEnum.COIN, 3));
            if(resource.getResource(ResourceTypeEnum.COIN) < 0)
                return false;
        }

        //we add the bonuses that come from blu cards and leader cards
        resource.addResource(player.getPersonalBoard().getCharacterCardsCollector().getDiscountOnTower(towerFloorAS.getCard().getColor()));
        resource.addResource(player.getPermanentLeaderCardCollector().getDiscountOnCardCost(towerFloorAS.getCard().getColor()));
        ArrayList<ImmediateEffectInterface>effectInterfaces = towerFloorAS.getEffects();
        Resource resTmp;
        int resValue = 0;
        for(ImmediateEffectInterface effectIter : effectInterfaces){
            if(effectIter instanceof GainResourceEffect){
                //we have to check if there's a malus coming from excommunication tiles
                resTmp = ((GainResourceEffect) effectIter).getResource();
                resValue = resTmp.getValue();
                resValue -= player.getExcommunicationTilesCollector().gainFewResource(resTmp.getType());
                resource.addResource(new Resource(resTmp.getType(), resValue));
            }
        }

            //it asks the card if the player can buy it with this resources
             if(towerFloorAS.getCard().canBuy(resource))
              return true;
            Debug.printVerbose("cannot buy the card");
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
            boolean canPlaceOccupiedActionSpace = familyMember.getPlayer().
                    getPermanentLeaderCardCollector().canPlaceFamilyMemberInOccupiedActionSpace();
            int servantNeeded = harvestPlace.getValueNeeded(canPlaceOccupiedActionSpace) - familyMember.getValue() -
                    familyMember.getPlayer().getPersonalBoard().getCharacterCardsCollector().getBonusOnHarvest()
                    + familyMember.getPlayer().getExcommunicationTilesCollector().buildDiceMalusEffect(); //excomm tiles malus

            //malus coming from excommunication tile
            servantNeeded *= familyMember.getPlayer().getExcommunicationTilesCollector().payMoreServant();

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
       //if the game is a two players gameme only one family member can be placed
       if(!harvestPlace.checkIfFirst() && harvestPlace.isTwoPlayersOneSpace()
               && !familyMember.getPlayer().getPermanentLeaderCardCollector().canPlaceFamilyMemberInOccupiedActionSpace())
           return false;
        //control on the action space, if the player already has a family member
        if(findFamilyMemberNotNeutral(familyMember.getPlayer(), harvestPlace.getFamilyMembers())
                && familyMember.getColor()!=DiceAndFamilyMemberColorEnum.NEUTRAL)
            //this means that the player has already placed a family member on that action space
            return false;
       boolean canPlaceOccupiedActionSpace = familyMember.getPlayer().
               getPermanentLeaderCardCollector().canPlaceFamilyMemberInOccupiedActionSpace();
        //control if the family member has a right value to harvest
        if((servant/familyMember.getPlayer().getExcommunicationTilesCollector().payMoreServant())
                //we divide by the exchange rate given by the malus of excommunication tiles
                +familyMember.getValue() +
                familyMember.getPlayer().getPersonalBoard().getCharacterCardsCollector().getBonusOnHarvest() //blue cards bonus
                - familyMember.getPlayer().getExcommunicationTilesCollector().harvestDiceMalusEffect() //excomm tiles malus
                < harvestPlace.getValueNeeded(canPlaceOccupiedActionSpace))
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
            boolean canPlaceOccupiedActionSpace = familyMember.getPlayer().
                    getPermanentLeaderCardCollector().canPlaceFamilyMemberInOccupiedActionSpace();
            int servantNeeded = buildPlace.getValueNeeded(canPlaceOccupiedActionSpace)
                    - familyMember.getValue() -
                    familyMember.getPlayer().getPersonalBoard().getCharacterCardsCollector().getBonusOnBuild()
                    + familyMember.getPlayer().getExcommunicationTilesCollector().buildDiceMalusEffect(); //excomm tiles malus


            //malus coming from excommunication tile
            servantNeeded *= familyMember.getPlayer().getExcommunicationTilesCollector().payMoreServant();

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
        //if the game is a two players gameme only one family member can be placed
        if(!buildPlace.checkIfFirst() && buildPlace.isTwoPlayersOneSpace()
                && !familyMember.getPlayer().getPermanentLeaderCardCollector().canPlaceFamilyMemberInOccupiedActionSpace())
            return false;
        //control on the action space, if the player already has a family member
        if(findFamilyMemberNotNeutral(familyMember.getPlayer(), buildPlace.getFamilyMembers())
                && familyMember.getColor()!=DiceAndFamilyMemberColorEnum.NEUTRAL)
            //this means that the player has already placed a family member on that action space
            return false;
        boolean canPlaceOccupiedActionSpace = familyMember.getPlayer().
                getPermanentLeaderCardCollector().canPlaceFamilyMemberInOccupiedActionSpace();
        //control if the family member has a right value to build
        if((servant/familyMember.getPlayer().getExcommunicationTilesCollector().payMoreServant())
                //we divide by the exchange rate given by the malus of excommunication tiles
                +familyMember.getValue() +
                familyMember.getPlayer().getPersonalBoard().getCharacterCardsCollector().getBonusOnBuild() //blue cards bonus
                - familyMember.getPlayer().getExcommunicationTilesCollector().buildDiceMalusEffect() //excomm tiles malus
                < buildPlace.getValueNeeded(canPlaceOccupiedActionSpace))
            //cannot place this family members because the value is too low
            return false;

        return true;
    }

    /**
     * This methos is called when the player builds, it is the entry point to the model
     * @param familyMember the family member the player performed the action with
     * @param servants the number of servants the player performed the action with
     *                 malus coming from excommunication tiles is already included
     */
    public void build(FamilyMember familyMember, int servants) {

        Player player = familyMember.getPlayer();

        //set the family member as used in the player
        player.playFamilyMember(familyMember);
        player.subResource(new Resource(ResourceTypeEnum.SERVANT, servants));
        //just adds the family member to the BuildAS
        gameBoard.build(familyMember);
        Debug.printVerbose("before calling player.build");

        int realDiceValueNoBlueBonusYesLeadersYesExcomm = familyMember.getValue() + servants;
        //we check if he's not the first inside the action space
        if(!gameBoard.getBuild().checkIfFirst() && !player.getPermanentLeaderCardCollector().canPlaceFamilyMemberInOccupiedActionSpace())
            realDiceValueNoBlueBonusYesLeadersYesExcomm -=  gameBoard.getBuild().getValueMalus();

        realDiceValueNoBlueBonusYesLeadersYesExcomm -= player.getExcommunicationTilesCollector().buildDiceMalusEffect();


        player.build(realDiceValueNoBlueBonusYesLeadersYesExcomm, choicesController);
        Debug.printVerbose("afterd calling player.build");

    }

    /**
     * This method is called when the player harvests, it is the entry point to the model
     * @param familyMember the family member the player performed the action with
     * @param servants the number of servants the player performed the action with
     *                 malus coming from excommunication tiles is already included
     */
    public void harvest(FamilyMember familyMember, int servants) {

        Player player = familyMember.getPlayer();

        //set the family member as used in the player
        player.playFamilyMember(familyMember);
        player.subResource(new Resource(ResourceTypeEnum.SERVANT, servants));
        //just adds the family member to the harvestAS
        gameBoard.harvest(familyMember);

        int realDiceValueNoBlueBonusYesLeadersYesExcomm = familyMember.getValue() + servants;
        //we check if he's not the first inside the action space
        if(!gameBoard.getHarvest().checkIfFirst() && !player.getPermanentLeaderCardCollector().canPlaceFamilyMemberInOccupiedActionSpace())
            realDiceValueNoBlueBonusYesLeadersYesExcomm -=  gameBoard.getHarvest().getValueMalus();
        //excommunication tiles malus
        realDiceValueNoBlueBonusYesLeadersYesExcomm -= player.getExcommunicationTilesCollector().harvestDiceMalusEffect();

        player.harvest(realDiceValueNoBlueBonusYesLeadersYesExcomm, choicesController);
    }

    /**
     * this method is used to control if the family member can be placed on the space merket
     * @param servant number of servants the player owns
     * @return the index of the space, and the servant needed
     */
    private boolean isMarketActionLegal(FamilyMember familyMember, int servant, MarketAS spaceMarket){

        //we check the excommunications tiles first
        if(familyMember.getPlayer().getExcommunicationTilesCollector().marketNotAvailable())
            return false;

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
        if((servant/familyMember.getPlayer().getExcommunicationTilesCollector().payMoreServant())
                //we divide by the exchange rate given by the malus of excommunication tiles
                +familyMember.getValue() +
                familyMember.getPlayer().getPersonalBoard().getCharacterCardsCollector().getBonusOnBuild() < spaceMarket.getDiceRequirement())
            //cannot place this familymembers because the value is too low
            return false;

        //we check if the action space is not occupied and if the user has a leader that lets him place the fm there anyway
        if(spaceMarket.getOccupyingFamilyMemberNumber() >= 1 && !familyMember.getPlayer().getPermanentLeaderCardCollector().canPlaceFamilyMemberInOccupiedActionSpace())
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

            //malus coming from excommunication tile
            servantNeeded *= familyMember.getPlayer().getExcommunicationTilesCollector().payMoreServant();

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
        if((servant/familyMember.getPlayer().getExcommunicationTilesCollector().payMoreServant())
                //we divide by the exchange rate given by the malus of excommunication tiles
                +familyMember.getValue()
                < councilPlace.getDiceRequirement())
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

    public void discardLeaderCard(String playerNickname, String nameLeader){

        Player playerMove = null;
        for(Player player : players){
            if(player.getNickname().equals(playerNickname)){
                player.discardLeaderCard(nameLeader);
                playerMove = player;
                Debug.printVerbose("Sono nella discardLeader Model Controller nel for");
            }
        }
        Debug.printVerbose("Sono nella discardLeader Model Controller");
        List<GainResourceEffect> choices = choicesController.callbackOnCouncilGift("discard leader", 1);
        for(GainResourceEffect effectIterator : choices)
            effectIterator.applyToPlayer(playerMove, choicesController, nameLeader);
    }

    public void activateLeaderCard(String nickname, String nameLeader){

        Player playerMove = null;
        for(Player player : players){
            if(player.getNickname().equals(nickname)){
                player.activateLeaderCardAbility(player.getLeaderCardsNotUsed(nameLeader), choicesController);
                playerMove = player;
            }
        }

        List<GainResourceEffect> choices = choicesController.callbackOnCouncilGift("discard leader", 1);
        for(GainResourceEffect effectIterator : choices)
            effectIterator.applyToPlayer(playerMove, choicesController, nameLeader);

    }

    /**
     * this method is called to add all the victory points to the players at the end of the game
     */
    public ArrayList<PlayerPositionEndGamePacket> endGame(){

        //add the victory points to all the players based on the number of green cards
        players.forEach(Player::greenPoints);
        //add the victory points to all the players based on the number of blue cards
        players.forEach(Player::bluePoints);
        //add the victory points to all the players based on the effects of the venture cards
        players.forEach(Player::purplePoints);
        addVictoryPointsOnMilitary();
        addVictoryPointsOnFaith();
        addVictoryPointsOnResources();
        ArrayList<PlayerPositionEndGamePacket> playerPositionEndGames = new ArrayList<>(players.size());
        playerPositionEndGames = endGameOrderPlayer();
        return playerPositionEndGames;

    }

    /**
     * this method is used to add the victory points to the players based on the resources of the player
     */
    private void addVictoryPointsOnResources() {

        for(Player playerIter : players){
            int numOfResources = playerIter.getNumResources();
            playerIter.addResource(new Resource(ResourceTypeEnum.VICTORY_POINT, numOfResources/4));
        }
    }

    /**
     * this method is used to calculate and deliver the result of the game
     */
    private ArrayList<PlayerPositionEndGamePacket> endGameOrderPlayer() {
        //todo check this block
        //this iteration is used to find the order of victory of the players
        for(int i = 0 ; i < players.size() ; i++){
            for(int e = 0 ; e < players.size()-i-1 ; e++){
                if(players.get(e).getResource(ResourceTypeEnum.VICTORY_POINT) < players.get(e+1).getResource(ResourceTypeEnum.VICTORY_POINT))
                    players.add(e+2,players.get(e));
                    players.remove(e);
            }
        }
        ArrayList<PlayerPositionEndGamePacket> playerPositionEndGames = new ArrayList<>(players.size());
        playerPositionEndGames.add(new PlayerPositionEndGamePacket(players.get(0).getNickname(), 1,
                players.get(0).getResource(ResourceTypeEnum.VICTORY_POINT)));
        for(int i = 2 ; i < players.size() ; i++){
            if(playerPositionEndGames.get(i-2).getVictoryPoints() == players.get(i-1).getResource(ResourceTypeEnum.VICTORY_POINT)){
                playerPositionEndGames.add(new PlayerPositionEndGamePacket(players.get(i-1).getNickname(), playerPositionEndGames.get(i-2).getPosition(),
                        players.get(i-1).getResource(ResourceTypeEnum.VICTORY_POINT)));
            }
            else
                playerPositionEndGames.add(new PlayerPositionEndGamePacket(players.get(i-1).getNickname(), i,
                        players.get(i-1).getResource(ResourceTypeEnum.VICTORY_POINT)));
        }

        return playerPositionEndGames;

    }

    /**
     * this method is used to add the victory points based on the military points at the end of the turn
     */
    private void addVictoryPointsOnMilitary(){

        int firstNumMilPoints = 0;
        int secondNumMilPoints = 0;

        //this iteration is used to find the number of military points int the first place
        for(Player playerIter : players){
            if(playerIter.getResource(ResourceTypeEnum.MILITARY_POINT) > firstNumMilPoints)
                firstNumMilPoints = playerIter.getResource(ResourceTypeEnum.MILITARY_POINT);
        }

        //this iteration is used to find the number of military points int the second place
        for(Player playerIter : players){
            if(playerIter.getResource(ResourceTypeEnum.MILITARY_POINT) < firstNumMilPoints &&
                    playerIter.getResource(ResourceTypeEnum.MILITARY_POINT) > secondNumMilPoints)
                secondNumMilPoints = playerIter.getResource(ResourceTypeEnum.MILITARY_POINT);
        }

        //this iteration is used to add the resources based on the number of military point
        for(Player playerIter : players){
            if(playerIter.getResource(ResourceTypeEnum.MILITARY_POINT) == firstNumMilPoints)
                playerIter.addResource(new Resource(ResourceTypeEnum.MILITARY_POINT, 5));

            else if(playerIter.getResource(ResourceTypeEnum.MILITARY_POINT) == secondNumMilPoints)
                playerIter.addResource(new Resource(ResourceTypeEnum.MILITARY_POINT, 2));
        }
    }

    /**
     * this method is used to add the victory point based on the faith point at the end of the turn
     */
    private void addVictoryPointsOnFaith(){

        for(Player playerIter : players){

            int faithPointPlayer = playerIter.getResource(ResourceTypeEnum.FAITH_POINT);
            playerIter.addResource(new Resource(ResourceTypeEnum.VICTORY_POINT, gameBoard.getVaticanFaithAgeIndex(faithPointPlayer)));

        }

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
     * @param nameLeader the card that should be set as played inside the player
     * @param player the player that had chosen to play the card
     */
    public void playLeaderCard(String nameLeader, Player player){

        LeaderCard leaderCard = player.getLeaderCardsNotUsed(nameLeader);
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

    /**
     * this method is called to place all the cards on the board
     * @param cardsToPlace the cards to place on the board this turn
     */
    public void placeCardOnBoard(ArrayList<AbstractCard> cardsToPlace) {

        int cardPlaced = 0;

        for(CardColorEnum colorEnum : CardColorEnum.values()) {
            int floor = 0;

            for (AbstractCard card : cardsToPlace) {
                if (card.getColor() == colorEnum) {
                    gameBoard.setCardsOnTower(card, colorEnum, floor++);
                    cardPlaced++;
                }
            }
        }
        Debug.printVerbose("Carte Posizionate : " +cardPlaced);
    }

    public void setPersonalTile(PersonalTile personalTile, String nicknamePlayer) {

        for(Player player : players){
            if(player.getNickname().equals(nicknamePlayer))
                player.setPersonalTile(personalTile);
        }

    }

    @Deprecated
    public ArrayList<LeaderCard> getLeaderCardsNotPlayed(String nickname){
        ArrayList<LeaderCard> leaderCards = new ArrayList<>(4);
        for(Player player : players){
            if(player.getNickname().equals(nickname))
                leaderCards = player.getLeaderCardsNotUsed();
        }
        return leaderCards;
    }

    @Deprecated
    public List<LeaderCard> getLeaderCardsPlayed(String nickname){
        List<LeaderCard> leaderCards = new ArrayList<>(4);
        for(Player player : players){
            if(player.getNickname().equals(nickname))
                leaderCards = player.getPlayedLeaders();
        }
        return leaderCards;
    }

    /**
     * this method is used to remove a player from the board , this happens when a player had left the game
     * @param player the player to remove
     */
    public void removePlayer(Player player) {
        players.remove(player);
    }

    /**
     * this method is called to find the player that doesn't have the needed resources to avoid the excommunication
     * @param faithNeeded the needed faith point to avoid the excommunication
     * @return the nickname f the player excommunicated
     */
    public ArrayList<String> controlExcommunication(int faithNeeded) {

        ArrayList<String> playersExcommunicated = new ArrayList<>(players.size());
        for(Player playerIter : players){
            if(playerIter.getResource(ResourceTypeEnum.FAITH_POINT) < faithNeeded)
                playersExcommunicated.add(playerIter.getNickname());
        }
        excommunicatePlayer(playersExcommunicated, faithNeeded-3);
        return playersExcommunicated;
    }

    /**
     * this method is used to excommunicate the players
     * @param playersExcommunicated the nickname of the players excommunicated
     */
    public void excommunicatePlayer(ArrayList<String> playersExcommunicated, int numTile) {

        ExcommunicationTile tile = getBoard().getExcommunicationTiles().get(numTile);
        for(Player player : players){
            if(playersExcommunicated.contains(player.getNickname())){
                player.addExcommunicationTile(tile);
            }
        }
    }

    /**
     * this method is used to excommunicate the player
     * @param playerExcommunicated the nickname of the player excommunicated
     */
    public void excommunicatePlayer(String playerExcommunicated, int numTile) {

        ExcommunicationTile tile = getBoard().getExcommunicationTiles().get(numTile);
        for(Player player : players){
            if(playerExcommunicated.equals(player.getNickname())){
                player.addExcommunicationTile(tile);
            }
        }
    }
}


