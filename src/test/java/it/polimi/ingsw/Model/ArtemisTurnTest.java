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
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        Turn turn = new ArtemisTurn(player);
        player.getPlayerMenu().replace("start", true);
        turn.start(worker);
        turn.move(0, 1);
        turn.move(1,1);
        turn.build(0,0);
        turn.end();
        assertEquals(board.getBox(1,1).getPawn(), worker);
        assertNull(board.getBox(0,1).getPawn());
        assertNull(board.getBox(0,0).getPawn());
        assertEquals(board.getBox(0,0).getBlock(), Block.LONE);
    }

    @Test
    public void artemisOneMove(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        Turn turn = new ArtemisTurn(player);
        player.getPlayerMenu().replace("start", true);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0,0);
        turn.end();
        assertEquals(board.getBox(0,1).getPawn(), worker);
        assertNull(board.getBox(0,0).getPawn());
        assertEquals(board.getBox(0,0).getBlock(), Block.LONE);
    }

    @Test(expected = RuntimeException.class)
    public void artemisMoveBuildMove(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        Turn turn = new ArtemisTurn(player);
        turn.start(worker);
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
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        Turn turn = new ArtemisTurn(player);
        player.getPlayerMenu().replace("start", true);
        turn.start(worker);
        turn.move(0, 1);
        turn.move(0,0);
        turn.build(0,0);
        turn.end();
    }

}