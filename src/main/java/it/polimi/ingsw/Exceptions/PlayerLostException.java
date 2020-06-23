package it.polimi.ingsw.Exceptions;

/**
 * exception used when client want to make a move but he can't
 * because it lost
 */

public class PlayerLostException extends RuntimeException {

    public PlayerLostException(String message){
        super(message);
    }
}
