package it.polimi.ingsw.model.cards;

import java.util.ArrayList;

/**
 * Created by higla on 23/05/2017.
 */
public class Deck {
    private ArrayList<AbstractCard> allCards;
    private ArrayList<TerritoryCard> territoryCards;
    private ArrayList<CharacterCard> characterCards;
    private ArrayList<BuildingCard> buildingCards;
    private ArrayList<VentureCard> ventureCards;

    public void shuffle(){
        ;
    }
    public void getNextCard(){
        ;
    }

    public ArrayList<AbstractCard> getAllCards() {
        return allCards;
    }

    public void setAllCards(ArrayList<AbstractCard> allCards) {
        this.allCards = allCards;
    }

    public ArrayList<TerritoryCard> getTerritoryCards() {
        return territoryCards;
    }

    public void setTerritoryCards(ArrayList<TerritoryCard> territoryCards) {
        this.territoryCards = territoryCards;
    }

    public ArrayList<CharacterCard> getCharacterCards() {
        return characterCards;
    }

    public void setCharacterCards(ArrayList<CharacterCard> characterCards) {
        this.characterCards = characterCards;
    }

    public ArrayList<BuildingCard> getBuildingCards() {
        return buildingCards;
    }

    public void setBuildingCards(ArrayList<BuildingCard> buildingCards) {
        this.buildingCards = buildingCards;
    }

    public ArrayList<VentureCard> getVentureCards() {
        return ventureCards;
    }

    public void setVentureCards(ArrayList<VentureCard> ventureCards) {
        this.ventureCards = ventureCards;
    }
}
