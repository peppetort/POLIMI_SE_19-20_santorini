package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidBuildException;
import it.polimi.ingsw.Exceptions.TurnNotStartedException;
import it.polimi.ingsw.Messages.ActionsUpdateMessage;

public class AtlasTurn extends DefaultTurn {
    public AtlasTurn(Player player){super(player);}
    public void buildDome(int x, int y) throws IndexOutOfBoundsException, NullPointerException, InvalidBuildException {
        if (!running) {
            throw new TurnNotStartedException("Turn not started!");
        }
        if (!canBuild) {
            throw new RuntimeException("You can't build!");
        }
        buildAction.buildDome(worker, x, y);
        canBuild = false;
        playerMenu.replace("build", false);
        playerMenu.replace("end", true);

        ActionsUpdateMessage message = new ActionsUpdateMessage();
        message.addAction("end");
        player.notify(message);

    }
}
