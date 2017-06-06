package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;

/**
 * You skip first round.
 */
public class SkipRoundEffect extends AbstractExcommunicationTileEffect{
    private boolean skipRound;


    /**
     * this method signals that player has to skip the round.
     * @return
     */
    public boolean skipRound(){
        return skipRound;
    }

    public String getShortEffectDescription(){
        return "Skip first round";
    }
}
