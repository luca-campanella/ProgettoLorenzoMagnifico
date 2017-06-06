package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.resource.Resource;

/**
 * Each time you spend m resource, you spend n less resources.
 * By default, we assume real value spent is one, and it will be handled by the controller
 */
public class PayMoreServantsEffect extends AbstractExcommunicationTileEffect{
    //example: 1 servant. You will need to pay 2 more servants to have 1 dice value
    //so it's resourceSpending + default to have default
    private int moreServants;


    /**
     * this method indicates how many more servants a player has to pay to have +1 on action value
     * @return
     */
    public int payMoreServant()
    {
        return moreServants;
    }

    public String getShortEffectDescription(){
        return "+"+ moreServants + " For +1 on dice";
    }
}
