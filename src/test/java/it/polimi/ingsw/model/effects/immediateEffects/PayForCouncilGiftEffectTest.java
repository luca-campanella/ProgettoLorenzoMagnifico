package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.cards.VentureCardMilitaryCost;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class tests payForCouncilGiftEffect
 */
public class PayForCouncilGiftEffectTest {
    Resource resource = new Resource(ResourceTypeEnum.WOOD, 1);
    ArrayList<Resource> resources = new ArrayList<>();
    PayForCouncilGiftEffect effect;
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
    };
    @Before
    public void setUp() throws Exception {
        resources.add(resource);
        effect = new PayForCouncilGiftEffect(resources);
    }


    @Test
    public void applyToPlayer() throws Exception {

        effect.applyToPlayer(player, choicesHandlerInterface, "cardName");
        //assertEquals(1, player.getResource(ResourceTypeEnum.WOOD));
       // assertEquals(2, player.getResource(ResourceTypeEnum.MILITARY_POINT));

    }

}