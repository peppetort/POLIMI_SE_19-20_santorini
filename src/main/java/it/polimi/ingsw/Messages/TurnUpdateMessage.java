package it.polimi.ingsw.Messages;

public class TurnUpdateMessage implements Message{

    String username;

    public TurnUpdateMessage(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }
}
