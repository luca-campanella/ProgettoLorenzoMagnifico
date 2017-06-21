package it.polimi.ingsw.client.cli.notblockingmenus;

import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.client.network.NetworkTypeEnum;

/**
 * This menu asks if he wants to connect with socket or with rmi
 */
public class NetworkTypePickerMenu extends BasicCLIMenu {

    public NetworkTypePickerMenu(ViewControllerCallbackInterface controller) {
        super("Do you want to use rmi or socket to connect to the server?", controller);

        addOption("rmi", "use Remote Method Invocation",
                () -> controller.callbackNetworkType(NetworkTypeEnum.RMI));
        addOption("socket", "use traditional Socket",
                () -> controller.callbackNetworkType(NetworkTypeEnum.SOCKET));
    }


}
