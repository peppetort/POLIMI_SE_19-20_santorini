package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Messages.ActionsUpdateMessage;

public class ArtemisTurn extends DefaultTurn {

    Integer startX;
    Integer startY;
    boolean oneMove;

    public ArtemisTurn(Player player) {
        super(player);
    }

    @Override
    public void start(Worker worker) {
        super.start(worker);
        startX = null;
        startY = null;
        oneMove = false;
    }

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
            if(!canGoUp){
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
                //player.notify(message);
                player.getSession().notify(message);
            }

        } catch (NullPointerException e) {
            startX = worker.getXPos();
            startY = worker.getYPos();
            if(!canGoUp){
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
                //player.notify(message);
                player.getSession().notify(message);
            }
        }

        canBuild = true;
        playerMenu.replace(Actions.BUILD, true);
    }

    @Override
    public void build(int x, int y) throws IndexOutOfBoundsException, NullPointerException, InvalidBuildException {
        canMove = false; //una volta costruito non posso più muovere
        playerMenu.replace(Actions.MOVE, false);
        super.build(x, y);
    }

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
