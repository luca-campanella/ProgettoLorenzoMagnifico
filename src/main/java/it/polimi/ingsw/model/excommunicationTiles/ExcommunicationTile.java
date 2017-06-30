package it.polimi.ingsw.model.excommunicationTiles;

import java.io.Serializable;

/**
 * This card represents che excommunication tiles available
 */
public class ExcommunicationTile implements Serializable {

    int period;
    AbstractExcommunicationTileEffect effect;
    String imgName;

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public AbstractExcommunicationTileEffect getEffect() {
        return effect;
    }

    public void setEffect(AbstractExcommunicationTileEffect effect) {
        this.effect = effect;
    }

    public String getImgName() {
        return imgName;
    }
}
