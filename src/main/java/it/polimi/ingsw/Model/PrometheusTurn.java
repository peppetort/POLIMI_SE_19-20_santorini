package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Messages.ActionsUpdateMessage;

public class PrometheusTurn extends DefaultTurn {

    boolean startBuild;

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

            running = true;
            this.worker = worker;
            canMove = true; // abilita la mossa
            canBuild = true;
            startBuild = false;
            playerMenu.replace(Actions.SELECT, false);
            playerMenu.replace(Actions.MOVE, true);
            playerMenu.replace(Actions.UNDO, true);

            //TODO: da verificare

            playerMenu.replace(Actions.BUILD, true);

            ActionsUpdateMessage message = new ActionsUpdateMessage();
            message.addAction(Actions.BUILD);
            message.addAction(Actions.MOVE);
            message.addAction(Actions.UNDO);

            player.notify(message);
        }
    }

    @Override
    public void move(int x, int y) throws IndexOutOfBoundsException, NullPointerException, CantGoUpException, InvalidMoveException {
        if (!running) {
            throw new TurnNotStartedException("Turn not started!");
        }
        if (!canMove) {
            throw new RuntimeException("You can't move!");
        }


        ActionsUpdateMessage message = new ActionsUpdateMessage();

        //TODO: da verificare

        if(startBuild){
            try {
                moveAction.moveNoGoUp(worker, x, y);
            }catch (CantGoUpException e){
                throw new CantGoUpException(e.getMessage() + " You used Prometheus' power");
            }
        }else {
            startBuild = true;

            if (!canGoUp) { // se è true un giocatore avversario ha usato ATHENA
                try {
                    moveAction.moveNoGoUp(worker, x, y);
                }catch (CantGoUpException e){
                    throw new CantGoUpException(e.getMessage() + " Opponent used Athena's power");
                }
            } else {
                moveAction.move(worker, x, y);
            }
        }

        canMove = false;
        canBuild = true;
        playerMenu.replace(Actions.BUILD, true);
        playerMenu.replace(Actions.MOVE, false);
        message.addAction(Actions.BUILD);

        win = winAction.winChecker();

        if(!win) {
            message.addAction(Actions.UNDO);
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

        ActionsUpdateMessage message = new ActionsUpdateMessage();


        if (!startBuild) {
            startBuild = true;
            message.addAction(Actions.MOVE);
        }else {
            playerMenu.replace(Actions.END, true);
            message.addAction(Actions.END);
        }

        message.addAction(Actions.UNDO);
        player.notify(message);

    }

}
