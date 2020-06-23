package it.polimi.ingsw.Exceptions;

/**
 * exception used when client want to go up but you can't because
 * your opponent used the {@link it.polimi.ingsw.Model.God} equals to Athena
 */

public class CantGoUpException extends RuntimeException{
    public CantGoUpException(String message){
        super(message);
    }
}
