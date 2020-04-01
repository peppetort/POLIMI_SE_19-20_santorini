package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidBuildException;
import org.junit.Test;

import static org.junit.Assert.*;

public class DemeterTurnTest {

    @Test
    public void demeterDoubleBuild(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        Turn turn = new DemeterTurn(player);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0,0);
        turn.build(0,2);
        turn.end();
    }

    @Test
    public void demeterOneBuild(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        Turn turn = new DemeterTurn(player);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0,0);
        turn.end();
    }

    @Test(expected = InvalidBuildException.class)
    public void demeterDoubleBuildSamePos(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        Turn turn = new DemeterTurn(player);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0,0);
        turn.build(0,0);
        turn.end();
    }

}