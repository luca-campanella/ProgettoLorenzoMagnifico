package it.polimi.ingsw.model.player;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is the personal tile a player can choose at the start of the game
 */
public class PersonalTile implements Serializable{
    //for game balancing, we don't allow players to customize their own dice tile
    private static final int DICEONHARVEST = 1;
    private static final int DICEONBUILD = 1;

    /**
     * this arrayList is called every time a user builds with a diceValue of 1
     */
    private ArrayList<GainResourceEffect> effectOnBuild;
    /**
     * this arrayList is called every time a user harvests with a diceValue of 1
     */
    private ArrayList<GainResourceEffect> effectOnHarvest;

    public ArrayList<GainResourceEffect> getEffectOnBuild() {
        return effectOnBuild;
    }

    public void setEffectOnBuild(ArrayList<GainResourceEffect> effectOnBuild) {
        this.effectOnBuild = effectOnBuild;
    }

    public ArrayList<GainResourceEffect> getEffectOnHarvest() {
        return effectOnHarvest;
    }

    public void activateEffectsOnBuild(Player player, ChoicesHandlerInterface choicesController) {
        effectOnBuild.forEach(effect -> effect.applyToPlayer(player, choicesController));
    }

    public void setEffectOnHarvest(ArrayList<GainResourceEffect> effectOnHarvest) {
        this.effectOnHarvest = effectOnHarvest;
    }
}
