package it.polimi.ingsw.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.PersonalTile;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.effects.immediateEffects.*;
import it.polimi.ingsw.model.effects.permanentEffects.*;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.testingGSON.boardLoader.BoardCreator;
import it.polimi.ingsw.testingGSON.boardLoader.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.utils.Debug;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.lang.reflect.*;
/**
 * Created by higla on 30/05/2017.
 */
public class JSONLoader {
    /**
     * Reads deck from json and loads that on the board.
     * @return
     * @throws Exception
     */
    public Deck createNewDeck() throws IOException {
        Debug.instance(Debug.LEVEL_VERBOSE);
        GsonBuilder gsonBuilder = new GsonBuilder();

        RuntimeTypeAdapterFactory<ImmediateEffectInterface> immediateEffectAdapter = RuntimeTypeAdapterFactory.of(ImmediateEffectInterface.class, "immediateEffect");
        immediateEffectAdapter.registerSubtype(NoEffect.class, "NoEffect");
        immediateEffectAdapter.registerSubtype(TakeOrPaySomethingEffect.class, "TakeOrPaySomethingEffect");
        immediateEffectAdapter.registerSubtype(GiveCouncilGiftEffect.class, "GiveCouncilGiftEffect");
        immediateEffectAdapter.registerSubtype(TakeCardNoFamilyMemberEffect.class, "TakeCardNoFamilyMemberEffect");
        immediateEffectAdapter.registerSubtype(DiscountEffect.class, "DiscountEffect");
        immediateEffectAdapter.registerSubtype(TakeOrPaySomethingConditionedOnCardEffect.class, "TakeOrPaySomethingConditionedOnCardEffect");
        immediateEffectAdapter.registerSubtype(HarvestNoFamilyMembersEffect.class, "HarvestNoFamilyMembersEffect");
        immediateEffectAdapter.registerSubtype(BuildNoFamilyMembersEffect.class, "BuildNoFamilyMembersEffect");
        immediateEffectAdapter.registerSubtype(TakeOrPaySomethingConditionedEffect.class, "TakeOrPaySomethingConditionedEffect");
        immediateEffectAdapter.registerSubtype(PayForSomethingEffect.class, "PayForSomethingEffect");
        immediateEffectAdapter.registerSubtype(PayForGiftEffect.class, "PayForGiftEffect");

        RuntimeTypeAdapterFactory<AbstractPermanentEffect> permanentEffectAdapter = RuntimeTypeAdapterFactory.of(AbstractPermanentEffect.class, "permanentEffect");
        permanentEffectAdapter.registerSubtype(BonusOnHarvestEffect.class, "BonusOnHarvestEffect");
        permanentEffectAdapter.registerSubtype(BonusOnBuildEffect.class, "BonusOnBuildEffect");
        permanentEffectAdapter.registerSubtype(BonusOnTowerEffect.class, "BonusOnTowerEffect");
        permanentEffectAdapter.registerSubtype(MalusDisabledImmediateEffectsEffect.class, "MalusDisabledImmediateEffectsEffect");
        permanentEffectAdapter.registerSubtype(NoPermanentEffect.class, "NoPermanentEffect");
        permanentEffectAdapter.registerSubtype(BonusOnTowerEffectChoice.class,"BonusOnTowerEffectChoice");

        Gson gson = gsonBuilder.setPrettyPrinting().registerTypeAdapterFactory(immediateEffectAdapter).registerTypeAdapterFactory(permanentEffectAdapter).create();

        try (Reader reader = new InputStreamReader(BoardCreator.class.getResourceAsStream("/DeckCFG.json"), "UTF-8")) {
            Deck deck = gson.fromJson(reader, Deck.class);
            return deck;
        }

    }

    /**
     * this method is called when gameController is created and loads the 4 player-board
     * @return the4-orderPlayers board
     * @throws Exception in case GSON isn't able to read the file
     */
    public Board boardCreator() throws Exception
    {
        GsonBuilder gsonBuilder = new GsonBuilder();

        RuntimeTypeAdapterFactory<ImmediateEffectInterface> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(ImmediateEffectInterface.class, "effectName");
        runtimeTypeAdapterFactory.registerSubtype(NoEffect.class, "NoEffect");
        runtimeTypeAdapterFactory.registerSubtype(TakeOrPaySomethingEffect.class, "TakeOrPaySomethingEffect");
        runtimeTypeAdapterFactory.registerSubtype(GiveCouncilGiftEffect.class, "GiveCouncilGiftEffect");

        Gson gson = gsonBuilder.setPrettyPrinting().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();

        try (Reader reader = new InputStreamReader(BoardCreator.class.getResourceAsStream("/BoardCFG.json"), "UTF-8")) {
            Board board = gson.fromJson(reader, Board.class);
            return board;
        }
    }

    /**
     * this loads an arrayList of tiles. The first element of the array list is the standard tile
     * @return an array list of tiles
     * @throws IOException
     */
    public ArrayList<PersonalTile> loadTiles() throws IOException{
        GsonBuilder gsonBuilder = new GsonBuilder();

        RuntimeTypeAdapterFactory<ImmediateEffectInterface> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(ImmediateEffectInterface.class, "effectName");
        runtimeTypeAdapterFactory.registerSubtype(NoEffect.class, "NoEffect");
        runtimeTypeAdapterFactory.registerSubtype(TakeOrPaySomethingEffect.class, "TakeOrPaySomethingEffect");

        Gson gson = gsonBuilder.setPrettyPrinting().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();


        try (Reader reader = new InputStreamReader(PersonalTile.class.getResourceAsStream("/PersonalTiles.json"), "UTF-8")) {

            Type type = new TypeToken<ArrayList<PersonalTile>>(){}.getType();
            ArrayList<PersonalTile> inList = gson.fromJson(reader, type);
            for (PersonalTile i : inList) {
                System.out.println(i);
            }
            return inList;
        }

    }
}
