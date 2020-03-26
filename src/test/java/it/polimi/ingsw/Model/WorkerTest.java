package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {

    @Test
    public void setPos() {
        Worker worker = new Worker("1");
        worker.setPos(0, 0);
        assertEquals(0, worker.getXPos());
        assertEquals(0, worker.getYPos());
    }

    @Test (expected = RuntimeException.class)
    public void setPosInvalidX() {
        Worker worker = new Worker("1");
        worker.setPos(-1, 0);
    }

    @Test (expected = RuntimeException.class)
    public void setPosInvalidY() {
        Worker worker = new Worker("1");
        worker.setPos(0, 6);
    }

    @Test (expected = NullPointerException.class)
    public void getXPosNotInizialized(){
        Worker worker = new Worker("1");
        worker.getXPos();
    }

    @Test (expected = NullPointerException.class)
    public void getYPosNotInizialized(){
        Worker worker = new Worker("1");
        worker.getYPos();
    }

    @Test
    public void updateLastBoxNotSameObject() {
        Worker worker = new Worker("1");
        Box box = new Box();
        worker.updateLastBox(box);
        assertNotEquals(box, worker.getLastBox());
    }

    @Test
    public void updateLastBoxSamePawn() {
        Worker worker = new Worker("1");
        Box box = new Box();
        worker.updateLastBox(box);
        assertEquals(box.getPawn(), worker.getLastBox().getPawn());
    }


    @Test
    public void canMoveFalse() {
        Worker w1 = new Worker("1");
        Worker w2 = new Worker("2");
        Board b=new Board();
        b.placePawn(w1,0,0);
        b.placePawn(w2,1,1);
        b.getBox(0,1).build(Block.LTWO);
        b.getBox(1,0).build(Block.LTWO);
        assertEquals(false,w1.canMove(b));
    }

    @Test
    public void canMoveTrue() {
        Worker w1 = new Worker("1");
        Board b=new Board();
        b.placePawn(w1,4,4);
        b.getBox(4,3).build(Block.LTWO);
        b.getBox(3,4).build(Block.LTWO);
        assertEquals(true,w1.canMove(b));
    }
}