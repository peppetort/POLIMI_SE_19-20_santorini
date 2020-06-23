package it.polimi.ingsw.Exceptions;

/**
 * exception used when client want to create a {@link it.polimi.ingsw.Server.Session}
 * with the same name of an other one
 */

public class AlreadyExistingSessionException extends RuntimeException{
	public AlreadyExistingSessionException(String message){
		super(message);
	}
}
