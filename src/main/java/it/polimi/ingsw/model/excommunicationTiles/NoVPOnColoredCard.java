package it.polimi.ingsw.model.excommunicationTiles;

import it.polimi.ingsw.model.board.CardColorEnum;

/**
 * This malus doesn't allow you to take VP for a certain color.
 * So it will just recalculate if it needs to sub resources and if he does, he will.
 * */
public class NoVPOnColoredCard extends AbstractExcommunicationTileEffect{
    private CardColorEnum colorExcomunnication;

    /**
     * this method returns true if you don't score any point for a certain
     * @param color ed card
     * @return s true if you don't get VP. False if you get VP
     */
    public boolean noVPColoredCard(CardColorEnum color)
    {
        if(colorExcomunnication == color)
            return true;
        return false;
    }
    public String getShortEffectDescription(){
        return "You can't take "+ colorExcomunnication.toString() + "-cards bonus at the end of the game";
    }

}
