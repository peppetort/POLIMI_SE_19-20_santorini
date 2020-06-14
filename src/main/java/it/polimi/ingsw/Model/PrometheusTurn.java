package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Messages.ActionsUpdateMessage;

/**
 * Represents the turn in case the
 * {@link Player} has the card PROMETHEUS
 */
public class PrometheusTurn extends DefaultTurn {

    boolean startBuild;

    /**
     * Constructor of the class {@link PrometheusTurn}
     *
     * @param player
     */
    public PrometheusTurn(Player player) {
        super(player);
    }

    /**
     * Start the turn by setting the {@link Worker} you want to play with.
     * It's the first method to be invoked to perform any other action within the turn.
     *
     * @param worker you want to play with
     */
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
        boolean canGoUp = player.getSession().getCanGoUp();

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

    /**
     * Move the chosen {@link Worker} into the specified coordinates making checks for PROMETHEUS' power
     *
     * @param x coordinate for the board
     * @param y coordinate for the board
     * @throws IndexOutOfBoundsException if chosen coordinates go outside the board limits
     * @throws NullPointerException if you try to move a worker that has null reference
     * @throws CantGoUpException if you try to level up but you can't
     * @throws InvalidMoveException if chosen coordinates are already occupied by another player
     */
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

            if (!player.getSession().getCanGoUp()) { // se è true un giocatore avversario ha usato ATHENA
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
            player.getSession().notify(message);
        }
    }

    /**
     * Build into the specified coordinates making checks for PROMETHEUS' power
     *
     * @param x coordinate for the board
     * @param y coordinate for the board
     * @throws IndexOutOfBoundsException if chosen coordinates go outside the board limits
     * @throws NullPointerException if you try to move a worker that has null reference
     * @throws InvalidBuildException if you try to build too far from {@link Worker} position or into a box containing a DOME
     */
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
        player.getSession().notify(message);

    }

}
