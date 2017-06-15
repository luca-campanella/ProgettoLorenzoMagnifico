package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.controller.ControllerCallbackInterface;
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

    public ActionSpacePickerMenu(ControllerCallbackInterface controller,
                                 Optional<Integer> servantsNeededHarvest,
                                 Optional<Integer> servantsNeededBuild,
                                 Optional<Integer> servantsNeededCouncil,
                                 List<MarketWrapper> activeMarketSpaces,
                                 List<TowerWrapper> activeTowerSpaces) {
        super("Please select where to place your family member, only available action spaces (regarding to your resources) are proposed.", controller);

        this.servantsNeededHarvest = servantsNeededHarvest;
        this.servantsNeededBuild = servantsNeededBuild;
        this.servantsNeededCouncil = servantsNeededCouncil;
        this.activeMarketSpaces = activeMarketSpaces;
        this.activeTowerSpaces = activeTowerSpaces;

        if(servantsNeededHarvest.isPresent())
            addOption("HARV", "Place on the harvest action space (" + servantsNeededHarvest.get() + " additional servants needed)", () -> controller.callbackPlacedFMOnHarvest());
        if(servantsNeededBuild.isPresent())
            addOption("BUILD", "Place on the build action space (" + servantsNeededBuild.get() + " additional servants needed)", () -> controller.callbackPlacedFMOnBuild());
        if(servantsNeededCouncil.isPresent())
            addOption("COUNC", "Place on the council action space (" + servantsNeededCouncil.get() + " additional servants needed)", () -> controller.callbackPlacedFMOnCouncil());
        if(!activeMarketSpaces.isEmpty())
            addOption("MARK", "Place on one of the market action spaces (you will be asked which)", () -> this.placeFMOnMarket());
        if(!activeTowerSpaces.isEmpty())
            addOption("TOWER", "Place on one of the towers action spaces (you will be asked which)", () -> this.placeFMOnTower());
    }

    private void placeFMOnMarket() {

        Debug.printVerbose("placeFMOnMarket");
        int indexRes = 0;
        if(activeMarketSpaces.size() == 1) {
            System.out.println("You can only place on space n " + activeMarketSpaces.get(0).getMarketIndex() + "with " + activeMarketSpaces.get(0).getServantNeeded() + "servants needed");
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

    private void placeFMOnTower() {

    }

}
