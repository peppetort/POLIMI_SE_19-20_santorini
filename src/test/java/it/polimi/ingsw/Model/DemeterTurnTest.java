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
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        board.placePawn(worker1, 0, 0);
        board.placePawn(worker2, 4, 4);
        Turn turn = new DemeterTurn(player);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        turn.start(worker1);
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
        Worker worker1 = player.getWorker1();
        board.placePawn(worker1, 0, 0);
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
        Turn turn = new DemeterTurn(player);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        turn.start(worker1);
        turn.move(0, 1);
        turn.build(0,0);
        turn.end();
    }

    @Test(expected = InvalidBuildException.class)
    public void demeterDoubleBuildSamePos(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        board.placePawn(worker1, 0, 0);
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
        Turn turn = new DemeterTurn(player);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        turn.start(worker1);
        turn.move(0, 1);
        turn.build(0,0);
        turn.build(0,0);
        turn.end();
    }

}