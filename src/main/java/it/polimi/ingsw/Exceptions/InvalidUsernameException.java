package it.polimi.ingsw.Exceptions;

/**
 * exception used when client want to join a {@link it.polimi.ingsw.Server.Session}
 * with the name equals to another {@link it.polimi.ingsw.Model.Player}
 */


public class InvalidUsernameException extends RuntimeException{
	public InvalidUsernameException(String message){
		super(message);
	}
}
