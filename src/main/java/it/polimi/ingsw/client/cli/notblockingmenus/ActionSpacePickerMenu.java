package it.polimi.ingsw.client.cli.notblockingmenus;

import it.polimi.ingsw.client.cli.CliOptionsHandler;
import it.polimi.ingsw.client.cli.StdinSingleton;
import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.model.resource.MarketWrapper;
import it.polimi.ingsw.model.resource.TowerWrapper;
import it.polimi.ingsw.utils.Debug;

import java.util.List;
import java.util.Optional;

/**
 * Created by campus on 11/06/2017.
 */
public class ActionSpacePickerMenu extends BasicCLIMenu {

    Optional<Integer> servantsNeededHarvest;
    Optional<Integer> servantsNeededBuild;
    Optional<Integer> servantsNeededCouncil;
    List<MarketWrapper> activeMarketSpaces;
    List<TowerWrapper> activeTowerSpaces;
    int availableServants;

    public ActionSpacePickerMenu(ViewControllerCallbackInterface controller,
                                 Optional<Integer> servantsNeededHarvest,
                                 Optional<Integer> servantsNeededBuild,
                                 Optional<Integer> servantsNeededCouncil,
                                 List<MarketWrapper> activeMarketSpaces,
                                 List<TowerWrapper> activeTowerSpaces,
                                 int availableServants) {
        super("Please select where to place your family member, only available action spaces (regarding to your resources) are proposed.", controller);

        this.servantsNeededHarvest = servantsNeededHarvest;
        this.servantsNeededBuild = servantsNeededBuild;
        this.servantsNeededCouncil = servantsNeededCouncil;
        this.activeMarketSpaces = activeMarketSpaces;
        this.activeTowerSpaces = activeTowerSpaces;
        this.availableServants = availableServants;

        if(servantsNeededHarvest.isPresent())
            addOption("HARV", "Place on the harvest action space (at least" + servantsNeededHarvest.get()
                    + " additional servants needed)", this::placeFMOnHarvest);
        if(servantsNeededBuild.isPresent())
            addOption("BUILD", "Place on the build action space (at least" + servantsNeededBuild.get()
                    + " additional servants needed)", this::placeFMOnBuild);
        if(servantsNeededCouncil.isPresent())
            addOption("COUNC", "Place on the council action space (" + servantsNeededCouncil.get()
                    + " additional servants needed)", controller::callbackPlacedFMOnCouncil);
        if(!activeMarketSpaces.isEmpty())
            addOption("MARK", "Place on one of the market action spaces (you will be asked which)",
                    this::placeFMOnMarket);
        if(!activeTowerSpaces.isEmpty())
            addOption("TOWER", "Place on one of the towers action spaces (you will be asked which)",
                    this::placeFMOnTower);
    }

    private void placeFMOnMarket() {

        Debug.printVerbose("placeFMOnMarket");
        int indexRes;
        if(activeMarketSpaces.size() == 1) {
            System.out.println("You can only place on space n " + activeMarketSpaces.get(0).getMarketIndex() + "with " + activeMarketSpaces.get(0).getServantNeeded() + "servants needed");
            System.out.println("I'm placing it over there");
            indexRes = activeMarketSpaces.get(0).getMarketIndex();
        } else { //we have to ask the user for a choice
            CliOptionsHandler marketSpaceChooser = new CliOptionsHandler(activeMarketSpaces.size());

            for (MarketWrapper marketIter : activeMarketSpaces) {
                marketSpaceChooser.addOption("Place on market action space n " + marketIter.getMarketIndex() + "with " + marketIter.getServantNeeded() + "servants needed");
            }
            indexRes = activeMarketSpaces.get(marketSpaceChooser.askUserChoice()).getMarketIndex();
        }
        getController().callbackPlacedFMOnMarket(indexRes);

    }

    private void placeFMOnTower() {

        Debug.printVerbose("placeFMOnTower");
        int indexRes;
        if(activeTowerSpaces.size() == 1) {
            System.out.println("You can only place on space of tower number " + activeMarketSpaces.get(0).getMarketIndex() + "with " + activeMarketSpaces.get(0).getServantNeeded() + "servants needed");
            System.out.println("I'm placing it over there");
            indexRes = activeMarketSpaces.get(0).getMarketIndex();
        } else { //we have to ask the user for a choice
            CliOptionsHandler marketSpaceChooser = new CliOptionsHandler(activeMarketSpaces.size());

            for (MarketWrapper maketIter : activeMarketSpaces) {
                marketSpaceChooser.addOption("Place on market action space n " + maketIter.getMarketIndex() + "with " + maketIter.getServantNeeded() + "servants needed");
            }
            indexRes = activeMarketSpaces.get(marketSpaceChooser.askUserChoice()).getMarketIndex();
        }
        getController().callbackPlacedFMOnMarket(indexRes);
    }

    private void placeFMOnBuild() {
        int servantsAdded = 0;
        if(servantsNeededHarvest.isPresent())
             servantsAdded = readServants(servantsNeededBuild.get());
        getController().callbackPlacedFMOnBuild(servantsAdded);
    }

    private void placeFMOnHarvest() {
        int servantsAdded = 0;
        if(servantsNeededHarvest.isPresent())
            servantsAdded = readServants(servantsNeededHarvest.get());
        getController().callbackPlacedFMOnHarvest(servantsAdded);
    }

    private int readServants(int baseValue) {
        int addableServants = availableServants - baseValue;
        if(addableServants == 0) //if he can't add any servant we return immediately
            return 0;
        System.out.println("Choose how many servants you want to add to the base action:\n" +
                            "Base number of servants needed: " + baseValue + "\n" +
                            "You can add up to " + addableServants + " servants");
        int choice = readAndParseInt();
        while(choice < 0 || choice > addableServants) {
            System.out.println("Please insert a number between 0 and " + addableServants);
            choice = readAndParseInt();
        }

        return choice;
    }

    /**
     * reads a line from the console and tries to parse it as an integer, if it cannot returns -1
     * @return the integer read or -1 if error
     */
    private int readAndParseInt(){
        String line = StdinSingleton.nextLine();
        try{
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            Debug.printVerbose("Not entered a number");
            return -1;
        }
    }
}
