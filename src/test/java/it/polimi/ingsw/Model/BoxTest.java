package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoxTest {

    Box b = new Box();

    @Test
    public void getPawn() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker w = new Worker(1, player);
        b.setPawn(w);
        assertEquals(b.getPawn(), w);
    }

    @Test
    public void removePawn() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker w = new Worker(1, player);
        b.setPawn(w);
        b.removePawn();
        assertNull(b.getPawn());
    }

    @Test
    public void getBlock() {
        assertEquals(Block.TERRAIN, b.getBlock());
    }

    @Test
    public void build() {
        b.setBlock(Block.LONE);
        assertEquals(Block.LONE, b.getBlock());
    }

    @Test
    public void isFreeYes() {
        assertTrue(b.isFree());
    }

    @Test
    public void isFreeNo() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker w = new Worker(1, player);
        b.setPawn(w);
        assertFalse(b.isFree());
    }

    @Test
    public void getDifference() {
        Box[] box = new Box[5];
        box[0] = new Box();
        box[1] = new Box();
        box[1].setBlock(Block.LONE);
        box[2] = new Box();
        box[2].setBlock(Block.LTWO);
        box[3] = new Box();
        box[3].setBlock(Block.LTHREE);
        box[4] = new Box();
        box[4].setBlock(Block.DOME);
        assertEquals(0, box[0].getDifference(box[0]));
        assertEquals(0, box[1].getDifference(box[1]));
        assertEquals(0, box[2].getDifference(box[2]));
        assertEquals(0, box[3].getDifference(box[3]));
        assertEquals(0, box[4].getDifference(box[4]));
        //TERRAIN
        assertEquals(-1, box[0].getDifference(box[1]));
        assertEquals(-2, box[0].getDifference(box[2]));
        assertEquals(-3, box[0].getDifference(box[3]));
        assertEquals(-4, box[0].getDifference(box[4]));
        //LONE
        assertEquals(1, box[1].getDifference(box[0]));
        assertEquals(-1, box[1].getDifference(box[2]));
        assertEquals(-2, box[1].getDifference(box[3]));
        assertEquals(-3, box[1].getDifference(box[4]));
        //LTWO
        assertEquals(2, box[2].getDifference(box[0]));
        assertEquals(1, box[2].getDifference(box[1]));
        assertEquals(-1, box[2].getDifference(box[3]));
        assertEquals(-2, box[2].getDifference(box[4]));
        //LTHREE
        assertEquals(3, box[3].getDifference(box[0]));
        assertEquals(2, box[3].getDifference(box[1]));
        assertEquals(1, box[3].getDifference(box[2]));
        assertEquals(-1, box[3].getDifference(box[4]));
        //DOME
        assertEquals(4, box[4].getDifference(box[0]));
        assertEquals(3, box[4].getDifference(box[1]));
        assertEquals(2, box[4].getDifference(box[2]));
        assertEquals(1, box[4].getDifference(box[3]));
    }

    @Test
    public void compareTrue1() {
        Box b1 = new Box();
        b1.setBlock(Block.LONE);
        assertTrue(b1.compare(b));
    }

    @Test
    public void compareFalse1() {
        Box b1 = new Box();
        Box b2 = new Box();
        b1.setBlock(Block.LTHREE);
        b2.setBlock(Block.LONE);
        assertFalse(b2.compare(b1));
    }

    @Test
    public void compareFalse2() {
        Box b1 = new Box();
        Box b2 = new Box();
        b1.setBlock(Block.LTHREE);
        b2.setBlock(Block.DOME);
        assertFalse(b1.compare(b2));
    }

}