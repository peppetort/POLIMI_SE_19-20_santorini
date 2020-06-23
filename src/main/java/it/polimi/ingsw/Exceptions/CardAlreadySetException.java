package it.polimi.ingsw.Exceptions;


/**
 * exception used when client want to reselect a card,
 * you can select a {@link it.polimi.ingsw.Model.God} only one time
 */

public class CardAlreadySetException extends RuntimeException {

    public CardAlreadySetException(String message){
        super(message);
    }
}

