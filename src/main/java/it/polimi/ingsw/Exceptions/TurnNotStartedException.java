package it.polimi.ingsw.Exceptions;


/**
 * exception used when client want to play but it isn't
 * its own {@link it.polimi.ingsw.Model.Turn}
 */

public class TurnNotStartedException extends RuntimeException {
    public TurnNotStartedException(String message){
        super(message);
    }

}
