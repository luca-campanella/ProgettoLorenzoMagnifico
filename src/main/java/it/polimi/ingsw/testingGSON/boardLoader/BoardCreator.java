package it.polimi.ingsw.testingGSON.boardLoader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.CliPrinter;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.GiveCouncilGiftEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.NoEffect;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.utils.Debug;

import java.io.InputStreamReader;
import java.io.Reader;

/**
* Created by higla on 17/05/2017.
*/
public class BoardCreator {

    public static void main(String[] args) throws Exception {
        Debug.instance(Debug.LEVEL_VERBOSE);
        GsonBuilder gsonBuilder = new GsonBuilder();

        //Gson gson = gsonBuilder.create();

        RuntimeTypeAdapterFactory<ImmediateEffectInterface> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(ImmediateEffectInterface.class, "effectName");
        runtimeTypeAdapterFactory.registerSubtype(NoEffect.class, "NoEffect");
        runtimeTypeAdapterFactory.registerSubtype(GainResourceEffect.class, "GainResourceEffect");
        runtimeTypeAdapterFactory.registerSubtype(GiveCouncilGiftEffect.class, "GiveCouncilGiftEffect");

        Gson gson = gsonBuilder.setPrettyPrinting().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
        /*
        Board boardTest = getBoardForTest();

        String boardInJson = gson.toJson(boardTest);
        System.out.println(boardInJson);

        Board boardFormJson = gson.fromJson(boardInJson, Board.class);

        System.out.println(boardTest.toString());

        CliPrinter printer = new CliPrinter();
        printer.printBoard(boardFormJson);
        */
        // The JSON data
       try (Reader reader = new InputStreamReader(BoardCreator.class.getResourceAsStream("/BoardCFG.json"), "UTF-8")) {
            Board board = gson.fromJson(reader, Board.class);
            CliPrinter printer = new CliPrinter();
            printer.printBoard(board);

        }
    }

    private static Tower getTowerForTest() {
        Tower tower = new Tower();
        tower.setColorTower(CardColorEnum.BLUE);
        TowerFloorAS[] floorsArray = new TowerFloorAS[4];

        TowerFloorAS tfas = new TowerFloorAS();

        for(int i = 0; i < 4; i++)
        {
            //tfas = new TowerFloorAS();
            //tfas.addEffect(new NoEffect());
            tfas.addEffect(new GainResourceEffect(new Resource(ResourceTypeEnum.COIN, 10)));
            //tfas.addEffect(new GainResourceEffect(new Resource(ResourceTypeEnum.WOOD, 4)));
            floorsArray[i] = tfas;
        }
        tower.setFloors(floorsArray);
        /*for(int i = 0; i < 4; i++)
        {
            System.out.println("Floor " + i + ": " + floorsArray[i].getEffectShortDescription());
        }*/

        return tower;
    }

    private static MarketAS getMarketASForTest() {
        MarketAS marketAS = new MarketAS();
        marketAS.addEffect(new NoEffect());
        marketAS.addEffect(new GainResourceEffect(new Resource(ResourceTypeEnum.COIN, 10)));
        //marketAS.addEffect(new GainResourceEffect(new Resource(ResourceTypeEnum.WOOD, 4)));

        return marketAS;
    }

    private static Board getBoardForTest()
    {
        Board boardTest = new Board();

        Tower towerstest[] = new Tower[4];
        for(int i = 0; i < 4; i++)
        {
            towerstest[i] = getTowerForTest();
        }

        MarketAS marketASArray[] = new MarketAS[4];
        for(int i = 0; i < 4; i++)
        {
            marketASArray[i] = getMarketASForTest();
        }

        BuildAS buildAS = new BuildAS(4,5,false);
        buildAS.addEffect(new NoEffect());
        HarvestAS harvestAS = new HarvestAS(3,5);
        harvestAS.addEffect(new NoEffect());

        CouncilAS councilAS = new CouncilAS();
        councilAS.addEffect(new GainResourceEffect(new Resource(ResourceTypeEnum.COIN, 10)));
        councilAS.addEffect(new GainResourceEffect(new Resource(ResourceTypeEnum.COIN, 10)));

        VaticanReport vaticanReport = new VaticanReport(new int[VaticanReport.NUMBER_OF_AGES], new int[VaticanReport.WALK_OF_FAITH]);

        //boardTest.createNewBoard(towerstest, marketASArray, buildAS, harvestAS, councilAS, vaticanReport);

        return boardTest;
    }
}
