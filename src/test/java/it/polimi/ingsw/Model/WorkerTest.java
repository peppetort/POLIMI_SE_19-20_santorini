package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {

    @Test
    public void setPos() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker=new Worker(1, player);
        worker.setPos(0, 0);
        assertEquals(0, (int)worker.getXPos());
        assertEquals(0, (int)worker.getYPos());
    }

    @Test (expected = RuntimeException.class)
    public void setPosInvalidX() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker=new Worker(1, player);
        worker.setPos(-1, 0);
    }

    @Test (expected = RuntimeException.class)
    public void setPosInvalidY() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker=new Worker(1, player);
        worker.setPos(0, 6);
    }

    @Test (expected = NullPointerException.class)
    public void getXPosNotInizialized(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker=new Worker(1, player);
        worker.getXPos();
    }

    @Test (expected = NullPointerException.class)
    public void getYPosNotInizialized(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker=new Worker(1, player);
        worker.getYPos();
    }

    @Test
    public void updateLastBoxNotSameObject() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker=new Worker(1, player);
        Box box = new Box();
        worker.updateLastBox(box);
        assertNotEquals(box, worker.getLastBox());
    }

    @Test
    public void updateLastBoxSamePawn() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker=new Worker(1, player);
        Box box = new Box();
        worker.updateLastBox(box);
        assertEquals(box.getPawn(), worker.getLastBox().getPawn());
    }

    @Test
    public void canBuildFalse() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker w1=new Worker(1, player);
        Worker w2=new Worker(2, player);
        board.placePawn(w1,0,0);
        board.placePawn(w2,1,1);
        board.getBox(0,1).build(Block.DOME);
        board.getBox(1,0).build(Block.DOME);
        assertFalse(w1.canBuild());
    }

    @Test
    public void canBuildTrue() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker w1=new Worker(1, player);
        Worker w2=new Worker(2, player);
        board.placePawn(w1,0,0);
        board.placePawn(w2,1,1);
        board.getBox(0,1).build(Block.LTHREE);
        board.getBox(1,0).build(Block.DOME);
        assertTrue(w1.canBuild());
    }


    @Test
    public void canMoveFalse() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker w1=new Worker(1, player);
        Worker w2=new Worker(2, player);
        board.placePawn(w1,0,0);
        board.placePawn(w2,1,1);
        board.getBox(0,1).build(Block.LTWO);
        board.getBox(1,0).build(Block.LTWO);
        assertFalse(w1.canMove(true));
    }

    @Test
    public void canMoveTrue() {
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, true);
        Player player = session.getPlayers().get(0);
        Worker worker=new Worker(1, player);
        board.placePawn(worker,4,4);
        board.getBox(4,3).build(Block.LTWO);
        board.getBox(3,4).build(Block.LTWO);
        assertTrue(worker.canMove(true));
    }

    @Test
    public void canMoveApolloTrue(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, false);
        Player player1 = session.getPlayers().get(0);
        Player player2 = session.getPlayers().get(1);
        Card apollo = new Card(God.APOLLO);
        player1.setCard(apollo);
        Worker wp1 = player1.getWorker1();
        Worker wp2 = player2.getWorker2();
        board.placePawn(wp1, 0, 0);
        board.placePawn(wp2, 1,1);
        board.getBox(0,1).build(Block.LTWO);
        board.getBox(1,0).build(Block.LTWO);
        assertTrue(wp1.canMove(true));
    }

    @Test
    public void canMoveApolloCantBuild(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, false);
        Player player1 = session.getPlayers().get(0);
        Player player2 = session.getPlayers().get(1);
        Card apollo = new Card(God.APOLLO);
        player1.setCard(apollo);
        Worker wp1 = player1.getWorker1();
        Worker wp2 = player2.getWorker2();
        board.placePawn(wp1, 0, 0);
        board.placePawn(wp2, 1,1);
        board.getBox(0,1).build(Block.DOME);
        board.getBox(0,2).build(Block.DOME);
        board.getBox(1,0).build(Block.DOME);
        board.getBox(2,0).build(Block.DOME);
        board.getBox(2,2).build(Block.DOME);
        board.getBox(2,1).build(Block.DOME);
        board.getBox(1,2).build(Block.DOME);
        assertFalse(wp1.canMove( true));
    }

    @Test
    public void canMoveMinotaurTrue(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, false);
        Player player1 = session.getPlayers().get(0);
        Player player2 = session.getPlayers().get(1);
        Card minotaur = new Card(God.MINOTAUR);
        player1.setCard(minotaur);
        Worker wp1 = player1.getWorker1();
        Worker wp2 = player2.getWorker1();
        board.placePawn(wp1, 0, 0);
        board.placePawn(wp2, 1,1);
        board.getBox(0,1).build(Block.LTWO);
        board.getBox(1,0).build(Block.LTWO);
        assertTrue(wp1.canMove(true));
    }

    @Test
    public void canMoveMinotaurFalse(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, false);
        Player player1 = session.getPlayers().get(0);
        Player player2 = session.getPlayers().get(1);
        Card minotaur = new Card(God.MINOTAUR);
        player1.setCard(minotaur);
        Worker wp1 = player1.getWorker1();
        Worker wp2 = player2.getWorker2();
        board.placePawn(wp1, 0, 0);
        board.placePawn(wp2, 1,1);
        board.getBox(0,1).build(Block.DOME);
        board.getBox(0,2).build(Block.DOME);
        board.getBox(1,0).build(Block.DOME);
        board.getBox(2,0).build(Block.DOME);
        board.getBox(2,2).build(Block.LTHREE);
        board.getBox(2,1).build(Block.DOME);
        board.getBox(1,2).build(Block.DOME);
        assertFalse(wp1.canMove(true));
    }

    @Test
    public void canMoveApolloVsMinotaur(){
        Board board = new Board();
        Game session = new Game("Pippo", "Pluto", board, false);
        Player player1 = session.getPlayers().get(0);
        Player player2 = session.getPlayers().get(1);
        Card minotaur = new Card(God.MINOTAUR);
        Card apollo = new Card(God.APOLLO);
        player1.setCard(minotaur);
        player2.setCard(apollo);
        Worker wp1 = player1.getWorker1();
        Worker wp2 = player2.getWorker2();
        board.placePawn(wp1, 0, 0);
        board.placePawn(wp2, 1,1);
        board.getBox(0,1).build(Block.LTWO);
        board.getBox(0,2).build(Block.DOME);
        board.getBox(1,0).build(Block.DOME);
        board.getBox(2,0).build(Block.DOME);
        board.getBox(2,2).build(Block.DOME);
        board.getBox(2,1).build(Block.DOME);
        board.getBox(1,2).build(Block.DOME);
        assertFalse(wp1.canMove(true));
    }
}