package it.polimi.ingsw.testingGSON.boardLoader;

import it.polimi.ingsw.model.effects.EffectInterface;
import it.polimi.ingsw.model.effects.NoEffect;

import com.google.gson.*;
import it.polimi.ingsw.utils.Debug;

/**
 * Created by higla on 20/05/2017.
 */
public class EffectParser {
    /**
     * this method returns the proper effect from
     * @param json file
     * @param context given a context
     * @return
     */
    public static EffectInterface parseEffect(JsonObject json, JsonDeserializationContext context) {
        EffectInterface effect = new NoEffect();
        String effectName = json.get("effect").getAsString();
        effectName = "it.polimi.ingsw.model.effects." + effectName;
        try {
            effect = context.deserialize(json.get("Effect"), Class.forName(effectName));
        } catch (ClassNotFoundException e) {
            Debug.printError("Class not found " + effectName, e);
        }
        return effect;
    }
}