package it.polimi.ingsw.testingGSON.testingGSONBoard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.CliPrinter;
import it.polimi.ingsw.gamelogic.Board.*;
import it.polimi.ingsw.utils.Debug;
import java.io.InputStreamReader;
import java.io.Reader;

/**
* Created by higla on 17/05/2017.
*/
public class ExampleMain {

//qui chiamo il BoardDeserializer
//stampo anche in console la board per vedere se è passata correttamente
    public static void main(String[] args) throws Exception {
        Debug.instance(Debug.LEVEL_VERBOSE);

        // Configure Gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Board.class, new BoardDeserializer());
        gsonBuilder.registerTypeAdapter(Tower.class, new TowerDeserializer());
        gsonBuilder.registerTypeAdapter(TowerFloorAS.class, new TowerFloorDeserializer());
        gsonBuilder.registerTypeAdapter(MarketAS.class, new MarketASDeserializer());
        gsonBuilder.registerTypeAdapter(BuildAS.class, new BuildDeserializer());
        gsonBuilder.registerTypeAdapter(HarvestAS.class, new HarvestDeserializer());

        gsonBuilder.registerTypeAdapter(VaticanReport.class, new VaticanReportDeserialize());

        Gson gson = gsonBuilder.create();
        // The JSON data
        try(Reader reader = new InputStreamReader(ExampleMain.class.getResourceAsStream("/BoardCFG.json"), "UTF-8")) {
            Board board = gson.fromJson(reader, Board.class);
            CliPrinter printer = new CliPrinter();
            printer.printBoard(board);
        }

    }


}
