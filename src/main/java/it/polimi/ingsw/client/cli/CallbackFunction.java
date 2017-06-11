package it.polimi.ingsw.client.cli;

/**
 * This is the functional interface used to make callback from view to controller
 * It is used in {@link BasicCLIMenu}
 */
@FunctionalInterface
public interface CallbackFunction {
    public void callback();
}
