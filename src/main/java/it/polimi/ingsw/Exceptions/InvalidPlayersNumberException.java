package it.polimi.ingsw.Exceptions;


/**
 * exception used when client want to create a {@link it.polimi.ingsw.Server.Session}
 * with one or more than 3 players
 */

public class InvalidPlayersNumberException extends RuntimeException{
	public InvalidPlayersNumberException(String message){
		super(message);
	}
}
