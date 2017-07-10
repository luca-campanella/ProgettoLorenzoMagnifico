package it.polimi.ingsw.model.controller;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.server.JSONLoader;
import it.polimi.ingsw.utils.Debug;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This class tests the controller of the model
 */
public class ModelControllerTest {
    ModelController modelController;
    ArrayList<Player> players = new ArrayList<>(2);
    Board board;
    @Before
    public void setUp()
    {
        try {
            board = JSONLoader.boardCreator();
            Deck deck  = JSONLoader.createNewDeck();
            board.setDeck(deck);
            deck.fillBoard(board, 1);
        }
        catch(IOException e)
        {
            Debug.printVerbose("Error loading JSON");}
        Player player = new Player("Alpha");
        Player player1 = new Player("Bravo");
        players.add(player);

        players.add(player1);
        modelController = new ModelController(players, board);
    }
    @Test
    public void setFamilyMemberDices() throws Exception {
        Dice dice = new Dice(DiceAndFamilyMemberColorEnum.ORANGE);
        ArrayList<Dice> dices = new ArrayList<>(1);
        dices.add(dice);
        modelController.setDice(dices);
        modelController.setFamilyMemberDices();
        assertEquals(modelController.getPlayerByNickname("Alpha").getNotUsedFamilyMembers().get(0).getValue(), modelController.getDices().get(0).getValue());
    }

    @Test
    public void addCoinsStartGame() throws Exception {
        modelController.addCoinsStartGame(players);
        assertEquals(5, players.get(0).getResource(ResourceTypeEnum.COIN));
        assertEquals(6, players.get(1).getResource(ResourceTypeEnum.COIN));
    }



    @Test
    public void getFamilyMemberCouncil() throws Exception {
        FamilyMember familyMember = new FamilyMember(new Dice(DiceAndFamilyMemberColorEnum.ORANGE), players.get(0));
        board.getCouncil().addFamilyMember(familyMember);
        assertEquals(modelController.getFamilyMemberCouncil().get(0), familyMember);
    }


    @Test
    public void spaceTowerAvailable() throws Exception {

        Debug.setLevel(0);
        Dice dice = new Dice(DiceAndFamilyMemberColorEnum.ORANGE);
        FamilyMember familyMember = new FamilyMember(dice, players.get(0));
        ArrayList<Dice> dices = new ArrayList<>(1);
        dice.setValue(7);
        dices.add(dice);

        players.get(0).setFamilyMembers(dices);
        players.get(0).addResource(new Resource(ResourceTypeEnum.COIN, 10));
        players.get(0).addResource(new Resource(ResourceTypeEnum.WOOD, 10));
        players.get(0).addResource(new Resource(ResourceTypeEnum.STONE, 10));
        players.get(0).addResource(new Resource(ResourceTypeEnum.MILITARY_POINT, 30));
        players.get(0).addResource(new Resource(ResourceTypeEnum.FAITH_POINT, 10));
        players.get(0).subResource(new Resource(ResourceTypeEnum.SERVANT, 3));

        //familyMember.setValueFamilyMember(7);
        assertEquals(16, modelController.spaceTowerAvailable(players.get(0).getNotUsedFamilyMembers().get(0), false).size());
        players.get(0).getNotUsedFamilyMembers().get(0).setValueFamilyMember(5);
        assertEquals(12, modelController.spaceTowerAvailable(players.get(0).getNotUsedFamilyMembers().get(0), false).size());
        players.get(0).getNotUsedFamilyMembers().get(0).setValueFamilyMember(3);
        assertEquals(8, modelController.spaceTowerAvailable(players.get(0).getNotUsedFamilyMembers().get(0), false).size());
        players.get(0).getNotUsedFamilyMembers().get(0).setValueFamilyMember(1);
        assertEquals(4, modelController.spaceTowerAvailable(players.get(0).getNotUsedFamilyMembers().get(0), false).size());
        //care: probably modifying something this result can change from 4 to 2... Cause i just have 2 players but i loaded 4 players board.
        assertEquals(4, modelController.spaceMarketAvailable(players.get(0).getNotUsedFamilyMembers().get(0)).size());
        players.get(0).getNotUsedFamilyMembers().get(0).setValueFamilyMember(0);
        assertEquals(0, modelController.spaceTowerAvailable(players.get(0).getNotUsedFamilyMembers().get(0), false).size());

    }

    @Test
    public void placeOnTower() throws Exception {
    }

    @Test
    public void spaceHarvestAvailable() throws Exception {
    }

    @Test
    public void spaceBuildAvailable() throws Exception {
    }

    @Test
    public void build() throws Exception {
    }

    @Test
    public void harvest() throws Exception {
    }

    @Test
    public void placeOnMarket() throws Exception {
    }

    @Test
    public void spaceCouncilAvailable() throws Exception {
    }

    @Test
    public void placeOnCouncil() throws Exception {
    }

    @Test
    public void discardLeaderCard() throws Exception {
    }

    @Test
    public void activateLeaderCard() throws Exception {
    }

    @Test
    public void endGame() throws Exception {
    }

    @Test
    public void getPlayerByNickname() throws Exception {
    }

    @Test
    public void getYellowBuildingCards() throws Exception {
    }

    @Test
    public void getBoard() throws Exception {
    }

    @Test
    public void addLeaderCardToPlayer() throws Exception {
    }

    @Test
    public void playLeaderCard() throws Exception {
    }

    @Test
    public void placeCardOnBoard() throws Exception {
    }

    @Test
    public void setPersonalTile() throws Exception {
    }

    @Test
    public void getLeaderCardsNotPlayed() throws Exception {
    }

    @Test
    public void getLeaderCardsPlayed() throws Exception {
    }

    @Test
    public void removePlayer() throws Exception {
    }

}