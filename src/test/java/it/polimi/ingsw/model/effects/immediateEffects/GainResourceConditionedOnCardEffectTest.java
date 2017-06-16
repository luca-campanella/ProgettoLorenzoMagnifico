package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.cards.VentureCardMilitaryCost;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.server.JSONLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * This class tests gainResource Conditioned On cardWrong.
 * example. gain 2 VP for each blue cardWrong you have.
 */
public class GainResourceConditionedOnCardEffectTest {
    Resource resource = new Resource(ResourceTypeEnum.VICTORY_POINT,2);
    GainResourceConditionedOnCardEffect effect1 = new GainResourceConditionedOnCardEffect(resource, CardColorEnum.BLUE,1);
    AbstractCard cardWrong;
    AbstractCard cardRight;
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
        JSONLoader.instance();
        Deck deck = new Deck();
        deck = JSONLoader.createNewDeck();
        cardWrong = deck.getBuildingCards().get(0);
        cardRight = deck.getCharacterCards().get(0);
    }

    @Test
    public void applyToPlayer() throws Exception {
        effect1.applyToPlayer(player,choicesHandlerInterface,"TEST-GRCONC");
        assertEquals(0,player.getResource(ResourceTypeEnum.VICTORY_POINT));
        //adding a cardWrong to a player and reactivating the effect.

        player.addCard(cardWrong);

        //check if with yellow cards adds bonus..
        effect1.applyToPlayer(player,choicesHandlerInterface,"TEST-GRCONC");
        assertEquals(CardColorEnum.YELLOW, cardWrong.getColor());
        assertEquals(0,player.getResource(ResourceTypeEnum.VICTORY_POINT));

        //now it should add resources properly
        player.addCard(cardRight);
        effect1.applyToPlayer(player,choicesHandlerInterface,"TEST-GRCONC");
        assertEquals(2,player.getResource(ResourceTypeEnum.VICTORY_POINT));
        assertEquals(1,player.getNumberOfColoredCard(CardColorEnum.BLUE));


    }


}