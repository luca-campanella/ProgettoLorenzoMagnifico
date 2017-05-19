package it.polimi.ingsw.testingGSON.testingGSONBoard;

import com.google.gson.*;
import it.polimi.ingsw.gamelogic.Board.TowerFloorAS;
import it.polimi.ingsw.gamelogic.Effects.EffectInterface;
import it.polimi.ingsw.gamelogic.Effects.NoEffect;
import it.polimi.ingsw.gamelogic.Effects.TakeOrPaySomethingEffect;
import it.polimi.ingsw.gamelogic.Resource.Resource;
import it.polimi.ingsw.gamelogic.Resource.ResourceEnum;
import it.polimi.ingsw.utils.Debug;

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
        String effectName = jsonFloor.get("effect").getAsString();

        Debug.printVerbose("TowerFloorDeserializer");

        switch (effectName) {
            case "TakeOrPaySomethingEffect":
                Debug.printVerbose("Case take or pay");
                final String resourceTaken = jsonFloor.get("resourceTaken").getAsString();
                final int valueTaken = jsonFloor.get("valueTaken").getAsInt();
                final Resource resource = getCorrectResource(resourceTaken, valueTaken);
                effect = new TakeOrPaySomethingEffect(resource);
                break;
            case "NoEffect":
                Debug.printVerbose("No Effect");
                effect = new NoEffect();
                break;
            default:
                Debug.printVerbose("No Effect");
                effect = new NoEffect();
                break;
        }
        Debug.printVerbose(effect.descriptionOfEffect());

        TowerFloorAS towerFloorAS = new TowerFloorAS();
        towerFloorAS.setDiceValue(diceValue);
        towerFloorAS.setEffect(effect);
        return towerFloorAS;

    }

    private Resource getCorrectResource(String resourceType, int resourceValue) {
        switch (resourceType) {
            case "wood":
                return new Resource(ResourceEnum.WOOD, resourceValue);
            case "stone":
                return new Resource(ResourceEnum.STONE, resourceValue);
            case "coin":
                return new Resource(ResourceEnum.COIN, resourceValue);
            case "faith_points":
                return new Resource(ResourceEnum.FAITH_POINTS, resourceValue);
            case "military_points":
                return new Resource(ResourceEnum.MILITARY_POINTS, resourceValue);
            case "victory_points":
                return new Resource(ResourceEnum.VICTORY_POINTS, resourceValue);
            default:
                return new Resource(ResourceEnum.WOOD, 0);
        }
    }
}


