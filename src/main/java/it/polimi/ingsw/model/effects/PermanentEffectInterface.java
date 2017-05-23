package it.polimi.ingsw.model.effects;

/**
 * Created by higla on 23/05/2017.
 */
public interface PermanentEffectInterface {
     abstract public int getBonusOnTower();
     abstract public int getBonusOnHarvest();
     abstract public int getBonusOnBuild();
     abstract public boolean isImmediateEffectDisabled();

}
