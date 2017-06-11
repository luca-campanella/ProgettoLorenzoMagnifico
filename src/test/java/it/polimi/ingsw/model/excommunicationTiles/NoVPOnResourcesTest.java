package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.server.JSONLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Testing noVP on resource excommunication
 * This test covers: case no resource effected  (ThirdTest)
 *                   case double resource effected (FourthTest)
 *                   case 1 yes 1 no (SecondTest)
 *                   case 1 resource only effected (FirstTest)
 */
public class NoVPOnResourcesTest {
    JSONLoader jsonLoader;
    ArrayList<ExcommunicationTile> excommunicationTiles;
    NoVPOnResources cardEffect;
    /**
     * we load the excommunication tiles necessary for the test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        jsonLoader = new JSONLoader();
        excommunicationTiles = jsonLoader.loadExcommunicationTiles();
        //creating a resource test
        Resource resource = new Resource(ResourceTypeEnum.COIN, 1);
        ArrayList<Resource> resources = new ArrayList<>();
        resources.add(resource);
        cardEffect = new NoVPOnResources(resources);
    }
    @Test
    public void noVPonResource() throws Exception {
        Resource resourceTest = new Resource(ResourceTypeEnum.COIN, 5);
        ArrayList<Resource> resourcesTest = new ArrayList<>();
        ArrayList<Resource> resourcesNoExcommunicationTest = new ArrayList<>();

        resourcesTest.add(resourceTest);
        int valueTest = cardEffect.noVPonResource(resourcesTest);
        //making second test. Same excommunication effect, resources not effected added
        Resource resourceSecondTest = new Resource(ResourceTypeEnum.WOOD, 5);
        resourcesTest.add(resourceSecondTest);
        int valueSecondTest = cardEffect.noVPonResource(resourcesTest);
        //third test, 0
        resourcesNoExcommunicationTest.add(resourceSecondTest);
        int valueThirdTest = cardEffect.noVPonResource(resourcesNoExcommunicationTest);

        //adding same resource. It will never happen. BUT in case...
        resourcesTest.add(resourceTest);
        int valueFourthTest = cardEffect.noVPonResource(resourcesTest);

        assertEquals(5, valueTest);
        assertEquals(5, valueSecondTest);
        assertEquals(0, valueThirdTest);
        assertEquals(10, valueFourthTest);

    }

}