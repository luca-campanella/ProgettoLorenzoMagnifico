package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.utils.Debug;

import java.io.Serializable;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Random;
/**
 * This class has all cards.
 */
public class Deck implements Serializable{
    private ArrayList<AbstractCard> allCards;
    private ArrayList<TerritoryCard> territoryCards;
    private ArrayList<CharacterCard> characterCards;
    private ArrayList<BuildingCard> buildingCards;
    private ArrayList<VentureCard> ventureCards;

    public void shufflePartOfArrayList(ArrayList<? extends  AbstractCard> developmentCard){


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
        board = fillTower(board, period, territoryCards, CardColorEnum.GREEN);
        board = fillTower(board, period, characterCards, CardColorEnum.BLUE);
        board = fillTower(board, period, buildingCards, CardColorEnum.YELLOW);
        board = fillTower(board, period, ventureCards, CardColorEnum.PURPLE);
        return board;
    }

    public ArrayList<AbstractCard> getRandomCards(int period)
    {
        int i;
        ArrayList<AbstractCard> listOfCards = new ArrayList<>(24);
        listOfCards.addAll(fillListOfRandomCards(territoryCards, period));
        listOfCards.addAll(fillListOfRandomCards(characterCards, period));
        listOfCards.addAll(fillListOfRandomCards(buildingCards, period));
        listOfCards.addAll(fillListOfRandomCards(ventureCards, period));

        return listOfCards;
    }
    private ArrayList<? extends AbstractCard> fillListOfRandomCards(ArrayList<? extends AbstractCard> developmentCard, int period)
    {
        ArrayList<AbstractCard> listOfCards = new ArrayList<>(4);
        //k goes from 8 to 4 again and again, depending on round
        final int numberOfCardsPicked = 4;
        int k = ((developmentCard.size()+4)%8)+4;
        int temp;
        for (int i = 0; i < numberOfCardsPicked; i++) {
            temp =  (int)(Math.random()*k);
            //i take a random number - 0 to 7
            //Debug.printDebug("K vale " + k + " temp invece " +temp );
            //then in base of the period, i choose the right card
            while (developmentCard.get(temp).getPeriod() != period)
                temp = (int) (Math.random() * k);
            //Debug.printDebug("temp vale " + temp + " period vale " + period);
            listOfCards.add(developmentCard.get(temp));
            //  Debug.printDebug(developmentCard.get(temp).getName());
            //then i remove the card from all territory card, and decrease the card count
            developmentCard.remove(temp);
            k--;
        }
        return listOfCards;
    }
    //This method needs to be changed.. It isn't really working probably
    /**
     * this method fills towers with correct cards
     * @param board
     * @param period
     * @param developmentCard
     * @param colorTower
     * @return
     */
    public Board fillTower(Board board, int period, ArrayList<? extends AbstractCard> developmentCard, CardColorEnum colorTower)
    {
        int i;
        //k goes from 8 to 4 again and again, depending on round
        int k = ((developmentCard.size()+4)%8)+4;
        int temp;
        for (i = 0; i < board.getNUMBER_OF_FLOORS(); i++) {
            temp =  (int)(Math.random()*k);
            //i take a random number - 0 to 7
            //Debug.printDebug("K vale " + k + " temp invece " +temp );
            //then in base of the period, i choose the right card
            while (developmentCard.get(temp).getPeriod() != period)
                temp = (int) (Math.random() * k);
            //Debug.printDebug("temp vale " + temp + " period vale " + period);
            board.setCardsOnTower(developmentCard.get(temp), colorTower, i);
            //  Debug.printDebug(developmentCard.get(temp).getName());
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
