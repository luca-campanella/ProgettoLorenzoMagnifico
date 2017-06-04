package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.effects.immediateEffects.GainSomethingEffect;

import java.util.ArrayList;

/**
 * This class is the personal tile a player can choose at the start of the game
 */
public class PersonalTile {
    //for game balancing, we don't allow players to customize their own dice tile
    final int DICEONHARVEST = 1;
    final int DICEONBUILD = 1;
    /**
     * this arrayList is called every time a user builds with a diceValue of 1
     */
    ArrayList<GainSomethingEffect> effectOnBuild;
    /**
     * this arrayList is called every time a user harvests with a diceValue of 1
     */
    ArrayList<GainSomethingEffect> effectOnHarvest;

    public ArrayList<GainSomethingEffect> getEffectOnBuild() {
        return effectOnBuild;
    }

    public void setEffectOnBuild(ArrayList<GainSomethingEffect> effectOnBuild) {
        this.effectOnBuild = effectOnBuild;
    }

    public ArrayList<GainSomethingEffect> getEffectOnHarvest() {
        return effectOnHarvest;
    }

    public void setEffectOnHarvest(ArrayList<GainSomethingEffect> effectOnHarvest) {
        this.effectOnHarvest = effectOnHarvest;
    }
}
