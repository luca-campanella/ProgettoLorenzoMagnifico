package it.polimi.ingsw.model.excommunicationTiles;

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
}
