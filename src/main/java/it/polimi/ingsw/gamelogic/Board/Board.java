package it.polimi.ingsw.gamelogic.Board;

/**
 * Created by higla on 16/05/2017.
 */
public class Board {
    int NUMBER_OF_TOWERS = 4;
    int NUMBER_OF_MARKETS = 4;

    Tower[] towers = new Tower[NUMBER_OF_TOWERS];
    MarketAS[] market = new MarketAS[NUMBER_OF_MARKETS];
    BuildAS build = new BuildAS();
    HarvestAS harvest = new HarvestAS();
    VaticanReport vaticanReport = new VaticanReport();
    CouncilAS council;

    public void createNewBoard(Tower[] towers, MarketAS[] market, BuildAS build, HarvestAS harvest, CouncilAS councilAS, VaticanReport vaticanReport)
    {
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
    public Tower getTower(int i)
    {
        return this.towers[i];
    }
    public Tower[] getTowers() {
        return towers;
    }

    public void setTowers(Tower[] towers) {
        this.towers = towers;
    }
    public TowerFloorAS[] getTowerFloor(Tower tower){
        return tower.getFloors();
    }
    public TowerFloorAS[] getFloorLevel(int i){
        int k;
        final int NUMBER_OF_FLOORS = 4;
        TowerFloorAS[] iFloor = new TowerFloorAS[NUMBER_OF_FLOORS];
        for(k=0; k< NUMBER_OF_FLOORS; k++)
            iFloor[k] = towers[k].getFloor(i);
        return iFloor;
    }
    public String getTowerColor(Tower tower){
        return tower.getTowerColor();
    }

    public MarketAS[] getMarket() {
        return market;
    }

    public void setMarket(MarketAS[] market) {
        this.market = market;
    }
    public MarketAS getMarketSpaceByIndex(int index)
    {
        return market[index];
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
    public int[] getVaticanFaithAge(){
        return this.vaticanReport.getRequiredFaithPoints();
    }
    public int getVaticanFaithAgeIndex(int index)
    {
        return vaticanReport.getRequiredFaithPointsByIndex(index);
    }
    public int getVictoryPointsByIndex(int index)
    {
        return vaticanReport.getRequiredVictoryPointByIndex(index);
    }

    public int[] getVaticanVictoryPoints(){
        return this.vaticanReport.getCorrespondingVictoryPoints();
    }

    public void setVaticanReport(VaticanReport vaticanReport) {
        this.vaticanReport = vaticanReport;
    }

    public CouncilAS getCouncil() {
        return council;
    }

    public void setCouncil(CouncilAS council) {
        this.council = council;
    }

}


//