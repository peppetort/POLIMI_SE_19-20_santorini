package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void getUsername(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, false);
        Player player1 = session.getPlayers().get(0);
        Player player2 = session.getPlayers().get(1);
        assertEquals("Pippo", player1.getUsername());
        assertEquals("Pluto", player2.getUsername());
    }

    @Test
    public void getWorker1(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, false);
        Player player = session.getPlayers().get(0);
        assertEquals("Pippo1", player.getWorker1().getId());
    }

    @Test
    public void getWorker2(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, false);
        Player player = session.getPlayers().get(0);
        assertEquals("Pippo2", player.getWorker2().getId());
    }

    @Test
    public void worker1DifferentFromWorker2(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, false);
        Player player = session.getPlayers().get(0);
        assertNotEquals(player.getWorker1(), player.getWorker2());
    }

    @Test
    public void setCardSimpleGameTrue() {
        Board board = new Board();
        God pan =God.PAN;
        Game session = new Game("Pippo", "Pluto", board, false);
        Player player = session.getPlayers().get(0);
        player.setCard(pan);
        assertEquals(God.PAN, player.getCard());
    }

    @Test
    public void getSession(){
        Board board = new Board();
        God pan =God.PAN;
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player1 = session.getPlayers().get(0);
        Player player2 = session.getPlayers().get(1);
        assertEquals(player1.getSession(), session);
        assertEquals(player2.getSession(), session);
    }

    @Test(expected = RuntimeException.class)
    public void setCardSimpleGameFalse() {
        Board board = new Board();
        God pan = God.PAN;
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        player.setCard(pan);
    }

    @Test(expected = RuntimeException.class)
    public void setCardPlayerCardNotNull() {
        Board board = new Board();
        God pan = God.PAN;
        God Atlas = God.ATLAS;
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        player.setCard(pan);
        player.setCard(Atlas);
    }

    @Test(expected = RuntimeException.class)
    public void setCardUnexpectedCase() {
        Board board = new Board();
        God pan = null;
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        player.setCard(pan);
    }
}