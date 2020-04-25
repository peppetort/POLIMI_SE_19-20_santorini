package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultWinTest {

    @Test
    public void winCheckerTrueWorker1() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        Box box1 = board.getBox(0, 0);
        Box box2 = board.getBox(4,4);
        box1.setPawn(worker1);
        box2.setPawn(worker2);
        worker1.setPos(0, 0);
        worker2.setPos(4,4);
        box1.setBlock(Block.LTHREE);
        box2.setBlock(Block.TERRAIN);
        Win winAction = new DefaultWin(player);
        Boolean res = winAction.winChecker();
        assertEquals(true, res);
    }

    @Test
    public void winCheckerTrueWorker2() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        Box box1 = board.getBox(0, 0);
        Box box2 = board.getBox(4,4);
        box1.setPawn(worker1);
        box2.setPawn(worker2);
        worker1.setPos(0, 0);
        worker2.setPos(4,4);
        box1.setBlock(Block.LTWO);
        box2.setBlock(Block.LTHREE);
        Win winAction = new DefaultWin(player);
        Boolean res = winAction.winChecker();
        assertEquals(true, res);
    }

    @Test
    public void winCheckerFalse() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        Box box1 = board.getBox(0, 0);
        Box box2 = board.getBox(4,4);
        box1.setPawn(worker1);
        box2.setPawn(worker2);
        worker1.setPos(0, 0);
        worker2.setPos(4,4);
        box1.setBlock(Block.LTWO);
        box2.setBlock(Block.TERRAIN);
        Win winAction = new DefaultWin(player);
        Boolean res = winAction.winChecker();
        assertEquals(false, res);
    }

    @Test
    public void winCheckerWorkerNotPlaced(){
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        Box box1 = board.getBox(0, 0);
        Box box2 = board.getBox(4,4);
        box1.setPawn(worker1);
        box2.setPawn(worker2);
        worker2.setPos(4,4);
        box1.setBlock(Block.LTWO);
        box2.setBlock(Block.TERRAIN);
        Win winAction = new DefaultWin(player);
        Boolean res = winAction.winChecker();
        assertEquals(false, res);
    }
}