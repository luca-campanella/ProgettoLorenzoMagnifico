package it.polimi.ingsw.testingGSON.testingGSONBoard;

import com.google.gson.*;
import it.polimi.ingsw.gamelogic.Board.Tower;
import it.polimi.ingsw.gamelogic.Board.TowerFloorAS;
import it.polimi.ingsw.utils.Debug;

import java.lang.reflect.Type;

/**
 * Created by higla on 17/05/2017.
 */
public class TowerDeserializer implements JsonDeserializer<Tower> {
    //final int NUMBER_OF_FLOORS = 4;
    int i = 0;

    @Override
    public Tower deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonTower = json.getAsJsonObject();

        final String towerName = jsonTower.get("towerId").getAsString();
        Tower tower = new Tower(towerName);
        Debug.printVerbose("Sono in TowerDeserialize");
        TowerFloorAS[] floors = context.deserialize(jsonTower.get("floors"), TowerFloorAS[].class);
        tower.addFloorsToTower(floors);

        return tower;
    }

}

