package it.polimi.ingsw.Exceptions;

/**
 * exception used when client want to move in a incorrect way
 */

public class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(String message){
        super(message);
    }
}
