package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidMoveException;
import it.polimi.ingsw.Exceptions.TurnNotStartedException;
import it.polimi.ingsw.Messages.ActionsUpdateMessage;

public class AthenaTurn extends DefaultTurn {


    public AthenaTurn(Player player) {
        super(player);
    }

    @Override
    public void move(int x, int y) throws IndexOutOfBoundsException, NullPointerException, InvalidMoveException {
        canGoUp = true; //di default tutti possono salire
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
        playerMenu.replace("move", false);
        playerMenu.replace("build", true);
        win = winAction.winChecker();

        ActionsUpdateMessage message = new ActionsUpdateMessage();
        message.addAction("build");
        //TODO: verificare che l'UNDO riporti canGoUp a true nel caso il giocatore sia salito di livello
        message.addAction("undo");
        player.notify(message);

        //se la box su cui mi sono mosso ha una costruzione > di
        //quella da cui sono partito, la differenza è > 0.
        //Quindi vuol dire che sono salito di livello
        if (board.getBox(x, y).getDifference(workerBox) > 0) {
            canGoUp = false;
        }
    }
}
