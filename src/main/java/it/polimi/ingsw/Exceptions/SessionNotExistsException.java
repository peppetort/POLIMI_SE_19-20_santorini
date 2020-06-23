package it.polimi.ingsw.Exceptions;


/**
 * exception used when client want to join a {@link it.polimi.ingsw.Server.Session}
 * which not exists
 */

public class SessionNotExistsException extends RuntimeException{
	public SessionNotExistsException(String message){
		super(message);
	}
}
