package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.cards.VentureCardMilitaryCost;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.leaders.leadersabilities.AbstractLeaderAbility;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * This class tests PayForSomethingEffect
 */
public class PayForSomethingEffectTest {
    ArrayList<Resource> toPay = new ArrayList<>();
    ArrayList<Resource> toGain = new ArrayList<>();
    Resource resource = new Resource(ResourceTypeEnum.COIN, 1);
    Resource resource2 = new Resource(ResourceTypeEnum.WOOD , 1);
    PayForSomethingEffect payForSomethingEffect = new PayForSomethingEffect(toPay, toGain);
    Player player = new Player();

    ChoicesHandlerInterface choicesHandlerInterface = new ChoicesHandlerInterface() {
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
    };

    @Before
    public void setUp() throws Exception {
        toPay.add(resource2);
        toGain.add(resource);
    }

    @Test
    public void applyToPlayer() throws Exception {
        payForSomethingEffect.applyToPlayer(player, choicesHandlerInterface, "PAYFORSOMETHING");

        assertEquals(1, player.getResource(ResourceTypeEnum.WOOD));
        assertEquals(1, player.getResource(ResourceTypeEnum.COIN));

        payForSomethingEffect.applyToPlayer(player, choicesHandlerInterface, "PAYFORSOMETHING");

        assertEquals(0, player.getResource(ResourceTypeEnum.WOOD));
        assertEquals(2, player.getResource(ResourceTypeEnum.COIN));

        //todo: handle negative resources.
        payForSomethingEffect.applyToPlayer(player, choicesHandlerInterface, "PAYFORSOMETHING");

        assertEquals(-1, player.getResource(ResourceTypeEnum.WOOD));
        assertEquals(3, player.getResource(ResourceTypeEnum.COIN));


    }

}