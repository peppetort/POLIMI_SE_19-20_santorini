package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Card;
import it.polimi.ingsw.Model.Player;

import java.util.ArrayList;

public class DeckChoose implements Message{

    Player player;
    ArrayList<Card> cards = new ArrayList<Card>();

    public DeckChoose(Player player,ArrayList<Card> cards) {
        this.player = player;
        this.cards = cards;
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
