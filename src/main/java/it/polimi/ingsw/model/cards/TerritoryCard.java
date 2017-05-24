package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

/**
 * Created by higla on 23/05/2017.
 */
public class TerritoryCard extends AbstractCard{
    /*
  this parameter indicates minimum dice's value to attivate card's build effect
   */
    private int harvestEffectValue;
    ArrayList<ImmediateEffectInterface> effectsOnHarvest;
    //TODO: implement method
    private void applyEffectToPlayer(Player player)
    {
        ;
    }

    public void setHarvestEffectValue(int value){ this.harvestEffectValue = value; }

    public int getHarvestEffectValue() {
        return harvestEffectValue;
    }

    public ArrayList<ImmediateEffectInterface> getEffectsOnHarvest() {
        return effectsOnHarvest;
    }

    public void setEffectsOnHarvest(ArrayList<ImmediateEffectInterface> effectsOnHarvest) {
        this.effectsOnHarvest = effectsOnHarvest;
    }
}
