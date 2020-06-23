package it.polimi.ingsw.Exceptions;


/**
 * exception used when client want to join a {@link it.polimi.ingsw.Server.Session}
 * which is full
 */


public class FullSessionException extends RuntimeException {

    public FullSessionException(String message){
        super(message);
    }
}
