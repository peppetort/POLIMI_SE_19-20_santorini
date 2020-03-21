package it.polimi.ingsw.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultMoveTest {

    @Test(expected = RuntimeException.class)
    public void move() {
        Board board = new Board();
        Game game = new Game("Marco","Giuseppe",board,true);
        Player player = new Player("Marco",game);
        DefaultMove mover = new DefaultMove(player);
        DefaultBuild builder = new DefaultBuild(player);
        Worker worker = player.getWorker1();

        worker.setPos(0,0);
        mover.move(worker,1,1);
        assertEquals(1,worker.getXPos());
        assertEquals(1,worker.getYPos());
        assertEquals(worker,board.getBox(1,1).getPawn());

        worker.setPos(0,0);
        mover.move(worker,0,1);
        assertEquals(0,worker.getXPos());
        assertEquals(1,worker.getYPos());
        assertEquals(worker,board.getBox(0,1).getPawn());

        worker.setPos(0,0);
        mover.move(worker,1,0);
        assertEquals(1,worker.getXPos());
        assertEquals(0,worker.getYPos());
        assertEquals(worker,board.getBox(1,0).getPawn());

        worker.setPos(0,0);
        mover.move(worker,0,2); // deve lanciare eccezione non posso muovermi di due celle
        assertEquals(0,worker.getXPos());
        assertEquals(0,worker.getYPos());
        assertEquals(worker,board.getBox(0,0).getPawn());

        worker.setPos(0,0);
        mover.move(worker,2,0);
        assertEquals(0,worker.getXPos());
        assertEquals(0,worker.getYPos());
        assertEquals(worker,board.getBox(0,0).getPawn());

        worker.setPos(0,0);
        mover.move(worker,2,2);
        assertEquals(0,worker.getXPos());
        assertEquals(0,worker.getYPos());
        assertEquals(worker,board.getBox(0,0).getPawn());

        worker.setPos(0,0);
        mover.move(worker,-1,-1); //muovermi su indici negativi
        assertEquals(0,worker.getXPos());
        assertEquals(0,worker.getYPos());
        assertEquals(worker,board.getBox(0,0).getPawn());

        worker.setPos(0,0);

        builder.build(1,1);

        mover.move(worker,1,1);
        assertEquals(1,worker.getXPos());
        assertEquals(1,worker.getYPos());
        assertEquals(worker,board.getBox(1,1).getPawn());

        builder.build(0,1);
        builder.build(0,1);

        mover.move(worker,0,1);
        assertEquals(0,worker.getXPos());
        assertEquals(0,worker.getYPos());
        assertEquals(worker,board.getBox(0,1).getPawn()); //il giocatore si trova su un livello due

        builder.build(1,1);
        builder.build(1,1); //in 1,1 ho livello 3

        mover.move(worker,1,1);
        assertEquals(1,worker.getXPos());
        assertEquals(1,worker.getYPos());
        assertEquals(worker,board.getBox(1,1).getPawn()); //il giocatore è su un livello 3

        builder.build(0,1);
        builder.build(0,1); // 0,1 ha una dome

        mover.move(worker,0,1); //non posso muovermi sulla dome
        assertEquals(1,worker.getXPos());
        assertEquals(1,worker.getYPos());
        assertEquals(worker,board.getBox(1,1).getPawn());


        mover.move(worker,0,0);
        assertEquals(0,worker.getXPos());
        assertEquals(0,worker.getYPos());
        assertEquals(worker,board.getBox(0,0).getPawn()); //il giocatore scende a livello 0

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        builder.build(2,2);

        builder.build(1,2);
        builder.build(1,2);

        builder.build(2,1);
        builder.build(2,1);
        builder.build(2,1);

        worker.setPos(2,1);   ///////////worker su lvl 3 lo sposto su livello 2
        mover.move(worker,1,2);
        assertEquals(1,worker.getXPos());
        assertEquals(2,worker.getYPos());
        assertEquals(worker,board.getBox(1,2).getPawn());

        worker.setPos(2,1); // worker su livello 3 lo sposto su livello 1
        mover.move(worker,2,2);
        assertEquals(2,worker.getXPos());
        assertEquals(2,worker.getYPos());
        assertEquals(worker,board.getBox(2,2).getPawn());

        worker.setPos(2,1); // worker su livello 3 lo sposto su livello 0
        mover.move(worker,2,3);
        assertEquals(2,worker.getXPos());
        assertEquals(3,worker.getYPos());
        assertEquals(worker,board.getBox(2,3).getPawn());

        /////////////////////////////////////////// 2-1 lvl3 1-2 lvl2 2-2 lvl1

        worker.setPos(2,2);
        mover.move(worker,1,2);
        assertEquals(1,worker.getXPos());
        assertEquals(2,worker.getYPos());
        assertEquals(worker,board.getBox(1,2).getPawn());

        worker.setPos(1,2);
        mover.move(worker,2,1);
        assertEquals(2,worker.getXPos());
        assertEquals(1,worker.getYPos());
        assertEquals(worker,board.getBox(2,1).getPawn());

        worker.setPos(2,2);
        mover.move(worker,2,1);
        assertEquals(2,worker.getXPos());
        assertEquals(2,worker.getYPos());
        assertEquals(worker,board.getBox(2,2).getPawn());

        ////////////////////////////////////////////////////////////////////////////////

        builder.build(3,3);
        builder.build(3,4);

        worker.setPos(3,3);
        mover.move(worker,3,4);
        assertEquals(3,worker.getXPos());
        assertEquals(4,worker.getYPos());
        assertEquals(worker,board.getBox(3,4).getPawn());

        worker.setPos(0,0);

        builder.build(3,3);
        builder.build(3,4);
        mover.move(worker,3,4);
        assertEquals(3,worker.getXPos());
        assertEquals(4,worker.getYPos());
        assertEquals(worker,board.getBox(3,4).getPawn());

        worker.setPos(0,0);

        builder.build(3,3);
        builder.build(3,4);
        mover.move(worker,3,4);
        assertEquals(3,worker.getXPos());
        assertEquals(4,worker.getYPos());
        assertEquals(worker,board.getBox(3,4).getPawn());


        Worker worker2 = player.getWorker2();
        worker2.setPos(3,3);
        mover.move(worker,3,3);
        assertEquals(3,worker.getXPos());
        assertEquals(4,worker.getYPos());
        assertEquals(worker,board.getBox(3,4).getPawn());

        worker.setPos(4,4);
        mover.move(worker,4,5);
        assertEquals(4,worker.getXPos());
        assertEquals(4,worker.getYPos());
        assertEquals(worker,board.getBox(4,4).getPawn());

    }
}