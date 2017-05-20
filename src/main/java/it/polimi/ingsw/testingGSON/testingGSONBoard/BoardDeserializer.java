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

       // final String towerName = jsonBoard.get("towerId").getAsString();
        Board board = new Board();
        Debug.printVerbose("Sono in BoardDeserialize");
        Tower[] towers = context.deserialize(jsonBoard.get("towers"), Tower[].class);
        MarketAS[] market = context.deserialize(jsonBoard.get("market"), MarketAS[].class);
        board.addTowersToBoard(towers);
        board.addMarketToBoard(market);
        return board;
    }

}

