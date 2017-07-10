package it.polimi.ingsw.model.board;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.cards.VentureCardMilitaryCost;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.excommunicationTiles.ExcommunicationTile;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.leaders.leadersabilities.AbstractLeaderAbility;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.model.resource.TowerWrapper;
import it.polimi.ingsw.server.JSONLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Testing the board
 */
public class BoardTest {
    Board board = new Board();
    static final int NUMBEROFEXCOMMUNICATIONTILES = 16;
    static final int NUMBEROFTESTS = 100;
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
        board = JSONLoader.boardCreator();
    }


    @Test
    public void setExcommunicationTiles() throws Exception {
        Random random = new Random(NUMBEROFEXCOMMUNICATIONTILES);
        ArrayList<ExcommunicationTile> excommunicationTiles = new ArrayList<>();
        excommunicationTiles = JSONLoader.loadExcommunicationTiles();
        board.setExcommunicationTiles(excommunicationTiles);
        assertEquals(excommunicationTiles, board.getExcommunicationTiles());
    }

    @Test
    public void getNUMBER_OF_TOWERS() throws Exception {
        assertEquals(4, board.getNUMBER_OF_TOWERS());
    }

    @Test
    public void getNUMBER_OF_MARKETS() throws Exception {
        assertEquals(4, board.getNUMBER_OF_MARKETS());

    }

    @Test

    public void getFloorLevel() throws Exception {
        Random randomNumberOfTowersGenerator = new Random();
        Board boardTest = new Board();
        ArrayList<Tower> towers = new ArrayList<>();
        for(int i=0; i<randomNumberOfTowersGenerator.nextInt(5); i++){
            Tower towerTest = new Tower();
            TowerFloorAS towerFloor = new TowerFloorAS();
            //towerTest.setFloors();
            towers.add(towerTest);
        }
    }

    @Test
    public void getTowerColor() throws Exception {
        assertEquals(CardColorEnum.GREEN, board.getTower(0).getTowerColor());
        assertEquals(CardColorEnum.BLUE, board.getTower(1).getTowerColor());
        assertEquals(CardColorEnum.YELLOW, board.getTower(2).getTowerColor());
        assertEquals(CardColorEnum.PURPLE, board.getTower(3).getTowerColor());

    }


    @Test
    public void getMarketSpaceByIndex() throws Exception {
        Random random = new Random();
        int temp;
        for(int i=0; i<NUMBEROFTESTS; i++) {
            temp = random.nextInt(4);
            assertEquals(board.getMarket().get(temp), board.getMarketSpaceByIndex(temp));
        }
    }

    @Test
    public void getVaticanFaithAge() throws Exception {

        int[] listOfFaithAge;
       listOfFaithAge = JSONLoader.boardCreator().getVaticanFaithAge();
        int[] listOfVP;
       int temp;
       Random random = new Random();
       //This array contains how many faith points someone has to won in order to avoid excommunciation
       for(int i = 0; i<listOfFaithAge.length; i++) {
           assertEquals(listOfFaithAge[i], board.getVaticanFaithAgeIndex(i));
       }
        listOfVP = JSONLoader.boardCreator().getVaticanVictoryPoints();
        //This array contains how many faith points someone has to won in order to avoid excommunciation

        for(int i = 0; i<listOfFaithAge.length; i++) {
            assertEquals(listOfVP[i], board.getVictoryPointsByIndex(i));
        }

    }
    @Test
    public void getNUMBER_OF_FLOORS() throws Exception {
        assertEquals(4, board.getNUMBER_OF_FLOORS());
    }

    @Test
    public void addCardToTower() throws Exception {
    TowerFloorAS blueTower = new TowerFloorAS();
    AbstractCard card = JSONLoader.createNewDeck().getCharacterCards().get(2);
    String cardName = card.getName();
    blueTower.setCard(card);
    assertEquals(cardName, blueTower.getCard().getName());
    }

    @Test
    public void genericPlacement() throws Exception {
        FamilyMember familyMember = new FamilyMember(new Dice(DiceAndFamilyMemberColorEnum.ORANGE), new Player("Charlie"));
        FamilyMember familyMember1 = new FamilyMember(new Dice(DiceAndFamilyMemberColorEnum.ORANGE), new Player("Johnny"));
        AbstractCard card = JSONLoader.createNewDeck().getTerritoryCards().get(0);
        board.build(familyMember);
        assertEquals(1,board.getBuild().getOccupyingFamilyMemberNumber());
        board.build(familyMember1);
        assertEquals(2,board.getBuild().getOccupyingFamilyMemberNumber());
        assertEquals(false, board.getBuild().isTwoPlayersOneSpace());

        assertEquals(3,board.getBuild().getValueMalus());
        assertEquals(false,board.getBuild().checkIfFirst());
        assertEquals(false,board.getBuild().isTwoPlayersOneSpace());


        //this is one because i didn't add a family member to the AS.
        assertEquals(1, board.getBuild().getValueNeeded(true));

        board.clearBoard();
        board.harvest(familyMember);
        assertEquals(1,board.getHarvest().getOccupyingFamilyMemberNumber());
        board.harvest(familyMember1);
        assertEquals(2, board.getHarvest().getOccupyingFamilyMemberNumber());
        assertEquals(3,board.getHarvest().getValueMalus());
        assertEquals(false,board.getHarvest().checkIfFirst());
        assertEquals(false,board.getHarvest().isTwoPlayersOneSpace());
        assertEquals(0,board.getHarvest().getValueStandard());


        assertEquals(0 , board.getBuild().getOccupyingFamilyMemberNumber());
        board.placeOnCouncil(familyMember,choicesHandlerInterface);
        assertEquals(1 , board.getCouncil().getOccupyingFamilyMemberNumber());

        board.getTower(0).getFloorByIndex(0).setCard(card);

        board.placeOnTower(familyMember,0,0,choicesHandlerInterface);
        assertEquals(1 , board.getTower(0).getFloorByIndex(0).getOccupyingFamilyMemberNumber());
        board.placeOnMarket(familyMember,0,choicesHandlerInterface);
        assertEquals(1 , board.getMarketSpaceByIndex(0).getOccupyingFamilyMemberNumber());
        board.clearBoard();
        assertEquals(0 , board.getMarketSpaceByIndex(0).getOccupyingFamilyMemberNumber());
        assertEquals(0 , board.getTower(0).getFloorByIndex(0).getOccupyingFamilyMemberNumber());
        assertEquals(0 , board.getCouncil().getOccupyingFamilyMemberNumber());

    }

    @Test
    public void setCardsOnTower() throws Exception {
        Deck deck = new Deck();
        deck = JSONLoader.createNewDeck();
        AbstractCard card = deck.getTerritoryCards().get(0);
        board.setCardsOnTower(card, CardColorEnum.GREEN, 0);
        assertEquals(card , board.getTower(0).getFloorByIndex(0).getCard());
    }

    
    @Test
    public void marketTest() throws Exception{
        int numberOfPlayers = 2;
        Market market = new Market(2);
        assertEquals(0, market.getMarketSpaces().size());
    }
}