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
        assertNotSame(player.getWorker1(), player.getWorker2());
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

    @Test
    public void setCardPlayer1() {
        Board board = new Board();
        God god1 = God.ARTEMIS;
        God god2 = God.ATHENA;
        Game session = new Game("Pippo", "Pluto", board, false);
        Player player = session.getPlayers().get(0);
        Player player1 = session.getPlayers().get(1);
        player.setCard(god1);
        player1.setCard(god2);
        assertTrue(player.getBuildAction() instanceof DefaultBuild);
        assertTrue(player1.getBuildAction() instanceof DefaultBuild);

        assertTrue(player1.getMoveAction() instanceof DefaultMove);
        assertTrue(player.getMoveAction() instanceof DefaultMove);

        assertTrue(player1.getTurn() instanceof AthenaTurn);
        assertTrue(player.getTurn() instanceof ArtemisTurn);

        assertTrue(player.getWinAction() instanceof DefaultWin);
        assertTrue(player1.getWinAction() instanceof DefaultWin);
    }

    @Test
    public void setCardPlayer2() {
        Board board = new Board();
        God god1 = God.PROMETHEUS;
        God god2 = God.MINOTAUR;
        Game session = new Game("Pippo", "Pluto", board, false);
        Player player = session.getPlayers().get(0);
        Player player1 = session.getPlayers().get(1);
        player.setCard(god1);
        player1.setCard(god2);

        assertTrue(player.getBuildAction() instanceof DefaultBuild);
        assertTrue(player1.getBuildAction() instanceof DefaultBuild);

        assertTrue(player1.getMoveAction() instanceof MinotaurMove);
        assertTrue(player.getMoveAction() instanceof DefaultMove);

        assertTrue(player1.getTurn() instanceof DefaultTurn);
        assertTrue(player.getTurn() instanceof PrometheusTurn);

        assertTrue(player.getWinAction() instanceof DefaultWin);
        assertTrue(player1.getWinAction() instanceof DefaultWin);

    }

    @Test
    public void setCardPlayer3() {
        Board board = new Board();
        God god1 = God.HEPHAESTUS;
        God god2 = God.DEMETER;
        God god3 = God.ATLAS;
        Game session = new Game("Pippo", "Pluto","Pino", board, false);
        Player player = session.getPlayers().get(0);
        Player player1 = session.getPlayers().get(1);
        Player player2 = session.getPlayers().get(2);
        player.setCard(god1);
        player1.setCard(god2);
        player2.setCard(god3);
        assertTrue(player.getBuildAction() instanceof DefaultBuild);
        assertTrue(player1.getBuildAction() instanceof DefaultBuild);
        assertTrue(player2.getBuildAction() instanceof AtlasBuild);

        assertTrue(player.getMoveAction() instanceof DefaultMove);
        assertTrue(player1.getMoveAction() instanceof DefaultMove);
        assertTrue(player2.getMoveAction() instanceof DefaultMove);

        assertTrue(player.getTurn() instanceof HephaestusTurn);
        assertTrue(player1.getTurn() instanceof DemeterTurn);
        assertTrue(player2.getTurn() instanceof DefaultTurn);

        assertTrue(player.getWinAction() instanceof DefaultWin);
        assertTrue(player1.getWinAction() instanceof DefaultWin);
        assertTrue(player2.getWinAction() instanceof DefaultWin);
    }


}