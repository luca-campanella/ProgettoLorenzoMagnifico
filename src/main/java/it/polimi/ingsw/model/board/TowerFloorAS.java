package it.polimi.ingsw.model.board;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.cards.CharacterCardCollector;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.TakeCardNoFamilyMemberEffect;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceCollector;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.utils.Debug;

import java.io.Serializable;
import java.util.List;

/**
 * This action space is the one placed on the tower, with a corresponding card to it
 */
public class TowerFloorAS extends AbstractActionSpace implements Serializable {

    private AbstractCard card;

    public TowerFloorAS(/*int diceCost, ImmediateEffectInterface effect*/){
        /*this.diceCost = diceCost;
        this.effect = effect;*/
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

        player.playFamilyMember(familyMember);

        subServantsToPlayer(familyMember);

        applyCardEffect(player, choiceController);
    }

    private void subServantsToPlayer(FamilyMember familyMember) {
        Player player = familyMember.getPlayer();

        //subtract corresponding family members needed
        int servantsNeeded = getDiceRequirement() - familyMember.getValue();

        //check the excommunication tile malus
        servantsNeeded *= player.getExcommunicationTilesCollector().payMoreServant();

        List<Resource> discountOnTowers = player.getPersonalBoard().getCharacterCardsCollector().getDiscountOnTower(card.getColor());
        for(Resource resIter : discountOnTowers) {
            if(resIter.getType() == ResourceTypeEnum.SERVANT)
                servantsNeeded -= resIter.getValue();
        }

        if(servantsNeeded<0)
            servantsNeeded=0;
        player.subResource(new Resource(ResourceTypeEnum.SERVANT, servantsNeeded));
    }

    /**
     * Applies the effects of the card to the player
     * @param player the player to apply to
     * @param choiceController the interface for callback on choices
     */
    private void applyCardEffect(Player player, ChoicesHandlerInterface choiceController) {
        CharacterCardCollector blueCards = player.getPersonalBoard().getCharacterCardsCollector();
        //We check if the player has some blue card that disables immediate effects, otherwise we activate them
        if(!blueCards.isImmediateEffectDisabled(getDiceRequirement())){
            Debug.printVerbose("Immediate effects are not disabled for this tower level, activating them");
            getEffects().forEach(effect -> effect.applyToPlayer(player, choiceController, "TowerFloorAS"));
        }
        boolean hasToSubtractRes = true;
        for(ImmediateEffectInterface effectIter : card.getImmediateEffect()) {
            if (effectIter instanceof TakeCardNoFamilyMemberEffect) {
                hasToSubtractRes = false;
                break;
            }
        }
                if(hasToSubtractRes) {
                    //this is general for venture
                    ResourceCollector resToSubtractToPlayer = new ResourceCollector(card.getCostAskChoice(choiceController));
                    //we check if there is a discount on the tower coming from blue cards
                    resToSubtractToPlayer.subResourcesSafely(blueCards.getDiscountOnTower(card.getColor()));
                    resToSubtractToPlayer.subResourcesSafely(player.getPermanentLeaderCardCollector().getDiscountOnCardCost(card.getColor()));

                    player.subResources(resToSubtractToPlayer);
                }
        player.addCard(card);
        card.applyImmediateEffectsToPlayer(player, choiceController);
    }

    /**
     * This method performs the real action on the model when the player uses the effect of a card that
     * lets you place on a tower for free
     * @param player the family member to perform the action with
     */
    //@Override
    public void performActionNoFamilyMember(Player player, int diceValue, ChoicesHandlerInterface choiceController) {
        //subtract corresponding family members needed
        int servantsNeeded = getDiceRequirement() - diceValue;

        //check the excommunication tile malus
        servantsNeeded *= player.getExcommunicationTilesCollector().payMoreServant();

        if(servantsNeeded<0)
            servantsNeeded=0;
        player.subResource(new Resource(ResourceTypeEnum.SERVANT, servantsNeeded));

        applyCardEffect(player, choiceController);
    }

    /**
     * this method is used to add the card on the floor and to clear the family member
     * @param card the card placed on the floor
     */
    public void setCard(AbstractCard card) {

        this.card = card;
        getFamilyMembers().clear();

    }
}
