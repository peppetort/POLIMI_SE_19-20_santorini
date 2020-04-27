package it.polimi.ingsw.Messages;

public class WinMessage implements Message{
    String username;

    public WinMessage(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

}
