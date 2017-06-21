package it.polimi.ingsw.model.leaders.leadersabilities.ImmediateLeaderAbility;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.player.DiceAndFamilyMemberColorEnum;
import it.polimi.ingsw.model.player.FamilyMember;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.utils.Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * This leader ability gives you a bonus on one of your colored family members
 * This ability is usually implemented by "Federico da Montefeltro"
 */
public class OncePerRoundBonusOneColoredFamilyMemberLeaderAbility extends AbstractImmediateLeaderAbility {

    private int bonusValue;

    public OncePerRoundBonusOneColoredFamilyMemberLeaderAbility(int bonusValue){
        super();
        this.bonusValue = bonusValue;
    }

    public int getBonusValue() {
        return bonusValue;
    }

    @Override
    public String getAbilityDescription() {
        return "One of your colored Family Members has a value of " + bonusValue + ", regardless of its related dice.";
    }

    @Override
    public void applyToPlayer(Player player, ChoicesHandlerInterface choicesHandlerInterface, String cardName) {
        //only coloured family members
        List<FamilyMember> availableColored = new ArrayList<FamilyMember>(4);
        availableColored.addAll(player.getNotUsedFamilyMembers());

        for(FamilyMember fmIter : availableColored) {
            if(fmIter.getColor() == DiceAndFamilyMemberColorEnum.NEUTRAL) {
                availableColored.remove(fmIter);
                break;
            }
        }

        DiceAndFamilyMemberColorEnum choiceColor;
        try {
            choiceColor = choicesHandlerInterface.callbackOnFamilyMemberBonus(cardName + ":fm", availableColored);
        } catch (IllegalArgumentException e) {
            Debug.printVerbose(cardName + " - Effect applied when no family member available, not applying anything");
            return;
        }
        //we set the family member chosen from the user at his corresponding value
        player.getFamilyMemberByColor(choiceColor).setValueFamilyMember(bonusValue);
    }
}