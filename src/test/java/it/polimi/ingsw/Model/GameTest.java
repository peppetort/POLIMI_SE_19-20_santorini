package it.polimi.ingsw.Model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {

    @Test(expected = RuntimeException.class)
    public void getCards() {
            Board board = new Board();
            Card c1 = new Card(God.PAN);
            Card c2 = new Card(God.APOLLO);
            Card c3 = new Card(God.ARTEMIS);
            ArrayList<Card> cards = new ArrayList<Card>();
            ArrayList<Card> cards1;
            Game game = new Game("Marco","Giuseppe",board,false);
            cards.add(c1);
            cards.add(c2);
            cards.add(c3);
            cards1 = game.getCards();
            assertEquals(God.PAN,cards1.get(0).getName());
            assertEquals(God.APOLLO,cards1.get(1).getName());
            assertFalse(cards1.contains(c3));

    }
    @Test
    public void getPlayers() {
        Board board = new Board();
        Game game = new Game("Marco","Giuseppe",board,false);
        ArrayList<Player> players = new ArrayList<Player>();
        players = game.getPlayers();
        assertEquals("Marco",players.get(0).getUsername());
        assertEquals("Giuseppe",players.get(1).getUsername());

    }
    @Test
    public void isSimple() {
        Board board = new Board();
        Game game = new Game("Marco","Giuseppe",board,false);
        assertEquals(false,game.isSimple());
    }





}