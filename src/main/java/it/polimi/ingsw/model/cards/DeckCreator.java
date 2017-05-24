package it.polimi.ingsw.model.cards;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.CliPrinter;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.effects.immediateEffects.NoEffect;
import it.polimi.ingsw.model.effects.immediateEffects.TakeOrPaySomethingEffect;
import it.polimi.ingsw.model.effects.permanentEffects.AbstractPermanentEffect;
import it.polimi.ingsw.model.effects.permanentEffects.BonusOnBuildEffect;
import it.polimi.ingsw.model.effects.permanentEffects.BonusOnHarvestEffect;
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

        Gson gson = gsonBuilder.setPrettyPrinting().registerTypeAdapterFactory(immediateEffectAdapter).create();

        Deck deckTest = getDeckForTest();

        String deckInJson = gson.toJson(deckTest);
        System.out.println(deckInJson);

        Deck deckFromJson = gson.fromJson(deckInJson, Deck.class);

        System.out.println(deckTest.toString());

        //CliPrinter printer = new CliPrinter();
        /*
        // The JSON data
        try (Reader reader = new InputStreamReader(BoardCreator.class.getResourceAsStream("/DeckCFG.json"), "UTF-8")) {
            Deck deck = gson.fromJson(reader, Board.class);
            CliPrinter printer = new CliPrinter();
            printer.printDeck(deck);

        }*/
    }

    private static Deck getDeckForTest(){
        Deck deck = new Deck();
        ArrayList<TerritoryCard> territoryCards = new ArrayList<TerritoryCard>();
        territoryCards.add(getTerritoryCard());
        territoryCards.add(getTerritoryCard());
        deck.setTerritoryCards(territoryCards);
        return deck;
    }
    public static TerritoryCard getTerritoryCard()
    {
        TerritoryCard territoryCard = new TerritoryCard();
        territoryCard.setHarvestEffectValue(2);
        territoryCard.setName("Bosco");
        territoryCard.setImmediateEffect(getImmediateEffect());
        //territoryCard.setPermanentEffect(getPermanentEffect());

        return territoryCard;
    }
    private static ArrayList<ImmediateEffectInterface> getImmediateEffect(){
        Resource resource = new Resource(ResourceType.WOOD, 2);
        Resource resource2 = new Resource(ResourceType.WOOD, 3);

        ImmediateEffectInterface effect = new TakeOrPaySomethingEffect(resource);
        ImmediateEffectInterface effect2 = new TakeOrPaySomethingEffect(resource2);

        ArrayList<ImmediateEffectInterface> temp = new ArrayList<ImmediateEffectInterface>();
        temp.add(effect);
        temp.add(effect2);
        return temp;
    }
    private static ArrayList<AbstractPermanentEffect> getPermanentEffect(){
        AbstractPermanentEffect effect = new BonusOnBuildEffect(2);
        AbstractPermanentEffect effect2 = new BonusOnHarvestEffect(2);

        ArrayList<AbstractPermanentEffect> temp = new ArrayList<AbstractPermanentEffect>();
        temp.add(effect);
        temp.add(effect2);
        return temp;
    }
}
