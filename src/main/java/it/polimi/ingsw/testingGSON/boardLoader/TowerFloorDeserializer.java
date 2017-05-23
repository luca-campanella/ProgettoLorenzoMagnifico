package it.polimi.ingsw.testingGSON.boardLoader;

import com.google.gson.*;
import it.polimi.ingsw.model.board.TowerFloorAS;
import it.polimi.ingsw.model.effects.EffectInterface;
import it.polimi.ingsw.model.effects.NoEffect;

import java.lang.reflect.Type;

/**
 * Created by higla on 20/05/2017.
 */
public class TowerFloorDeserializer implements JsonDeserializer<TowerFloorAS> {
    int diceValue;
    public TowerFloorAS deserialize (final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException{
        EffectInterface effect = new NoEffect();
        JsonObject jsonFloor = json.getAsJsonObject();

        diceValue = jsonFloor.get("diceValue").getAsInt();
        effect = EffectParser.parseEffect(jsonFloor, context);

        TowerFloorAS towerFloorAS = new TowerFloorAS();

        towerFloorAS.setDiceValue(diceValue);
        towerFloorAS.addEffect(effect);

        return towerFloorAS;

    }

}


