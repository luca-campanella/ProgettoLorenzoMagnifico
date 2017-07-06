package it.polimi.ingsw.client.gui.blockingdialogs;

import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.leaders.LeaderCard;
import it.polimi.ingsw.utils.Debug;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * This class is used to handle Lorenzo Il magnifico effect.
 */
public class AskWhichLeaderAbilityToCopyDialog implements Callable<Integer> {
    //todo: make this dialog
    private ToggleGroup toggleGroup = new ToggleGroup();

    private List<LeaderCard> possibleLeaders;

    public AskWhichLeaderAbilityToCopyDialog(List<LeaderCard> possibleLeaders) {
        this.possibleLeaders = possibleLeaders;
    }

    @Override
    public Integer call() throws Exception {

        return 0;
    }
}
