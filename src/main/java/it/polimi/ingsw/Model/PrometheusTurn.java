package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Messages.BoardUpdate;
import it.polimi.ingsw.Messages.MenuMessage;

public class PrometheusTurn extends DefaultTurn {

    boolean startBuild;

    public PrometheusTurn(Player player) {
        super(player);
    }

//    @Override
//    public void start(Worker worker) {
//        super.start(worker);
//        if (!worker.moveGoUp()) {
//            canMove = true;
//            canBuild = true;
//            playerMenu.replace("build", true);
//        }
//        startBuild = false;
//    }

//    @Override
//    public void move(int x, int y) throws IndexOutOfBoundsException, NullPointerException, AthenaGoUpException, InvalidMoveException {
//        super.move(x, y);
//        startBuild = true;
//
//    }


//    @Override
//    public void build(int x, int y) throws IndexOutOfBoundsException, NullPointerException, InvalidBuildException {
//        super.build(x, y);
//        if (!startBuild) {
//            startBuild = true;
//            playerMenu.replace("build", false);
//        }
//    }

    @Override
    public void start(Worker worker) {

        if(player.getWorker1().equals(worker)){
            otherWorker = player.getWorker2();
        }else {
            otherWorker = player.getWorker1();
        }

        if(!playerMenu.get("start")){
            throw new RuntimeException("Can't start!");
        }
        if (running) { // controlla che il turno non è stato già iniziato
            throw new RuntimeException("Already start!");
        }
        if (!worker.canMove(canGoUp) && !otherWorker.canMove(canGoUp)) { //controlla che il giocatore ha almeno una possibilità di muoversi
            throw new PlayerLostException("Your workers cannot make any moves!");
        }
        running = true;
        this.worker = worker;
        canMove = true; // abilita la mossa
        canBuild = false;
        playerMenu.replace("start", false);
        playerMenu.replace("move", true);

        if (!worker.moveGoUp()) {
            canMove = true;
            canBuild = true;
            playerMenu.replace("build", true);
        }
        startBuild = false;

        player.notify(new MenuMessage(playerMenu));
        player.notify(new BoardUpdate(board.data(),player.getSession().getPlayers()));

    }

    @Override
    public void move(int x, int y) throws IndexOutOfBoundsException, NullPointerException, AthenaGoUpException, InvalidMoveException {
        super.move(x, y);
        startBuild = true;

    }

    @Override
    public void build(int x, int y) throws IndexOutOfBoundsException, NullPointerException, InvalidBuildException {
        if (!running) {
            throw new TurnNotStartedException("Turn not started!");
        }
        if (!canBuild) {
            throw new RuntimeException("You can't build!");
        }
        buildAction.build(worker, x, y);
        canBuild = false;
        playerMenu.replace("build", false);
        playerMenu.replace("end", true);
        if (!startBuild) {
            startBuild = true;
            playerMenu.replace("build", false);
        }

        player.notify(new MenuMessage(playerMenu));
        player.notify(new BoardUpdate(board.data(),player.getSession().getPlayers()));

    }

}
