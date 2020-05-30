package it.polimi.ingsw.Model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {

    @Test(expected = RuntimeException.class)
    public void getCards() {
            Board board = new Board();
            God c1 = God.PAN;
            God c2 = God.APOLLO;
            God c3 = God.ARTEMIS;
            ArrayList<God> cards = new ArrayList<God>();
            ArrayList<God> cards1;
            Game game = new Game("Marco","Giuseppe",board,false);
            cards.add(c1);
            cards.add(c2);
            cards.add(c3);
            cards1 = game.getCards();
            assertEquals(God.PAN,cards1.get(0));
            assertEquals(God.APOLLO,cards1.get(1));
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

    @Test
    public void AddCardTest() {
        Board board = new Board();
        Game game = new Game("Marco","Giuseppe",board,false);
        ArrayList<God> god=new ArrayList<>();
        god.add(God.APOLLO);
        god.add(God.PAN);
        game.addCards(god);
        assertEquals(game.getCards(),god);
    }

    @Test (expected = RuntimeException.class)
    public void AddCardTestIsSimple() {
        Board board = new Board();
        Game game = new Game("Marco","Giuseppe",board,true);
        ArrayList<God> god=new ArrayList<>();
        god.add(God.APOLLO);
        god.add(God.PAN);
        game.addCards(god);
        assertEquals(game.getCards(),god);
    }

    @Test
    public void RemovePlayer() {
        Board board = new Board();
        Game game = new Game("Marco","Giuseppe",board,true);
        Player player= game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        board.placePawn(worker1, 0, 0);
        board.placePawn(worker2,4,4);
        game.removePlayer(player);
        assertEquals(board.getBox(0,0).getPawn(),null);
        assertEquals(board.getBox(4,4).getPawn(),null);
    }





}