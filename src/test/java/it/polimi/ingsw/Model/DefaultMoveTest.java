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
        worker.setPos(4,4);
        mover.move(worker,4,5);
        assertEquals(4,worker.getXPos());
        assertEquals(4,worker.getYPos());
    }
    @Test (expected = RuntimeException.class)
    public void noMoveZero() {
        Board board = new Board();
        Game game = new Game("Marco", "Giuseppe", board, true);
        Player player = new Player("Marco", game);
        DefaultMove mover = new DefaultMove(player);
        DefaultBuild builder = new DefaultBuild(player);
        Worker worker = player.getWorker1();
        worker.setPos(4,4);
        mover.move(worker,4,4);
        assertEquals(4,worker.getXPos());
        assertEquals(4,worker.getYPos());
    }

    @Test
    public void rightMoveZeroLevel() {
        Board board = new Board();
        Game game = new Game("Marco", "Giuseppe", board, true);
        Player player = new Player("Marco", game);
        DefaultMove mover = new DefaultMove(player);
        Worker worker = player.getWorker1();
        worker.setPos(0, 0);
        mover.move(worker, 0, 1);
        assertEquals(0, worker.getXPos());
        assertEquals(1, worker.getYPos());
        mover.move(worker, 1, 1);
        assertEquals(1, worker.getXPos());
        assertEquals(1, worker.getYPos());
        mover.move(worker, 0, 0);
        assertEquals(0, worker.getXPos());
        assertEquals(0, worker.getYPos());
    }

    @Test (expected = RuntimeException.class)
    public void noMoveOverDome() {
        Board board = new Board();
        Game game = new Game("Marco", "Giuseppe", board, true);
        Player player = new Player("Marco", game);
        DefaultMove mover = new DefaultMove(player);
        DefaultBuild builder = new DefaultBuild(player);
        Worker worker = player.getWorker1();

        builder.build(0,0);
        builder.build(0,0);
        builder.build(0,0);

        builder.build(0,1);
        builder.build(0,1);
        builder.build(0,1);
        builder.build(0,1);

        worker.setPos(0,0);
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

        builder.build(0,1);
        builder.build(0,1);

        worker.setPos(0,0);
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

        builder.build(0,0);
        builder.build(0,1);

        worker.setPos(0,0);
        mover.move(worker,0,1);
        assertEquals(0, worker.getXPos());
        assertEquals(1, worker.getYPos());

        builder.build(0,2);
        builder.build(0,2);

        mover.move(worker,0,2);
        assertEquals(0, worker.getXPos());
        assertEquals(2, worker.getYPos());

        builder.build(0,3);
        builder.build(0,3);

        mover.move(worker,0,3);
        assertEquals(0, worker.getXPos());
        assertEquals(3, worker.getYPos());

        builder.build(0,4);
        builder.build(0,4);
        builder.build(0,4);

        mover.move(worker,0,4);
        assertEquals(0, worker.getXPos());
        assertEquals(4, worker.getYPos());

        builder.build(1,4);
        builder.build(1,4);
        builder.build(1,4);

        mover.move(worker,1,4);
        assertEquals(1, worker.getXPos());
        assertEquals(4, worker.getYPos());
    }

    @Test
    public void stepDownMove() {
        Board board = new Board();
        Game game = new Game("Marco", "Giuseppe", board, true);
        Player player = new Player("Marco", game);
        DefaultMove mover = new DefaultMove(player);
        DefaultBuild builder = new DefaultBuild(player);
        Worker worker = player.getWorker1();

        builder.build(0,0);

        builder.build(0,1);
        builder.build(0,1);

        builder.build(1,0);
        builder.build(1,0);
        builder.build(1,0);

        worker.setPos(0,0);
        mover.move(worker,1,1);
        assertEquals(1, worker.getXPos());
        assertEquals(1, worker.getYPos());
        board.getBox(1,1).removePawn();

        worker.setPos(0,1);
        mover.move(worker,1,1);
        assertEquals(1, worker.getXPos());
        assertEquals(1, worker.getYPos());
        board.getBox(1,1).removePawn();

        worker.setPos(1,0);
        mover.move(worker,1,1);
        assertEquals(1, worker.getXPos());
        assertEquals(1, worker.getYPos());
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
        worker1.setPos(0,0);
        board.placePawn(worker2,0,1);
        mover.move(worker1,0,1);
    }

}