package it.polimi.ingsw.testingGSON.testingGSONBoard;

import com.google.gson.*;
import it.polimi.ingsw.gamelogic.Board.TowerFloorAS;
import it.polimi.ingsw.gamelogic.Effects.EffectInterface;
import it.polimi.ingsw.utils.Debug;

import java.lang.reflect.Type;

/**
 * Created by higla on 20/05/2017.
 */
public class TowerFloorDeserializer implements JsonDeserializer<TowerFloorAS> {
    int diceValue;
    public TowerFloorAS deserialize (final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException{
        EffectInterface effect;
        JsonObject jsonFloor = json.getAsJsonObject();

        diceValue = jsonFloor.get("diceValue").getAsInt();
        String effectName = jsonFloor.get("effect").getAsString();

        Debug.printVerbose("TowerFloorDeserializer");
        EffectParser effectParser = new EffectParser();
        effect = effectParser.parseEffect(effectName, jsonFloor);
        Debug.printVerbose(effect.descriptionOfEffect());

        TowerFloorAS towerFloorAS = new TowerFloorAS();
        towerFloorAS.setDiceValue(diceValue);
        towerFloorAS.setEffect(effect);

        return towerFloorAS;

    }

}


