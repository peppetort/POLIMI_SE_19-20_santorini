package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidBuildException;
import org.junit.Test;

import static org.junit.Assert.*;

public class HephaestusTurnTest {

    @Test
    public void hephaestusDoubleBuildSameBox(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        Turn turn = new HephaestusTurn(player);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0,0);
        turn.build(0,0);
        turn.end();
        assertEquals(board.getBox(0,1).getPawn(), worker);
        assertEquals(board.getBox(0,0).getBlock(), Block.LTWO);
    }

    @Test(expected = InvalidBuildException.class)
    public void hephaestusDoubleBuildDifferentBox(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        Turn turn = new HephaestusTurn(player);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0,0);
        turn.build(0,2);
        turn.end();
    }

    @Test(expected = RuntimeException.class)
    public void hephaestusDoubleBuildSameBoxLevelThree(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
        board.placePawn(worker, 0, 0);
        board.build(0,0,Block.LTWO);
        Turn turn = new HephaestusTurn(player);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0,0);
        turn.build(0,0);
        turn.end();
    }

    @Test
    public void hephaestusOneBuild(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
        Turn turn = new HephaestusTurn(player);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0,0);
        turn.end();
        assertEquals(board.getBox(0,1).getPawn(), worker);
        assertEquals(board.getBox(0,0).getBlock(), Block.LONE);
    }

}