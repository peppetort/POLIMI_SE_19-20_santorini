package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.God;

import java.util.ArrayList;

/**
 * message from server to client used for
 * update the deck of the game
 *
 */
public class DeckUpdateMessage implements Message{
    ArrayList<God> deck;

    public DeckUpdateMessage(ArrayList<God> deck){
        this.deck = deck;
    }

    public ArrayList<God> getDeck(){
        return deck;
    }
}
