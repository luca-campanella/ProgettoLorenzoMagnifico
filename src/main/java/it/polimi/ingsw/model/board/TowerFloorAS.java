package it.polimi.ingsw.model.board;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.cards.CharacterCardCollector;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Debug;

import java.io.Serializable;

/**
 * This action space is the one placed on the tower, with a corresponding card to it
 */
public class TowerFloorAS extends AbstractActionSpace implements Serializable {

    private int diceCost;

    private AbstractCard card;

    private FamilyMember familyMember = null;

    public TowerFloorAS(/*int diceCost, ImmediateEffectInterface effect*/){
        /*this.diceCost = diceCost;
        this.effect = effect;*/
    }

    public void placeCard(AbstractCard card){

        this.card = card;

    }

    public AbstractCard getCard(){
        return card;
    }

    /**
     * This method performs the real action on the model when the player places a FM on a tower
     * @param familyMember the family member to perform the action with
     */
    @Override
    public void performAction(FamilyMember familyMember, ChoicesHandlerInterface choiceController) {
        Debug.printVerbose("Perform action called on TowerFloorAS, about to perform the action ");
        this.familyMember = familyMember;
        Player player = familyMember.getPlayer();

        CharacterCardCollector blueCards = player.getPersonalBoard().getCharacterCardsCollector();

        //TODO review this method to use the right parameters according to refactor of AS
        //We check if the player has some blue card that disables immediate effects, otherwise we activate them
        if(!blueCards.isImmediateEffectDisabled(diceCost)){
            Debug.printVerbose("Immediate effects are not disabled for this tower level, activating them");
            getEffects().forEach(effect -> effect.applyToPlayer(player, choiceController, "TowerFloorAS"));
        }

        //todo discount on card already checked in tower -> not ok! should be activated in the card because we are not sure we are gonna use it all


    }

    public void clearFloor(){
        this.familyMember = null;
        this.card = null;
    }

    public int getDiceCost(){
        return diceCost;
    }

    public FamilyMember getFamilyMember(){
        return  familyMember;
    }

    public void setCard(AbstractCard card) {
        this.card = card;
    }
}
