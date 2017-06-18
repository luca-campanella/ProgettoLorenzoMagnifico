package it.polimi.ingsw.client.cli.notblockingmenus;

import it.polimi.ingsw.client.cli.CallbackFunction;
import it.polimi.ingsw.client.cli.StdinSingleton;
import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.utils.Debug;

import java.util.HashMap;

/**
 * Created by campus on 11/06/2017.
 */
public abstract class BasicCLIMenu implements Runnable {

    private HashMap<String, DescrCallbackContainer> optionsMap;

    private String initialMenu;

    private ViewControllerCallbackInterface controller;

    public BasicCLIMenu(String initialMenu, ViewControllerCallbackInterface controller) {
        this.initialMenu = initialMenu;
        this.controller = controller;
        optionsMap = new HashMap<String, DescrCallbackContainer>();
        Debug.printDebug("BasicCLIMenu constructor");
    }

    public void addOption(String abbrev, String descr, CallbackFunction callbackFunction) {
        optionsMap.put(abbrev, new DescrCallbackContainer(callbackFunction, descr));
    }

    /**
     *
     */
    @Override
    public void run() {
        Debug.printVerbose("Process Started");

        printMenu();

        String choice = StdinSingleton.getScanner().nextLine();
        DescrCallbackContainer callbackContainer = optionsMap.get(choice.toUpperCase());

        while(callbackContainer == null) {
            System.out.println(choice + " is not a recognised option, please choose a correct one");
            choice = StdinSingleton.getScanner().nextLine();
            callbackContainer = optionsMap.get(choice.toUpperCase());
        }
        callbackContainer.getFunction().callback();
    }

    private void printMenu() {
        System.out.println(initialMenu);
        optionsMap.forEach((abbrev, descrCallback) -> System.out.println(abbrev + " - " + descrCallback.getDescription()));
    }

    private class DescrCallbackContainer {
        CallbackFunction function;
        String description;

        public DescrCallbackContainer(CallbackFunction function, String description) {
            this.function = function;
            this.description = description;
        }

        public CallbackFunction getFunction() {
            return function;
        }

        public String getDescription() {
            return description;
        }
    }

    protected ViewControllerCallbackInterface getController() {
        return controller;
    }
}
