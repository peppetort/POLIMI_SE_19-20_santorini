package it.polimi.ingsw.Exceptions;

public class PlayerLostException extends RuntimeException {

    public PlayerLostException(String message){
        super(message);
    }
}
