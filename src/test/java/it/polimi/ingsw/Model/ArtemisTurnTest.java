package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidMoveException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArtemisTurnTest {

    @Test
    public void artemisStandard(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        board.placePawn(worker1, 0, 0);
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
        Turn turn = new ArtemisTurn(player);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        turn.start(worker1);
        turn.move(0, 1);
        turn.move(1,1);
        turn.build(0,0);
        turn.end();
        assertEquals(board.getBox(1,1).getPawn(), worker1);
        assertNull(board.getBox(0,1).getPawn());
        assertNull(board.getBox(0,0).getPawn());
        assertEquals(board.getBox(0,0).getBlock(), Block.LONE);
    }

    @Test
    public void artemisOneMove(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        board.placePawn(worker1, 0, 0);
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
        Turn turn = new ArtemisTurn(player);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        turn.start(worker1);
        turn.move(0, 1);
        turn.build(0,0);
        turn.end();
        assertEquals(board.getBox(0,1).getPawn(), worker1);
        assertNull(board.getBox(0,0).getPawn());
        assertEquals(board.getBox(0,0).getBlock(), Block.LONE);
    }

    @Test(expected = RuntimeException.class)
    public void artemisMoveBuildMove(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        board.placePawn(worker1, 0, 0);
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
        Turn turn = new ArtemisTurn(player);
        turn.start(worker1);
        turn.move(0, 1);
        turn.build(0,0);
        turn.move(0,2);
        turn.end();
    }

    @Test(expected = InvalidMoveException.class)
    public void artemisMoveOnLastPos(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        board.placePawn(worker1, 0, 0);
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
        Turn turn = new ArtemisTurn(player);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        turn.start(worker1);
        turn.move(0, 1);
        turn.move(0,0);
        turn.build(0,0);
        turn.end();
    }

    @Test(expected = RuntimeException.class)
    public void artemisMoveTurnNotStarted1(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        board.placePawn(worker1, 0, 0);
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
        Turn turn = new ArtemisTurn(player);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        turn.move(0, 1);

    }

    @Test(expected = RuntimeException.class)
    public void artemisMoveTurnNotStarted2(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        board.placePawn(worker1, 0, 0);
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
        Turn turn = new ArtemisTurn(player);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        turn.end();
    }

    @Test(expected = RuntimeException.class)
    public void artemisEndYouHaveToMove(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        board.placePawn(worker1, 0, 0);
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
        Turn turn = new ArtemisTurn(player);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        turn.start(worker1);
        turn.end();
    }

    @Test(expected = RuntimeException.class)
    public void artemisEndYouHaveToBuild(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        board.placePawn(worker1, 0, 0);
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
        Turn turn = new ArtemisTurn(player);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        turn.start(worker1);
        turn.move(1,0);
        turn.end();
    }
}