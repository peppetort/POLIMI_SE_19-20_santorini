package it.polimi.ingsw.Messages;

/**
 * message from server to client used to communicate
 * a choice error for example an invalid move
 *
 */

public class InvalidChoiceMessage implements Message{
	private final String message;

	public InvalidChoiceMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return this.message;
	}
}
