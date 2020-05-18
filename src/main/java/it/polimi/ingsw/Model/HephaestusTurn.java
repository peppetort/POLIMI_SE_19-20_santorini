package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Messages.ActionsUpdateMessage;

public class HephaestusTurn extends DefaultTurn {

    Integer lastX;
    Integer lastY;
    boolean oneBuild;

    public HephaestusTurn(Player player) {
        super(player);
        lastX = null;
        lastY = null;
    }

    @Override
    public void start(Worker worker) throws IndexOutOfBoundsException, NullPointerException, CantGoUpException, InvalidMoveException {
        super.start(worker);
        lastX = null;
        lastY = null;
        oneBuild = false;
    }


    @Override
    public void build(int x, int y) throws IndexOutOfBoundsException, NullPointerException, InvalidBuildException {
        if (!running) {
            throw new TurnNotStartedException("Turn not started!");
        }
        if (!canBuild) {
            throw new RuntimeException("You can't build!");
        }
        try {
            //controllo che la seconda volta che costruisco, costruisco sulla stessa
            //box della prima
            if (lastX != x || lastY != y) {
                throw new InvalidBuildException("Can't build here!");
            }
            buildAction.build(worker, x, y);
            canBuild = false;
            playerMenu.replace(Actions.BUILD, false);

            ActionsUpdateMessage message = new ActionsUpdateMessage();
            message.addAction(Actions.END);
            message.addAction(Actions.UNDO);
            player.notify(message);
        } catch (NullPointerException e) {
            buildAction.build(worker, x, y);
            oneBuild = true;


            ActionsUpdateMessage message = new ActionsUpdateMessage();
            message.addAction(Actions.BUILD);
            message.addAction(Actions.END);
            message.addAction(Actions.UNDO);
            player.notify(message);

            //se la prima volta che costruisco, costruisco un livello
            //tre, allora non posso usare il potere della carta => disabilito la costruzione
            if (board.getBox(x, y).getBlock() == Block.LTHREE) {
                canBuild = false;
            } else {
                lastX = x;
                lastY = y;
            }
        }
        playerMenu.replace(Actions.END, true);

    }

    @Override
    public void end() {
        if (!running) {
            throw new TurnNotStartedException("Turn not started!");
        } else if (canMove) {
            throw new RuntimeException("Can't end turn! You have to move!");
        } else if (!oneBuild) {
            throw new RuntimeException("Can't end turn! You have to build!");
        }
        running = false;
        playerMenu.replace(Actions.END, false);
        playerMenu.replace(Actions.BUILD, false);
    }
}
