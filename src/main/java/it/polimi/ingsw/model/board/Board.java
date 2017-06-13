package it.polimi.ingsw.model.board;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.excommunicationTiles.ExcommunicationTile;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.server.JSONLoader;
import it.polimi.ingsw.utils.Debug;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class is the board where the game develops
 * todo: when we create the board, should we pass all the deck to it?
 * Isn't it wrong? Players could already know cards before those are fitted in the right spaces.
 * Of course deck is standard.. Potrebbe non essere un problema a dire il vero.
 * Di fatti, piu ci giochi piu impari quali carte ci sono, quindi basi anche un po la tua strategia su quello....
 */
public class Board implements Serializable {
    //the following constants are loaded from file and establish the number of action spaces / towers of the board
    private int NUMBER_OF_TOWERS;
    private int NUMBER_OF_MARKETS;
    private int NUMBER_OF_FLOORS;
    private Deck deck;
    private Tower[] towers = new Tower[NUMBER_OF_TOWERS];
    private ArrayList<MarketAS> market = new ArrayList<MarketAS>();
    private BuildAS build = new BuildAS();
    private HarvestAS harvest = new HarvestAS();
    private CouncilAS council;
    private VaticanReport vaticanReport = new VaticanReport();
    private ArrayList<ExcommunicationTile> excommunicationDeck;
    private ArrayList<ExcommunicationTile> excommunicationTiles = new ArrayList<>(3);
    // this is the constructor
    public void createNewBoard(Tower[] towers, ArrayList<MarketAS> market, BuildAS build, HarvestAS harvest, CouncilAS councilAS, VaticanReport vaticanReport) {
        this.towers = towers;
        this.market = market;
        this.build = build;
        this.harvest = harvest;
        this.vaticanReport = vaticanReport;
        this.council = councilAS;
        /*
        JSONLoader.instance();
        try {
            excommunicationDeck = JSONLoader.loadExcommunicationTiles();
            this.getExcommunicationTilesForGame(excommunicationDeck);
        }catch(IOException e)
        {
            Debug.instance(2);
            Debug.printError("Json couldn't load excommunication cards");
        }
        */
    }
    public void loadExcommunicationCards()
    {
        JSONLoader.instance();
        try {
            excommunicationDeck = JSONLoader.loadExcommunicationTiles();
            this.getExcommunicationTilesForGame(excommunicationDeck);
        }catch(IOException e)
        {
            Debug.instance(2);
            Debug.printError("Json couldn't load excommunication cards");
        }
    }
    /**
     * this method loads 3 excommunication cards on board
     * @param excommunicationDeck is a list of excommunication cards loaded from JSON
     */
    private void getExcommunicationTilesForGame(ArrayList<ExcommunicationTile> excommunicationDeck)
    {
        Random random = new Random();
        int valueIndex;
        final int numberOfPeriods = 3;
        //this cicle looks for 3 excommunication tiles
        for(int i=numberOfPeriods; i>0; i--){
            //i look for a random number between 0 and 8
            valueIndex = random.nextInt(excommunicationDeck.size()/i);
            //then i pick the corrispective card and i check if it is the same period.
            if(excommunicationDeck.get(valueIndex).getPeriod() == (numberOfPeriods-i+1)) {
                //if it is i add that to ecommunicationTiles
                excommunicationTiles.add(excommunicationDeck.get(valueIndex));
                //and i remove all the others
                for(int k= 0; k < excommunicationDeck.size()/i; k++)
                    if(excommunicationDeck.get(k).getPeriod() == (numberOfPeriods-i+1))
                        excommunicationDeck.remove(k);
                //then i look for another card
            }
            //If i don't find it, i make another cicle
            else
            {
                i++;
            }
        }

    }

    public int getNUMBER_OF_TOWERS() {
        return NUMBER_OF_TOWERS;
    }

    public int getNUMBER_OF_MARKETS() {
        return NUMBER_OF_MARKETS;
    }

    public Tower getTower(int i) {
        return this.towers[i];
    }

    public Tower[] getTowers() {
        return towers;
    }
    //todo: we have to cancel this method
    public void setTowers(Tower[] towers) {
        this.towers = towers;
    }

    public TowerFloorAS[] getTowerFloor(Tower tower) {
        return tower.getFloors();
    }

    public TowerFloorAS[] getFloorLevel(int i) {
        int k;
        //final int NUMBER_OF_FLOORS = 4;
        TowerFloorAS[] iFloor = new TowerFloorAS[NUMBER_OF_FLOORS];
        for (k = 0; k < NUMBER_OF_TOWERS; k++)
            iFloor[k] = towers[k].getFloorByIndex(i);
        return iFloor;
    }

    public CardColorEnum getTowerColor(Tower tower) {
        return tower.getTowerColor();
    }

    public ArrayList<MarketAS> getMarket() {
        return market;
    }
    //todo: we should cancel this one too
/*    public void setMarket(MarketAS[] market) {
        this.market = market;
    }*/

    public MarketAS getMarketSpaceByIndex(int index) {
        return market.get(index);
    }

    public BuildAS getBuild() {
        return build;
    }

    public void setBuild(BuildAS build) {
        this.build = build;
    }

    public HarvestAS getHarvest() {
        return harvest;
    }

    public void setHarvest(HarvestAS harvest) {
        this.harvest = harvest;
    }

    public VaticanReport getVaticanReport() {
        return vaticanReport;
    }

    public int[] getVaticanFaithAge() {
        return this.vaticanReport.getRequiredFaithPoints();
    }

    public int getVaticanFaithAgeIndex(int index) {
        return vaticanReport.getRequiredFaithPointsByIndex(index);
    }

    public int getVictoryPointsByIndex(int index) {
        return vaticanReport.getRequiredVictoryPointByIndex(index);
    }

    public int[] getVaticanVictoryPoints() {
        return this.vaticanReport.getCorrespondingVictoryPoints();
    }
    //todo we should cancel this one also
    public void setVaticanReport(VaticanReport vaticanReport) {
        this.vaticanReport = vaticanReport;
    }

    public CouncilAS getCouncil() {
        return council;
    }

    /*public String getCouncilShortEffect()
    {
        return this.getCouncil().getEffect().descriptionShortOfEffect();
    }*/

    public int getNUMBER_OF_FLOORS() {
        return NUMBER_OF_FLOORS;
    }

    /**
     * just adds the family member to the {@link HarvestAS}
     * @param familyMember the family member to perform the action with
     */
    public void harvest(FamilyMember familyMember) {
        harvest.addFamilyMember(familyMember);
    }


    /**
     * just adds the family member to the {@link BuildAS}
     * @param familyMember the family member to perform the action with
     */
    public void build(FamilyMember familyMember) {
        build.addFamilyMember(familyMember);
    }

    public Tower getTowerByColor(CardColorEnum color)
    {
        for(int i= 0; i< getNUMBER_OF_TOWERS(); i++)
            if(color == this.getTower(i).getTowerColor())
                return this.getTower(i);
        return null;
    }

    public void setCardsOnTower(AbstractCard card, CardColorEnum color, int level)
    {
        this.getTowerByColor(color).setCardOnFloor(level, card);
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    /**
     * clear all the spaces on the board removing all the family members placed
     */
    public void clearBoard(){

        for(int i = 0 ; i < NUMBER_OF_TOWERS ; i++ )
            towers[i].clearTower();
        market.forEach(MarketAS::clearAS);
        build.clearAS();
        harvest.clearAS();
        council.clearAS();

    }

    public CouncilAS getCouncilAS() {
        return council;
    }

    /**
     * This method performs the real action on the model when the player places a FM on a tower
     * This method goes down on the model to perform the action calling {@link it.polimi.ingsw.model.board.MarketAS}
     * @param familyMember the family member to perform the action with
     * @param towerIndex the tower to place the family member to
     * @param floorIndex the floor to place the family member to
     * @param choicesController needed because there can be some decisions tied to the action
     */
    public void placeOnTower(FamilyMember familyMember, int towerIndex, int floorIndex, ChoicesHandlerInterface choicesController) {
        towers[towerIndex].placeFamilyMember(familyMember, floorIndex, choicesController);
        return;
    }

    /**
     * This method performs the real action on the model when the player places a FM on a tower
     * This method goes down on the model to perform the action calling {@link it.polimi.ingsw.model.board.Tower}, {@link it.polimi.ingsw.model.board.TowerFloorAS}
     * @param familyMember the family member to perform the action with
     * @param marketASIndex the marketAS to place the family member to
     * @param choicesController needed because there can be some decisions tied to the action
     */
    public void placeOnMarket(FamilyMember familyMember, int marketASIndex, ChoicesHandlerInterface choicesController) {
        market.get(marketASIndex).performAction(familyMember, choicesController);
        return;
    }

    /**
     * This method performs the real action on the model when the player places a FM int he council
     * This method goes down on the model to perform the action calling {@link it.polimi.ingsw.model.board.CouncilAS}
     * @param familyMember the family member to perform the action with
     * @param choicesController needed because there can be some decisions tied to the action
     */
    public void placeOnCouncil(FamilyMember familyMember, ChoicesHandlerInterface choicesController) {
        council.performAction(familyMember, choicesController);
    }

    public ArrayList<ExcommunicationTile> getExcommunicationTiles() {
        return excommunicationTiles;
    }
}



//