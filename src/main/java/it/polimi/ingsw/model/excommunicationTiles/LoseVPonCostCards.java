package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.board.CardColorEnum;

/**
 * This effects gives you less VP for each resource on a particular coloredCard
 */
public class LoseVPonCostCards extends AbstractExcommunicationTileEffect{
    //if this colored card has a cost, you take less vp for each resource it costs.
    private CardColorEnum coloredCard;
}
