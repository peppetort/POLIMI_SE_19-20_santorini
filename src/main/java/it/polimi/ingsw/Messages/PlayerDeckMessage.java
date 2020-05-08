package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Model.Player;

import java.util.ArrayList;
import java.util.Set;

public class PlayerDeckMessage implements Message{

    private Player player;
    ArrayList<God> deck = new ArrayList<>();

    public PlayerDeckMessage(ArrayList<God> deck){
        for(God g: deck){
            this.deck.add(g);
        }
    }

    public PlayerDeckMessage(Player player,ArrayList<God> deck){
        this.player = player;
        for(God g: deck){
            this.deck.add(g);
        }
    }


    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<God> getDeck() {
        return deck;
    }
}
