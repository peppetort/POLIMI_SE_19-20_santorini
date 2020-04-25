package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player;
import java.util.Set;

public class PlayerDeckMessage implements Message{

    Player player;
    Set<String> cards;

    public PlayerDeckMessage(Player player, Set<String> cards) {
        this.player = player;
        this.cards = cards;
    }

    public Set<String> getCards(){
        return cards;
    }


    public Player getPlayer() {
        return player;
    }
}
