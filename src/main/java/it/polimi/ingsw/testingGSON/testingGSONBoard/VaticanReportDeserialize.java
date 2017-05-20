package it.polimi.ingsw.testingGSON.testingGSONBoard;

import com.google.gson.*;
import it.polimi.ingsw.gamelogic.Board.TowerFloorAS;
import it.polimi.ingsw.gamelogic.Board.VaticanReport;
import it.polimi.ingsw.gamelogic.Effects.EffectInterface;
import it.polimi.ingsw.utils.Debug;

import java.lang.reflect.Type;

/**
 * Created by higla on 20/05/2017.
 */
public class VaticanReportDeserialize implements JsonDeserializer<VaticanReport> {
    ;
    public VaticanReport deserialize (final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        int i = 0;
        JsonObject jsonVatican = json.getAsJsonObject();
        JsonArray jsonRequiredFaithPoints = jsonVatican.get("requiredFaithPoints").getAsJsonArray();


        int[] requiredFaithPointsArray = new int[jsonRequiredFaithPoints.size()];
        for(i = 0; i<requiredFaithPointsArray.length; i++)
            requiredFaithPointsArray[i] = jsonRequiredFaithPoints.get(i).getAsInt();
        JsonArray jsonVictoryPoints = jsonVatican.get("victoryPointsLinked").getAsJsonArray();
        int[] victoryPointsArray = new int[jsonVictoryPoints.size()];
        for(i = 0; i<victoryPointsArray.length; i++)
            victoryPointsArray[i] = jsonVictoryPoints.get(i).getAsInt();
        VaticanReport vaticanReport = new VaticanReport();
        vaticanReport.setRequiredFaithPoints(requiredFaithPointsArray);
        vaticanReport.setCorrespondingVictoryPoints(victoryPointsArray);


        return vaticanReport;
    }
}
