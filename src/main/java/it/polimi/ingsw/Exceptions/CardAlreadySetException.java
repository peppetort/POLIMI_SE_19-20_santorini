package it.polimi.ingsw.Exceptions;

public class CardAlreadySetException extends RuntimeException {

    public CardAlreadySetException(String message){
        super(message);
    }
}

