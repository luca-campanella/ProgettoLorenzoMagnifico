package it.polimi.ingsw.client.cli.notblockingmenus;

import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.PersonalTile;

import java.util.List;

/**
 * this is the class used to choose the leader cards delivered by the server
 */
public class PersonalTilesPickerMenu extends BasicCLIMenu {

    public PersonalTilesPickerMenu(ViewControllerCallbackInterface controller, PersonalTile standardTile, PersonalTile specialTile) {
        super("Please select which tiles you want to use", controller);
        addOption("STANDARD", "Take the standard tile which has the bonuses: " + standardTile.getDescription(),
                            () -> controller.callbackOnTileChosen(standardTile));
        addOption("SPECIAL", "Take the special tile which has the bonuses: " + specialTile.getDescription(),
                () -> controller.callbackOnTileChosen(specialTile));
        }
    }

