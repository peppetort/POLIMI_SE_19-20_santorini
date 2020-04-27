package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.God;

import java.util.ArrayList;

public class DeckUpdateMessage implements Message{
    ArrayList<God> deck;

    public DeckUpdateMessage(ArrayList<God> deck){
        this.deck = deck;
    }

    public ArrayList<God> getDeck(){
        return deck;
    }
}
