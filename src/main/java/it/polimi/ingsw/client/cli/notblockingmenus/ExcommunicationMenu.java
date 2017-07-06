package it.polimi.ingsw.client.cli.notblockingmenus;

import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;

/**
 * this class is used to ask the player if he wants to be excommunicated or if he wants toavoi the excommunication
 */
public class ExcommunicationMenu extends BasicCLIMenu{

    public ExcommunicationMenu(ViewControllerCallbackInterface controller, int numTile){
        super("Do you want to avoid to be excommunicated:", controller);
        addOption("YES","you will lose all your faith points, but you will not be excommunicated", () -> controller.callbackExcommunicationChoice("YES", numTile));
        addOption("NO","you will not lose all your faith points, but you will be excommunicated", () -> controller.callbackExcommunicationChoice("NO", numTile));
    }
}
