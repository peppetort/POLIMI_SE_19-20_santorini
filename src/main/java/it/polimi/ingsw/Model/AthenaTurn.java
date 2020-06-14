package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidMoveException;
import it.polimi.ingsw.Exceptions.TurnNotStartedException;
import it.polimi.ingsw.Messages.ActionsUpdateMessage;

/**
 * Represents the turn in case the
 * {@link Player} has the card ATHENA
 */
public class AthenaTurn extends DefaultTurn {

    /**
     * Constructor of the class {@link AthenaTurn}
     *
     * @param player
     */
    public AthenaTurn(Player player) {
        super(player);
    }

    /**
     * Move the chosen {@link Worker} into the specified coordinates.
     * It set a variable into {@link Game} that controls the possibility of leveling up or not
     *
     * @param x coordinate for the board
     * @param y coordinate for the board
     * @throws IndexOutOfBoundsException if chosen coordinates go outside the board limits
     * @throws NullPointerException if you try to move a worker that has null reference
     * @throws InvalidMoveException if chosen coordinates are already occupied by another player
     */
    @Override
    public void move(int x, int y) throws IndexOutOfBoundsException, NullPointerException, InvalidMoveException {
        player.getSession().updateCanGoUp(true); //di default tutti possono salire
        Box workerBox = board.getBox(worker.getXPos(), worker.getYPos()); //box iniziale della pedina
        if (!running) {
            throw new TurnNotStartedException("Turn not started!");
        }
        if (!canMove) {
            throw new RuntimeException("You can't move!");
        }
        moveAction.move(worker, x, y);
        canMove = false;
        canBuild = true;
        playerMenu.replace(Actions.MOVE, false);
        playerMenu.replace(Actions.BUILD, true);
        win = winAction.winChecker();

        if(!win) {
            ActionsUpdateMessage message = new ActionsUpdateMessage();
            message.addAction(Actions.BUILD);
            message.addAction(Actions.UNDO);
            //player.notify(message);
            player.getSession().notify(message);
        }

        //se la box su cui mi sono mosso ha una costruzione > di
        //quella da cui sono partito, la differenza è > 0.
        //Quindi vuol dire che sono salito di livello
        if (board.getBox(x, y).getDifference(workerBox) > 0) {
            player.getSession().updateCanGoUp(false);
        }
    }
}
