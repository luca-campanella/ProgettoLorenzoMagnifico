package it.polimi.ingsw.client.cli.notblockingmenus;

import it.polimi.ingsw.client.cli.CallbackFunction;
import it.polimi.ingsw.client.cli.StdinSingleton;
import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.utils.Debug;

import java.util.HashMap;

/**
 * This is the base menu class for all the menus of the cli when the user has choices
 * and the cli should call callbacks accordingly
 * If run is not overridden this thread cannot (and should not) be interrupted
 */
public abstract class BasicCLIMenu extends Thread {

    private HashMap<String, DescrCallbackContainer> optionsMap;

    private String initialMenu;

    private ViewControllerCallbackInterface controller;

    public BasicCLIMenu(String initialMenu, ViewControllerCallbackInterface controller) {
        this.initialMenu = initialMenu;
        this.controller = controller;
        optionsMap = new HashMap<String, DescrCallbackContainer>();
        //this option will only be visible if the player was disconnected
        optionsMap.put("CONNECT", new DescrCallbackContainer(() -> this.connectPlayerAgain(),
                "reconnect me"));
        Debug.printDebug("BasicCLIMenu constructor");
    }

    public void addOption(String abbrev, String descr, CallbackFunction callbackFunction) {
        optionsMap.put(abbrev.toUpperCase(), new DescrCallbackContainer(callbackFunction, descr));
    }

    /**
     * Just shows the menu and waits for the input of the user
     */
    @Override
    public void run() {
        Debug.printVerbose("Process Started");

        showMenuAndAsk();
    }

    /**
     * Shows the menu and waits for the input.
     * If this method is running the thread cannot be interrupted
     */
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

    /**
     * Shows the menu and wait for the input.
     * If this method is running the thread <b>can</b> be interrupted via {@link Thread#interrupt()}
     */
    protected void showMenuAndAskNonBlocking() {
        Debug.printVerbose("showMenuAndAskNonBlocking started");
        printMenu();

        try {
            while(!Thread.currentThread().isInterrupted()) {
                String choice = StdinSingleton.nextLineNonBlocking();
                if (choice != null) {
                    DescrCallbackContainer callbackContainer = optionsMap.get(choice.toUpperCase());

                    if (callbackContainer == null) {
                        System.out.println(choice + " is not a recognised option, please choose a correct one");
                        continue;
                    }
                    Debug.printVerbose("before callback from showMenuAndAskNonBlocking");
                    callbackContainer.getFunction().callback();
                    Debug.printVerbose("after callback from showMenuAndAskNonBlocking");
                    Thread.currentThread().interrupt();
                    Debug.printVerbose("after sleep showMenuAndAskNonBlocking");
                }
                else {
                    Thread.sleep(100);
                }
            }
        } catch (Exception e) {
                Debug.printVerbose("THIS IS NOT AN ERROR Interrupting thread caused by IOex", e);
                Thread.currentThread().interrupt();
        }
        Debug.printVerbose("showMenuAndAskNonBlocking ended");
    }


    private void printMenu() {
        if(!controller.callbackObtainIsThisPlayerSuspended()) {
            System.out.println(initialMenu);
            optionsMap.forEach((abbrev, descrCallback) ->
            {
                if (!"CONNECT".equals(abbrev))
                    System.out.println(abbrev + " - " + descrCallback.getDescription());
            });
        } else {
            System.out.println("You are disconnected, write CONNECT to reconnect");
        }
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

    private void connectPlayerAgain() {
        if(!controller.callbackObtainIsThisPlayerSuspended()) {
            System.out.println("You are not disconnected, please choose a valid option");
            showMenuAndAsk();
        } else {
            System.out.println("You are being reconnected");
            controller.callbackConnectPlayerAgain();
        }
    }
}
