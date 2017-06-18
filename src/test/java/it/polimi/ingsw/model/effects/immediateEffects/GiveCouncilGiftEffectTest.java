package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.choices.NetworkChoicesPacketHandler;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Testing council gift
 * case tested: give first gift to player.
 */
public class GiveCouncilGiftEffectTest {
    private GiveCouncilGiftEffect effect = new GiveCouncilGiftEffect(1);
    private GiveCouncilGiftEffect effect2 = new GiveCouncilGiftEffect(2);

    private Player player = new Player();
    private HashMap<String, Integer> choichesAvailable = new HashMap<>();
    private ArrayList<GainResourceEffect> possibleGifts = new ArrayList<>();
    private Resource resource = new Resource(ResourceTypeEnum.COIN,2);
    private Resource resource2 = new Resource(ResourceTypeEnum.WOOD,2);
    private GainResourceEffect gainResourceEffect = new GainResourceEffect(resource);
    private GainResourceEffect gainResourceEffect2 = new GainResourceEffect(resource2);

    private ChoicesHandlerInterface choicesHandlerInterface;

    @Before
    public void setUp() throws Exception {
        possibleGifts.add(gainResourceEffect);
        possibleGifts.add(gainResourceEffect2);

        //i assume that the name of the card is cardName -> where 0 means 1 actually
        String code = "cardName:councilGift0";
        String code2 = "cardName:councilGift1";
        choichesAvailable.put(code, 0);
        choichesAvailable.put(code2, 1);
        //choichesAvailable.
        choicesHandlerInterface = new NetworkChoicesPacketHandler(choichesAvailable, new HashMap<String, String>(), possibleGifts);

    }

    @Test
    public void applyToPlayer() throws Exception {
        assertEquals(0, player.getResource(ResourceTypeEnum.COIN));

        //start resources for a player is W 2 S 2 C0 L 3 VP 0 MP 0 FP 0
        effect.applyToPlayer(player,choicesHandlerInterface, "cardName");

        assertEquals(2, player.getResource(ResourceTypeEnum.COIN));

        //effect.applyToPlayer(player, choicesHandlerInterface, "cardName");
    }

}