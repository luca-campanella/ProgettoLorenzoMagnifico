package it.polimi.ingsw.gamelogic.effects;

import it.polimi.ingsw.gamelogic.player.Player;

/**
 * Created by higla on 20/05/2017.
 */
public class GiveCouncilGiftEffect implements EffectInterface{
    public void applyToPlayer(Player player)
    {
        ;
    }
    public String descriptionOfEffect(){
        return "Gives a Council Gift to a player.";
    }
    public String descriptionShortOfEffect(){return "Gift";}
}
