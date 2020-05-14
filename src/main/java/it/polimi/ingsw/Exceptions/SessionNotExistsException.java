package it.polimi.ingsw.Exceptions;

public class SessionNotExistsException extends RuntimeException{
	public SessionNotExistsException(String message){
		super(message);
	}
}
