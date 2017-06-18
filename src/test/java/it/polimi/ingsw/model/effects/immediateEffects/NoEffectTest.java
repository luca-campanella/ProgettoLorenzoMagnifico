package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.cards.VentureCardMilitaryCost;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * This class hasn't effect.
 */
public class NoEffectTest {
    Player player = new Player();
    NoEffect effect = new NoEffect();
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
    };

    @Test
    public void applyToPlayer() throws Exception {

        effect.applyToPlayer(player,choicesHandlerInterface,"NoEf");
        assertEquals(2,player.getResource(ResourceTypeEnum.WOOD));
        assertEquals(0,player.getResource(ResourceTypeEnum.COIN));
        assertEquals(2,player.getResource(ResourceTypeEnum.STONE));
        assertEquals(3,player.getResource(ResourceTypeEnum.SERVANT));


    }

}