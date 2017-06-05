package it.polimi.ingsw.model.leaders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.leaders.leadersabilities.*;
import it.polimi.ingsw.model.leaders.requirements.AbstractRequirement;
import it.polimi.ingsw.model.leaders.requirements.CardRequirement;
import it.polimi.ingsw.model.leaders.requirements.ResourceRequirement;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;
import it.polimi.ingsw.testingGSON.boardLoader.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * This is the class that reads Leader cards from file and generates them
 */
public class LeaderCreator {

    private static ArrayList<LeaderCard> creteLeadersForTesting() {
        ArrayList<LeaderCard> leaders = new ArrayList<LeaderCard>(20);


        leaders.add(new LeaderCard(createOneReqArray(new CardRequirement(5, CardColorEnum.PURPLE)), "Francesco Sforza",
                "E per dirlo ad un tratto non ci fu guerra famosa nell’Italia, che Francesco Sforza non vi si trovasse, e le Repubbliche, Prencipi, Re e Papi andavano a gara per haverlo al suo sevigio.", new OncePerRoundHarvestLeaderAbility(1)));

        leaders.add(new LeaderCard(createOneReqArray(new CardRequirement(5, CardColorEnum.BLUE)), "Ludovico Ariosto", "Io desidero intendere da voi Alessandro fratel, compar mio Bagno, S’in la Cort’è memoria " +
                "più di noi; Se più il Signor m’accusa; se compagno Per me si lieva.", new CanPlaceFMInOccupiedASLeaderAbility()));

        leaders.add(new LeaderCard(createOneReqArray(new CardRequirement(5, CardColorEnum.YELLOW)), "Filippo Brunelleschi",
                "[...] sparuto de la persona [...], ma di ingegno tanto elevato che ben si può dire che e’" +
                        "ci fu donato dal cielo per dar nuova forma alla architettura.", new NotToSpendForOccupiedTowerLeaderAbility()));

        leaders.add(new LeaderCard(createTwoReqArray(new ResourceRequirement(new Resource(ResourceTypeEnum.MILITARY_POINT, 7)),
                new ResourceRequirement(new Resource(ResourceTypeEnum.FAITH_POINT,3))),
                "Sigismondo Malatesta",
                "Era a campo la maistà del re de Ragona. [...] el fé levare de campo cum la soe gente e" +
        "cum lo altre di fiorentini, cum gram danno e poco onore del re.", new BonusNeutralFMLeaderAbility(3)));

        leaders.add(new LeaderCard(createOneReqArray(new ResourceRequirement(new Resource(ResourceTypeEnum.STONE, 10))),
                "Girolamo Savonarola",
                "Dai quali tutti Michelagnolo molto era accarezzato, et acceso al honorato suo studio, ma sopra" +
                        " tutti dal Magni co, il quale spesse volte il giorno lo faceva chiamare monstrandogli sue gioie [...].",
                new OncePerRoundBonusLeaderAbility(createOneResourceBonusArray(new Resource(ResourceTypeEnum.COIN, 3)))));

        leaders.add(new LeaderCard(createOneReqArray(new ResourceRequirement(new Resource(ResourceTypeEnum.MILITARY_POINT, 12))),
                "Giovanni dalle Bande Nere",
                "Egli apprezzava più gli huomini prodi che le ricchezze le quai desiderava per donar a " +
                        "loro.",
                new OncePerRoundBonusLeaderAbility(createThreeResourceBonusArray(
                        new Resource(ResourceTypeEnum.WOOD, 1),
                        new Resource(ResourceTypeEnum.STONE, 1),
                        new Resource(ResourceTypeEnum.COIN, 1)))));

        leaders.add(new LeaderCard(createTwoReqArray(new CardRequirement(4, CardColorEnum.BLUE), new CardRequirement(2, CardColorEnum.GREEN)),
                "Leonardo Da Vinci",
                "Ogniomo senpre si trova nel mezo del mondo en essotto il mezo del suo emisperio e sopra " +
                        "il cientro desso mondo.",
                new OncePerRoundProductionLeaderAbility(0)));

        leaders.add(new LeaderCard(createOneReqArray(new ResourceRequirement(new Resource(ResourceTypeEnum.WOOD, 10))),
                "Sandro Botticelli",
                "[...] ancora che agevolmente apprendesse tutto quello che e’ voleva, era nientedimanco " +
                        "inquieto sempre, né si contentava di scuola alcuna [...].",
                new OncePerRoundBonusLeaderAbility(createTwoResourceBonusArray(
                        new Resource(ResourceTypeEnum.MILITARY_POINT, 2),
                        new Resource(ResourceTypeEnum.VICTORY_POINT, 1)))));

        leaders.add(new LeaderCard(createFourReqArray(
                new CardRequirement(2, CardColorEnum.GREEN),
                new CardRequirement(2, CardColorEnum.YELLOW),
                new CardRequirement(2, CardColorEnum.BLUE),
                new CardRequirement(2, CardColorEnum.PURPLE)),
                "Ludovico il Moro",
                "Ludovicum Sfortiam Mediolanensium principem, cui Moro cognomen fuit, nequaquam a suscedine oris, quod esset aequo pallidior ita vocatum ferunt, quod pro insigni gestabat Mori arboris.",
                new FixedFamilyMembersValueLeaderAbility(5)));

        leaders.add(new LeaderCard(createOneReqArray(
                new CardRequirement(6, CardColorEnum.YELLOW)),
                "Lucrezia Borgia",
                "Donna Lucretia, benché avvezza homai a mutar mariti secondo il capriccio et interesse dei suoi, [...] si trattenne  n che il tempo unico medico di queste passioni le fece volger l’animo a più soavi pensieri.",
                new BonusColoredFamilyMembersLeaderAbility(2)));

        leaders.add(new LeaderCard(createOneReqArray(
                new CardRequirement(5, CardColorEnum.GREEN)),
                "Federico da Montefeltro",
            "[...] la gloriosa memoria del Duca Federico, il quale a dì suoi fu lume della Italia. Né quivi [Urbino] cosa alcuna volse, se non rarissima et eccellente.",
                new BonusOneColoredFamilyMemberLeaderAbility(6)));
        //todo lorenzo de' Medici
        //todo: permanent effect on Santa Rita
        leaders.add(new LeaderCard(createOneReqArray(
                new ResourceRequirement(new Resource(ResourceTypeEnum.FAITH_POINT, 8))),
                "Santa Rita",
                "Fu talmente abbracciata la santa astinenza, e l’aspro vestire dalla nostra Beata Rita,\n" +
                        "che chi la mirava, restava meravigliato, e quasi fuor di se stesso rimaneva.",
                new FixedFamilyMembersValueLeaderAbility(5)));

        leaders.add(new LeaderCard(createTwoReqArray(
                new CardRequirement(4, CardColorEnum.YELLOW),
                new CardRequirement(2, CardColorEnum.BLUE)),
                "Cosimo de' Medici",
                "Debebunt igitur Medici magno Cosmo omnis Medicea, et Florentina posteritas.",
                new OncePerRoundBonusLeaderAbility(createTwoResourceBonusArray(
                        new Resource(ResourceTypeEnum.SERVANT, 3),
                        new Resource(ResourceTypeEnum.VICTORY_POINT, 1)))));

        leaders.add(new LeaderCard(createTwoReqArray(
                new CardRequirement(2, CardColorEnum.PURPLE),
                new CardRequirement(4, CardColorEnum.GREEN)),
                "Bartolomeo Colleoni",
                "Et era allhor frequente per le bocche del volgo un sì fatto motto: «Havere il Coglione\n" +
                        "allo Sforza, il gioco di maniera in man concio, che non facendo ei torto alle carte più\n" +
                        "non potea perdere».",
                new OncePerRoundBonusLeaderAbility(createOneResourceBonusArray(
                        new Resource(ResourceTypeEnum.VICTORY_POINT, 4)))));
        //todo: council gift
        leaders.add(new LeaderCard(createOneReqArray(
                new ResourceRequirement(new Resource(ResourceTypeEnum.SERVANT, 15))),
                "Ludovico III Gonzaga",
                "[…] la qual cosa sopportava con sdegno Lodovico, parendogli che nota infame gli fosse\n" +
                        "l’essergli preposto dal padre il fratello, il quale veramente odiava.",
                new OncePerRoundBonusLeaderAbility(createOneResourceBonusArray(
                        new Resource(ResourceTypeEnum.VICTORY_POINT, 4)))));
        //todo: second effect, discount of 3 coins
        leaders.add(new LeaderCard(createTwoReqArray(
                new CardRequirement(4, CardColorEnum.PURPLE),
                new CardRequirement(2, CardColorEnum.YELLOW)),
                "Pico della Mirandola",
                "Ioannes Picus Mirandula merito cognomine phoenix appellatus est, quod in eum,\n" +
                        "Dii superi, supra familiae claritatem, omnis corporis, ac animi vel rarissima dona\n" +
                        "contulerint.",
                new OncePerRoundBonusLeaderAbility(createOneResourceBonusArray(
                        new Resource(ResourceTypeEnum.VICTORY_POINT, 4)))));



        return leaders;
    }

    private static ArrayList<AbstractRequirement> createOneReqArray(AbstractRequirement req) {
        ArrayList<AbstractRequirement> reqs = new ArrayList<AbstractRequirement>(1);
        reqs.add(req);

        return reqs;
    }

    private static ArrayList<AbstractRequirement> createTwoReqArray(AbstractRequirement req1, AbstractRequirement req2) {
        ArrayList<AbstractRequirement> reqs = new ArrayList<AbstractRequirement>(2);
        reqs.add(req1);
        reqs.add(req2);

        return reqs;
    }


    private static ArrayList<AbstractRequirement> createFourReqArray(AbstractRequirement req1, AbstractRequirement req2, AbstractRequirement req3, AbstractRequirement req4) {
        ArrayList<AbstractRequirement> reqs = new ArrayList<AbstractRequirement>(2);
        reqs.add(req1);
        reqs.add(req2);
        reqs.add(req3);
        reqs.add(req4);

        return reqs;
    }

    private static ArrayList<Resource> createOneResourceBonusArray(Resource res) {
        ArrayList<Resource> resArr = new ArrayList<Resource>(1);
        resArr.add(res);

        return resArr;
    }

    private static ArrayList<Resource> createTwoResourceBonusArray(Resource res1, Resource res2) {
        ArrayList<Resource> resArr = new ArrayList<Resource>(2);
        resArr.add(res1);
        resArr.add(res2);

        return resArr;
    }

    private static ArrayList<Resource> createThreeResourceBonusArray(Resource res1, Resource res2, Resource res3) {
        ArrayList<Resource> resArr = new ArrayList<Resource>(3);
        resArr.add(res1);
        resArr.add(res2);
        resArr.add(res3);

        return resArr;
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
        runtimeAdapterFactoryAbility.registerSubtype(BonusNeutralFMLeaderAbility.class, "BonusNeutralFMLeaderAbility");
        runtimeAdapterFactoryAbility.registerSubtype(OncePerRoundProductionLeaderAbility.class, "OncePerRoundProductionLeaderAbility");
        runtimeAdapterFactoryAbility.registerSubtype(FixedFamilyMembersValueLeaderAbility.class, "FixedFamilyMembersValueLeaderAbility");
        runtimeAdapterFactoryAbility.registerSubtype(BonusColoredFamilyMembersLeaderAbility.class, "BonusColoredFamilyMembersLeaderAbility");
        runtimeAdapterFactoryAbility.registerSubtype(BonusOneColoredFamilyMemberLeaderAbility.class, "BonusOneColoredFamilyMemberLeaderAbility");


        Gson gson = gsonBuilder.setPrettyPrinting().registerTypeAdapterFactory(runtimeAdapterFactoryReq).registerTypeAdapterFactory(runtimeAdapterFactoryAbility).create();

        LeadersDeck leadersDeck = new LeadersDeck(creteLeadersForTesting());
        String leadersInJson = gson.toJson(leadersDeck);
        System.out.println(leadersInJson);

        LeadersDeck leadersFormJson = gson.fromJson(leadersInJson, LeadersDeck.class);

        System.out.println(leadersFormJson.toString());

        //Prints the description of all leader fields with functional java
        leadersFormJson.getLeaders().forEach(leader -> System.out.println("**" + leader.getName() + "**" + "\n"
                + leader.getDescription() + "\n"
                + "Requirement: " + leader.getRequirements().stream().map(req -> req.getDescription()).collect(Collectors.joining()) + "\n"
                + "Ability: "+ leader.getAbility().getAbilityDescription() + "\n"));
    }

    public static void main(String args[]) {
        writeJsonExampleOnOutput();
    }
}
