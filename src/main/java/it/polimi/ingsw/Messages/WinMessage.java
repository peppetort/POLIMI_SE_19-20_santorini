package it.polimi.ingsw.Messages;


/**
 * message from server to client used to communicate
 * that the client won
 *
 */

public class WinMessage implements Message{
    String username;

    public WinMessage(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

}
