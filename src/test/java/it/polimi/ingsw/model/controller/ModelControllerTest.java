package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.choices.NetworkChoicesPacketHandler;
import it.polimi.ingsw.client.controller.ClientMain;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.cards.TerritoryCard;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.PayResourceEffect;
import it.polimi.ingsw.model.excommunicationTiles.ExcommunicationTile;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.server.ControllerGame;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * //todo: need to test all methods. For now, i test harvest
 */
public class ModelControllerTest {
    Player player;
    HashMap<String, Integer> hashEmpty;
    GainResourceEffect pay = new GainResourceEffect(new Resource(ResourceTypeEnum.COIN,0));
    ArrayList<GainResourceEffect> pays = new ArrayList<>();

    NetworkChoicesPacketHandler choicesHandlerInterface;
    @Before
            public void setUp() throws Exception{
        Player player = new Player();
        pays.add(pay);
        choicesHandlerInterface = new NetworkChoicesPacketHandler(hashEmpty, pays);

        TerritoryCard tempCard = new TerritoryCard();
        ControllerGame controllerGame = new ControllerGame(3);
        tempCard = (TerritoryCard) controllerGame.getBoardGame().getTower(0).getFloorByIndex(0).getCard();
        player.addCard(tempCard, CardColorEnum.GREEN);
    }

    @Test
    public void harvest() throws Exception {
        player.harvest(1, choicesHandlerInterface);
        assertEquals(player.getResource(ResourceTypeEnum.WOOD),4);
    }

}