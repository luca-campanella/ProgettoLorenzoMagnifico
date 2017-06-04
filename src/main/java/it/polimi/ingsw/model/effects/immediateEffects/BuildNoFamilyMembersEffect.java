package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;

/**
 * This method allows you to build without having a family member. example: 69 - Cardinal
 */
public class BuildNoFamilyMembersEffect extends AbstractPerformActionEffect {
        public BuildNoFamilyMembersEffect(int buildValue)
    {
        this.diceValue = buildValue;
    }

    /**
     * todo: handling choices
     * @param player
     * @param choicesHandlerInterface
     */
    @Override
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface) {
        //todo: add choicesHandlerInterface.askNumberOfServants
        player.build(diceValue);
    }

    @Override
    public String descriptionOfEffect() {
        return "This method allows player to build";
    }
    @Override
    public String descriptionShortOfEffect() {
        return "Build";
    }
}
