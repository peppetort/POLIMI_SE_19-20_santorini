package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Card;
import it.polimi.ingsw.Model.Player;

import java.util.ArrayList;

public class DeckChoice implements Message{

    Player player;
    ArrayList<Card> cards = new ArrayList<Card>();

    public DeckChoice(Player player,ArrayList<Card> cards) {
        this.player = player;
        this.cards = cards;
    }

    public ArrayList<Card> getCards(){
        return cards;
    }


    public Player getPlayer() {
        return player;
    }
}
