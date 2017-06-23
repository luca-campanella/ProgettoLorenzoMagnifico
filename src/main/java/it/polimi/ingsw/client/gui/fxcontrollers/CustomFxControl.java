package it.polimi.ingsw.client.gui.fxcontrollers;

import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.utils.Debug;
import javafx.scene.layout.Pane;

/**
 * This custom fx control is the base for all controls class
 * It offers a method to set the {@link ViewControllerCallbackInterface} in order to let single controls make
 * callbacks on the controller ({@link it.polimi.ingsw.client.controller.ClientMain}
 */
public class CustomFxControl extends Pane {

    /**
     * the controller for callbacks
     */
    private ViewControllerCallbackInterface controller;

    /**
     * This method sets the controller for callbacks
     * @param controller the controller to set to
     */
    public void setController(ViewControllerCallbackInterface controller) {
        Debug.printVerbose("Controller setted to " + controller.toString());
        this.controller = controller;
    }

    protected ViewControllerCallbackInterface getController() {
        return controller;
    }
}
