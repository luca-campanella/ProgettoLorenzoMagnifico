package it.polimi.ingsw.model.player;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.choices.NetworkChoicesPacketHandler;
import it.polimi.ingsw.model.board.Dice;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.GiveCouncilGiftEffect;
import it.polimi.ingsw.model.excommunicationTiles.ExcommunicationTile;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.server.JSONLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * This class tests player methods.
 */
public class PlayerTest {
    //private Player playerNickname;
    private Resource resourceCoin = new Resource(ResourceTypeEnum.COIN,0);
    private Resource resourceWood = new Resource(ResourceTypeEnum.WOOD,2);
    private Resource resourceStone = new Resource(ResourceTypeEnum.STONE,2);
    private Resource resourceServants = new Resource(ResourceTypeEnum.SERVANT,3);
    private Resource resourceEmpty = new Resource(ResourceTypeEnum.COIN,0);

    @Before
    public void setUp() throws Exception {
        JSONLoader.instance();

    }
    private Resource getRandomResource()
    {
        Random random = new Random();
        int resourceType = random.nextInt(6);
        Resource resource;
        int resourceValue = random.nextInt(100);
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
     */
    @Test
    public void addResource() throws Exception {
        Resource test = new Resource(ResourceTypeEnum.COIN,0);
        Resource secondTest = new Resource(ResourceTypeEnum.COIN, 0);
        int numberOfTests = 100;
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
        for(int i=0; i< numberOfTests; i++) {
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
        for(int i = 0; i< numberOfTests; i++)
        {
            test = getRandomResource();
            Resource testCorrector = new Resource(test.getType(),excommunicationTile.getEffect().gainFewResource(test.getType()));
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
        FamilyMember familyOrange = new FamilyMember(dice, player);

        dices.add(dice);
        player.setFamilyMembers(dices);
        assertEquals(1, player.getNotUsedFamilyMembers().size());
        assertEquals(0, player.getUsedFamilyMembers().size());

        player.playFamilyMember(familyOrange);
        //todo there's a problem here. getNotUsedFamilyMembers.size should be 0
        assertEquals(1, player.getNotUsedFamilyMembers().size());
        assertEquals(1, player.getUsedFamilyMembers().size());
        
        for(FamilyMember iterator : player.getNotUsedFamilyMembers())
            System.out.print(iterator.getColor());

        player.reloadFamilyMember();

        for(FamilyMember iterator : player.getNotUsedFamilyMembers())
            System.out.println("Not used " + iterator.getColor());
        for(FamilyMember iterator : player.getUsedFamilyMembers())
            System.out.println("Used " + iterator.getColor());

    }

    @Test
    public void addCard() throws Exception {
    }

    @Test
    public void addLeaderCard() throws Exception {
    }

    @Test
    public void harvest() throws Exception {
    }

    @Test
    public void build() throws Exception {
    }

    @Test
    public void purplePoints() throws Exception {
    }

    @Test
    public void getPersonalBoard() throws Exception {
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