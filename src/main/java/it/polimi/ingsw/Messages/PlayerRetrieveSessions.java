package it.polimi.ingsw.Messages;

public class PlayerRetrieveSessions implements Message {
    //è un messaggio di "ping"
    String string;
    //TODO: rivedere
    public PlayerRetrieveSessions(String string){
        this.string = string;
    }
}
