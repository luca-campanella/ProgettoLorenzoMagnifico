package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.effects.immediateEffects.GainResourceEffect;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.model.resource.ResourceCollector;
import it.polimi.ingsw.model.resource.ResourceTypeEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract card class.
 */
public abstract class AbstractCard implements Serializable{
    private String name;
    private int period;
    ArrayList<ImmediateEffectInterface> immediateEffect;
    private CardColorEnum cardColor;

    public abstract ArrayList<Resource> getCost();

    public abstract List<Resource> getCostAskChoice(ChoicesHandlerInterface choicesController);

    public abstract String secondEffect();

    //abstract public int characteristicValue();
    public void addImmediateEffect(ImmediateEffectInterface effect){
        immediateEffect.add(effect);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public ArrayList<ImmediateEffectInterface> getImmediateEffect() {
        return immediateEffect;
    }

    public void setImmediateEffect(ArrayList<ImmediateEffectInterface> immediateEffect) {
        this.immediateEffect = immediateEffect;
    }

    public void setCardColor(CardColorEnum cardColor) {
        this.cardColor = cardColor;
    }

    public CardColorEnum getColor() {
        return cardColor;
    }

    public void applyImmediateEffectsToPlayer(Player player, ChoicesHandlerInterface choiceController) {
        for(ImmediateEffectInterface effectIter : immediateEffect) {
            effectIter.applyToPlayer(player, choiceController, name);

            //we need to check if a leader says the immediate effect has to be applied more than once
            int multipleTimes = player.getPermanentLeaderCardCollector().getMoreTimesResourcesOnImmediateEffects(cardColor);
            if(multipleTimes > 0) {
                if (effectIter instanceof GainResourceEffect) {
                    ResourceTypeEnum resourceType = ((GainResourceEffect) effectIter).getResource().getType();
                    if ((resourceType == ResourceTypeEnum.WOOD) ||
                            (resourceType == ResourceTypeEnum.STONE) || (resourceType == ResourceTypeEnum.COIN)
                            || (resourceType == ResourceTypeEnum.SERVANT)) {
                        for(int i = 0; i < multipleTimes; i++)
                            effectIter.applyToPlayer(player, choiceController, name+i);
                    }
                }
            }
        }

    }

    /**
     * this method is used by the cad to understand if the player can buy this card with the following resources
     * @param resource are the resources of the player
     * @return is true if you can buy the card, false otherwise
     */
    public abstract boolean canBuy(ResourceCollector resource);
}
