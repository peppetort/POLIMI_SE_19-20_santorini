package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidBuildException;
import it.polimi.ingsw.Exceptions.TurnNotStartedException;
import it.polimi.ingsw.Messages.ActionsUpdateMessage;

public class DemeterTurn extends DefaultTurn {

    Integer lastX;
    Integer lastY;
    boolean oneBuild;


    public DemeterTurn(Player player) {
        super(player);
        lastX = null;
        lastY = null;
    }

    @Override
    public void start(Worker worker) {
        super.start(worker);
        lastX = null;
        lastY = null;
        oneBuild = false;
    }

    @Override
    public void build(int x, int y) throws IndexOutOfBoundsException, NullPointerException, InvalidBuildException {
        if (!running) {
            throw new RuntimeException("Turn not started!");
        }
        if (!canBuild) {
            throw new RuntimeException("You can't build!");
        }

        try {
            // controllo che quando voglio costruire la seconda volta
            // non costruisco sulla stessa posizione della prima
            if (lastX == x && lastY == y) {
                throw new InvalidBuildException("Can't build here!");
            }
            buildAction.build(worker, x, y);
            canBuild = false; //non posso più costruire
            playerMenu.replace(Actions.BUILD, false);

            ActionsUpdateMessage message = new ActionsUpdateMessage();
            message.addAction(Actions.END);
            message.addAction(Actions.UNDO);
           // player.notify(message);
            player.getSession().notify(message);
        } catch (NullPointerException e) { // vuol dire che è la prima volta che costruisco poichè lastX lastY sono null
            buildAction.build(worker, x, y);
            //salvo la posizione della prima costruzione
            lastX = x;
            lastY = y;
            oneBuild = true; //indico che ho costruito almeno una volta

            playerMenu.replace(Actions.BUILD ,true);

            ActionsUpdateMessage message = new ActionsUpdateMessage();
            message.addAction(Actions.BUILD);
            message.addAction(Actions.END);
            message.addAction(Actions.UNDO);
           // player.notify(message);
            player.getSession().notify(message);
        }
        playerMenu.replace(Actions.END ,true);
    }

    @Override
    public void end() {
        if (!running) {
            throw new TurnNotStartedException("Turn not started!");
        } else if (canMove) {
            throw new RuntimeException("Can't end turn! You have to move!");
        } else if (!oneBuild) { //controllo di aver costruito almeno una volta
            throw new RuntimeException("Can't end turn! You have to build!");
        }
        running = false;
        playerMenu.replace(Actions.END, false);
        playerMenu.replace(Actions.BUILD, false);
    }
}
