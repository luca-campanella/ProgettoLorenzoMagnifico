package it.polimi.ingsw.testingGSON.testingGSONBoard;

/**
 * Created by higla on 17/05/2017.
 */
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import it.polimi.ingsw.gamelogic.Board.AbstractActionSpace;
import it.polimi.ingsw.gamelogic.Board.TowerFloorAS;
import it.polimi.ingsw.gamelogic.Effects.EffectInterface;
import it.polimi.ingsw.gamelogic.Effects.NoEffect;

public class ActionSpaceDeserializer implements JsonDeserializer<AbstractActionSpace> {
    String tempEffect;
    EffectInterface effect;
    private final ThreadLocal<Map<Integer, AbstractActionSpace>> cache = new ThreadLocal<Map<Integer, AbstractActionSpace>>() {
        @Override
        protected Map<Integer, AbstractActionSpace> initialValue() {
            return new HashMap<>();
        }
    };

    @Override
    public AbstractActionSpace deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        // Only the ID is available
        /*if (json.isJsonPrimitive()) {
            final JsonPrimitive primitive = json.getAsJsonPrimitive();
            ;
        }*/

        // The whole object is available
        if (json.isJsonObject()) {
            final JsonObject jsonObject = json.getAsJsonObject();
            tempEffect = jsonObject.get("EFFECT").getAsString();
            //tempParametres =
            effect = (EffectInterface) createObject(tempEffect);
            AbstractActionSpace actionSpace = new TowerFloorAS(jsonObject.get("DICEVALUE").getAsInt(),effect);

            return actionSpace;
        }

        throw new JsonParseException("Unexpected JSON type: " + json.getClass().getSimpleName());
    }

    static Object createObject(String className){
        Object object = new NoEffect();
        try {
            Class classDefinition = Class.forName(className);
            object = classDefinition.newInstance();
        } catch (InstantiationException e) {
            System.out.println(e);
        } catch (IllegalAccessException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        return object;
    }
}