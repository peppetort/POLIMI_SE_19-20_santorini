package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultBuildTest {


    @Test
    public void rightBuild() {
        Board board = new Board();
        Game game = new Game("Marco", "Giuseppe", board, true);
        Player player = new Player("Marco", game);
        DefaultBuild builder = new DefaultBuild(player);
        Worker worker = player.getWorker1();
        board.placePawn(worker,0,0);
        builder.build(worker,0,1);
        assertEquals(1,board.getBox(0,1).getBlock().getValue());
        builder.build(worker,1,1);
        builder.build(worker,1,1);
        assertEquals(2,board.getBox(1,1).getBlock().getValue());
        builder.build(worker,1,0);
        builder.build(worker,1,0);
        builder.build(worker,1,0);
        assertEquals(3,board.getBox(1,0).getBlock().getValue());
        builder.build(worker,1,0);
        assertEquals(4,board.getBox(1,0).getBlock().getValue());
    }
    @Test (expected = RuntimeException.class)
    public void noBuildOverDome() {
        Board board = new Board();
        Game game = new Game("Marco", "Giuseppe", board, true);
        Player player = new Player("Marco", game);
        DefaultBuild builder = new DefaultBuild(player);
        Worker worker = player.getWorker1();
        board.placePawn(worker,0,0);
        builder.build(worker,0,1);
        builder.build(worker,0,1);
        builder.build(worker,0,1);
        builder.build(worker,0,1);
        builder.build(worker,0,1); //non posso costruire su una cupola
    }
    @Test (expected = RuntimeException.class) //impossibile costruire a due celle di distanza
    public void noBuildDistanceOver() {
        Board board = new Board();
        Game game = new Game("Marco", "Giuseppe", board, true);
        Player player = new Player("Marco", game);
        DefaultBuild builder = new DefaultBuild(player);
        Worker worker = player.getWorker1();
        board.placePawn(worker,0,0);
        builder.build(worker,0,2);
    }
    @Test (expected = RuntimeException.class) //impossibile costruire sulla stessa cella del worker
    public void noBuildSameBox() {
        Board board = new Board();
        Game game = new Game("Marco", "Giuseppe", board, true);
        Player player = new Player("Marco", game);
        DefaultBuild builder = new DefaultBuild(player);
        Worker worker = player.getWorker1();
        board.placePawn(worker,0,0);
        builder.build(worker,0,0);
    }

    @Test (expected = RuntimeException.class) //impossibile costruire sulla stessa cella di un altro worker
    public void noBuildOverPawn() {
        Board board = new Board();
        Game game = new Game("Marco", "Giuseppe", board, true);
        Player player = new Player("Marco", game);
        DefaultBuild builder = new DefaultBuild(player);
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker1();
        board.placePawn(worker1,0,0);
        board.placePawn(worker2,0,1);
        builder.build(worker1,0,1);
    }



}