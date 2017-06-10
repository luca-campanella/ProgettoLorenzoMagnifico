package it.polimi.ingsw.model.board;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.cards.CharacterCardCollector;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.ResourceCollector;
import it.polimi.ingsw.utils.Debug;

import java.io.Serializable;

/**
 * This action space is the one placed on the tower, with a corresponding card to it
 */
public class TowerFloorAS extends AbstractActionSpace implements Serializable {

    private AbstractCard card;

    //private FamilyMember familyMember = null;

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
    //@Override
    public void performAction(FamilyMember familyMember, ChoicesHandlerInterface choiceController) {
        Debug.printVerbose("Perform action called on TowerFloorAS, about to perform the action ");
        getFamilyMembers().add(familyMember);
        Player player = familyMember.getPlayer();


        CharacterCardCollector blueCards = player.getPersonalBoard().getCharacterCardsCollector();

        //We check if the player has some blue card that disables immediate effects, otherwise we activate them
        if(!blueCards.isImmediateEffectDisabled(getDiceValue())){
            Debug.printVerbose("Immediate effects are not disabled for this tower level, activating them");
            getEffects().forEach(effect -> effect.applyToPlayer(player, choiceController, "TowerFloorAS"));
        }

        //we check if there is a discount on the tower coming from blue cards

        ResourceCollector resToSubtractToPlayer = new ResourceCollector(card.getCostAskChoice(choiceController));
        resToSubtractToPlayer.subResources(blueCards.getDiscountOnTower(card.getColor()));

        player.subResources(resToSubtractToPlayer);
        player.addCard(card);
        card.applyImmediateEffectsToPlayer(player, choiceController);
    }

    public void setCard(AbstractCard card) {
        this.card = card;
    }
}
