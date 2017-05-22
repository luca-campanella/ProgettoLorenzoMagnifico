package it.polimi.ingsw.testingGSON.testingGSONBoard;

import com.google.gson.*;
import it.polimi.ingsw.gamelogic.Board.HarvestAS;
import it.polimi.ingsw.gamelogic.effects.EffectInterface;
import it.polimi.ingsw.gamelogic.effects.NoEffect;

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
