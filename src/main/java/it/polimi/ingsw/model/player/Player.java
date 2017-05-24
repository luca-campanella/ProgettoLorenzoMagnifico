package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The main player class, no network
 */
public abstract class Player {

    private String nickname;

    private PersonalBoard personalBoard;

    private HashMap<ResourceType, Integer> resource;

    private ArrayList<FamilyMember> familyMembers;

    private ArrayList<FamilyMember> usedFamilyMembers;

    //private ArrayList<AbstractLeaderCard> leaderCard;

    //private ArrayList<AbstractLeaderCard> playedLeaderCard;

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
        resource = new HashMap<>();
        loadResource();
        familyMembers = new ArrayList<>(4);
        //excommunicanionCard = new ArrayList<>(3);
        //leaderCard = new ArrayList<>(3);

    }

    /**
     * you load all the resources needed by the player
     */
    private void loadResource(){
        resource.put(ResourceType.COIN, 0);
        resource.put(ResourceType.WOOD, 0);
        resource.put(ResourceType.STONE, 0);
        resource.put(ResourceType.SERVANT, 0);
        resource.put(ResourceType.FAITH_POINT, 0);
        resource.put(ResourceType.MILITARY_POINT, 0);
        resource.put(ResourceType.VICTORY_POINT, 0);
    }

    public void addResource(ResourceType type, int value){

        Integer valueNow = resource.get(type);
        valueNow = valueNow + value;

    }
    //TODO: we need to put Cards Containers in Player and then implement this method.
    public int getNumberOfColoredCard(CardColorEnum color)
    {
        return personalBoard.getNumberOfColoredCard(color);
    }

    /*public void excommunication(ExcommunicationCard card){

        excommunicationCard.add(card);
    }*/

    public int getResource(ResourceType type){

        return resource.get(type);

    }

    public String getNickname()
    {
        return nickname;
    }

    protected void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setFamilyMembers(ArrayList<FamilyMember> familyMembers){

        for(FamilyMember i : familyMembers)
            this.familyMembers.add(i);

    }

    public void playFamilyMember(FamilyMember familyMember){

        this.familyMembers.remove(familyMember);
        this.usedFamilyMembers.add(familyMember);

    }

    public void resetFamilyMember(){

        familyMembers.addAll(usedFamilyMembers);
        usedFamilyMembers.clear();

    }

    public void addCard(AbstractCard card, CardColorEnum color){

        personalBoard.addCard(card, color);

    }

    /*public void addLeaderCard(AbstractLeaderCard leaderCard){

        this.leaderCard.add(leaderCard);
    }

    public ArrayList<AbstractLeaderCard> viewLeaderCard(){

        return leaderCard;
    }

    public void playLeaderCard(AbstractLeaderCard leaderCard){

        this.leaderCard.remove(leaderCard);
        playedLeaderCard.add(leaderCard);

    }*/

}
