package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.God;

/**
 * message from server to client used to update the client turn
 *
 */

public class TurnUpdateMessage implements Message{

    String username;
    Color color;
    God god;

    public TurnUpdateMessage(String username, Color color){
        this.username = username;
        this.color = color;
        this.god = null;
    }

    public TurnUpdateMessage(String username, Color color, God god){
        this.username = username;
        this.color = color;
        this.god = god;
    }

    public String getUsername(){
        return username;
    }

    public Color getColor(){
        return color;
    }

    public God getGod(){
        return this.god;
    }
}
