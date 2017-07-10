package it.polimi.ingsw.model.player;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.cards.BuildingCard;
import it.polimi.ingsw.model.excommunicationTiles.ExcommunicationTile;
import it.polimi.ingsw.model.excommunicationTiles.ExcommunicationTilesCollector;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.leaders.PermanentLeaderCardCollector;
import it.polimi.ingsw.model.leaders.leadersabilities.AbstractLeaderAbility;
import it.polimi.ingsw.model.leaders.leadersabilities.ImmediateLeaderAbility.AbstractImmediateLeaderAbility;
import it.polimi.ingsw.model.leaders.leadersabilities.LeaderAbilityTypeEnum;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceCollector;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.utils.Debug;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The main player class, no network
 */
public class Player implements Serializable{

    private String nickname;

    private PersonalBoard personalBoard;

    private ResourceCollector resourcesMap;

    private ArrayList<FamilyMember> notUsedFamilyMembers;

    private ArrayList<FamilyMember> usedFamilyMembers;

    private transient ExcommunicationTilesCollector excommunicationTilesCollector;

    /**
     * The color this player will be displayed with
     */
    private PlayerColorEnum playerColor;

    //private PersonalTile personalTile = null; MOVED TO PERSONALBOARD

    /**
     * These are the leader cards the user chose at the beginning of the game and hasn't played yet
     * Players should be moved out of this list once played
     */
    private ArrayList<LeaderCard> leaderCardsNotUsed;

    /**
     * These are the leader cards the user decided to play,
     * but of which he still hasn't activated the once per round ability
     * only once per round leaders should fit in this list
     */
    private LinkedList<LeaderCard> playedOncePerRoundLeaderCards;

    /**
     * There are the leader cards the user has played and activated this round,
     * only once per round leaders should fit in this list
     */
    private LinkedList<LeaderCard> playedAndActivatedOncePerRoundLeaderCards;

    /**
     * This is the collector of leader cards who have a permanent ability and are played by the user
     * This collector is used during checks inside the model for bonuses and discounts
     */
    private transient PermanentLeaderCardCollector permanentLeaderCardCollector;

    //private ArrayList<ExcommuncationCard> excommuncationCard;

    public Player()
    {
        super();
        loadPlayer();
    }

    public Player(String nickname)
    {
        loadPlayer();
        setNickname(nickname);
    }

    private void loadPlayer(){

        playedOncePerRoundLeaderCards = new LinkedList<LeaderCard>();
        playedAndActivatedOncePerRoundLeaderCards = new LinkedList<LeaderCard>();
        leaderCardsNotUsed = new ArrayList<LeaderCard>(4);
        permanentLeaderCardCollector = new PermanentLeaderCardCollector();
        excommunicationTilesCollector = new ExcommunicationTilesCollector();

        personalBoard = new PersonalBoard();
        resourcesMap = new ResourceCollector();
        loadInitialResources();
        notUsedFamilyMembers = new ArrayList<>(4);
        usedFamilyMembers = new ArrayList<>(4);
        //excommunicanionCard = new ArrayList<>(3);
        //leaderCardsNotUsed = new ArrayList<>(3);
    }

    /**
     * you load all the resources needed by the player
     */
    private void loadInitialResources(){
        resourcesMap.addResource(new Resource(ResourceTypeEnum.WOOD, 2));
        resourcesMap.addResource(new Resource(ResourceTypeEnum.STONE, 2));
        resourcesMap.addResource(new Resource(ResourceTypeEnum.SERVANT, 3));

    }


    /**
     * this method is used to add a resource on the player
     * if {@link Resource#getValue()} < 0 subtracts the resource instead
     * It takes into account the malus of the excommunication tiles
     * @param resource the object of the resource, it contains the value and the type
     */
    public void addResource(Resource resource){

        int value = resource.getValue();
        if(value > 0) {
            //we have to check malus from excommunication tiles
            value -= excommunicationTilesCollector.gainFewResource(resource.getType());
            if (value <= 0)
                return; // the malus made the addition useless
        }
        resourcesMap.addResource(new Resource(resource.getType(), value));
    }

    /**
     * this method is used to add an array of resources on the player
     * It takes into account the malus of the excommunication tiles
     * @param resources the object of the resource, it contains the value and the type
     */
    public void addResources(List<Resource> resources) {
        resources.forEach(this::addResource);
    }

    /**
     * this method is used to add an array of resources on the player
     * It <b>doesn't</b> take into account the malus of the excommunication tiles
     * @param resources the object of the resource, it contains the value and the type
     */
    public void addResourcesNoMalus(List<Resource> resources) {
        resourcesMap.addResource(resources);
    }

    /**
     * this method is used to add a resource on the player
     * if {@link Resource#getValue()} < 0 subtracts the resource instead
     * It <b>doesn't</b> take into account the malus of the excommunication tiles
     * @param resource the object of the resource, it contains the value and the type
     */
    public void addResourceNoMalus(Resource resource){
        resourcesMap.addResource(resource);
    }


    /**
     * this method is used to subtract a single resource,
     * {@link Resource#getValue()} should be positive to work as a subtractor
     * @param resource the object of the resource, it contains the value and the type
     */
    public void subResource(Resource resource) {
        resourcesMap.subResource(resource);
    }

    /**
     * this method is used to subtract resources,
     * {@link Resource#getValue()} should be positive to work as a subtractor
     * @param resources the List of the resource, it contains the value and the type
     */
    public void subResources(ResourceCollector resources) {
        resourcesMap.subResource(resources);
    }

    /**
     * this method is used to subtract resources
     * @param resources the resources the player has to pay
     */
    public void subResources(List<Resource> resources) {
        resourcesMap.subResource(resources);
    }


    public int getResource(ResourceTypeEnum type){

        return resourcesMap.getResource(type);

    }

    /**
     * Used to perfroms checks on excommunications mali
     * @return the collector to call methods on
     */
    public ExcommunicationTilesCollector getExcommunicationTilesCollector() {
        return excommunicationTilesCollector;
    }

    /**
     * Adds one excommunication tile to the player, should be called at the end of a period
     * if the player is excommunicated
     * @param tile
     */
    public void addExcommunicationTile(ExcommunicationTile tile) {
        excommunicationTilesCollector.addExcommunicationTile(tile);
    }

    public int getNumberOfColoredCard(CardColorEnum color)
    {
        return personalBoard.getNumberOfColoredCard(color);
    }


    public String getNickname()
    {
        return nickname;
    }

    protected void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setFamilyMembers(ArrayList<Dice> dices){

        for(Dice i : dices) {
            Debug.printVerbose("Adding fm with value" + i.getValue());
            this.notUsedFamilyMembers.add(new FamilyMember(i, this));
        }

    }

    public void playFamilyMember(FamilyMember familyMember){

        notUsedFamilyMembers.remove(familyMember);
        usedFamilyMembers.add(familyMember);

    }

    /**
     * this method is used to return available the family member used on the previous turn
     */
    public void reloadFamilyMember(){

        notUsedFamilyMembers.addAll(usedFamilyMembers);
        usedFamilyMembers.clear();

        //align th family member with the value of the linked dice, to delete any changes of the family members' values
        notUsedFamilyMembers.forEach(FamilyMember::alignValue);

    }

    public void addCard(AbstractCard card){
        personalBoard.addCard(card);
    }
/*

    /**
     * this method is called when a player harvests. It increments player's resources
     * @param realDiceValueNoBlueBonus the real value (not considered the bonus from blue cards) to perform the action with
     * @param choicesController the controller to make callback on choices
     */
    public void harvest(int realDiceValueNoBlueBonus, ChoicesHandlerInterface choicesController){
        personalBoard.harvest(realDiceValueNoBlueBonus, this, choicesController);

    }

    /**
     * this method is called when a player builds. It increments player's resources
     * @param realDiceValueNoBlueBonus the real value (not considered the bonus from blue cards) to perform the action with
     * @param choicesController the controller to make callback on choices
     */
    public void build(int realDiceValueNoBlueBonus, ChoicesHandlerInterface choicesController){
        Debug.printVerbose("player.Build start");
        personalBoard.build(realDiceValueNoBlueBonus, this, choicesController);
    }

    /**
     * this method is called by the model controller to add all the victory points an the venture cards
     */
    public void purplePoints(){

        personalBoard.purplePoints(this);
    }

    public ArrayList<FamilyMember> getNotUsedFamilyMembers(){
        return notUsedFamilyMembers;
    }


    /**
     * this method returns all the resources the user has.
     * @return the {@link ResourceCollector} of the resources the user has
     */
    public ResourceCollector getResourcesCollector() {
        return resourcesMap;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    public FamilyMember getFamilyMemberByColor(DiceAndFamilyMemberColorEnum familyMemberColor){

        for(FamilyMember familyMember : notUsedFamilyMembers){

            if(familyMember.getColor() == familyMemberColor)
                return familyMember;

        }
        return null;
    }

    /**
     * This method performs all the actions needed to prepare the player class for a new round
     */
    public void prepareForNewRound() {
        reloadFamilyMember();

        //make once per round leaders able to be activated again
        playedOncePerRoundLeaderCards.addAll(playedAndActivatedOncePerRoundLeaderCards);
        playedOncePerRoundLeaderCards.clear();
    }


    /**
     * This method adds a leader card to the ones the user has
     * it should be used only during the draft initial phase
     * @param leaderCard the leader to add
     */
    public synchronized void addLeaderCard(LeaderCard leaderCard){

        this.leaderCardsNotUsed.add(leaderCard);
    }

    /**
     * Returns the List of leader cards the user hasn't played yet (hence neither activated)
     * @return he List of leader cards the user hasn't played yet
     */
    public ArrayList<LeaderCard> getLeaderCardsNotUsed(){

        return leaderCardsNotUsed;
    }

    /**
     * Returns the leader card with that name
     */
    public LeaderCard getLeaderCardsNotUsed(String nameLeader){

        for(LeaderCard leaderCard : leaderCardsNotUsed){
            if(leaderCard.getName().equals(nameLeader))
                return leaderCard;
        }

        return null;
    }

    /**
     * this method is used to take the leader card player but not activated by a player knowing the name
     * @param nameLeader the name of the leader card
     * @return return the leader card with the same name
     */
    public LeaderCard getLeaderCardsPlayedButNotActivated(String nameLeader){

        for(LeaderCard leaderCard : playedOncePerRoundLeaderCards){
            if(leaderCard.getName().equals(nameLeader))
                return leaderCard;
        }

        return null;
    }

    /**
     * This method returns a list of LeaderCards not yet played, but playable,
     * i.e. they player meets their the requirements to be played
     * @return a list of playable LeaderCards, epty if none is playable (yet)
     */
    public List<LeaderCard> getPlayableLeaders() {
        ArrayList<LeaderCard> playableLeaders = new ArrayList<LeaderCard>(1);

        for(LeaderCard leaderIter : leaderCardsNotUsed) {
            if(leaderIter.isPlayable(this))
                playableLeaders.add(leaderIter);
        }

        return playableLeaders;
    }

    /**
     * getter for a list of all played leader, regardless of their ability type
     * @return a list of all played leader, regardless of their ability type
     */
    public List<LeaderCard> getPlayedLeaders(){
        ArrayList<LeaderCard> immediateAndPermanentPlayedLeaders = new ArrayList<>(
                playedAndActivatedOncePerRoundLeaderCards.size() +
                            playedOncePerRoundLeaderCards.size() +
                            permanentLeaderCardCollector.getPermanentLeaders().size());

        immediateAndPermanentPlayedLeaders.addAll(playedAndActivatedOncePerRoundLeaderCards);
        immediateAndPermanentPlayedLeaders.addAll(playedOncePerRoundLeaderCards);
        immediateAndPermanentPlayedLeaders.addAll(permanentLeaderCardCollector.getPermanentLeaders());

        return immediateAndPermanentPlayedLeaders;
    }

    /**
     * getter for a list of all played leader, regardless of their ability type
     * @return a list of all played leader, regardless of their ability type
     */
    public List<LeaderCard> getPlayedNotActivatedOncePerRoundLeaderCards(){
        return playedOncePerRoundLeaderCards;
    }

    /**
     * This method is called when the user wants to play a leader card,
     * if the card has a permanent ability it is automatically activated
     * if the card has a once per round ability the user is asked if he wants to activate it now or leave it for later
     * @param leaderToBePlayed the leader card to be played
     * @param choicesHandler for callbacks on activation
     */
    public void playLeader(LeaderCard leaderToBePlayed, ChoicesHandlerInterface choicesHandler) {

        //We assume that as long as the player decided to play this leader
        // he also wants to activate his permanent ability
        leaderCardsNotUsed.remove(leaderToBePlayed);
        if(leaderToBePlayed.getAbility().getAbilityType() == LeaderAbilityTypeEnum.PERMANENT) {
            permanentLeaderCardCollector.addLeaderCard(leaderToBePlayed);
        }
        else { //we should ask the user if he also wants to activate the leader ability
            if(choicesHandler.callbackOnAlsoActivateLeaderCard()) { //we should activate the leader ability
                ((AbstractImmediateLeaderAbility) (leaderToBePlayed.getAbility())).applyToPlayer(this, choicesHandler, leaderToBePlayed.getName());
                playedAndActivatedOncePerRoundLeaderCards.add(leaderToBePlayed);
            } else {
                playedOncePerRoundLeaderCards.add(leaderToBePlayed);
            }
        }
    }

    /**
     * This method is called when the user wants to activate the ability of a leader card he's already played,
     * @param leaderCardToBeActivated the leader card to be activated
     * @param choicesHandler for callbacks on activation
     */
    public void activateLeaderCardAbility(LeaderCard leaderCardToBeActivated, ChoicesHandlerInterface choicesHandler) {
        playedOncePerRoundLeaderCards.remove(leaderCardToBeActivated);
        playedAndActivatedOncePerRoundLeaderCards.add(leaderCardToBeActivated);
        AbstractLeaderAbility leaderAbility = leaderCardToBeActivated.getAbility();
        if(leaderAbility.getAbilityType() == LeaderAbilityTypeEnum.ONCE_PER_ROUND) {
            ((AbstractImmediateLeaderAbility) (leaderAbility)).applyToPlayer(this, choicesHandler, leaderCardToBeActivated.getName());
        } else
            Debug.printError("activateLeaderCardAbility called with a PERMANENT ability (?)");
    }

    /*
     * This method allows to discard a leader card, the leader card passed as an argument should no be alredy played,
     * thet's against the rules
     * @param leaderCardToBeDiscarded the card to be discarded
     */
   /*public void discardLeader(LeaderCard leaderCardToBeDiscarded, ChoicesHandlerInterface choicesHandler) {
        leaderCardsNotUsed.remove(leaderCardToBeDiscarded);
        List<GainResourceEffect> resourceChoice = choicesHandler.callbackOnCouncilGift("discardLeader", 1);
        for(GainResourceEffect effectIter : resourceChoice)
            effectIter.applyToPlayer(this, choicesHandler, "discardLeaderInside");
    }*/

    public void setPersonalTile(PersonalTile personalTile){
        personalBoard.setPersonalTile(personalTile);
    }

    public PermanentLeaderCardCollector getPermanentLeaderCardCollector() {
        return permanentLeaderCardCollector;
    }

    /**
     * this method is used to discard a leader card of a player
     * @param nameLeader the name of the leader card
     */
    public void discardLeaderCard(String nameLeader) {

        for(LeaderCard leaderCard : leaderCardsNotUsed){
            if(leaderCard.getName().equals(nameLeader)){
                leaderCardsNotUsed.remove(leaderCard);
                break;
            }
        }
    }

    public ArrayList<FamilyMember> getUsedFamilyMembers() {
        return usedFamilyMembers;
    }

    public PlayerColorEnum getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(PlayerColorEnum playerColor) {
        this.playerColor = playerColor;
    }

    /**
     * this method is called by the model controller to add to the player the victory points based on the number of green cards
     */
    public void greenPoints() {

        personalBoard.greenPoints(this);
    }

    /**
     * this method is called by the model controller to add to the player the victory points based on the number of blue cards
     */
    public void bluePoints() {

        personalBoard.bluePoints(this);
    }

    /**
     * this method is used to calculate the number of the resources of the player(wood, coins, stone, servants)
     * this number is used to calculate the victory points at the end of the turn
     * @return the number of resources
     */
    public int getNumResources() {

        int numTotResources = 0;
        numTotResources += resourcesMap.getResource(ResourceTypeEnum.WOOD);
        numTotResources += resourcesMap.getResource(ResourceTypeEnum.STONE);
        numTotResources += resourcesMap.getResource(ResourceTypeEnum.SERVANT);
        numTotResources += resourcesMap.getResource(ResourceTypeEnum.COIN);
        return numTotResources;

    }

    /**
     * this method is called y the delto reset the faith points ofa player
     */
    public void resetFaithPoints() {
        resourcesMap.resetResource(ResourceTypeEnum.FAITH_POINT);
    }

    /**
     * this method is called by the model to get the victory points to take away by the client
     */
    public void MalusVictoryPoints() {

        MalusVictoryPointsOnResources();
        MalusVictoryPointsOnBuildingCard();

    }

    /**
     * this method is called to remove victory points based on the resources the player has
     */
    private void MalusVictoryPointsOnResources(){
        ArrayList<Resource> resources = new ArrayList<>(resourcesMap.getAllResources());
        Resource victoryPointsToRemove = new Resource(ResourceTypeEnum.VICTORY_POINT, excommunicationTilesCollector.noVPonResource(resources));
        resourcesMap.subResource(victoryPointsToRemove);
    }

    /**
     * this method is called to remove victory points based on the yellow cards the player has
     */
    public void MalusVictoryPointsOnBuildingCard() {
        ArrayList<BuildingCard> buildingCards = new ArrayList<>(personalBoard.getYellowBuildingCards());
        Resource victoryPointsToRemove = new Resource(ResourceTypeEnum.VICTORY_POINT, excommunicationTilesCollector.loseVPonCosts(buildingCards));
        resourcesMap.subResource(victoryPointsToRemove);
    }
}
