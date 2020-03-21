package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApolloMoveTest {

    @Test
    public void moveNoApollo() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Box startBox = board.getBox(2,2);
        startBox.setPawn(worker1);
        worker1.setPos(2,2);
        Move moveAction = new ApolloMove(player);
        moveAction.move(worker1, 2, 3);
        assertEquals(board.getBox(2,3).getPawn(), worker1);
        assertEquals(worker1.getXPos(), 2);
        assertEquals(worker1.getYPos(), 3);
        assertTrue(startBox.isFree());
    }

    @Test
    public void moveApollo() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Worker workerPlayer1 = player1.getWorker1();
        Worker workerPlayer2 = player2.getWorker1();
        Box boxPlayer1 = board.getBox(2,2);
        Box boxPlayer2 = board.getBox(2, 3);
        boxPlayer1.setPawn(workerPlayer1);
        workerPlayer1.setPos(2,2);
        boxPlayer2.setPawn(workerPlayer2);
        workerPlayer2.setPos(2,3);
        Move moveAction = new ApolloMove(player1);
        moveAction.move(workerPlayer1, 2, 3);
        assertEquals(boxPlayer1.getPawn(), workerPlayer2);
        assertEquals(boxPlayer2.getPawn(), workerPlayer1);
        assertEquals(workerPlayer1.getXPos(), 2);
        assertEquals(workerPlayer1.getYPos(),3);
        assertEquals(workerPlayer2.getXPos(), 2);
        assertEquals(workerPlayer2.getYPos(),2);
    }

    @Test(expected = RuntimeException.class)
    public void moveInvalidMove() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Box startBox = board.getBox(2,2);
        startBox.setPawn(worker1);
        worker1.setPos(2,2);
        Move moveAction = new ApolloMove(player);
        moveAction.move(worker1, 2, 2);
    }

    @Test(expected = RuntimeException.class)
    public void moveLevelNotConpatible() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Box startBox = board.getBox(2,2);
        startBox.setPawn(worker1);
        worker1.setPos(2,2);
        board.getBox(2,3).build(Block.LTWO);
        Move moveAction = new ApolloMove(player);
        moveAction.move(worker1, 2, 3);
    }

    @Test(expected = RuntimeException.class)
    public void moveApolloSamePlayerWorkers() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player1 = game.getPlayers().get(0);
        Worker worker1 = player1.getWorker1();
        Worker worker2 = player1.getWorker2();
        Box boxWorker1 = board.getBox(2,2);
        Box boxWorker2 = board.getBox(2, 3);
        boxWorker1.setPawn(worker1);
        boxWorker2.setPawn(worker2);
        worker1.setPos(2,2);
        worker2.setPos(2,3);
        Move moveAction = new ApolloMove(player1);
        moveAction.move(worker1, 2, 3);
        assertEquals(boxWorker1.getPawn(), worker1);
        assertEquals(boxWorker2.getPawn(), worker2);
        assertEquals(worker1.getYPos(), 2);
        assertEquals(worker2.getYPos(), 3);
    }

    @Test
    public void movePawnNotPlaced() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Box startBox = board.getBox(2,2);
        Move moveAction = new ApolloMove(player);
        moveAction.move(worker1, 2, 3);
        assertTrue(board.getBox(2,3).isFree());
        assertTrue(startBox.isFree());
    }

    @Test
    public void moveIndexOutOfBoardLimits() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Box startBox = board.getBox(2,2);
        startBox.setPawn(worker1);
        worker1.setPos(2,2);
        Move moveAction = new ApolloMove(player);
        moveAction.move(worker1, 2, 5);
        assertEquals(startBox.getPawn(), worker1);
        assertEquals(worker1.getXPos(), 2);
        assertEquals(worker1.getYPos(), 2);
    }
}