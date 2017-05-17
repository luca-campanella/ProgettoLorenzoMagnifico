package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.packet.ErrorType;

import java.io.IOException;

/**
 * exception throwed when the player tries to do an illegal move
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
