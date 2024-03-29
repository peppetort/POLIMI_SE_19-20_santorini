package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PanWinTest {

    @Test
    public void winCheckerDefaultWinTrue() {
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
        Win winAction = new PanWin(player);
        assertTrue(winAction.winChecker());
    }

    @Test
    public void winCheckerPanWinTrueLTWOtoTERRAIN() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        Box box2 = board.getBox(4,4);
        Box lastBox1 = board.getBox(0, 0);
        Box box1 = board.getBox(0,1);
        box2.setPawn(worker2);
        worker2.setPos(4,4);
        lastBox1.setBlock(Block.LTWO);
        box1.setPawn(worker1);
        worker1.setPos(0,1);
        worker1.updateLastBox(lastBox1);
        Win winAction = new PanWin(player);
        assertTrue(winAction.winChecker());
    }

    @Test
    public void winCheckerFalse1() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        Box box2 = board.getBox(4,4);
        Box lastBox1 = board.getBox(0, 0);
        Box box1 = board.getBox(0,1);
        box2.setPawn(worker2);
        worker2.setPos(4,4);
        lastBox1.setBlock(Block.LONE);
        box1.setPawn(worker1);
        worker1.setPos(0,1);
        worker1.updateLastBox(lastBox1);
        Win winAction = new PanWin(player);
        assertFalse(winAction.winChecker());
    }

    @Test
    public void winCheckerFalse2() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        Box box1 = board.getBox(4,4);
        Box lastBox2 = board.getBox(0, 0);
        Box box2 = board.getBox(0,1);
        box2.setPawn(worker2);
        worker2.setPos(4,4);
        lastBox2.setBlock(Block.LONE);
        box2.setPawn(worker1);
        worker1.setPos(0,1);
        worker2.updateLastBox(lastBox2);
        Win winAction = new PanWin(player);
        winAction.winChecker();
        assertFalse(winAction.winChecker());
    }

    @Test
    public void winCheckerFalse3() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        Box box2 = board.getBox(4,4);
        Box lastBox1 = board.getBox(0, 0);
        Box lastBox2 = board.getBox(3, 4);
        Box box1 = board.getBox(0,1);
        box2.setPawn(worker2);
        worker2.setPos(4,4);
        lastBox1.setBlock(Block.LONE);
        box1.setPawn(worker1);
        worker1.setPos(0,1);
        worker1.updateLastBox(lastBox1);
        worker2.updateLastBox(lastBox2);
        Win winAction = new PanWin(player);
        assertFalse(winAction.winChecker());
    }

    @Test
    public void winCheckerFalse4() {
        Board board = new Board();
        Game game = new Game("Pippo", "Pluto", board, false);
        Player player = game.getPlayers().get(0);
        Worker worker1 = player.getWorker1();
        Worker worker2 = player.getWorker2();
        Box box2 = board.getBox(4,4);
        Box lastBox1 = board.getBox(0, 0);
        Box lastBox2 = board.getBox(3, 4);
        Box box1 = board.getBox(0,1);
        box2.setPawn(worker2);
        worker2.setPos(4,4);
        lastBox1.setBlock(Block.LONE);
        box1.setPawn(worker1);
        worker1.setPos(0,1);

        Win winAction = new PanWin(player);
        assertFalse(winAction.winChecker());
    }

}