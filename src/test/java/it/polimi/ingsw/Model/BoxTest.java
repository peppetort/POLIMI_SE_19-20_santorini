package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoxTest {

    Box b=new Box();

    @Test
    public void getPawn() {
        Worker w=new Worker(1);
        b.setPawn(w);
        assertEquals(b.getPawn(),w);
    }

    @Test
    public void removePawn() {
        Worker w=new Worker(1);
        b.setPawn(w);
        b.removePawn();
        assertEquals(null,b.getPawn());
    }

    @Test
    public void getBlock() {
       assertEquals(Block.TERRAIN, b.getBlock());
    }

    @Test
    public void build() {
        b.build(Block.LONE);
        assertEquals(Block.LONE, b.getBlock());
    }

    @Test
    public void isFreeYes() {
        assertEquals(true,b.isFree());
    }
    public void isFreeNo() {
        Worker w=new Worker(1);
        b.setPawn(w);
        assertEquals(false,b.isFree());
    }
    @Test
    public void canBuild() {

    }

    @Test
    public void getDifference() {
    }

    @Test
    public void compare() {
    }
}