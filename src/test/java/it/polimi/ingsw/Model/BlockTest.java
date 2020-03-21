package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BlockTest {

    @Test
    public void getValue() {
        Block b0=Block.TERRAIN;
        assertEquals(0,b0.getValue());
        Block b1=Block.LONE;
        assertEquals(1,b1.getValue());
        Block b2=Block.LTWO;
        assertEquals(2,b2.getValue());
        Block b3=Block.LTHREE;
        assertEquals(3,b3.getValue());
        Block b4=Block.DOME;
        assertEquals(4,b4.getValue());
    }
}