package it.polimi.ingsw.Exceptions;

/**
 * exception used when the game is simple
 */

public class SimpleGameException extends RuntimeException {

    public SimpleGameException(String message){
        super(message);
    }
}
