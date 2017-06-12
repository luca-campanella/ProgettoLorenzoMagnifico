package it.polimi.ingsw.testingGSON.boardLoader;

import com.google.gson.*;
import it.polimi.ingsw.model.board.MarketAS;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.NoEffect;

import java.lang.reflect.Type;

/**
 * Created by higla on 17/05/2017.
 */
public class MarketASDeserializer implements JsonDeserializer<MarketAS> {

    public MarketAS deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
        throws JsonParseException {
        ImmediateEffectInterface effect = new NoEffect();
        JsonObject jsonMarket = json.getAsJsonObject();
        //String idMarketAS = jsonMarket.get("marketId").getAsString();
        int valueTaken = jsonMarket.get("diceValue").getAsInt();

        effect = EffectParser.parseEffect(jsonMarket, context);

        MarketAS market = new MarketAS();
        //market.setMarketASId(idMarketAS);
        market.setDiceRequirement(valueTaken);
        market.addEffect(effect);

        return market;
        }
}





