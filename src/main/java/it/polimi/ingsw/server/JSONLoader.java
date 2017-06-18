package it.polimi.ingsw.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.excommunicationTiles.*;
import it.polimi.ingsw.model.leaders.LeadersDeck;
import it.polimi.ingsw.model.leaders.leadersabilities.AbstractLeaderAbility;
import it.polimi.ingsw.model.leaders.leadersabilities.CopyAnotherLeaderAbility;
import it.polimi.ingsw.model.leaders.leadersabilities.ImmediateLeaderAbility.*;
import it.polimi.ingsw.model.leaders.leadersabilities.PermanentLeaderAbility.*;
import it.polimi.ingsw.model.leaders.requirements.AbstractRequirement;
import it.polimi.ingsw.model.leaders.requirements.CardRequirement;
import it.polimi.ingsw.model.leaders.requirements.ResourceRequirement;
import it.polimi.ingsw.model.player.PersonalTile;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.effects.immediateEffects.*;
import it.polimi.ingsw.model.effects.permanentEffects.*;
import it.polimi.ingsw.testingGSON.boardLoader.BoardCreator;
import it.polimi.ingsw.testingGSON.boardLoader.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.utils.Debug;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.lang.reflect.*;
import java.util.Collections;

/**
 * This class is a singleton that handles all the classes loaded from file
 */
public class JSONLoader {
    private static JSONLoader  internalInstance = null;

    private JSONLoader() {
    }

    /**
     * this method allows to create an instance of JSONLoader
     * @return the instantiated JSONLoader
     */
    public static JSONLoader instance()
    {
        if(internalInstance == null)
            internalInstance = new JSONLoader();
        return internalInstance;
    }

    /**
     * Reads deck from json and loads that on the board.
     * @return a deck full of development card
     * @throws IOException in case of file can't be opened
     */
    public static Deck createNewDeck() throws IOException {
        Debug.instance(Debug.LEVEL_VERBOSE);
        GsonBuilder gsonBuilder = new GsonBuilder();

        RuntimeTypeAdapterFactory<ImmediateEffectInterface> immediateEffectAdapter = RuntimeTypeAdapterFactory.of(ImmediateEffectInterface.class, "immediateEffect");
        immediateEffectAdapter.registerSubtype(NoEffect.class, "NoEffect");
        immediateEffectAdapter.registerSubtype(GainResourceEffect.class, "GainResourceEffect");
        immediateEffectAdapter.registerSubtype(GiveCouncilGiftEffect.class, "GiveCouncilGiftEffect");
        immediateEffectAdapter.registerSubtype(TakeCardNoFamilyMemberEffect.class, "TakeCardNoFamilyMemberEffect");
        immediateEffectAdapter.registerSubtype(DiscountEffect.class, "DiscountEffect");
        immediateEffectAdapter.registerSubtype(GainResourceConditionedOnCardEffect.class, "GainResourceConditionedOnCardEffect");
        immediateEffectAdapter.registerSubtype(HarvestNoFamilyMembersEffect.class, "HarvestNoFamilyMembersEffect");
        immediateEffectAdapter.registerSubtype(BuildNoFamilyMembersEffect.class, "BuildNoFamilyMembersEffect");
        immediateEffectAdapter.registerSubtype(GainOrPayResourceConditionedEffect.class, "GainOrPayResourceConditionedEffect");
        immediateEffectAdapter.registerSubtype(PayForSomethingEffect.class, "PayForSomethingEffect");
        immediateEffectAdapter.registerSubtype(PayForCouncilGiftEffect.class, "PayForCouncilGiftEffect");

        RuntimeTypeAdapterFactory<AbstractPermanentEffect> permanentEffectAdapter = RuntimeTypeAdapterFactory.of(AbstractPermanentEffect.class, "permanentEffect");
        permanentEffectAdapter.registerSubtype(BonusOnHarvestEffect.class, "BonusOnHarvestEffect");
        permanentEffectAdapter.registerSubtype(BonusOnBuildEffect.class, "BonusOnBuildEffect");
        permanentEffectAdapter.registerSubtype(BonusOnTowerEffect.class, "BonusOnTowerEffect");
        permanentEffectAdapter.registerSubtype(MalusDisabledImmediateEffectsEffect.class, "MalusDisabledImmediateEffectsEffect");
        permanentEffectAdapter.registerSubtype(NoPermanentEffect.class, "NoPermanentEffect");
        permanentEffectAdapter.registerSubtype(BonusOnTowerEffectChoice.class,"BonusOnTowerEffectChoice");

        Gson gson = gsonBuilder.setPrettyPrinting().registerTypeAdapterFactory(immediateEffectAdapter).registerTypeAdapterFactory(permanentEffectAdapter).create();

        Reader reader = new InputStreamReader(BoardCreator.class.getResourceAsStream("/DeckCFG.json"), "UTF-8");
            return gson.fromJson(reader, Deck.class);


    }

    /**
     * this method is called when controllerGame is created and loads the 4 player-board
     * @return the4-players board
     * @throws Exception in case GSON isn't able to read the file
     */
    public static Board boardCreator() throws IOException
    {
        Board board;
        GsonBuilder gsonBuilder = new GsonBuilder();

        RuntimeTypeAdapterFactory<ImmediateEffectInterface> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(ImmediateEffectInterface.class, "effectName");
        runtimeTypeAdapterFactory.registerSubtype(NoEffect.class, "NoEffect");
        runtimeTypeAdapterFactory.registerSubtype(GainResourceEffect.class, "GainResourceEffect");
        runtimeTypeAdapterFactory.registerSubtype(GiveCouncilGiftEffect.class, "GiveCouncilGiftEffect");

        Gson gson = gsonBuilder.setPrettyPrinting().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();

        Reader reader = new InputStreamReader(BoardCreator.class.getResourceAsStream("/BoardCFG.json"), "UTF-8");
        board = gson.fromJson(reader, Board.class);

        //we insert excommunication tiles inside the board
        ArrayList<ExcommunicationTile> excomTiles = loadExcommunicationTiles();
        Collections.shuffle(excomTiles);
        ArrayList<ExcommunicationTile> randomTiles = new ArrayList<ExcommunicationTile>(3);
        randomTiles.addAll(excomTiles.subList(0,3));
        board.setExcommunicationTiles(randomTiles);

        return board;
    }

    /**
     * this loads an arrayList of tiles. The first element of the array list is the standard tile
     * @return an array list of tiles
     * @throws IOException in case file can't be opened
     */
    public static ArrayList<PersonalTile> loadPersonalTiles() throws IOException{
        GsonBuilder gsonBuilder = new GsonBuilder();

        RuntimeTypeAdapterFactory<ImmediateEffectInterface> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(ImmediateEffectInterface.class, "effectName");
        runtimeTypeAdapterFactory.registerSubtype(NoEffect.class, "NoEffect");
        runtimeTypeAdapterFactory.registerSubtype(GainResourceEffect.class, "GainResourceEffect");

        Gson gson = gsonBuilder.setPrettyPrinting().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();


        Reader reader = new InputStreamReader(PersonalTile.class.getResourceAsStream("/PersonalTiles.json"), "UTF-8");

            Type type = new TypeToken<ArrayList<PersonalTile>>(){}.getType();
            ArrayList<PersonalTile> inList = gson.fromJson(reader, type);
            /*for (PersonalTile i : inList) {
                Debug.printDebug(i.toString());
            }*/
            return inList;
    }

    public static ArrayList<ExcommunicationTile> loadExcommunicationTiles() throws IOException{
        GsonBuilder gsonBuilder = new GsonBuilder();
        ArrayList<ExcommunicationTile> excommunicationDeck;
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
        //3rd period card effects
        runtimeTypeAdapterFactory.registerSubtype(NoVPOnColoredCard.class, "NoVPOnColoredCard");
        runtimeTypeAdapterFactory.registerSubtype(NoVPOnResources.class, "NoVPOnResources");
        runtimeTypeAdapterFactory.registerSubtype(LoseVPonCostCards.class, "LoseVPonCostCards");

        Gson gson = gsonBuilder.setPrettyPrinting().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();


        Reader reader = new InputStreamReader(ExcommunicationTilesCreator.class.getResourceAsStream("/ExcommunicationTiles.json"), "UTF-8");
            Type type = new TypeToken<ArrayList<ExcommunicationTile>>(){}.getType();
            excommunicationDeck = gson.fromJson(reader, type);
            return excommunicationDeck;

    }

    public static LeadersDeck loadLeaders() throws IOException{
        Debug.instance(Debug.LEVEL_VERBOSE);
        GsonBuilder gsonBuilder = new GsonBuilder();

        RuntimeTypeAdapterFactory<AbstractRequirement> runtimeAdapterFactoryReq = RuntimeTypeAdapterFactory.of(AbstractRequirement.class, "reqType");
        runtimeAdapterFactoryReq.registerSubtype(CardRequirement.class, "CardRequirement");
        runtimeAdapterFactoryReq.registerSubtype(ResourceRequirement.class, "ResourceRequirement");

        RuntimeTypeAdapterFactory<AbstractLeaderAbility> runtimeAdapterFactoryAbility = RuntimeTypeAdapterFactory.of(AbstractLeaderAbility.class, "abilityType");
        runtimeAdapterFactoryAbility.registerSubtype(AbstractPermanentLeaderAbility.class, "AbstractPermanentLeaderAbility");
        runtimeAdapterFactoryAbility.registerSubtype(AbstractImmediateLeaderAbility.class, "AbstractImmediateLeaderAbility");


//        RuntimeTypeAdapterFactory<AbstractPermanentLeaderAbility> runtimeAdapterFactoryPermanentAbility = RuntimeTypeAdapterFactory.of(AbstractPermanentLeaderAbility.class, "abilityType");
        runtimeAdapterFactoryAbility.registerSubtype(CanPlaceFMInOccupiedASLeaderAbility.class, "CanPlaceFMInOccupiedASLeaderAbility");
        runtimeAdapterFactoryAbility.registerSubtype(NotToSpendForOccupiedTowerLeaderAbility.class, "NotToSpendForOccupiedTowerLeaderAbility");
        runtimeAdapterFactoryAbility.registerSubtype(BonusNeutralFMLeaderAbility.class, "BonusNeutralFMLeaderAbility");
        runtimeAdapterFactoryAbility.registerSubtype(FixedFamilyMembersValueLeaderAbility.class, "FixedFamilyMembersValueLeaderAbility");
        runtimeAdapterFactoryAbility.registerSubtype(BonusColoredFamilyMembersLeaderAbility.class, "BonusColoredFamilyMembersLeaderAbility");
        runtimeAdapterFactoryAbility.registerSubtype(AddMoreTimesResourcesOnImmediateCardEffectAbility.class, "AddMoreTimesResourcesOnImmediateCardEffectAbility");
        runtimeAdapterFactoryAbility.registerSubtype(NoMilitaryPointsNeededForTerritoryCardsLeaderAbility.class, "NoMilitaryPointsNeededForTerritoryCardsLeaderAbility");
        runtimeAdapterFactoryAbility.registerSubtype(GainFaithPointsSupportingChurchLeaderAbility.class, "GainFaithPointsSupportingChurchLeaderAbility");
        runtimeAdapterFactoryAbility.registerSubtype(DiscountOnCardCostLeaderAbility.class, "DiscountOnCardCostLeaderAbility");

        // RuntimeTypeAdapterFactory<AbstractImmediateLeaderAbility> runtimeAdapterFactoryImmediateAbility = RuntimeTypeAdapterFactory.of(AbstractImmediateLeaderAbility.class, "abilityType");
        runtimeAdapterFactoryAbility.registerSubtype(OncePerRoundResourceLeaderAbility.class, "OncePerRoundResourceLeaderAbility");
        runtimeAdapterFactoryAbility.registerSubtype(OncePerRoundBuildLeaderAbility.class, "OncePerRoundBuildLeaderAbility");
        runtimeAdapterFactoryAbility.registerSubtype(OncePerRoundHarvestLeaderAbility.class, "OncePerRoundHarvestLeaderAbility");
        runtimeAdapterFactoryAbility.registerSubtype(OncePerRoundCouncilGiftAbility.class, "OncePerRoundCouncilGiftAbility");
        runtimeAdapterFactoryAbility.registerSubtype(OncePerRoundBonusOneColoredFamilyMemberLeaderAbility.class, "OncePerRoundBonusOneColoredFamilyMemberLeaderAbility");
        runtimeAdapterFactoryAbility.registerSubtype(CopyAnotherLeaderAbility.class, "CopyAnotherLeaderAbility");

        Gson gson = gsonBuilder.setPrettyPrinting().registerTypeAdapterFactory(runtimeAdapterFactoryReq).registerTypeAdapterFactory(runtimeAdapterFactoryAbility).create();

        Reader reader = new InputStreamReader(LeadersDeck.class.getResourceAsStream("/LeadersCFG.json"), "UTF-8");

        return  gson.fromJson(reader, LeadersDeck.class);
    }

    public static RoomConfigurator loadTimeoutInSec() throws IOException
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        Reader reader = new InputStreamReader(RoomConfigurator.class.getResourceAsStream("/RoomCFG.json"), "UTF-8");
            RoomConfigurator roomConfigurator = gson.fromJson(reader, RoomConfigurator.class);
            return  roomConfigurator;
    }
}
