package it.polimi.ingsw.testingGSON.testingGSONBoard;

import com.google.gson.*;
import it.polimi.ingsw.gamelogic.Board.MarketAS;
import it.polimi.ingsw.gamelogic.Effects.EffectInterface;
import it.polimi.ingsw.gamelogic.Effects.NoEffect;
import it.polimi.ingsw.utils.Debug;

import java.lang.reflect.Type;

/**
 * Created by higla on 17/05/2017.
 */
public class MarketASDeserializer implements JsonDeserializer<MarketAS> {

    public MarketAS deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
        throws JsonParseException {
        EffectInterface effect = new NoEffect();
        JsonObject jsonMarket = json.getAsJsonObject();
        String idMarketAS = jsonMarket.get("marketId").getAsString();
        int valueTaken = jsonMarket.get("diceValue").getAsInt();
        String effectName = jsonMarket.get("effect").getAsString();

        EffectParser effectParser = new EffectParser();
        effect = effectParser.parseEffect(effectName, jsonMarket);

        MarketAS market = new MarketAS();
        market.setMarketASId(idMarketAS);
        market.setDiceValue(valueTaken);
        market.setEffect(effect);

        return market;
        }
}




