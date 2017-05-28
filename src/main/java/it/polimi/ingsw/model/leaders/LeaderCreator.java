package it.polimi.ingsw.model.leaders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.leaders.leadersabilities.*;
import it.polimi.ingsw.model.leaders.requirements.AbstractRequirement;
import it.polimi.ingsw.model.leaders.requirements.CardRequirement;
import it.polimi.ingsw.model.leaders.requirements.ResourceRequirement;
import it.polimi.ingsw.testingGSON.boardLoader.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * This is the class that reads Leader cards from file and generates them
 */
public class LeaderCreator {

    private static ArrayList<LeaderCard> creteLeadersForTesting() {
        ArrayList<LeaderCard> leaders = new ArrayList<LeaderCard>(2);

        ArrayList<AbstractRequirement> req0 = new ArrayList<AbstractRequirement>(1);
        req0.add(new CardRequirement(5, CardColorEnum.PURPLE));

        leaders.add(new LeaderCard(req0, "Francesco Sforza",
                "E per dirlo ad un tratto non ci fu guerra famosa nell’Italia, che Francesco Sforza non vi si trovasse, e le Repubbliche, Prencipi, Re e Papi andavano a gara per haverlo al suo sevigio.", new OncePerRoundHarvestLeaderAbility(1)));

        ArrayList<AbstractRequirement> req1 = new ArrayList<AbstractRequirement>(1);
        req1.add( new CardRequirement(5, CardColorEnum.BLUE));

        leaders.add(new LeaderCard(req1, "Ludovico Ariosto", "Io desidero intendere da voi Alessandro fratel, compar mio Bagno, S’in la Cort’è memoria\n" +
                "più di noi; Se più il Signor m’accusa; se compagno Per me si lieva.", new CanPlaceFMInOccupiedASLeaderAbility()));

        return leaders;
    }

    private static void writeJsonExampleOnOutput() {
        Debug.instance(Debug.LEVEL_VERBOSE);
        GsonBuilder gsonBuilder = new GsonBuilder();

        RuntimeTypeAdapterFactory<AbstractRequirement> runtimeAdapterFactoryReq = RuntimeTypeAdapterFactory.of(AbstractRequirement.class, "reqType");
        runtimeAdapterFactoryReq.registerSubtype(CardRequirement.class, "CardRequirement");
        runtimeAdapterFactoryReq.registerSubtype(ResourceRequirement.class, "ResourceRequirement");

        RuntimeTypeAdapterFactory<AbstractLeaderAbility> runtimeAdapterFactoryAbility = RuntimeTypeAdapterFactory.of(AbstractLeaderAbility.class, "abilityType");
        runtimeAdapterFactoryAbility.registerSubtype(CanPlaceFMInOccupiedASLeaderAbility.class, "CanPlaceFMInOccupiedASLeaderAbility");
        runtimeAdapterFactoryAbility.registerSubtype(NotToSpendForOccupiedTowerLeaderAbility.class, "NotToSpendForOccupiedTowerLeaderAbility");
        runtimeAdapterFactoryAbility.registerSubtype(OncePerRoundHarvestLeaderAbility.class, "OncePerRoundHarvestLeaderAbility");
        runtimeAdapterFactoryAbility.registerSubtype(OncePerRoundBonusLeaderAbility.class, "OncePerRoundBonusLeaderAbility");

        Gson gson = gsonBuilder.setPrettyPrinting().registerTypeAdapterFactory(runtimeAdapterFactoryReq).registerTypeAdapterFactory(runtimeAdapterFactoryAbility).create();

        LeadersDeck leadersDeck = new LeadersDeck(creteLeadersForTesting());
        String leadersInJson = gson.toJson(leadersDeck);
        System.out.println(leadersInJson);

        LeadersDeck leadersFormJson = gson.fromJson(leadersInJson, LeadersDeck.class);

        System.out.println(leadersFormJson.toString());

        leadersFormJson.getLeaders().forEach(leader -> System.out.println("**" + leader.getName() + "**" + "\n"
                + leader.getDescription() + "\n"
                + "Requirement: " + leader.getRequirements().stream().map(req -> req.getDescription()).collect(Collectors.joining()) + "\n"
                + "Ability: "+ leader.getAbility().getAbilityDescription() + "\n"));
    }

    public static void main(String args[]) {
        writeJsonExampleOnOutput();
    }
}
