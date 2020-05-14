package it.polimi.ingsw.Exceptions;

public class InvalidUsernameException extends RuntimeException{
	public InvalidUsernameException(String message){
		super(message);
	}
}
