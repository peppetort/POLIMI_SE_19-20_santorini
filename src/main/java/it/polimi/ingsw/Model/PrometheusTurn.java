package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Messages.ActionsUpdateMessage;

public class PrometheusTurn extends DefaultTurn {

    boolean startBuild;
    Box startingBox = null;

    public PrometheusTurn(Player player) {
        super(player);
    }

    @Override
    public void start(Worker worker) {

        if(player.getWorker1().equals(worker)){
            otherWorker = player.getWorker2();
        }else {
            otherWorker = player.getWorker1();
        }

        if(!playerMenu.get(Actions.SELECT)){
            throw new RuntimeException("Can't start!");
        }
        if (running) { // controlla che il turno non è stato già iniziato
            throw new RuntimeException("Already start!");
        }
        boolean canMoveSelected = worker.canMove(canGoUp);
        boolean canMoveOther = otherWorker.canMove(canGoUp);

        if(!canMoveSelected && !canMoveOther){
            throw new PlayerLostException("Your workers cannot make any moves!");
        }else if(!canMoveSelected){
            throw new RuntimeException("Selected worker cannot make any moves");
        }else {

            startingBox = board.getBox(worker.getXPos(), worker.getYPos());

            running = true;
            this.worker = worker;
            canMove = true; // abilita la mossa
            canBuild = false;
            playerMenu.replace(Actions.SELECT, false);
            playerMenu.replace(Actions.MOVE, true);
            playerMenu.replace(Actions.UNDO, true);

            ActionsUpdateMessage message = new ActionsUpdateMessage();
            message.addAction(Actions.MOVE);

            message.addAction(Actions.MOVE);
            if (!worker.moveGoUp()) {
                canMove = true;
                canBuild = true;
                playerMenu.replace(Actions.BUILD, true);

                message.addAction(Actions.BUILD);
            }

            player.notify(message);
            startBuild = false;
        }
    }

    @Override
    public void move(int x, int y) throws IndexOutOfBoundsException, NullPointerException, AthenaGoUpException, InvalidMoveException {
        if (!running) {
            throw new TurnNotStartedException("Turn not started!");
        }
        if (!canMove) {
            throw new RuntimeException("You can't move!");
        }
        if (!canGoUp) { // se è true un giocatore avversario ha usato ATHENA
            moveAction.moveNoGoUp(worker, x, y);
        } else {
            moveAction.move(worker, x, y);
        }
        canMove = false;
        playerMenu.replace(Actions.MOVE, false);
        startBuild = true;

        win = winAction.winChecker();


        ActionsUpdateMessage message;

        if(startingBox != null && startingBox.getDifference(board.getBox(x, y)) < 0 ){
            canBuild = false;
            playerMenu.replace(Actions.BUILD, false);
            playerMenu.replace(Actions.END, true);
            message = new ActionsUpdateMessage();
        }else {
            canBuild = true; // abilita la costruzione
            playerMenu.replace(Actions.BUILD, true);
            playerMenu.replace(Actions.END, true);
            message = new ActionsUpdateMessage();
            message.addAction(Actions.BUILD);
        }

        if(!win) {
            message.addAction(Actions.UNDO);
            message.addAction(Actions.END);
            player.notify(message);
        }
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
        playerMenu.replace(Actions.BUILD, false);
        playerMenu.replace(Actions.END, true);

        ActionsUpdateMessage message = new ActionsUpdateMessage();
        message.addAction(Actions.UNDO);

        if (!startBuild) {
            startBuild = true;
            playerMenu.replace(Actions.BUILD, false);
            message.addAction(Actions.MOVE);
        }else {
            message.addAction(Actions.END);
        }

        player.notify(message);

    }

}
