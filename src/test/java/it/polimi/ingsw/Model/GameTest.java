package it.polimi.ingsw.Model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void getCards() {
            Board board = new Board();
            Card c1 = new Card(God.PAN);
            Card c2 = new Card(God.APOLLO);
            ArrayList<Card> cards;
            Game game = new Game("Marco","Giuseppe",board,false);
            game.addCard(c1);
            game.addCard(c2);
            cards = game.getCards();
            assertEquals(God.PAN,cards.get(0).getName());
            assertEquals(God.APOLLO,cards.get(0).getName());
            assertFalse(cards.contains(new Card(God.ATHENA)));
    }
}