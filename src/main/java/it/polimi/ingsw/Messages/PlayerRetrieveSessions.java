package it.polimi.ingsw.Messages;

public class PlayerRetrieveSessions implements Message {
    //è un messaggio di "ping"
    String string;
    public PlayerRetrieveSessions(String string){
        this.string = string;
    }
}
