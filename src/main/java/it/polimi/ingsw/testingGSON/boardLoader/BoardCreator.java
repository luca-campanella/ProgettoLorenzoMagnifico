package it.polimi.ingsw.testingGSON.boardLoader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.CliPrinter;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.effects.EffectInterface;
import it.polimi.ingsw.model.effects.NoEffect;
import it.polimi.ingsw.model.effects.TakeOrPaySomethingEffect;
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
        /*gsonBuilder.registerTypeAdapter(TowerFloorAS.class, new TowerFloorDeserializer());
        gsonBuilder.registerTypeAdapter(MarketAS.class, new MarketASDeserializer());
        gsonBuilder.registerTypeAdapter(CouncilAS.class, new CouncilDeserializer());

        Gson gson = gsonBuilder.create();*/

        RuntimeTypeAdapterFactory<EffectInterface> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(EffectInterface.class, "effectName");
        runtimeTypeAdapterFactory.registerSubtype(NoEffect.class, "NoEffect");
        runtimeTypeAdapterFactory.registerSubtype(TakeOrPaySomethingEffect.class, "TakeOrPaySomethingEffect");

        Gson gson = gsonBuilder.registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();


        // The JSON data
        try (Reader reader = new InputStreamReader(BoardCreator.class.getResourceAsStream("/BoardCFG.json"), "UTF-8")) {
            Board board = gson.fromJson(reader, Board.class);

            CliPrinter printer = new CliPrinter();
            printer.printBoard(board);

        }
    }
}