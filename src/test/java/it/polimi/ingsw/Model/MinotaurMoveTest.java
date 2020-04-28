package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidMoveException;
import org.junit.Test;

import static org.junit.Assert.*;

public class MinotaurMoveTest {

    @Test
    public void moveDefault() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Box startBox = board.getBox(2,2);
        startBox.setPawn(worker1);
        worker1.setPos(2,2);
        Move moveAction = new MinotaurMove(player);
        moveAction.move(worker1, 2, 3);
        assertEquals(board.getBox(2,3).getPawn(), worker1);
        assertEquals((int)worker1.getXPos(), 2);
        assertEquals((int)worker1.getYPos(), 3);
        assertTrue(startBox.isFree());
    }

    @Test
    public void moveMinotaur0(){
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Worker workerPlayer1 = player1.getWorker1();
        Worker workerPlayer2 = player2.getWorker1();
        Box myBox = board.getBox(2,2);
        Box otherBox = board.getBox(1,1);
        myBox.setPawn(workerPlayer1);
        otherBox.setPawn(workerPlayer2);
        workerPlayer1.setPos(2,2);
        workerPlayer2.setPos(1,1);
        Move moveAction = new MinotaurMove(player1);
        moveAction.move(workerPlayer1, 1,1);
        assertEquals(otherBox.getPawn(), workerPlayer1);
        assertEquals(board.getBox(0,0).getPawn(), workerPlayer2);
        assertTrue(myBox.isFree());
        assertEquals((int)workerPlayer1.getYPos(), 1);
        assertEquals((int)workerPlayer1.getXPos(), 1);
        assertEquals((int)workerPlayer2.getYPos(), 0);
        assertEquals((int)workerPlayer2.getXPos(), 0);

    }

    @Test
    public void moveMinotaur1(){
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Worker workerPlayer1 = player1.getWorker1();
        Worker workerPlayer2 = player2.getWorker1();
        Box myBox = board.getBox(2,2);
        Box otherBox = board.getBox(2,1);
        myBox.setPawn(workerPlayer1);
        otherBox.setPawn(workerPlayer2);
        workerPlayer1.setPos(2,2);
        workerPlayer2.setPos(2,1);
        Move moveAction = new MinotaurMove(player1);
        moveAction.move(workerPlayer1, 2,1);
        assertEquals(otherBox.getPawn(), workerPlayer1);
        assertEquals(board.getBox(2,0).getPawn(), workerPlayer2);
        assertTrue(myBox.isFree());
        assertEquals((int)workerPlayer1.getXPos(), 2);
        assertEquals((int)workerPlayer1.getYPos(), 1);
        assertEquals((int)workerPlayer2.getXPos(), 2);
        assertEquals((int)workerPlayer2.getYPos(), 0);
    }

    @Test
    public void moveMinotaur2(){
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Worker workerPlayer1 = player1.getWorker1();
        Worker workerPlayer2 = player2.getWorker1();
        Box myBox = board.getBox(2,2);
        Box otherBox = board.getBox(3,1);
        myBox.setPawn(workerPlayer1);
        otherBox.setPawn(workerPlayer2);
        workerPlayer1.setPos(2,2);
        workerPlayer2.setPos(3,1);
        Move moveAction = new MinotaurMove(player1);
        moveAction.move(workerPlayer1, 3,1);
        assertEquals(otherBox.getPawn(), workerPlayer1);
        assertEquals(board.getBox(4,0).getPawn(), workerPlayer2);
        assertTrue(myBox.isFree());
        assertEquals((int)workerPlayer1.getXPos(), 3);
        assertEquals((int)workerPlayer1.getYPos(), 1);
        assertEquals((int)workerPlayer2.getXPos(), 4);
        assertEquals((int)workerPlayer2.getYPos(), 0);
    }

    @Test
    public void moveMinotaur3(){
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Worker workerPlayer1 = player1.getWorker1();
        Worker workerPlayer2 = player2.getWorker1();
        Box myBox = board.getBox(2,2);
        Box otherBox = board.getBox(1,2);
        myBox.setPawn(workerPlayer1);
        otherBox.setPawn(workerPlayer2);
        workerPlayer1.setPos(2,2);
        workerPlayer2.setPos(1,2);
        Move moveAction = new MinotaurMove(player1);
        moveAction.move(workerPlayer1, 1,2);
        assertEquals(otherBox.getPawn(), workerPlayer1);
        assertEquals(board.getBox(0,2).getPawn(), workerPlayer2);
        assertTrue(myBox.isFree());
        assertEquals((int)workerPlayer1.getXPos(), 1);
        assertEquals((int)workerPlayer1.getYPos(), 2);
        assertEquals((int)workerPlayer2.getXPos(), 0);
        assertEquals((int)workerPlayer2.getYPos(), 2);

    }

    @Test
    public void moveMinotaur4(){
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Worker workerPlayer1 = player1.getWorker1();
        Worker workerPlayer2 = player2.getWorker1();
        Box myBox = board.getBox(2,2);
        Box otherBox = board.getBox(3,2);
        myBox.setPawn(workerPlayer1);
        otherBox.setPawn(workerPlayer2);
        workerPlayer1.setPos(2,2);
        workerPlayer2.setPos(3,2);
        Move moveAction = new MinotaurMove(player1);
        moveAction.move(workerPlayer1, 3,2);
        assertEquals(otherBox.getPawn(), workerPlayer1);
        assertEquals(board.getBox(4,2).getPawn(), workerPlayer2);
        assertTrue(myBox.isFree());
        assertEquals((int)workerPlayer1.getXPos(), 3);
        assertEquals((int)workerPlayer1.getYPos(), 2);
        assertEquals((int)workerPlayer2.getXPos(), 4);
        assertEquals((int)workerPlayer2.getYPos(), 2);

    }

    @Test
    public void moveMinotaur5(){
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Worker workerPlayer1 = player1.getWorker1();
        Worker workerPlayer2 = player2.getWorker1();
        Box myBox = board.getBox(2,2);
        Box otherBox = board.getBox(1,3);
        myBox.setPawn(workerPlayer1);
        otherBox.setPawn(workerPlayer2);
        workerPlayer1.setPos(2,2);
        workerPlayer2.setPos(1,3);
        Move moveAction = new MinotaurMove(player1);
        moveAction.move(workerPlayer1, 1,3);
        assertEquals(otherBox.getPawn(), workerPlayer1);
        assertEquals(board.getBox(0,4).getPawn(), workerPlayer2);
        assertTrue(myBox.isFree());
        assertEquals((int)workerPlayer1.getXPos(), 1);
        assertEquals((int)workerPlayer1.getYPos(), 3);
        assertEquals((int)workerPlayer2.getXPos(), 0);
        assertEquals((int)workerPlayer2.getYPos(), 4);

    }

    @Test
    public void moveMinotaur6(){
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Worker workerPlayer1 = player1.getWorker1();
        Worker workerPlayer2 = player2.getWorker1();
        Box myBox = board.getBox(2,2);
        Box otherBox = board.getBox(2,3);
        myBox.setPawn(workerPlayer1);
        otherBox.setPawn(workerPlayer2);
        workerPlayer1.setPos(2,2);
        workerPlayer2.setPos(2,3);
        Move moveAction = new MinotaurMove(player1);
        moveAction.move(workerPlayer1, 2,3);
        assertEquals(otherBox.getPawn(), workerPlayer1);
        assertEquals(board.getBox(2,4).getPawn(), workerPlayer2);
        assertTrue(myBox.isFree());
        assertEquals((int)workerPlayer1.getXPos(), 2);
        assertEquals((int)workerPlayer1.getYPos(), 3);
        assertEquals((int)workerPlayer2.getXPos(), 2);
        assertEquals((int)workerPlayer2.getYPos(), 4);

    }

    @Test
    public void moveMinotaur7(){
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Worker workerPlayer1 = player1.getWorker1();
        Worker workerPlayer2 = player2.getWorker1();
        Box myBox = board.getBox(2,2);
        Box otherBox = board.getBox(3,3);
        myBox.setPawn(workerPlayer1);
        otherBox.setPawn(workerPlayer2);
        workerPlayer1.setPos(2,2);
        workerPlayer2.setPos(3,3);
        Move moveAction = new MinotaurMove(player1);
        moveAction.move(workerPlayer1, 3,3);
        assertEquals(otherBox.getPawn(), workerPlayer1);
        assertEquals(board.getBox(4,4).getPawn(), workerPlayer2);
        assertTrue(myBox.isFree());
        assertEquals((int)workerPlayer1.getXPos(), 3);
        assertEquals((int)workerPlayer1.getYPos(), 3);
        assertEquals((int)workerPlayer2.getXPos(), 4);
        assertEquals((int)workerPlayer2.getYPos(), 4);

    }


    @Test(expected = InvalidMoveException.class)
    public void moveMinotaurCantBackAwayOutOfBoard(){
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Worker workerPlayer1 = player1.getWorker1();
        Worker workerPlayer2 = player2.getWorker1();
        Box myBox = board.getBox(2,3);
        Box otherBox = board.getBox(2,4);
        myBox.setPawn(workerPlayer1);
        otherBox.setPawn(workerPlayer2);
        workerPlayer1.setPos(2,3);
        workerPlayer2.setPos(2,4);
        Move moveAction = new MinotaurMove(player1);
        moveAction.move(workerPlayer1, 2,4);
        assertEquals(myBox.getPawn(), workerPlayer1);
        assertEquals(otherBox.getPawn(), workerPlayer2);
        assertEquals((int)workerPlayer1.getYPos(), 3);
        assertEquals((int)workerPlayer2.getYPos(), 4);
    }

    @Test(expected = RuntimeException.class)
    public void moveMinotaurCantBackAwayBoxBusy(){
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Worker workerPlayer1 = player1.getWorker1();
        Worker workerPlayer12 = player1.getWorker2();
        Worker workerPlayer2 = player2.getWorker1();
        Box myBox = board.getBox(2,2);
        Box otherBox = board.getBox(2,3);
        Box myOtherBox = board.getBox(2,4);
        myBox.setPawn(workerPlayer1);
        otherBox.setPawn(workerPlayer2);
        myOtherBox.setPawn(workerPlayer12);
        workerPlayer12.setPos(2,4);
        workerPlayer1.setPos(2,2);
        workerPlayer2.setPos(2,3);
        Move moveAction = new MinotaurMove(player1);
        moveAction.move(workerPlayer1, 2,4);
        assertEquals(myBox.getPawn(), workerPlayer1);
        assertEquals(otherBox.getPawn(), workerPlayer2);
        assertEquals((int)workerPlayer1.getYPos(), 3);
        assertEquals((int)workerPlayer2.getYPos(), 4);
        assertEquals(myOtherBox.getPawn(), workerPlayer12);
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
        Move moveAction = new MinotaurMove(player);
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
        board.build(2,3,Block.LTWO);
        Move moveAction = new MinotaurMove(player);
        moveAction.move(worker1, 2, 3);
    }

    @Test(expected = RuntimeException.class)
    public void moveSamePlayerWorkers() {
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
        Move moveAction = new MinotaurMove(player1);
        moveAction.move(worker1, 2, 3);
        assertEquals(boxWorker1.getPawn(), worker1);
        assertEquals(boxWorker2.getPawn(), worker2);
        assertEquals((int)worker1.getYPos(), 2);
        assertEquals((int)worker2.getYPos(), 3);
        assertTrue(board.getBox(2,4).isFree());
    }

    @Test(expected = NullPointerException.class)
    public void movePawnNotPlaced() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Box startBox = board.getBox(2,2);
        Move moveAction = new MinotaurMove(player);
        moveAction.move(worker1, 2, 3);
        assertTrue(board.getBox(2,3).isFree());
        assertTrue(startBox.isFree());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void moveIndexOutOfBoardLimits() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Box startBox = board.getBox(2,2);
        startBox.setPawn(worker1);
        worker1.setPos(2,2);
        Move moveAction = new MinotaurMove(player);
        moveAction.move(worker1, 2, 5);
        assertEquals(startBox.getPawn(), worker1);
        assertEquals((int)worker1.getXPos(), 2);
        assertEquals((int)worker1.getYPos(), 2);
    }

    @Test (expected = RuntimeException.class)
    public void moveNoGoUpDefault(){
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Box startBox = board.getBox(2,2);
        startBox.setPawn(worker1);
        worker1.setPos(2,2);
        board.build(1,1,Block.LONE);
        board.build(1,2,Block.LONE);
        board.build(1,3,Block.LONE);
        board.build(2,1,Block.LONE);
        board.build(2,3,Block.LONE);
        board.build(3,1,Block.LONE);
        board.build(3,2,Block.LONE);
        board.build(3,3,Block.LONE);
        Move moveAction = new MinotaurMove(player);
        for(int i=-1; i<=1;i++){
            for(int j=-1; j<=1; j++){
                moveAction.moveNoGoUp(worker1, 2+i, 2+j);
            }
        }
        assertEquals(startBox.getPawn(), worker1);
        for(int i=-1; i<=1;i++){
            for(int j=-1; j<=1; j++){
                assertNull(board.getBox(2 + i, 2 + j).getPawn());
            }
        }
    }


    @Test(expected = RuntimeException.class)
    public void moveNoGoUpMinotaurNo() {
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
        boxPlayer2.setBlock(Block.LONE);
        Move moveAction = new MinotaurMove(player1);
        moveAction.moveNoGoUp(workerPlayer1, 2,3);
        assertEquals(workerPlayer1, boxPlayer1.getPawn());
        assertEquals(workerPlayer2, boxPlayer2.getPawn());
    }

    @Test
    public void moveNoGoUpApolloYes() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Worker workerPlayer1 = player1.getWorker1();
        Worker workerPlayer2 = player2.getWorker1();
        Box boxPlayer1 = board.getBox(2,1);
        Box boxPlayer2 = board.getBox(2, 2);
        boxPlayer1.setPawn(workerPlayer1);
        workerPlayer1.setPos(2,1);
        boxPlayer2.setPawn(workerPlayer2);
        workerPlayer2.setPos(2,2);
        Move moveAction = new MinotaurMove(player1);
        moveAction.moveNoGoUp(workerPlayer1, 2,2);
        assertEquals(boxPlayer2.getPawn(), workerPlayer1);
        assertEquals(board.getBox(2,3).getPawn(), workerPlayer2);
    }
}