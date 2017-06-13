package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.choices.NetworkChoicesPacketHandler;
import it.polimi.ingsw.client.controller.ClientMain;
import it.polimi.ingsw.model.cards.VentureCardMilitaryCost;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class tests payForCouncilGiftEffect
 */
public class PayForCouncilGiftEffectTest {
    //it is pay -> it is negative
    Resource resource = new Resource(ResourceTypeEnum.WOOD, 1);
    ArrayList<Resource> resources = new ArrayList<>();
    PayForCouncilGiftEffect effect;
    Player player = new Player();
    String code = "cardName:councilGift0";
    HashMap<String, Integer> hashMap = new HashMap<String, Integer>(1);

    ChoicesHandlerInterface choicesHandlerInterface;
    @Before
    public void setUp() throws Exception {
        hashMap.put(code, 0);
        //effect = new PayForCouncilGiftEffect(resources);
        //ArrayList<GainResourceEffect> councilEffect = new ArrayList<>(1);
        Resource temp = new Resource(ResourceTypeEnum.COIN, 1);
        GainResourceEffect effect1 = new GainResourceEffect(temp);
        ArrayList<GainResourceEffect> effects = new ArrayList<>();
        effects.add(effect1);
        choicesHandlerInterface = new NetworkChoicesPacketHandler(hashMap, effects);

        resources.add(resource);
        effect = new PayForCouncilGiftEffect(resources);

    }


    @Test
    public void applyToPlayer() throws Exception {

        effect.applyToPlayer(player, choicesHandlerInterface, "cardName");
        assertEquals(1, player.getResource(ResourceTypeEnum.WOOD));
        assertEquals(1, player.getResource(ResourceTypeEnum.COIN));
        // assertEquals(2, player.getResource(ResourceTypeEnum.MILITARY_POINT));

    }

}