package it.polimi.ingsw.testingGSON.testingGSONBoard;

/**
 * Created by higla on 17/05/2017.
 */
import com.google.gson.*;
import it.polimi.ingsw.gamelogic.Board.AbstractActionSpace;
import it.polimi.ingsw.gamelogic.Board.TowerFloorAS;
import it.polimi.ingsw.gamelogic.Effects.EffectInterface;
import it.polimi.ingsw.gamelogic.Effects.NoEffect;
import it.polimi.ingsw.utils.Debug;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


public class ActionSpaceDeserializer implements JsonDeserializer<AbstractActionSpace> {
    String tempEffect;
    EffectInterface effect;

    private final ThreadLocal<Map<Integer, AbstractActionSpace>> cache = new ThreadLocal<Map<Integer, AbstractActionSpace>>() {
        @Override
        protected Map<Integer, AbstractActionSpace> initialValue() {
            return new HashMap<>();
        }
    };

    /**
     * This method deserialize an AbstractActionSpace from a json file
     * @param json
     * @param typeOfT
     * @param context
     * @return an AbstractActionSpace, which will be the correct space for the correct location
     * @throws JsonParseException
     */
    @Override
    public AbstractActionSpace deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        if (json.isJsonObject()) {
            final JsonObject jsonObject = json.getAsJsonObject();
            tempEffect = jsonObject.get("EFFECT").getAsString();
            //tempParametres =

            tempEffect = "it.polimi.ingsw.gamelogic.Effects." + tempEffect;

            effect = (EffectInterface) createObject(tempEffect);
            AbstractActionSpace actionSpace = new TowerFloorAS(jsonObject.get("DICEVALUE").getAsInt(),effect);

            return actionSpace;
        }

        throw new JsonParseException("Unexpected JSON type: " + json.getClass().getSimpleName());
    }

    static Object createObject(String className){
        Object object = new NoEffect();

        Debug.printVerbose("ClassName: " + className);

        try {
            Class classDefinition = Class.forName(className);
            //object = classDefinition.newInstance();
            //object.getDeclaredConstructor(classDefinition).newInstance("Wood", 2);
            try{
                Class[] cArg = new Class[2];
                cArg[0] = String.class;
                cArg[1] = int.class;
                object = classDefinition.getDeclaredConstructor(cArg).newInstance("Wood", 2);
            }
            catch(NoSuchMethodException e){
                e.printStackTrace();
            }
            catch(InvocationTargetException e){
                e.printStackTrace();
            }
        } catch (InstantiationException e) {
            System.out.println(e);
        } catch (IllegalAccessException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            Debug.printError(e);
        }
        return object;
    }
}