package it.polimi.ingsw.model.effects.immediateEffects;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;

/**
 * This method allows you to build without having a family member. example: 69 - Cardinal
 */
public class BuildNoFamilyMembersEffect extends AbstractPerformActionEffect {
        public BuildNoFamilyMembersEffect(int buildValue)
    {
        this.diceValue = buildValue;
    }

    /**
     * Builds with no family member asking hte user how many servants he wants to add
     * @param player
     * @param choicesHandlerInterface
     * @param cardName
     */
    @Override
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface,String cardName) {
        int servantsToAdd = choicesHandlerInterface.callbackOnAddingServants(cardName,0, player.getResource(ResourceTypeEnum.SERVANT));
        player.build(diceValue + servantsToAdd, choicesHandlerInterface);
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
