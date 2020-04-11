package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player;

import java.util.ArrayList;

public class InitializePlayersMessage implements Message {
    ArrayList<Player> players = new ArrayList<Player>();
    public InitializePlayersMessage(ArrayList<Player> players){
        this.players = players;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
}
