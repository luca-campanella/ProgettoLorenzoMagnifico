package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.choices.ChoicesHandlerInterface;
import it.polimi.ingsw.model.board.CardColorEnum;
import it.polimi.ingsw.model.effects.immediateEffects.ImmediateEffectInterface;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resource.Resource;

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
        }
    }
}
