package it.polimi.ingsw.client.cli.notblockingmenus;

import it.polimi.ingsw.client.controller.ViewControllerCallbackInterface;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.model.player.PersonalTile;

import java.util.List;

/**
 * this is the class used to choose the leader cards delivered by the server
 */
/*public class PersonalTilesPicketMenu extends BasicCLIMenu {

    public PersonalTilesPicketMenu(ViewControllerCallbackInterface controller, PersonalTile standardTile, PersonalTile specialTile) {
        super("Please select which tiles you want to use", controller);
        addOption("STANDARD", "Take the tile " + leaderIter.getName()
                           + " who has ability: " + leaderIter.getAbility().getAbilityDescription(),
                            () -> controller.callbackOnTileChosen(standardTile));
        addOption("SPECIAL", "Take the tile " + leaderIter.getName()
                        + " who has ability: " + leaderIter.getAbility().getAbilityDescription(),
                () -> controller.callbackOnTileChosen(specialTile));
        }
    }
}*/
