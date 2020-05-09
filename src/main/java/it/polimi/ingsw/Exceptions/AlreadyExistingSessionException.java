package it.polimi.ingsw.Exceptions;

public class AlreadyExistingSessionException extends RuntimeException{
	public AlreadyExistingSessionException(String message){
		super(message);
	}
}
