package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultBuildTest {


    @Test (expected = RuntimeException.class)
    public void build() {
        Board board = new Board();
        Game game = new Game("Marco","Giuseppe",board,true);
        Player player = new Player("Marco",game);
        DefaultBuild builder = new DefaultBuild(player);
        int actualLevel;
        int x = 0;
        int y = 0;
        actualLevel = board.getBox(x,y).getBlock().getValue();
        assertEquals(0,actualLevel);
        builder.build(x,y);
        actualLevel = board.getBox(x,y).getBlock().getValue();
        assertEquals(1,actualLevel);
        builder.build(x,y);
        actualLevel = board.getBox(x,y).getBlock().getValue();
        assertEquals(2,actualLevel);
        builder.build(x,y);
        actualLevel = board.getBox(x,y).getBlock().getValue();
        assertEquals(3,actualLevel);
        builder.build(x,y);
        actualLevel = board.getBox(x,y).getBlock().getValue();
        assertEquals(4,actualLevel);
        builder.build(x,y);
        actualLevel = board.getBox(x,y).getBlock().getValue();
        assertEquals(4,actualLevel);



    }
}