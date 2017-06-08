package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.cards.AbstractCard;
import it.polimi.ingsw.model.cards.BuildingCard;
import it.polimi.ingsw.model.effects.immediateEffects.PayForSomethingEffect;
import it.polimi.ingsw.model.effects.immediateEffects.PayResourceEffect;
import it.polimi.ingsw.model.resource.Resource;

import java.util.ArrayList;

/**
 * This effects gives you less VP for each resource on a particular coloredCard
 */
public class LoseVPonCostCards extends AbstractExcommunicationTileEffect{
    //if this colored card has a cost, you take less vp for each resource it costs.
    private CardColorEnum coloredCard;

    public int loseVPonCosts(ArrayList<BuildingCard> cards){
        int numberOfVPLost = 0;
        //for each building cards i take the costs of that card
        for(BuildingCard costs : cards)
            //for each cost i take the resource effected
            for(PayResourceEffect cost : costs.getCost())
                //i sum all the resources values
                numberOfVPLost += cost.getCost().getValue();
        //then i return the number of VP lost.
        return numberOfVPLost;
    }
    public String getShortEffectDescription(){
        return "- VP on cost of color: "+ coloredCard.toString() + " cards ";
    }
}
