package it.polimi.ingsw.testingGSON.boardLoader;

import com.google.gson.*;
import it.polimi.ingsw.model.board.HarvestAS;
import it.polimi.ingsw.model.effects.EffectInterface;
import it.polimi.ingsw.model.effects.NoEffect;

import java.lang.reflect.Type;

/**
 * Created by higla on 20/05/2017.
 */
public class HarvestDeserializer implements JsonDeserializer<HarvestAS>{

    public HarvestAS deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        EffectInterface effect = new NoEffect();
        JsonObject jsonHarvest = json.getAsJsonObject();

        int valueStandard = jsonHarvest.get("diceValueStandard").getAsInt();
        int valueMalus = jsonHarvest.get("diceValueMalus").getAsInt();

        //EffectParser effectParser = new EffectParser();
        //effectParser.parseEffect(effectName, jsonHarvest);

        HarvestAS harvest = new HarvestAS();
        harvest.setValueStandard(valueStandard);
        harvest.setValueMalus(valueMalus);

        return harvest;
    }
}
