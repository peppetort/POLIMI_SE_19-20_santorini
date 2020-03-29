package it.polimi.ingsw.Exceptions;

public class DuplicateCardException extends RuntimeException {

    public DuplicateCardException(String message){
        super(message);
    }
}
