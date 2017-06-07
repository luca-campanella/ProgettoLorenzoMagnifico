package it.polimi.ingsw.model.excommunicationTiles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.testingGSON.boardLoader.RuntimeTypeAdapterFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * This class is used to create the proper Json excommunication tiles classes
 */
public class ExcommunicationTilesCreator {
    public static void main(String[] args) throws IOException{
        GsonBuilder gsonBuilder = new GsonBuilder();

        RuntimeTypeAdapterFactory<AbstractExcommunicationTileEffect> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(AbstractExcommunicationTileEffect.class, "excommunicationTiles");
        //1st period card effects
        runtimeTypeAdapterFactory.registerSubtype(GainFewerResourceEffect.class, "GainFewerResourceEffect");
        runtimeTypeAdapterFactory.registerSubtype(BuildMalusEffect.class, "BuildMalusEffect");
        runtimeTypeAdapterFactory.registerSubtype(HarvestMalusEffect.class, "HarvestMalusEffect");
        runtimeTypeAdapterFactory.registerSubtype(ReductionOnDiceEffect.class, "ReductionOnDiceEffect");
        //2nd period card effects
        runtimeTypeAdapterFactory.registerSubtype(MalusDiceOnTowerColorEffect.class, "MalusDiceOnTowerColorEffect");
        runtimeTypeAdapterFactory.registerSubtype(MalusOnMarketEffect.class, "MalusOnMarketEffect");
        runtimeTypeAdapterFactory.registerSubtype(PayMoreServantsEffect.class, "PayMoreServantsEffect");
        runtimeTypeAdapterFactory.registerSubtype(SkipRoundEffect.class, "SkipRoundEffect");

        Gson gson = gsonBuilder.setPrettyPrinting().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();

        ArrayList<ExcommunicationTile> excommunicationTiles = new ArrayList<ExcommunicationTile>();
        /* Period One Excommunication Tiles
        ExcommunicationTile excommunicationTile = new ExcommunicationTile();
        excommunicationTile.setPeriod(1);
        GainFewerResourceEffect gainF = new GainFewerResourceEffect();
        gainF.setResourceExcommunication(new Resource(ResourceTypeEnum.COIN, 1));
        excommunicationTile.setEffect(gainF);
        excommunicationTiles.add(excommunicationTile);
        ExcommunicationTile excommunicationTileTwo = new ExcommunicationTile();
        excommunicationTileTwo.setPeriod(1);
        HarvestMalusEffect malusHarvest = new HarvestMalusEffect();
        BuildMalusEffect buildHarvest = new BuildMalusEffect();
        buildHarvest.setMalusOnDice(3);
        excommunicationTileTwo.setEffect(buildHarvest);
        excommunicationTiles.add(excommunicationTileTwo);
        ExcommunicationTile excommunicationTileThree = new ExcommunicationTile();
        excommunicationTileTwo.setPeriod(1);
        ReductionOnDiceEffect effect = new ReductionOnDiceEffect();
        ArrayList<DiceAndFamilyMemberColorEnum> colors = new ArrayList<>();
        colors.add(DiceAndFamilyMemberColorEnum.BLACK);
        effect.setColorsEffected(colors);
        excommunicationTileThree.setEffect(effect);
        excommunicationTiles.add(excommunicationTileThree);
        */
        //Period 2 excommunication tile

        String temp = gson.toJson(excommunicationTiles);
        System.out.println(temp);

        try (Reader reader = new InputStreamReader(ExcommunicationTilesCreator.class.getResourceAsStream("/ExcommunicationTiles.json"), "UTF-8")) {
            Type type = new TypeToken<ArrayList<ExcommunicationTile>>(){}.getType();
            ArrayList<ExcommunicationTile> excommunicationDeck = gson.fromJson(reader, type);
            printExcommunciationDeck(excommunicationDeck);
           // return excommunicationDeck;
        }

    }
    private static void printExcommunciationDeck(ArrayList<ExcommunicationTile> excommunicationDeck)
    {
        for(ExcommunicationTile i : excommunicationDeck)
            System.out.println("Period: " + i.getPeriod() + ". Effect: " + i.getEffect().getShortEffectDescription());
    }
}
