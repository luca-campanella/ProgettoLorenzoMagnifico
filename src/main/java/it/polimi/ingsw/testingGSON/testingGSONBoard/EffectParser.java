package it.polimi.ingsw.testingGSON.testingGSONBoard;

import it.polimi.ingsw.gamelogic.Effects.EffectInterface;
import it.polimi.ingsw.gamelogic.Effects.NoEffect;
import it.polimi.ingsw.gamelogic.Effects.TakeOrPaySomethingEffect;
import it.polimi.ingsw.gamelogic.Resource.Resource;
import it.polimi.ingsw.gamelogic.Resource.ResourceEnum;
import it.polimi.ingsw.utils.Debug;

import com.google.gson.*;

/**
 * Created by higla on 20/05/2017.
 */
public class EffectParser {

    public EffectInterface parseEffect(String effectName, JsonObject jsonAction){
        switch (effectName) {
            case "TakeOrPaySomethingEffect":
                Debug.printVerbose("Case take or pay");
                final String resourceTaken = jsonAction.get("resourceTaken").getAsString();
                final int valueTaken = jsonAction.get("valueTaken").getAsInt();
                final Resource resource = getCorrectResource(resourceTaken, valueTaken);
                return  new TakeOrPaySomethingEffect(resource);
            case "NoEffect":
                Debug.printVerbose("No Effect");
                return new NoEffect();
            default:
                Debug.printVerbose("No Effect");
                return new NoEffect();
        }
    }

    private Resource getCorrectResource(String resourceType, int resourceValue) {
        switch (resourceType) {
            case "wood":
                return new Resource(ResourceEnum.WOOD, resourceValue);
            case "stone":
                return new Resource(ResourceEnum.STONE, resourceValue);
            case "coin":
                return new Resource(ResourceEnum.COIN, resourceValue);
            case "servants":
            case "servant":
                return new Resource(ResourceEnum.SERVANT, resourceValue);
            case "faith_points":
            case "faith_point":
                return new Resource(ResourceEnum.FAITH_POINTS, resourceValue);
            case "military_points":
            case "military_point":
                return new Resource(ResourceEnum.MILITARY_POINTS, resourceValue);
            case "victory_points":
            case "victory_point":
                return new Resource(ResourceEnum.VICTORY_POINTS, resourceValue);
            default:
                return new Resource(ResourceEnum.WOOD, 0);
        }
    }
}
