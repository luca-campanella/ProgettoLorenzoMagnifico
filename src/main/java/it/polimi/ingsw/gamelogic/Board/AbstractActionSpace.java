package it.polimi.ingsw.gamelogic.Board;

import it.polimi.ingsw.gamelogic.Player.FamilyMember;

/**
 * Created by higla on 16/05/2017.
 */
public abstract class AbstractActionSpace {
    /**
     * this method lets you perform an action
     */
    abstract public void performAction(FamilyMember familyMember);
}
