package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.utils.Debug;
import javafx.scene.layout.AnchorPane;

/**
 * Created by campus on 22/06/2017.
 */
public class CustomFxController extends AnchorPane {
    private ViewControllerCallbackInterface controller;


    public void setController(ViewControllerCallbackInterface controller) {
        Debug.printVerbose("Controller setted to " + controller.toString());
        this.controller = controller;
    }

    protected ViewControllerCallbackInterface getController() {
        return controller;
    }
}
