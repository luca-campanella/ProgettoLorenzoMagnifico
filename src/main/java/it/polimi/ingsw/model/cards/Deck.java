package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.utils.Debug;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Random;
/**
 * This class has all cards.
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

    /**
     * this method fills board
     * @param board
     * @param period
     * @return
     */
    public Board fillBoard(Board board, int period)
    {
/*
        board = fillTower(board, period, territoryCards, CardColorEnum.GREEN);
        board = fillTower(board, period, characterCards, CardColorEnum.BLUE);
        board = fillTower(board, period, buildingCards, CardColorEnum.YELLOW);
        board = fillTower(board, period, ventureCards, CardColorEnum.PURPLE);*/
        return board;
    }

    /**
     * this method fills towers
     * @param board
     * @param period
     * @param developmentCard
     * @param colorTower
     * @return
     */
    public Board fillTower(Board board, int period, ArrayList<AbstractCard> developmentCard, CardColorEnum colorTower)
    {
        int i;
        int k = 7;
        int temp = period * (int)(Math.random()*k);;
        for (i = 0; i < board.getNUMBER_OF_FLOORS(); i++) {
            //i take a random number - 0 to 7
            Debug.printDebug("K vale " + k);
            //then in base of the period, i choose the right card
            while (developmentCard.get(temp).getPeriod() != period)
                temp = period * (int) (Math.random() * k);
            Debug.printDebug("temp vale " + temp + " period vale " + period);
            board.setCardsOnTower(developmentCard.get(temp), colorTower, i);
            Debug.printDebug(developmentCard.get(temp).getName());

            //then i remove the card from all territory card, and decrease the card count
            developmentCard.remove(temp);
            k--;
        }
        return board;
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
