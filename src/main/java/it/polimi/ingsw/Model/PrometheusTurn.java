package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.AthenaGoUpException;
import it.polimi.ingsw.Exceptions.InvalidBuildException;
import it.polimi.ingsw.Exceptions.InvalidMoveException;

public class PrometheusTurn extends DefaultTurn {

    boolean startBuild;

    public PrometheusTurn(Player player) {
        super(player);
    }

    @Override
    public void start(Worker worker) {
        super.start(worker);
        if (!worker.moveGoUp()) {
            canMove = true;
            canBuild = true;
            playerMenu.replace("build", true);
        }
        startBuild = false;
    }

    @Override
    public void move(int x, int y) throws IndexOutOfBoundsException, NullPointerException, AthenaGoUpException, InvalidMoveException {
        super.move(x, y);
        startBuild = true;
    }


    @Override
    public void build(int x, int y) throws IndexOutOfBoundsException, NullPointerException, InvalidBuildException {
        super.build(x, y);
        if (!startBuild) {
            startBuild = true;
            playerMenu.replace("build", false);
        }
    }
}
