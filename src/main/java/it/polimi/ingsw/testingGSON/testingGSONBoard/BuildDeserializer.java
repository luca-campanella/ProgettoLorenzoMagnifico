package it.polimi.ingsw.testingGSON.testingGSONBoard;

import com.google.gson.*;
import it.polimi.ingsw.gamelogic.Board.BuildAS;
import it.polimi.ingsw.utils.Debug;

import java.lang.reflect.Type;

/**
 * Created by higla on 20/05/2017.
 */
public class BuildDeserializer implements JsonDeserializer<BuildAS>{

    public BuildAS deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonBuild = json.getAsJsonObject();


        int valueStandard = jsonBuild.get("diceValueStandard").getAsInt();
        int valueMalus = jsonBuild.get("diceValueMalus").getAsInt();

        BuildAS build = new BuildAS();
        build.setValueStandard(valueStandard);
        build.setValueMalus(valueMalus);

        return build;
    }
}