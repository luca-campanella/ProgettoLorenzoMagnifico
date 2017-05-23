package it.polimi.ingsw.testingGSON.boardLoader;

import com.google.gson.*;
import it.polimi.ingsw.model.board.CouncilAS;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.NoEffect;

import java.lang.reflect.Type;

/**
 * Created by higla on 22/05/2017.
 */
public class CouncilDeserializer implements JsonDeserializer<CouncilAS> {

    public CouncilAS deserialize (final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        ImmediateEffectInterface effect = new NoEffect();
        JsonObject jsonCouncil = json.getAsJsonObject();

        effect = EffectParser.parseEffect(jsonCouncil, context);
        CouncilAS council = new CouncilAS();
        council.setEffect(effect);
        return council;

    }

}
