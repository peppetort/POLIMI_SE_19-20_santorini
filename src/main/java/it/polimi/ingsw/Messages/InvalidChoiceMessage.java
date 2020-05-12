package it.polimi.ingsw.Messages;

public class InvalidChoiceMessage implements Message{
	private final String message;

	public InvalidChoiceMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return this.message;
	}
}
