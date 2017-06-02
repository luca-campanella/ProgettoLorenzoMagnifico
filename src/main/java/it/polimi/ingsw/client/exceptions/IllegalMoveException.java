package it.polimi.ingsw.client.exceptions;

import java.io.IOException;

/**
 * This exception is threw when the player tries to do an illegal move
 */
public class IllegalMoveException extends IOException {

    /**
     * the type of error on the move
     */
    private MoveErrorEnum moveErrorEnum;

    public IllegalMoveException(MoveErrorEnum moveErrorEnum){
        this.moveErrorEnum=moveErrorEnum;
    }

    public MoveErrorEnum getErrorType() {
        return moveErrorEnum;
    }
}
