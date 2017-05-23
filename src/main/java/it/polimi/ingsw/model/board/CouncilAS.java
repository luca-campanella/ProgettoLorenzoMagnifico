package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;

/**
 * Created by higla on 20/05/2017.
 */
public class CouncilAS {
    ImmediateEffectInterface effect;

    public ImmediateEffectInterface getEffect() {
        return effect;
    }

    public void setEffect(ImmediateEffectInterface effect) {
        this.effect = effect;
    }
}
