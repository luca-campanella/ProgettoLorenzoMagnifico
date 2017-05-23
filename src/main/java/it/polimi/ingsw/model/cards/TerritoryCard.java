package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.player.Player;

/**
 * Created by higla on 23/05/2017.
 */
public class TerritoryCard extends AbstractCard{
    /*
  this parameter indicates minimum dice's value to attivate card's build effect
   */
    private int harvestEffectValue;
    //TODO: implement method
    private void applyEffectToPlayer(Player player)
    {
        ;
    }

    public void setHarvestEffectValue(int value){ this.harvestEffectValue = value; }
}
