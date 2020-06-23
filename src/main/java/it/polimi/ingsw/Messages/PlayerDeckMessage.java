package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Model.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * message from client to server used to chose the {@link God}
 * that will be used in the game
 *
 */
public class PlayerDeckMessage implements Message{

    private Player player;
    private final Set<God> deck = new HashSet<>();

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

    public Set<God> getDeck() {
        return deck;
    }
}
