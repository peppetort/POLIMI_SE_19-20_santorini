package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PrometheusTurnTest {

    @Test(expected = RuntimeException.class)
    public void PrometheusCanGoUpThenCanBuildAfterMove(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        board.getBox(0,1).build(Block.LONE);
        Turn turn = new PrometheusTurn(player);
        turn.start(worker);
        turn.build(0,1);
    }

    @Test
    public void PrometheusCanTGoUpThenCanBuildBeforeAndAfterMove(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        Turn turn = new PrometheusTurn(player);

        TurnUtils util = new TurnUtils(player);
        boolean tmp = util.getCanGoUp();
        util.setCanGoUp(true);

        turn.start(worker);
        turn.build(0,1);
        turn.move(0,1);
        turn.build(0,0);
        turn.end();

        util.setCanGoUp(tmp);
    }

    @Test(expected = RuntimeException.class)
    public void PrometheusCanTGoUpTryBuildTwoTimesBeforeMove(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        Turn turn = new PrometheusTurn(player);
        turn.start(worker);
        turn.build(0,1);
        turn.build(1,1);
    }

    @Test(expected = RuntimeException.class)
    public void PrometheusCanTGoUpTryBuildTwoTimesAfterMove(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        Turn turn = new PrometheusTurn(player);
        turn.start(worker);
        turn.move(0,1);
        turn.build(0,0);
        turn.build(1,1);
    }

    @Test
    public void PrometheusCanTGoUpButNoUsePower(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        Turn turn = new PrometheusTurn(player);
        turn.start(worker);
        turn.move(0,1);
        turn.build(1,1);
        turn.end();
    }

}