package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    //private Box[][] board=new Box[5][5];

    @Test
    public void getBox() {
        Board b=new Board();
        b.getBox(2,2);
        assertEquals(Block.TERRAIN, b.getBox(2,2).getBlock());
        assertEquals(null, b.getBox(2,2).getPawn());
    }
    @Test
    public void placePawn0() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker w=new Worker(1, player);
        board.placePawn(w,2,3);
        assertEquals(w,board.getBox(2,3).getPawn());
        assertEquals(2,(int)w.getXPos());
        assertEquals(3,(int)w.getYPos());
    }
    @Test (expected = IndexOutOfBoundsException.class)
    public void placePawn1() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker w=new Worker(1, player);
        board.placePawn(w,-1,2);
    }
    @Test (expected = IndexOutOfBoundsException.class)
    public void placePawn2() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker w=new Worker(1, player);
        board.placePawn(w,5,2);
    }
    @Test (expected = IndexOutOfBoundsException.class)
    public void placePawn3() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker w=new Worker(1, player);
        board.placePawn(w,1,-1);
    }
    @Test (expected = IndexOutOfBoundsException.class)
    public void placePawn4() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker w=new Worker(1, player);
        board.placePawn(w,1,5);
    }

    @Test (expected = RuntimeException.class)
    public void initializePawnError() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker1 =player.getWorker1();
        Worker worker2 =player.getWorker2();
        board.initializePawn(worker1,worker2,1,1,1,1);
    }
    @Test
        public void initializePawn() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker1 =player.getWorker1();
        Worker worker2 =player.getWorker2();
        board.initializePawn(worker1,worker2,1,1,2,2);
        assertEquals((int)worker1.getXPos(),1);
        assertEquals((int)worker1.getYPos(),1);
        assertEquals((int)worker2.getXPos(),2);
        assertEquals((int)worker2.getYPos(),2);
    }
}