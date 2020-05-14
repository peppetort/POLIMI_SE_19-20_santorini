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
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
        board.build(0,1,Block.LONE);
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
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
        Turn turn = new PrometheusTurn(player);

        TurnUtils util = new TurnUtils(player);
        boolean tmp = util.getCanGoUp();
        util.setCanGoUp(true);

       player.getPlayerMenu().replace(Actions.SELECT, true);

        turn.start(worker);
        turn.build(0,1);
        turn.move(0,1);
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
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
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
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
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
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
        Turn turn = new PrometheusTurn(player);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        turn.start(worker);
        turn.move(0,1);
        turn.build(1,1);
        turn.end();
    }

    @Test(expected = RuntimeException.class)
    public void PrometheusBuildMoveCantBuild(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker = player.getWorker1();
        board.placePawn(worker, 0, 0);
        worker.updateLastBox(board.getBox(0, 0));
        Worker worker2 = player.getWorker2();
        board.placePawn(worker2, 4, 4);
        Turn turn = new PrometheusTurn(player);
        player.getPlayerMenu().replace(Actions.SELECT, true);
        turn.start(worker);
        turn.build(0, 1);
        turn.move(0, 1);
        turn.build(0, 0);
    }

}