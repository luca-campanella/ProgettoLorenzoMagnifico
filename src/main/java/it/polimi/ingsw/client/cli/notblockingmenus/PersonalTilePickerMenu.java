package it.polimi.ingsw.client.cli.notblockingmenus;

import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.model.player.PersonalTile;

/**
 * This menu asks the user to choose between the normal and the special personal tile
 */
public class PersonalTilePickerMenu extends BasicCLIMenu {

    public PersonalTilePickerMenu(ViewControllerCallbackInterface controller, PersonalTile standardTile, PersonalTile specialTile) {
        super("Please select between the special and the normal tile by typing the correspong number", controller);
        addOption("0", "Take normal tile: " + standardTile.getDescription(), () -> controller.callbackOnTileChosen(standardTile));
        addOption("1", "Take special tile: " + specialTile.getDescription(), () -> controller.callbackOnTileChosen(specialTile));
    }
}
