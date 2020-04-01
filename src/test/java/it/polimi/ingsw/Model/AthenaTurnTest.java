package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.AthenaGoUpException;
import org.junit.Test;

import static org.junit.Assert.*;

public class AthenaTurnTest {

    @Test(expected = AthenaGoUpException.class)
    public void athenaGoUpDefaultNo(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Player otherPlayer = session.getPlayers().get(1);
        Worker worker = player.getWorker1();
        Worker otherWorker = otherPlayer.getWorker1();
        board.placePawn(worker, 0, 0);
        board.placePawn(otherWorker, 4,4);
        board.getBox(0,1).build(Block.LONE);
        board.getBox(4,3).build(Block.LONE);
        Turn turn = new AthenaTurn(player);
        turn.start(worker);
        turn.move(0,1);
        turn.build(0,2);
        turn.end();
        Turn otherTurn = new DefaultTurn(otherPlayer);
        otherTurn.start(otherWorker);
        otherTurn.move(4,3);
    }

    @Test(expected = AthenaGoUpException.class)
    public void athenaGoUpDemeterNo() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Player otherPlayer = session.getPlayers().get(1);
        Worker worker = player.getWorker1();
        Worker otherWorker = otherPlayer.getWorker1();
        board.placePawn(worker, 0, 0);
        board.placePawn(otherWorker, 4, 4);
        board.getBox(0, 1).build(Block.LONE);
        board.getBox(4, 3).build(Block.LONE);
        Turn turn = new AthenaTurn(player);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0, 2);
        turn.end();
        Turn otherTurn = new DemeterTurn(otherPlayer);
        otherTurn.start(otherWorker);
        otherTurn.move(4, 3);
    }

    @Test(expected = AthenaGoUpException.class)
    public void athenaGoUpHaphestusNo() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Player otherPlayer = session.getPlayers().get(1);
        Worker worker = player.getWorker1();
        Worker otherWorker = otherPlayer.getWorker1();
        board.placePawn(worker, 0, 0);
        board.placePawn(otherWorker, 4, 4);
        board.getBox(0, 1).build(Block.LONE);
        board.getBox(4, 3).build(Block.LONE);
        Turn turn = new AthenaTurn(player);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0, 2);
        turn.end();
        Turn otherTurn = new HaphestusTurn(otherPlayer);
        otherTurn.start(otherWorker);
        otherTurn.move(4, 3);
    }

    @Test(expected = AthenaGoUpException.class)
    public void athenaGoUpArtemisNo() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Player otherPlayer = session.getPlayers().get(1);
        Worker worker = player.getWorker1();
        Worker otherWorker = otherPlayer.getWorker1();
        board.placePawn(worker, 0, 0);
        board.placePawn(otherWorker, 4, 4);
        board.getBox(0, 1).build(Block.LONE);
        board.getBox(4, 3).build(Block.LONE);
        Turn turn = new AthenaTurn(player);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0, 2);
        turn.end();
        Turn otherTurn = new ArtemisTurn(otherPlayer);
        otherTurn.start(otherWorker);
        otherTurn.move(4, 3);
    }

    @Test(expected = AthenaGoUpException.class)
    public void athenaGoUpPrometheusNo() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Player otherPlayer = session.getPlayers().get(1);
        Worker worker = player.getWorker1();
        Worker otherWorker = otherPlayer.getWorker1();
        board.placePawn(worker, 0, 0);
        board.placePawn(otherWorker, 4, 4);
        board.getBox(0, 1).build(Block.LONE);
        board.getBox(4, 3).build(Block.LONE);
        Turn turn = new AthenaTurn(player);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0, 2);
        turn.end();
        Turn otherTurn = new PrometheusTurn(otherPlayer);
        otherTurn.start(otherWorker);
        otherTurn.move(4, 3);
    }

    @Test
    public void athenaGoUpAthenaNoGoUpOtherCanGoUp(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Player otherPlayer = session.getPlayers().get(1);
        Worker worker = player.getWorker1();
        Worker otherWorker = otherPlayer.getWorker1();
        board.placePawn(worker, 0, 0);
        board.placePawn(otherWorker, 4, 4);
        board.getBox(0, 1).build(Block.LONE);
        board.getBox(4, 3).build(Block.LONE);
        Turn turn = new AthenaTurn(player);
        turn.start(worker);
        turn.move(0, 1);
        turn.build(0, 0);
        turn.end();
        turn.start(worker);
        turn.move(0, 0);
        turn.build(0, 1);
        turn.end();
        Turn otherTurn = new DemeterTurn(otherPlayer);
        otherTurn.start(otherWorker);
        otherTurn.move(4, 3);
        otherTurn.build(4,4);
        otherTurn.end();
    }
}