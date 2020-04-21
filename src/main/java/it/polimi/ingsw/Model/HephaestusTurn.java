package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.*;
import it.polimi.ingsw.Messages.BoardUpdate;
import it.polimi.ingsw.Messages.MenuMessage;

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
    public void start(Worker worker) throws IndexOutOfBoundsException, NullPointerException, AthenaGoUpException, InvalidMoveException {
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
            playerMenu.replace("build", false);
        } catch (NullPointerException e) {
            buildAction.build(worker, x, y);
            oneBuild = true;

            //se la prima volta che costruisco, costruisco un livello
            //tre, allora non posso usare il potere della carta => disabilito la costruzione
            if (board.getBox(x, y).getBlock() == Block.LTHREE) {
                canBuild = false;
            } else {
                lastX = x;
                lastY = y;
            }
        }
        playerMenu.replace("end", true);
        player.notify(new MenuMessage(playerMenu));
        player.notify(new BoardUpdate(board.data(),player.getSession().getPlayers()));

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
        playerMenu.replace("end", false);
        playerMenu.replace("build", false);
    }
}
