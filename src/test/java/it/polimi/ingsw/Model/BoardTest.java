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
        Worker w=new Worker("1");
        Board b=new Board();
        b.placePawn(w,2,3);
        assertEquals(w,b.getBox(2,3).getPawn());
        assertEquals(2,w.getXPos());
        assertEquals(3,w.getYPos());
    }
    @Test (expected = IndexOutOfBoundsException.class)
    public void placePawn1() {
        Worker w=new Worker("2");
        Board b=new Board();
        b.placePawn(w,-1,2);
    }
    @Test (expected = IndexOutOfBoundsException.class)
    public void placePawn2() {
        Worker w=new Worker("3");
        Board b=new Board();
        b.placePawn(w,5,2);
    }
    @Test (expected = IndexOutOfBoundsException.class)
    public void placePawn3() {
        Worker w=new Worker("4");
        Board b=new Board();
        b.placePawn(w,1,-1);
    }
    @Test (expected = IndexOutOfBoundsException.class)
    public void placePawn4() {
        Worker w=new Worker("5");
        Board b=new Board();
        b.placePawn(w,1,5);
    }

}