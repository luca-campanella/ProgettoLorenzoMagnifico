package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.effects.immediateEffects.TakeOrPaySomethingEffect;

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
    ArrayList<TakeOrPaySomethingEffect> effectOnBuild;
    /**
     * this arrayList is called every time a user harvests with a diceValue of 1
     */
    ArrayList<TakeOrPaySomethingEffect> effectOnHarvest;

    public ArrayList<TakeOrPaySomethingEffect> getEffectOnBuild() {
        return effectOnBuild;
    }

    public void setEffectOnBuild(ArrayList<TakeOrPaySomethingEffect> effectOnBuild) {
        this.effectOnBuild = effectOnBuild;
    }

    public ArrayList<TakeOrPaySomethingEffect> getEffectOnHarvest() {
        return effectOnHarvest;
    }

    public void setEffectOnHarvest(ArrayList<TakeOrPaySomethingEffect> effectOnHarvest) {
        this.effectOnHarvest = effectOnHarvest;
    }
}
