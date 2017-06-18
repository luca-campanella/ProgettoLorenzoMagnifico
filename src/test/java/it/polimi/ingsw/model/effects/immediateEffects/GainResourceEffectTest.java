package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.cards.VentureCardMilitaryCost;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.leaders.leadersabilities.AbstractLeaderAbility;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * This class tests gainRes. effect.
 */
public class GainResourceEffectTest {
    GainResourceEffect effectCoin = new GainResourceEffect(new Resource(ResourceTypeEnum.COIN, 1));
    GainResourceEffect effectWood = new GainResourceEffect(new Resource(ResourceTypeEnum.WOOD, 1));
    GainResourceEffect effectServ = new GainResourceEffect(new Resource(ResourceTypeEnum.SERVANT, 1));
    GainResourceEffect effectSton = new GainResourceEffect(new Resource(ResourceTypeEnum.STONE, 1));
    GainResourceEffect effectMilP = new GainResourceEffect(new Resource(ResourceTypeEnum.MILITARY_POINT, 1));
    GainResourceEffect effectVicP = new GainResourceEffect(new Resource(ResourceTypeEnum.VICTORY_POINT, 1));


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
    };


    @Test
    public void applyToPlayer() throws Exception {
        effectCoin.applyToPlayer(player, choicesHandlerInterface, "PIPPO");
        effectWood.applyToPlayer(player, choicesHandlerInterface, "PIPPO");
        effectServ.applyToPlayer(player, choicesHandlerInterface, "PIPPO");
        effectSton.applyToPlayer(player, choicesHandlerInterface, "PIPPO");
        effectMilP.applyToPlayer(player, choicesHandlerInterface, "PIPPO");
        effectVicP.applyToPlayer(player, choicesHandlerInterface, "PIPPO");

        //initial player resource: W 2 S 2 L 3 C 0 --todo: modify coins
        assertEquals(1, player.getResource(ResourceTypeEnum.COIN));
        assertEquals(3, player.getResource(ResourceTypeEnum.WOOD));
        assertEquals(4, player.getResource(ResourceTypeEnum.SERVANT));
        assertEquals(3,  player.getResource(ResourceTypeEnum.STONE));
        assertEquals(1, player.getResource(ResourceTypeEnum.MILITARY_POINT));
        assertEquals(1, player.getResource(ResourceTypeEnum.VICTORY_POINT));

        effectVicP.applyToPlayer(player, choicesHandlerInterface, "PIPPO");

        assertEquals(2, player.getResource(ResourceTypeEnum.VICTORY_POINT));


    }

}