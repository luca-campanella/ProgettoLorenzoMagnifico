package it.polimi.ingsw.model.cards;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.CliPrinter;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.NoEffect;
import it.polimi.ingsw.model.effects.immediateEffects.TakeOrPaySomethingEffect;
import it.polimi.ingsw.model.effects.permanentEffects.*;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.testingGSON.boardLoader.BoardCreator;
import it.polimi.ingsw.testingGSON.boardLoader.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.utils.Debug;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

/**
 * Created by higla on 24/05/2017.
 */
public class DeckCreator{
    public static void main(String[] args) throws Exception {
        Debug.instance(Debug.LEVEL_VERBOSE);
        GsonBuilder gsonBuilder = new GsonBuilder();
        /*gsonBuilder.registerTypeAdapter(TowerFloorAS.class, new TowerFloorDeserializer());
        gsonBuilder.registerTypeAdapter(MarketAS.class, new MarketASDeserializer());
        gsonBuilder.registerTypeAdapter(CouncilAS.class, new CouncilDeserializer());
        */
        //Gson gson = gsonBuilder.create();

        RuntimeTypeAdapterFactory<ImmediateEffectInterface> immediateEffectAdapter = RuntimeTypeAdapterFactory.of(ImmediateEffectInterface.class, "immediateEffect");
        immediateEffectAdapter.registerSubtype(NoEffect.class, "NoEffect");
        immediateEffectAdapter.registerSubtype(TakeOrPaySomethingEffect.class, "TakeOrPaySomethingEffect");

        RuntimeTypeAdapterFactory<AbstractPermanentEffect> permanentEffectAdapter = RuntimeTypeAdapterFactory.of(AbstractPermanentEffect.class, "permanentEffect");
        permanentEffectAdapter.registerSubtype(BonusOnHarvestEffect.class, "BonusOnHarvestEffect");
        permanentEffectAdapter.registerSubtype(BonusOnBuildEffect.class, "BonusOnBuildEffect");
        permanentEffectAdapter.registerSubtype(BonusOnTowerEffect.class, "BonusOnTowerEffect");
        permanentEffectAdapter.registerSubtype(MalusDisabledImmediateEffectsEffect.class, "MalusDisabledImmediateEffectsEffect");

        Gson gson = gsonBuilder.setPrettyPrinting().registerTypeAdapterFactory(immediateEffectAdapter).registerTypeAdapterFactory(permanentEffectAdapter).create();
        ///*
        Deck deckTest = getDeckForTest();

        String deckInJson = gson.toJson(deckTest);
        System.out.println(deckInJson);

        Deck deckFromJson = gson.fromJson(deckInJson, Deck.class);

        System.out.println(deckTest.toString());

        //*/
        /*
        CliPrinter printer = new CliPrinter();

        // The JSON data
        try (Reader reader = new InputStreamReader(BoardCreator.class.getResourceAsStream("/DeckCFG.json"), "UTF-8")) {
            Deck deck = gson.fromJson(reader, Deck.class);
            //CliPrinter printer = new CliPrinter();
            printer.printDeck(deck);

        }
        */
    }

    private static Deck getDeckForTest(){
        Deck deck = new Deck();
        /*ArrayList<TerritoryCard> territoryCards = new ArrayList<TerritoryCard>();
        territoryCards.add(getTerritoryCard());
        territoryCards.add(getTerritoryCard());
        deck.setTerritoryCards(territoryCards);
        */
        ArrayList<CharacterCard> characterCards = new ArrayList<CharacterCard>();
        characterCards.add(getCharacterCard());
        characterCards.add(getCharacterCard());
        deck.setCharacterCards(characterCards);
        /*
        ArrayList<BuildingCard> buildingCards = new ArrayList<BuildingCard>();
        buildingCards.add(getBuildingCard());
        buildingCards.add(getBuildingCard());
        deck.setBuildingCards(buildingCards);

        ArrayList<VentureCard> ventureCards = new ArrayList<VentureCard>();
        ventureCards.add(getVentureCard());
        ventureCards.add(getVentureCard());
        deck.setVentureCards(ventureCards);
        */
        return deck;
    }
    public static VentureCard getVentureCard(){
        VentureCard ventureCard = new VentureCard();
        ArrayList<TakeOrPaySomethingEffect> cost = new ArrayList<TakeOrPaySomethingEffect>();
        cost.add(getTakeOrPaySomethingEffect(5));
        ventureCard.setCostChoiceMilitary(cost);
        ventureCard.setCostChoiceResource(cost);
        ventureCard.setName("Viola");
        ventureCard.setImmediateEffect(getImmediateEffect());
        ventureCard.setVictoryEndPoints(5);
        return ventureCard;
    }
    public static CharacterCard getCharacterCard(){
        CharacterCard characterCard = new CharacterCard();
        ArrayList<TakeOrPaySomethingEffect> cost = new ArrayList<TakeOrPaySomethingEffect>();
        cost.add(getTakeOrPaySomethingEffect(6));
        characterCard.setCost(cost);
        characterCard.setName("Condottiero");
        characterCard.setImmediateEffect(getImmediateEffect());
        characterCard.setPermanentEffect(getPermanentEffect());
        return characterCard;
    }
    public static TerritoryCard getTerritoryCard()
    {
        TerritoryCard territoryCard = new TerritoryCard();
        territoryCard.setHarvestEffectValue(2);
        territoryCard.setName("Bosco");
        territoryCard.setImmediateEffect(getImmediateEffect());
        territoryCard.setEffectsOnHarvest(getImmediateEffect());
        return territoryCard;
    }

    public static BuildingCard getBuildingCard()
    {
        BuildingCard buildingCard = new BuildingCard();
        buildingCard.setBuildEffectValue(2);
        buildingCard.setName("Palazzo");
        buildingCard.setImmediateEffect(getImmediateEffect());
        buildingCard.setEffectsOnBuilding(getImmediateEffect());

        ArrayList<TakeOrPaySomethingEffect> cost = new ArrayList<TakeOrPaySomethingEffect>();
        cost.add(getTakeOrPaySomethingEffect(6));
        buildingCard.setCost(cost);
        return buildingCard;
    }
    private static TakeOrPaySomethingEffect getTakeOrPaySomethingEffect(int value){
        Resource resource = new Resource(ResourceType.COIN, value);
        TakeOrPaySomethingEffect effect = new TakeOrPaySomethingEffect(resource);
        return effect;
    }
    private static ArrayList<ImmediateEffectInterface> getImmediateEffect(){
        ImmediateEffectInterface effect = getTakeOrPaySomethingEffect(2);
        ImmediateEffectInterface effect2 = getTakeOrPaySomethingEffect(3);

        ArrayList<ImmediateEffectInterface> temp = new ArrayList<ImmediateEffectInterface>();
        temp.add(effect);
        temp.add(effect2);
        return temp;
    }
    private static ArrayList<AbstractPermanentEffect> getPermanentEffect(){
        Resource resource = new Resource(ResourceType.COIN, 1);
        CardColorEnum colorEnum = CardColorEnum.GREEN;
        AbstractPermanentEffect effect = new BonusOnBuildEffect(2);
        AbstractPermanentEffect effect2 = new BonusOnHarvestEffect(2);
        AbstractPermanentEffect effect3 = new MalusDisabledImmediateEffectsEffect();
        AbstractPermanentEffect effect4 = new BonusOnTowerEffect(colorEnum , resource, 2);

        ArrayList<AbstractPermanentEffect> temp = new ArrayList<AbstractPermanentEffect>();
        temp.add(effect);
        temp.add(effect2);
        temp.add(effect3);
        temp.add(effect4);

        return temp;
    }
}
