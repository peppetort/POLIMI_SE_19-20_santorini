package it.polimi.ingsw.Exceptions;

/**
 * exception used when client want to build in a incorrect way
 */

public class InvalidBuildException extends RuntimeException{
    public InvalidBuildException(String message){
        super(message);
    }
}
