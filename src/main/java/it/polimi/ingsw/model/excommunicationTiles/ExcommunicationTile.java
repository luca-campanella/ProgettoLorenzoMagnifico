package it.polimi.ingsw.model.excommunicationTiles;

/**
 * This card rappresents che excommunication tiles available
 */
public class ExcommunicationTile {
    int period;
    AbstractExcommunicationTileEffect effect;

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
}
