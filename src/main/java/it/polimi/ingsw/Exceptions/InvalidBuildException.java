package it.polimi.ingsw.Exceptions;

public class InvalidBuildException extends RuntimeException{
    public InvalidBuildException(String message){
        super(message);
    }
}
