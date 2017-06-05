package it.polimi.ingsw.model.player;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The main player class, no network
 */
public abstract class Player {

    private String nickname;

    private PersonalBoard personalBoard;

    private HashMap<ResourceTypeEnum, Integer> resourcesMap;

    private ArrayList<FamilyMember> notUsedFamilyMembers;

    private ArrayList<FamilyMember> usedFamilyMembers;

    //private PersonalTile personalTile = null; MOVED TO PERSONALBOARD

    //private ArrayList<LeaderCard> leaderCard;

    //private ArrayList<LeaderCard> playedLeaderCard;

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

        personalBoard = new PersonalBoard();
        //TODO CHOOSE TILES OF PERSONAL BOARD
        resourcesMap = new HashMap<>();
        loadResource();
        notUsedFamilyMembers = new ArrayList<>(4);
        usedFamilyMembers = new ArrayList<>(4);
        //excommunicanionCard = new ArrayList<>(3);
        //leaderCard = new ArrayList<>(3);

    }

    /**
     * you load all the resources needed by the player
     */
    private void loadResource(){
        resourcesMap.put(ResourceTypeEnum.COIN, 0);
        resourcesMap.put(ResourceTypeEnum.WOOD, 2);
        resourcesMap.put(ResourceTypeEnum.STONE, 2);
        resourcesMap.put(ResourceTypeEnum.SERVANT, 3);
        resourcesMap.put(ResourceTypeEnum.FAITH_POINT, 0);
        resourcesMap.put(ResourceTypeEnum.MILITARY_POINT, 0);
        resourcesMap.put(ResourceTypeEnum.VICTORY_POINT, 0);
    }


    /**
     * this method is used to add a resource on the player
     * @param resource the object of the resource, it contains the value and the type
     */
    public void addResource(Resource resource){

        this.resourcesMap.put(resource.getType(),this.resourcesMap.get(resource.getType())+resource.getValue());

    }

    /**
     * this method is used to add an array of resources on the player
     * @param resources the object of the resource, it contains the value and the type
     */
    public void addResource(ArrayList<Resource> resources){

        for(Resource resource : resources){
            this.resourcesMap.put(resource.getType(),this.resourcesMap.get(resource.getType())+resource.getValue());
        }

    }

    //TODO: we need to put Cards Containers in Player and then implement this method.
    public int getNumberOfColoredCard(CardColorEnum color)
    {
        return personalBoard.getNumberOfColoredCard(color);
    }

    /*public void excommunication(ExcommunicationCard card){

        excommunicationCard.add(card);
    }*/

    public int getResource(ResourceTypeEnum type){

        return resourcesMap.get(type);

    }

    public String getNickname()
    {
        return nickname;
    }

    protected void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setFamilyMembers(ArrayList<Dice> dices){

        for(Dice i : dices)
            this.notUsedFamilyMembers.add(new FamilyMember(i,this));

    }

    public void playFamilyMember(FamilyMember familyMember){

        this.notUsedFamilyMembers.remove(familyMember);
        this.usedFamilyMembers.add(familyMember);

    }

    public void reloadFamilyMember(){

        notUsedFamilyMembers.addAll(usedFamilyMembers);
        usedFamilyMembers.clear();

        //align th family member with the value of the linked dice, to delete any changes of the family members' values
        notUsedFamilyMembers.forEach(FamilyMember::alignValue);

    }

    public void addCard(AbstractCard card, CardColorEnum color){

        //personalBoard.addCard(card, color);

    }

    /*public void addLeaderCard(LeaderCard leaderCard){

        this.leaderCard.add(leaderCard);
    }

    public ArrayList<LeaderCard> viewLeaderCard(){

        return leaderCard;
    }

    public void activateLeaderCard(LeaderCard leaderCard){

        this.leaderCard.remove(leaderCard);
        playedLeaderCard.add(leaderCard);

    }

    public void discardLeaderCard(LeaderCard leaderCard){

        this.leaderCard.remove(leaderCard);
        //TODO get bonus
    }*/

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
        personalBoard.build(realDiceValueNoBlueBonus, this, choicesController);
    }

    public void purplePoints(){

        personalBoard.purplePoints(this);
    }

    public ArrayList<FamilyMember> getNotUsedFamilyMembers(){
        return notUsedFamilyMembers;
    }


    /**
     * this method returns all the resources the user has.
     * @return the hashmap of the resources the user has
     */
    public HashMap<ResourceTypeEnum, Integer> getResourcesMap() {
        return resourcesMap;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }
}
