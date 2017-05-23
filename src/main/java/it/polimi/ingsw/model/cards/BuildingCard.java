package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.effects.immediateEffects.TakeOrPaySomethingEffect;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;

/**
 * Created by higla on 23/05/2017.
 */
public class BuildingCard {
    private ArrayList<TakeOrPaySomethingEffect> cost;
    /*
    this parameter indicates minimum dice's value to attivate card's build effect
     */
    private int buildEffectValue;
    //apply to player deve avere anche il valore del dado.
    private void applyEffectsToPlayer(Player player){
        ;
    }
}
