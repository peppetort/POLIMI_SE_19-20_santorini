package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.TurnNotStartedException;

public class AthenaTurn extends DefaultTurn {


    public AthenaTurn(Player player) {
        super(player);
    }

    @Override
    public void move(Worker worker, int x, int y){
        canGoUp = true; //di default tutti possono salire
        Box workerBox = board.getBox(worker.getXPos(), worker.getYPos()); //box iniziale della pedina
        if(!running){
            throw new TurnNotStartedException("Turn not started!");
        }
        if(!canMove){
            throw new RuntimeException("You can't move!");
        }
        moveAction.move(worker, x, y);
        canMove = false;
        canBuild = true;
        win = winAction.winChecker();

        //se la box su cui mi sono mosso ha una costruzione > di
        //quella da cui sono partito, la differenza è > 0.
        //Quindi vuol dire che sono salito di livello
        if(board.getBox(x, y).getDifference(workerBox) > 0){
            canGoUp = false;
        }
    }
}
