package it.polimi.ingsw.model.board;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.FamilyMember;

import java.io.Serializable;

/**
 * this class is the place on the market where the player can place the family member
 */
public class MarketAS extends AbstractActionSpace implements Serializable{

    public MarketAS() {
        super();
    }

    /**
     * This method performs the real action on the model when the player places a FM on a market place
     * @param familyMember the family member to perform the action with
     * @param choicesController needed because there can be some decisions tied to the action
     */
    //@Override
    public void performAction(FamilyMember familyMember, ChoicesHandlerInterface choicesController)
    {
        addFamilyMember(familyMember);
        playFMandSubServantsToPlayer(familyMember);
        getEffects().forEach(effect -> effect.applyToPlayer(familyMember.getPlayer(), choicesController, "MarketAS"));
    }

}
