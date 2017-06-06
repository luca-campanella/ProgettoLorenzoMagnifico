package it.polimi.ingsw.model.board;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.cards.CharacterCardCollector;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * This class represents the single tower and collects action spaces regarding cards
 */
public class Tower implements Serializable{
    private CardColorEnum colorTower;
    private final int NUMBER_OF_FLOORS = 4;
    TowerFloorAS[] floors;

    public Tower(){
        floors = new TowerFloorAS[NUMBER_OF_FLOORS];
    }

    public void setColorTower(CardColorEnum colorTower) {
        this.colorTower = colorTower;
    }

    public void setFloors(TowerFloorAS[] floor)
    {
        floors = floor;
    }
    public TowerFloorAS getFloorByIndex(int index)
    {
        return floors[index];
    }

    public CardColorEnum getTowerColor() {
        return colorTower;
    }

    public int getNUMBER_OF_FLOORS() {
        return NUMBER_OF_FLOORS;
    }
    public TowerFloorAS[] getFloors() {
        return floors;
    }

    public void setCardOnFloor(int i, AbstractCard card)
    {
        this.floors[i].setCard(card);
    }

    public void clearTower(){

        for(int i = 0 ; i < NUMBER_OF_FLOORS ; i++)
            floors[i].clearFloor();
    }

    /**
     * This method checks if the player is the first to place a family member on this Tower,
     * if this is not the case he'll have to pay 3 coins
     * @return
     */
    private boolean isFirstToPlaceOnTower() {
        for(int i = 0; i < NUMBER_OF_FLOORS; i++) {
            if(floors[i].getFamilyMember() != null)
                return false;
        }

        return true;
    }

    /**
     * This method performs the real action on the model when the player places a FM on a tower
     * This method goes down on the model to perform the action calling {@link it.polimi.ingsw.model.board.TowerFloorAS}
     * This method also checks if the player is the first to place a family member on this Tower,
     * if this is not the case it subracts the player three coins
     * @param familyMember the family member to perform the action with
     * @param floorIndex the floor to place the family member to
     * @param choicesController needed because there can be some decisions tied to the action
     */
    public void placeFamilyMember(FamilyMember familyMember, int floorIndex, ChoicesHandlerInterface choicesController) {
        Player player = familyMember.getPlayer();

        if(!isFirstToPlaceOnTower())
            player.subResource(new Resource(ResourceTypeEnum.COIN, 3));

        //we check if there is a discount on the tower coming from blue cards, if there is we add this discount to the player
        CharacterCardCollector blueCards = player.getPersonalBoard().getCharacterCardsCollector();

        LinkedList<Resource> discount = blueCards.getDiscountOnTower(colorTower);
        player.addResources(discount);

        floors[floorIndex].performAction(familyMember, choicesController);
    }
}
