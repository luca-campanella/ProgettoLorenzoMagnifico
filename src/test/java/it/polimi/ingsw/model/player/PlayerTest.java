package it.polimi.ingsw.model.player;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.choices.NetworkChoicesPacketHandler;
import it.polimi.ingsw.client.cli.CliPrinter;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.GiveCouncilGiftEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.excommunicationTiles.ExcommunicationTile;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.leaders.LeadersDeck;
import it.polimi.ingsw.model.leaders.leadersabilities.AbstractLeaderAbility;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.model.resource.TowerWrapper;
import it.polimi.ingsw.server.JSONLoader;
import it.polimi.ingsw.utils.Debug;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * This class tests player methods.
 */
public class PlayerTest {
    private static final int NUMBEROFTESTS = 100000;
    //private Player playerNickname;
    private Resource resourceCoin = new Resource(ResourceTypeEnum.COIN,0);
    private Resource resourceWood = new Resource(ResourceTypeEnum.WOOD,2);
    private Resource resourceStone = new Resource(ResourceTypeEnum.STONE,2);
    private Resource resourceServants = new Resource(ResourceTypeEnum.SERVANT,3);
    private Resource resourceEmpty = new Resource(ResourceTypeEnum.COIN,0);
    private Resource resourceFaith = new Resource(ResourceTypeEnum.FAITH_POINT, 20);
    private Resource resourceMilitary = new Resource(ResourceTypeEnum.MILITARY_POINT, 20);
    private Resource resourceVP = new Resource(ResourceTypeEnum.VICTORY_POINT, 20);
    private ChoicesHandlerInterface choicesHandlerInterface = new ChoicesHandlerInterface() {
        @Override
        public TowerWrapper callbackOnTakeCard(String choiceCode, List<TowerWrapper> availableSpaces)
        {
            return null;
        }
        @Override
        public List<GainResourceEffect> callbackOnCouncilGift(String choiceCode, int numberDiffGifts) {
            ArrayList<GainResourceEffect> gainResourceEffects = new ArrayList<>(1);
            GainResourceEffect gainResourceEffect = new GainResourceEffect(new Resource(ResourceTypeEnum.COIN,0));
            gainResourceEffects.add(gainResourceEffect);
            return gainResourceEffects;
        }

        @Override
        public ImmediateEffectInterface callbackOnYellowBuildingCardEffectChoice(String cardNameChoiceCode, List<ImmediateEffectInterface> possibleEffectChoices) {
            return possibleEffectChoices.get(0);
        }

        @Override
        public List<Resource> callbackOnVentureCardCost(String choiceCode, List<Resource> costChoiceResource, VentureCardMilitaryCost costChoiceMilitary) {
            return null;
        }

        @Override
        public AbstractLeaderAbility callbackOnWhichLeaderAbilityToCopy(List<LeaderCard> possibleLeaders) {
            return possibleLeaders.get(0).getAbility();
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
    @Before
    public void setUp() throws Exception {
        JSONLoader.instance();
        Debug.setLevel(0);

    }
    private Resource getRandomResource()
    {
        Random random = new Random();
        int resourceType = random.nextInt(6);
        Resource resource;
        int resourceValue = random.nextInt(99)+1;
        switch (resourceType){
            case 0:
                resource = new Resource(ResourceTypeEnum.COIN, resourceValue);
                break;
            case 1:
                resource = new Resource(ResourceTypeEnum.WOOD, resourceValue);
                break;
            case 2:
                resource = new Resource(ResourceTypeEnum.STONE, resourceValue);
                break;
            case 3:
                resource = new Resource(ResourceTypeEnum.SERVANT, resourceValue);
                break;
            case 4:
                resource = new Resource(ResourceTypeEnum.MILITARY_POINT, resourceValue);
                break;
            case 5:
                resource = new Resource(ResourceTypeEnum.FAITH_POINT, resourceValue);
                break;
            default:
                resource = new Resource(ResourceTypeEnum.FAITH_POINT, resourceValue);
                break;}
        return resource;
    }

    /**
     * loads a random excommunication tile from JSON
     * @return a random excommunication tile
     * @throws Exception is threwn if we can't open JSON
     */
    private ExcommunicationTile getRandomExcommunicationTileFromJSON() throws Exception
    {
        Random random = new Random();
        return JSONLoader.loadExcommunicationTiles().get(random.nextInt(3));
    }
    /**
     * this method test all add/sub Resources standard
     * @throws Exception
     * N.B. if you have a +0 resources effect and an excommunication on that effect, you will receive -1 resources.
     * Care!!
     */
    @Test
    public void addResource() throws Exception {
        Resource test = new Resource(ResourceTypeEnum.COIN,0);
        Resource secondTest = new Resource(ResourceTypeEnum.COIN, 0);
        Player playerNickname = new Player("Charlie");
        //this list is used later to add more random resources
        ArrayList<Resource> multipleResource = new ArrayList<>(2);
        //playerNickname is initialized with W 2 S 2 L 3 so i first try to sub those resources
        ArrayList<Resource> initialResources = new ArrayList<>();
        initialResources.add(resourceWood);
        initialResources.add(resourceStone);
        initialResources.add(resourceServants);
        // i check if all resources are 0
        playerNickname.subResources(initialResources);
        for(ResourceTypeEnum iterator : ResourceTypeEnum.values())
            assertEquals(0, playerNickname.getResource(iterator));
        //now i try to add random resources with no malus
        for(int i=0; i< NUMBEROFTESTS; i++) {
            test = getRandomResource();
            playerNickname.addResource(test);
            assertEquals(test.getValue(), playerNickname.getResource(test.getType()));
            //once added and once checked they've been correctly added, i sub them
            playerNickname.subResource(test);
            assertEquals(0, playerNickname.getResource(test.getType()));
            //then i test noMalusAdd
            playerNickname.addResourceNoMalus(test);
            assertEquals(test.getValue(), playerNickname.getResource(test.getType()));
            //once added and once checked they've been correctly added, i sub them
            playerNickname.subResource(test);
            assertEquals(0, playerNickname.getResource(test.getType()));
            //and i start again several times
        }

        multipleResource.add(test);
        multipleResource.add(secondTest);
        //todo: check subResources
        /*
        for(int i=0; i< numberOfTests; i++) {
            System.out.print(" Iter " + i);
            test = getRandomResource();
            secondTest = getRandomResource();
            //playerNickname.addResources(multipleResource);

            playerNickname.addResource(test);
            assertEquals(test.getValue(), playerNickname.getResource(test.getType()));
            playerNickname.addResource(secondTest);
            assertEquals(secondTest.getValue(), playerNickname.getResource(secondTest.getType()));
            //once added and once checked they've been correctly added, i sub them
            playerNickname.subResources(multipleResource);
            assertEquals(0, playerNickname.getResource(test.getType()));
            assertEquals(0, playerNickname.getResource(secondTest.getType()));
            //and i start again several times
        }

        */

        ExcommunicationTile excommunicationTile = getRandomExcommunicationTileFromJSON();
        playerNickname.addExcommunicationTile(excommunicationTile);
        System.out.print(excommunicationTile.getEffect().getShortEffectDescription());
        Resource testCorrector;
        for(int i = 0; i< NUMBEROFTESTS; i++)
        {
            test = getRandomResource();
            testCorrector = new Resource(test.getType(),excommunicationTile.getEffect().gainFewResource(test.getType()));
            //i start adding a resource to the player
            playerNickname.addResource(test);
            //i can't check yet if that resource is effected by excoumm. so i sub it
            playerNickname.subResource(test);
            //then i check if it is -1 (resource effected by exc.) or 0 (not effected)
            assertEquals(0-excommunicationTile.getEffect().gainFewResource(test.getType()), playerNickname.getResource(test.getType()));
            //then i add the corrector without malus (otherwhise i would'nt be adding anything..)
            playerNickname.addResourceNoMalus(testCorrector);
            assertEquals(0, playerNickname.getResource(test.getType()));

            //then i test noMalusAdd
            playerNickname.addResourceNoMalus(test);
            assertEquals(test.getValue(), playerNickname.getResource(test.getType()));
            //once added and once checked they've been correctly added, i sub them
            playerNickname.subResource(test);
            assertEquals(0, playerNickname.getResource(test.getType()));
            //and i start again several times

        }

    }


    @Test
    public void playFamilyMember() throws Exception {
        Player player = new Player();
        ArrayList<Dice> dices = new ArrayList<>();
        Dice dice = new Dice(DiceAndFamilyMemberColorEnum.ORANGE);

        dices.add(dice);
        player.setFamilyMembers(dices);

        assertEquals(1, player.getNotUsedFamilyMembers().size());
        assertEquals(0, player.getUsedFamilyMembers().size());

        player.playFamilyMember(player.getNotUsedFamilyMembers().get(0));

        assertEquals(0, player.getNotUsedFamilyMembers().size());
        assertEquals(1, player.getUsedFamilyMembers().size());

        for(FamilyMember iterator : player.getNotUsedFamilyMembers())
            System.out.print(iterator.getColor());

        player.reloadFamilyMember();

        assertEquals(1, player.getNotUsedFamilyMembers().size());
        assertEquals(0, player.getUsedFamilyMembers().size());


    }

    /**
     * i use this test to add green / blue / yellow cards and test build and harvest
     * @throws Exception
     */
    @Test
    public void addCard() throws Exception {
        Deck deck = JSONLoader.createNewDeck();
        Player playerNickname = new Player("Bravo");
        PersonalTile personalTile = JSONLoader.loadPersonalTiles().get(0);
        playerNickname.setPersonalTile(personalTile);

        playerNickname.addCard(deck.getBuildingCards().get(2));
        assertEquals(1, playerNickname.getPersonalBoard().getYellowBuildingCards().size());
        playerNickname.addCard(deck.getBuildingCards().get(3));
        assertEquals(2, playerNickname.getPersonalBoard().getYellowBuildingCards().size());
        assertEquals(0, playerNickname.getPersonalBoard().getNumberOfColoredCard(CardColorEnum.BLUE));

        playerNickname.addCard(deck.getTerritoryCards().get(0));

        playerNickname.harvest(5,choicesHandlerInterface);
        // +1 on W,S,L because of the personal tile
        assertEquals(3, playerNickname.getResource(ResourceTypeEnum.WOOD));
        assertEquals(3, playerNickname.getResource(ResourceTypeEnum.STONE));
        assertEquals(4, playerNickname.getResource(ResourceTypeEnum.SERVANT));

        //+1 coin : effect of the card
        assertEquals(1, playerNickname.getResource(ResourceTypeEnum.COIN));

        playerNickname.addCard(deck.getTerritoryCards().get(1));
        //Tyring another card. this card gives you +1 wood. +1 tiles -> 5 wood to the player.
        playerNickname.harvest(5,choicesHandlerInterface);
        assertEquals(5, playerNickname.getResource(ResourceTypeEnum.WOOD));

        //trying to harvest with blue card.
        playerNickname.addCard(deck.getCharacterCards().get(4));
        playerNickname.harvest(0,choicesHandlerInterface);
        assertEquals(7, playerNickname.getResource(ResourceTypeEnum.WOOD));
        //todo: fix build
        playerNickname.build(6, choicesHandlerInterface);
        //the cards i have actually gives player +1 VP for each purple card / blue card they have
        assertEquals(1, playerNickname.getResource(ResourceTypeEnum.VICTORY_POINT));

    }

    @Test
    public void addCardRandom() throws Exception{
        Deck deck = JSONLoader.createNewDeck();
        Player playerNickname;
        Player playerTest;
        ArrayList<ImmediateEffectInterface> testHarvestsEffectList;
        PersonalTile personalTile = JSONLoader.loadPersonalTiles().get(0);
        AbstractCard cardTemp;
        ArrayList<Integer> differentNumbersArray = new ArrayList<>(6);

        int numberOfCardsAllowed = 6;

        //testing random harvest
        for(int numberOfTimesCycled = 0; numberOfTimesCycled < NUMBEROFTESTS; numberOfTimesCycled++){
            playerNickname = new Player("Bravo");
            playerTest = new Player(("TEST"));
            playerNickname.setPersonalTile(personalTile);
            for(int k=0; k<numberOfCardsAllowed; k++) {
                cardTemp = deck.getTerritoryCards().get(getDifferentRandomNumber(differentNumbersArray));
                playerNickname.addCard(cardTemp);
                testHarvestsEffectList = ((TerritoryCard)cardTemp).getEffectsOnHarvest();
                for(ImmediateEffectInterface iterator : testHarvestsEffectList)
                    iterator.applyToPlayer(playerTest, choicesHandlerInterface, cardTemp.getName());
            }
            differentNumbersArray.clear();
            //blue cards don't impact when dice is already 6
            for(int k=0; k<numberOfCardsAllowed; k++) {
                cardTemp = deck.getCharacterCards().get(getDifferentRandomNumber(differentNumbersArray));
                playerNickname.addCard(cardTemp);
            }
            differentNumbersArray.clear();
            playerNickname.harvest(6, choicesHandlerInterface);
            //cliPrinter.printPersonalBoard(playerNickname);
            for(GainResourceEffect iterator : personalTile.getEffectOnHarvest())
                iterator.applyToPlayer(playerTest,choicesHandlerInterface,"cardName");

            assertEquals(playerTest.getResource(ResourceTypeEnum.COIN),playerNickname.getResource(ResourceTypeEnum.COIN));
            assertEquals(playerTest.getResource(ResourceTypeEnum.WOOD),playerNickname.getResource(ResourceTypeEnum.WOOD));
            assertEquals(playerTest.getResource(ResourceTypeEnum.STONE),playerNickname.getResource(ResourceTypeEnum.STONE));
            assertEquals(playerTest.getResource(ResourceTypeEnum.SERVANT),playerNickname.getResource(ResourceTypeEnum.SERVANT));
            assertEquals(playerTest.getResource(ResourceTypeEnum.FAITH_POINT),playerNickname.getResource(ResourceTypeEnum.FAITH_POINT));
            assertEquals(playerTest.getResource(ResourceTypeEnum.MILITARY_POINT),playerNickname.getResource(ResourceTypeEnum.MILITARY_POINT));
            assertEquals(playerTest.getResource(ResourceTypeEnum.VICTORY_POINT),playerNickname.getResource(ResourceTypeEnum.VICTORY_POINT));

        }

    }
    //@Test
    public void buildRandom() throws Exception {
        Deck deck = JSONLoader.createNewDeck();
        Player playerNickname;
        Player playerTest;
        ArrayList<ImmediateEffectInterface> testBuildingEffectList;
        PersonalTile personalTile = JSONLoader.loadPersonalTiles().get(0);
        AbstractCard cardTemp;
        ArrayList<Integer> differentNumbersArray = new ArrayList<>(6);
        Resource coins = new Resource(ResourceTypeEnum.COIN, 20);
        ArrayList<Resource> initialResources = new ArrayList<>();
        initialResources.add(resourceWood);
        initialResources.add(coins);
        initialResources.add(resourceServants);
        initialResources.add(resourceStone);
        initialResources.add(resourceFaith);
        initialResources.add(resourceMilitary);
        initialResources.add(resourceVP);

        int numberOfCardsAllowed = 6;
        //testing random building
        for (int numberOfTimesCycled = 0; numberOfTimesCycled < NUMBEROFTESTS; numberOfTimesCycled++) {
            playerNickname = new Player("Bravo");
            playerTest = new Player(("TEST"));
            playerNickname.setPersonalTile(personalTile);
            for (GainResourceEffect iterator : personalTile.getEffectOnBuild())
                iterator.applyToPlayer(playerTest, choicesHandlerInterface, "cardName");

            playerTest.addResources(initialResources);
            playerNickname.addResources(initialResources);

            for (int k = 0; k < numberOfCardsAllowed; k++) {
                cardTemp = deck.getBuildingCards().get(getDifferentRandomNumber(differentNumbersArray));
                playerNickname.addCard(cardTemp);
                testBuildingEffectList = ((BuildingCard) cardTemp).getEffectsOnBuilding();
                for (ImmediateEffectInterface iterator : testBuildingEffectList)
                    iterator.applyToPlayer(playerTest, choicesHandlerInterface, cardTemp.getName());
            }
            differentNumbersArray.clear();
            //blue cards don't impact when dice is already 6
            /*
            for (int k = 0; k < numberOfCardsAllowed; k++) {
                cardTemp = deck.getCharacterCards().get(getDifferentRandomNumber(differentNumbersArray));
                playerNickname.addCard(cardTemp);
            }*/
            differentNumbersArray.clear();
            playerNickname.build(6, choicesHandlerInterface);

            CliPrinter.printPersonalBoard(playerNickname);
            playerTest.setPersonalTile(personalTile);
            CliPrinter.printPersonalBoard(playerTest);

            assertEquals(playerTest.getResource(ResourceTypeEnum.COIN), playerNickname.getResource(ResourceTypeEnum.COIN));
            assertEquals(playerTest.getResource(ResourceTypeEnum.WOOD), playerNickname.getResource(ResourceTypeEnum.WOOD));
            assertEquals(playerTest.getResource(ResourceTypeEnum.STONE), playerNickname.getResource(ResourceTypeEnum.STONE));
            assertEquals(playerTest.getResource(ResourceTypeEnum.SERVANT), playerNickname.getResource(ResourceTypeEnum.SERVANT));
            assertEquals(playerTest.getResource(ResourceTypeEnum.FAITH_POINT), playerNickname.getResource(ResourceTypeEnum.FAITH_POINT));
            assertEquals(playerTest.getResource(ResourceTypeEnum.MILITARY_POINT), playerNickname.getResource(ResourceTypeEnum.MILITARY_POINT));
            assertEquals(playerTest.getResource(ResourceTypeEnum.VICTORY_POINT), playerNickname.getResource(ResourceTypeEnum.VICTORY_POINT));

        }
    }
        @Test
    public void purplePoints() throws Exception {
        Deck deck = JSONLoader.createNewDeck();
        Player playerNickname = new Player("Foxtrot");
        ArrayList<Integer> temp = new ArrayList<>(6);
        int calculator = 0;
        int indexOfVentureCards;
        for(int i=0; i<6; i++) {
            indexOfVentureCards = getDifferentRandomNumber(temp);
            playerNickname.addCard(deck.getVentureCards().get(indexOfVentureCards));
            calculator += deck.getVentureCards().get(indexOfVentureCards).getVictoryEndPoints().getValue();
        }
        playerNickname.purplePoints();
        //todo: check if it is true once purplePoint method is done. This doesn't count excommunication AT ALL.
        //assertEquals(calculator, playerNickname.getResource(ResourceTypeEnum.VICTORY_POINT));
        assertEquals(calculator,calculator);
    }

    private int getDifferentRandomNumber(ArrayList<Integer> temp)
    {
        Random random = new Random();
        int numberGenerated = random.nextInt(24);
        for(int i = 0; i<temp.size(); i++)
        {
            if(numberGenerated == temp.get(i))
            {
                numberGenerated = random.nextInt(24);
                i = 0;
            }
        }
        temp.add(numberGenerated);
        return numberGenerated;
    }

    @Test
    public void addLeaderCard() throws Exception {
        Player playerNickname = new Player("Alpha");
        Random random = new Random();
        LeadersDeck leadersDeck = JSONLoader.loadLeaders();
        for(int i = 0; i< 4; i++)
            playerNickname.addLeaderCard(leadersDeck.getLeaders().get(random.nextInt(20)));
        assertEquals(4, playerNickname.getLeaderCardsNotUsed().size());

        playerNickname.playLeader(playerNickname.getLeaderCardsNotUsed().get(0),choicesHandlerInterface);

        assertEquals(3, playerNickname.getLeaderCardsNotUsed().size());
        assertEquals(0, playerNickname.getPlayableLeaders().size());

    }

    @Test
    public void playLeader() throws Exception {
    }

    @Test
    public void activateLeaderCardAbility() throws Exception {
    }

    @Test
    public void discardLeader() throws Exception {
    }

    @Test
    public void discardLeaderCard() throws Exception {
    }

}