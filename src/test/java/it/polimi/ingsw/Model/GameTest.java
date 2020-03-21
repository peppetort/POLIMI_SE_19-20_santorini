package it.polimi.ingsw.Model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {

    @Test(expected = RuntimeException.class)
    public void getCards() {
            Board board = new Board();
            Card c;
            Card c1 = new Card(God.PAN);
            Card c2 = new Card(God.APOLLO);
            Card c3 = new Card(God.ARTEMIS);
            ArrayList<Card> cards;
        ArrayList<Card> cards1;
            cards.add(c);
            Game game = new Game("Marco","Giuseppe",board,false);
            game.addCard(c1);
            game.addCard(c2);
            game.addCard(c2);
            game.addCard(c3);
            cards = game.getCards();
            assertEquals(God.PAN,cards.get(0).getName());
            assertEquals(God.APOLLO,cards.get(1).getName());
            assertFalse(cards.contains(c3));

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
    @Test
    public void getMatchID() {
        Board board = new Board();
        Game game = new Game("Marco","Giuseppe",board,false);
        long matchID = 1111111111;
        game.setMatchID(matchID);
        assertEquals(matchID,game.getMatchID());
    }




}