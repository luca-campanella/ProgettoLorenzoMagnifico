package it.polimi.ingsw.client.cli.notblockingmenus;

import it.polimi.ingsw.client.cli.CallbackFunction;
import it.polimi.ingsw.client.cli.StdinSingleton;
import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.utils.Debug;

import java.util.HashMap;

/**
 * This is the base menu class for all the menus of the cli when the user has choices
 * and the cli should call callbacks accordingly
 */
public abstract class BasicCLIMenu extends Thread {

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
        optionsMap.put(abbrev.toUpperCase(), new DescrCallbackContainer(callbackFunction, descr));
    }

    /**
     *
     */
    @Override
    public void run() {
        Debug.printVerbose("Process Started");

        showMenuAndAsk();
    }

    protected void showMenuAndAsk() {
        printMenu();

        String choice = StdinSingleton.nextLine();
        DescrCallbackContainer callbackContainer = optionsMap.get(choice.toUpperCase());

        while(callbackContainer == null) {
            System.out.println(choice + " is not a recognised option, please choose a correct one");
            choice = StdinSingleton.nextLine();
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
