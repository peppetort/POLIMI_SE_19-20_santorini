package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Color;

public class LostMessage implements Message{
    String username;
    Color color;

    public LostMessage(String username, Color color){
        this.username = username;
        this.color = color;
    }

    public String getUsername(){
        return username;
    }

    public Color getColor(){
        return this.color;
    }
}
