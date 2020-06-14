package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Messages.ActionsUpdateMessage;

/**
 * Represents the turn in case the
 * {@link Player} has the card ARTEMIS
 */
public class ArtemisTurn extends DefaultTurn {

    Integer startX;
    Integer startY;
    boolean oneMove;

    /**
     * Constructor of the class {@link ArtemisTurn}
     *
     * @param player
     */
    public ArtemisTurn(Player player) {
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
        super.start(worker);
        startX = null;
        startY = null;
        oneMove = false;
    }

    /**
     * Move the chosen {@link Worker} into the specified coordinates making checks for ARTEMIS' power
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


        try {
            if (startX == x && startY == y) {
                throw new InvalidMoveException("Can't move worker on this box! It's the starting box");
            }
            if(!player.getSession().getCanGoUp()){
                try {
                    moveAction.moveNoGoUp(worker, x, y);
                }catch (CantGoUpException e){
                    throw new CantGoUpException(e.getMessage() + " Opponent used Athena's power");
                }
            }else {
                moveAction.move(worker, x, y);
            }
            canMove = false;
            win = winAction.winChecker();

            playerMenu.replace(Actions.MOVE, false);

            if(!win) {
                ActionsUpdateMessage message = new ActionsUpdateMessage();
                message.addAction(Actions.BUILD);
                message.addAction(Actions.UNDO);
                player.getSession().notify(message);
            }

        } catch (NullPointerException e) {
            startX = worker.getXPos();
            startY = worker.getYPos();
            if(!player.getSession().getCanGoUp()){
                try {
                    moveAction.moveNoGoUp(worker, x, y);
                }catch (CantGoUpException e1){
                    startX=null;
                    startY=null;
                    throw new CantGoUpException(e1.getMessage() + " Opponent used Athena's power");
                }
            }else {
                moveAction.move(worker, x, y);
            }
            oneMove = true;

            win = winAction.winChecker();

            if(!win) {
                ActionsUpdateMessage message = new ActionsUpdateMessage();
                message.addAction(Actions.MOVE);
                message.addAction(Actions.BUILD);
                message.addAction(Actions.UNDO);
                player.getSession().notify(message);
            }
        }

        canBuild = true;
        playerMenu.replace(Actions.BUILD, true);
    }

    /**
     * Build into the specified coordinates making checks for ARTEMIS' power
     *
     * @param x coordinate for the board
     * @param y coordinate for the board
     * @throws IndexOutOfBoundsException if chosen coordinates go outside the board limits
     * @throws NullPointerException if you try to move a worker that has null reference
     * @throws InvalidBuildException if you try to build too far from {@link Worker} position or into a box containing a DOME
     */
    @Override
    public void build(int x, int y) throws IndexOutOfBoundsException, NullPointerException, InvalidBuildException {
        canMove = false; //una volta costruito non posso più muovere
        playerMenu.replace(Actions.MOVE, false);
        super.build(x, y);
    }

    /**
     * End making ARTEMIS checks in addition to the defaults one
     */
    @Override
    public void end() {
        if (!running) {
            throw new TurnNotStartedException("Turn not started!");
        } else if (!oneMove) {
            throw new RuntimeException("Can't end turn! You have to move!");
        } else if (canBuild) {
            throw new RuntimeException("Can't end turn! You have to build!");
        }
        running = false;
        playerMenu.replace(Actions.END, false);
    }
}
