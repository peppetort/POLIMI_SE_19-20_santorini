package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoxTest {

    Box b=new Box();

    @Test
    public void getPawn() {
        Worker w=new Worker("1");
        b.setPawn(w);
        assertEquals(b.getPawn(),w);
    }

    @Test
    public void removePawn() {
        Worker w=new Worker("1");
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
        Worker w=new Worker("1");
        b.setPawn(w);
        assertEquals(false,b.isFree());
    }
  /*  @Test
    public void canBuild() {
    b.build(Block.DOME);
    assertEquals(false,b.canBuild(Block.DOME));
    }*/

    @Test
    public void getDifference() {
        Box[] box=new Box[5];
        box[0]=new Box();
        box[1]=new Box();
        box[1].build(Block.LONE);
        box[2]=new Box();
        box[2].build(Block.LTWO);
        box[3]=new Box();
        box[3].build(Block.LTHREE);
        box[4]=new Box();
        box[4].build(Block.DOME);
        assertEquals(0,box[0].getDifference(box[0]));
        assertEquals(0,box[1].getDifference(box[1]));
        assertEquals(0,box[2].getDifference(box[2]));
        assertEquals(0,box[3].getDifference(box[3]));
        assertEquals(0,box[4].getDifference(box[4]));
        //TERRAIN
        assertEquals(-1,box[0].getDifference(box[1]));
        assertEquals(-2,box[0].getDifference(box[2]));
        assertEquals(-3,box[0].getDifference(box[3]));
        assertEquals(-4,box[0].getDifference(box[4]));
        //LONE
        assertEquals(1,box[1].getDifference(box[0]));
        assertEquals(-1,box[1].getDifference(box[2]));
        assertEquals(-2,box[1].getDifference(box[3]));
        assertEquals(-3,box[1].getDifference(box[4]));
        //LTWO
        assertEquals(2,box[2].getDifference(box[0]));
        assertEquals(1,box[2].getDifference(box[1]));
        assertEquals(-1,box[2].getDifference(box[3]));
        assertEquals(-2,box[2].getDifference(box[4]));
        //LTHREE
        assertEquals(3,box[3].getDifference(box[0]));
        assertEquals(2,box[3].getDifference(box[1]));
        assertEquals(1,box[3].getDifference(box[2]));
        assertEquals(-1,box[3].getDifference(box[4]));
        //DOME
        assertEquals(4,box[4].getDifference(box[0]));
        assertEquals(3,box[4].getDifference(box[1]));
        assertEquals(2,box[4].getDifference(box[2]));
        assertEquals(1,box[4].getDifference(box[3]));
    }

    @Test
    public void compareTrue1() {
    Box b1 =new Box();
    b1.build(Block.LONE);
    assertEquals(true,b1.compare(b));
    }
    @Test
    public void compareFalse1() {
        Box b1 =new Box();
        Box b2 =new Box();
        b1.build(Block.LTHREE);
        b2.build(Block.LONE);
        assertEquals(false,b2.compare(b1));
    }
    @Test
    public void compareFalse2() {
        Box b1 =new Box();
        Box b2 =new Box();
        b1.build(Block.LTHREE);
        b2.build(Block.DOME);
        assertEquals(false,b1.compare(b2));
    }

}