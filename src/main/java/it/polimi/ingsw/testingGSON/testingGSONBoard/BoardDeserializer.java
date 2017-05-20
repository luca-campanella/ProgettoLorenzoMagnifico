package it.polimi.ingsw.testingGSON.testingGSONBoard;

import com.google.gson.*;
import it.polimi.ingsw.gamelogic.Board.*;
import it.polimi.ingsw.utils.Debug;

import java.lang.reflect.Type;

/**
 * Created by higla on 17/05/2017.
 */
public class BoardDeserializer  implements JsonDeserializer<Board>{
    public Board deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonBoard = json.getAsJsonObject();

        Board board = new Board();
      //  Debug.printVerbose("I'm in BoardDeserialize");
        Tower[] towers = context.deserialize(jsonBoard.get("towers"), Tower[].class);

        MarketAS[] market = context.deserialize(jsonBoard.get("market"), MarketAS[].class);

        BuildAS build = context.deserialize(jsonBoard.get("build"), BuildAS.class);
        HarvestAS harvest = context.deserialize(jsonBoard.get("harvest"), HarvestAS.class);

        VaticanReport vaticanReport = context.deserialize(jsonBoard.get("vaticanReport"), VaticanReport.class);

        CouncilAS council = new CouncilAS();

        board.createNewBoard(towers, market, build, harvest, council, vaticanReport);
        return board;
    }

}

