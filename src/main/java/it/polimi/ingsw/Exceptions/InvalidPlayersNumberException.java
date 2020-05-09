package it.polimi.ingsw.Exceptions;

public class InvalidPlayersNumberException extends RuntimeException{
	public InvalidPlayersNumberException(String message){
		super(message);
	}
}
