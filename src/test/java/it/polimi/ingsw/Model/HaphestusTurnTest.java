package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidBuildException;
import org.junit.Test;

import static org.junit.Assert.*;

public class HaphestusTurnTest {

    @Test
    public void haphestusDoubleBuildSameBox(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        player.getPlayerMenu().replace("start", true);
        Turn turn = new HaphestusTurn(player);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0,0);
        turn.build(0,0);
        turn.end();
        assertEquals(board.getBox(0,1).getPawn(), worker);
        assertEquals(board.getBox(0,0).getBlock(), Block.LTWO);
    }

    @Test(expected = InvalidBuildException.class)
    public void haphestusDoubleBuildDifferentBox(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        player.getPlayerMenu().replace("start", true);
        Turn turn = new HaphestusTurn(player);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0,0);
        turn.build(0,2);
        turn.end();
    }

    @Test(expected = RuntimeException.class)
    public void haphestusDoubleBuildSameBoxLevelThree(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        board.getBox(0,0).build(Block.LTWO);
        Turn turn = new HaphestusTurn(player);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0,0);
        turn.build(0,0);
        turn.end();
    }

    @Test
    public void haphestusOneBuild(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        Turn turn = new HaphestusTurn(player);
        player.getPlayerMenu().replace("start", true);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0,0);
        turn.end();
        assertEquals(board.getBox(0,1).getPawn(), worker);
        assertEquals(board.getBox(0,0).getBlock(), Block.LONE);
    }

}