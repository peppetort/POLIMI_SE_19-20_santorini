package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultMoveTest {

    @Test
    public void noMoveOverIndex() {
        Board board = new Board();
        Game game = new Game("Marco", "Giuseppe", board, true);
        Player player = new Player("Marco", game);
        DefaultMove mover = new DefaultMove(player);
        DefaultBuild builder = new DefaultBuild(player);
        Worker worker = player.getWorker1();
        board.placePawn(worker,4,4);
        mover.move(worker,4,5);
    }
    @Test (expected = RuntimeException.class)
    public void noMoveZero() {
        Board board = new Board();
        Game game = new Game("Marco", "Giuseppe", board, true);
        Player player = new Player("Marco", game);
        DefaultMove mover = new DefaultMove(player);
        DefaultBuild builder = new DefaultBuild(player);
        Worker worker = player.getWorker1();
        board.placePawn(worker,4,4);
        mover.move(worker,4,4);;
    }

    @Test
    public void rightMoveZeroLevel() {
        Board board = new Board();
        Game game = new Game("Marco", "Giuseppe", board, true);
        Player player = new Player("Marco", game);
        DefaultMove mover = new DefaultMove(player);
        Worker worker = player.getWorker1();

        board.placePawn(worker,0,0);
        mover.move(worker, 0, 1);
        assertEquals(0, (int)worker.getXPos());
        assertEquals(1, (int)worker.getYPos());
        assertTrue(board.getBox(0,0).isFree());
        assertEquals(worker,board.getBox(0,1).getPawn());

        mover.move(worker, 1, 1);
        assertEquals(1, (int)worker.getXPos());
        assertEquals(1, (int)worker.getYPos());
        assertTrue(board.getBox(0,1).isFree());
        assertEquals(worker,board.getBox(1,1).getPawn());


        mover.move(worker, 0, 0);
        assertEquals(0,(int) worker.getXPos());
        assertEquals(0, (int)worker.getYPos());
        assertTrue(board.getBox(1,1).isFree());
        assertEquals(worker,board.getBox(0,0).getPawn());
    }

    @Test (expected = RuntimeException.class)
    public void noMoveOverDome() {
        Board board = new Board();
        Game game = new Game("Marco", "Giuseppe", board, true);
        Player player = new Player("Marco", game);
        DefaultMove mover = new DefaultMove(player);
        DefaultBuild builder = new DefaultBuild(player);
        Worker worker = player.getWorker1();

        board.getBox(0,0).build(Block.LTHREE);
        board.getBox(0,1).build(Block.DOME);

        board.placePawn(worker,0,0);
        mover.move(worker,0,1);
    }
    @Test (expected = RuntimeException.class)
    public void noMoveOverTwoSteps() {
        Board board = new Board();
        Game game = new Game("Marco", "Giuseppe", board, true);
        Player player = new Player("Marco", game);
        DefaultMove mover = new DefaultMove(player);
        DefaultBuild builder = new DefaultBuild(player);
        Worker worker = player.getWorker1();
        board.getBox(0,1).build(Block.LTWO);
        board.placePawn(worker,0,0);
        mover.move(worker,0,1);
    }
    @Test
    public void rightMoveUpper() {
        Board board = new Board();
        Game game = new Game("Marco", "Giuseppe", board, true);
        Player player = new Player("Marco", game);
        DefaultMove mover = new DefaultMove(player);
        DefaultBuild builder = new DefaultBuild(player);
        Worker worker = player.getWorker1();

        board.getBox(0,0).build(Block.LONE);
        board.getBox(0,1).build(Block.LONE);

        board.placePawn(worker,0,0);
        mover.move(worker,0,1);
        assertEquals(worker,board.getBox(0,1).getPawn());
        assertTrue(board.getBox(0,0).isFree());
        assertEquals(0, (int)worker.getXPos());
        assertEquals(1,(int) worker.getYPos());

        board.getBox(0,2).build(Block.LTWO);

        mover.move(worker,0,2);
        assertEquals(worker,board.getBox(0,2).getPawn());
        assertTrue(board.getBox(0,1).isFree());
        assertEquals(0, (int)worker.getXPos());
        assertEquals(2,(int) worker.getYPos());

        board.getBox(0,3).build(Block.LTWO);

        mover.move(worker,0,3);
        assertEquals(worker,board.getBox(0,3).getPawn());
        assertTrue(board.getBox(0,2).isFree());
        assertEquals(0,(int) worker.getXPos());
        assertEquals(3, (int)worker.getYPos());

        board.getBox(0,4).build(Block.LTHREE);

        mover.move(worker,0,4);
        assertEquals(worker,board.getBox(0,4).getPawn());
        assertTrue(board.getBox(0,3).isFree());
        assertEquals(0, (int)worker.getXPos());
        assertEquals(4,(int) worker.getYPos());

        board.getBox(1,4).build(Block.LTHREE);

        mover.move(worker,1,4);
        assertEquals(worker,board.getBox(1,4).getPawn());
        assertTrue(board.getBox(0,3).isFree());
        assertEquals(1,(int) worker.getXPos());
        assertEquals(4,(int) worker.getYPos());
    }

    @Test
    public void stepDownMove() {
        Board board = new Board();
        Game game = new Game("Marco", "Giuseppe", board, true);
        Player player = new Player("Marco", game);
        DefaultMove mover = new DefaultMove(player);
        DefaultBuild builder = new DefaultBuild(player);
        Worker worker = player.getWorker1();

        board.getBox(0,0).build(Block.LONE);
        board.getBox(0,1).build(Block.LTWO);
        board.getBox(1,0).build(Block.LTHREE);

        board.placePawn(worker,0,0);
        mover.move(worker,1,1);
        assertEquals(1, (int)worker.getXPos());
        assertEquals(1,(int) worker.getYPos());
        assertEquals(worker,board.getBox(1,1).getPawn());
        assertTrue(board.getBox(0,0).isFree());
        board.getBox(1,1).removePawn();

        board.placePawn(worker,0,1);
        mover.move(worker,1,1);
        assertEquals(1, (int)worker.getXPos());
        assertEquals(1, (int)worker.getYPos());
        assertEquals(worker,board.getBox(1,1).getPawn());
        assertTrue(board.getBox(0,1).isFree());
        board.getBox(1,1).removePawn();

        board.placePawn(worker,1,0);
        mover.move(worker,1,1);
        assertEquals(1,(int) worker.getXPos());
        assertEquals(1,(int) worker.getYPos());
        assertEquals(worker,board.getBox(1,1).getPawn());
        assertTrue(board.getBox(1,0).isFree());
    }

    @Test (expected = RuntimeException.class)
    public void noMoveOverPawn() {
        Board board = new Board();
        Game game = new Game("Marco", "Giuseppe", board, true);
        Player player = new Player("Marco", game);
        DefaultMove mover = new DefaultMove(player);
        DefaultBuild builder = new DefaultBuild(player);
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        board.placePawn(worker1,0,0);
        board.placePawn(worker2,0,1);
        mover.move(worker1,0,1);
    }

    @Test (expected = RuntimeException.class)
    public void moveNoGoUp(){
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Box startBox = board.getBox(2,2);
        startBox.setPawn(worker1);
        worker1.setPos(2,2);
        board.getBox(1,1).build(Block.LONE);
        board.getBox(1,2).build(Block.LONE);
        board.getBox(1,3).build(Block.LONE);
        board.getBox(2,1).build(Block.LONE);
        board.getBox(2,3).build(Block.LONE);
        board.getBox(3,1).build(Block.LONE);
        board.getBox(3,2).build(Block.LONE);
        board.getBox(3,3).build(Block.LONE);
        Move moveAction = new DefaultMove(player);
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

}