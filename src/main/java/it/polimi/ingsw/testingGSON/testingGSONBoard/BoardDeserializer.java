package it.polimi.ingsw.testingGSON.testingGSONBoard;

import com.google.gson.*;
import it.polimi.ingsw.gamelogic.Board.AbstractActionSpace;
import it.polimi.ingsw.gamelogic.Board.Board;
import it.polimi.ingsw.gamelogic.Board.Tower;
import it.polimi.ingsw.gamelogic.Board.TowerFloorAS;
import it.polimi.ingsw.gamelogic.Effects.EffectInterface;
import it.polimi.ingsw.gamelogic.Effects.NoEffect;
import it.polimi.ingsw.gamelogic.Effects.TakeOrPaySomethingEffect;
import it.polimi.ingsw.gamelogic.Player.Player;
import it.polimi.ingsw.gamelogic.Resource.Resource;
import it.polimi.ingsw.gamelogic.Resource.ResourceEnum;
import it.polimi.ingsw.utils.Debug;

import java.lang.reflect.Type;

/**
 * Created by higla on 17/05/2017.
 */
public class BoardDeserializer implements JsonDeserializer<TowerFloorAS> {
    //Board board = new Board();
    //Tower tower;
    TowerFloorAS towerFloorAS;

    //1) Build towers

    // @Override
    public TowerFloorAS deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        EffectInterface effect = new NoEffect();
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonElement jsonDiceValue = jsonObject.get("diceValue");
        final int diceValue = jsonDiceValue.getAsInt();
        final String effectName = jsonObject.get("effect").getAsString();
        Debug.printVerbose("Entro");

        /*switch (effectName) {
            case "TakeOrPaySomethingEffect":
                final String resourceTaken = jsonObject.get("resourceTaken").getAsString();
                final int valueTaken = jsonObject.get("valueTaken").getAsInt();
                final Resource resource = getCorrectResource(resourceTaken, valueTaken);
                effect = new TakeOrPaySomethingEffect(resource);
                break;
            case "NoEffect":
                effect = new NoEffect();
                break;
            default:
                effect = new NoEffect();
                break;
        }*/

        TowerFloorAS towerFloorAS = new TowerFloorAS(diceValue, effect);

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

