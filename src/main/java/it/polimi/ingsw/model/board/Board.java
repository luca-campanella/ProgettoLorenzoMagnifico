package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.player.FamilyMember;

import java.util.ArrayList;

/**
 * Created by higla on 16/05/2017.
 */
public class Board {
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


    public void createNewBoard(Tower[] towers, ArrayList<MarketAS> market, BuildAS build, HarvestAS harvest, CouncilAS councilAS, VaticanReport vaticanReport) {
        this.towers = towers;
        this.market = market;
        this.build = build;
        this.harvest = harvest;
        this.vaticanReport = vaticanReport;
        this.council = councilAS;
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
    public void setCouncil(CouncilAS council) {
        this.council = council;
    }

    public int getNUMBER_OF_FLOORS() {
        return NUMBER_OF_FLOORS;
    }

    public void harvest(FamilyMember familyMember) {
        harvest.addFamilyMember(familyMember);
    }

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
}


//