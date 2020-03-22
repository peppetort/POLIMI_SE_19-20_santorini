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
    public void updateLastBoxSameTypeOfBuilding() {
        Worker worker = new Worker("1");
        Box box = new Box();
        worker.updateLastBox(box);
        assertEquals(box.getBlock(), worker.getLastBox().getBlock());
    }
}