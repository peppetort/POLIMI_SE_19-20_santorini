package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Color;

import java.util.ArrayList;

public class ClientInitMessage implements Message{

    private final String username;
    private final ArrayList<Color> players;

    public ClientInitMessage(String username, ArrayList<Color> players){
        this.username = username;
        this.players = players;
    }

    public String getUsername(){
        return username;
    }

    public ArrayList<Color> getPlayers(){
        return players;
    }
}
