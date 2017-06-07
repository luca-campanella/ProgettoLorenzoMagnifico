package it.polimi.ingsw.testingGSON.boardLoader;

import com.google.gson.*;
import it.polimi.ingsw.model.board.BuildAS;

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
        build.setdiceRequirement(valueStandard);
        build.setValueMalus(valueMalus);

        return build;
    }
}
