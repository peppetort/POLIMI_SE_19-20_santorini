package it.polimi.ingsw.Exceptions;

public class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(String message){
        super(message);
    }
}
