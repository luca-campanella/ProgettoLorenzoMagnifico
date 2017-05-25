package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.model.player.Player;

/**
 * Created by higla on 25/05/2017.
 */
public class CouncilGiftEffect implements ImmediateEffectInterface {

    public void applyToPlayer(Player player)
    {
        ;
    }
    @Override
    public String descriptionOfEffect() {
        return "Questo effetto consegna un dono del consiglio ad un giocatore.";
    }
    @Override
    public String descriptionShortOfEffect() {
        return "CoGi";
    }
}
