package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.cards.VentureCardMilitaryCost;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.leaders.leadersabilities.AbstractLeaderAbility;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.model.resource.TowerWrapper;
import it.polimi.ingsw.server.JSONLoader;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Testing gainOrPayResourceConditionedEffect
 * Only general implements this effect. So i will test the general directly
 */
public class GainOrPayResourceConditionedEffectTest {
    int indexOfCard = 0;
    CharacterCard blueCard = new CharacterCard();
    Player player = new Player();
    ChoicesHandlerInterface choicesHandlerInterface = new ChoicesHandlerInterface() {
        @Override
        public TowerWrapper callbackOnTakeCard(String choiceCode, List<TowerWrapper> availableSpaces)
        {
            return null;
        }
        @Override
        public List<GainResourceEffect> callbackOnCouncilGift(String choiceCode, int numberDiffGifts) {
            return null;
        }

        @Override
        public ImmediateEffectInterface callbackOnYellowBuildingCardEffectChoice(String cardNameChoiceCode, List<ImmediateEffectInterface> possibleEffectChoices) {
            return null;
        }

        @Override
        public List<Resource> callbackOnVentureCardCost(String choiceCode, List<Resource> costChoiceResource, VentureCardMilitaryCost costChoiceMilitary) {
            return null;
        }

        @Override
        public AbstractLeaderAbility callbackOnWhichLeaderAbilityToCopy(List<LeaderCard> possibleLeaders) {
            return null;
        }

        @Override
        public boolean callbackOnAlsoActivateLeaderCard() {
            return false;
        }

        @Override
        public int callbackOnAddingServants(String choiceCode, int minimum, int maximum) {
            return 0;
        }

        @Override
        public DiceAndFamilyMemberColorEnum callbackOnFamilyMemberBonus(String choiceCode, List<FamilyMember> availableFamilyMembers) throws IllegalArgumentException {
            return null;
        }
    };

    @Test
    public void applyToPlayer() throws Exception {
        JSONLoader.instance();
        Deck deck = JSONLoader.createNewDeck();
        player.addResource(new Resource(ResourceTypeEnum.MILITARY_POINT,2));
        for(CharacterCard iterator : deck.getCharacterCards())
            if(iterator.getName().equalsIgnoreCase("General")) {
                iterator.applyImmediateEffectsToPlayer(player, choicesHandlerInterface);
            }
        assertEquals(1, player.getResource(ResourceTypeEnum.VICTORY_POINT));
        player.addResource(new Resource(ResourceTypeEnum.MILITARY_POINT,4));
        assertEquals(6, player.getResource(ResourceTypeEnum.MILITARY_POINT));
        for(CharacterCard iterator : deck.getCharacterCards())
            if(iterator.getName().equalsIgnoreCase("General")) {
                iterator.applyImmediateEffectsToPlayer(player, choicesHandlerInterface);
            }
        assertEquals(4, player.getResource(ResourceTypeEnum.VICTORY_POINT));
    }

}