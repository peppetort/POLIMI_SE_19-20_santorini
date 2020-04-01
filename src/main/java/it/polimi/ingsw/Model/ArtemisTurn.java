package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidMoveException;
import it.polimi.ingsw.Exceptions.TurnNotStartedException;

public class ArtemisTurn extends DefaultTurn {

    Integer startX;
    Integer startY;
    boolean oneMove;

    public ArtemisTurn(Player player) {
        super(player);
    }

    @Override
    public void start(Worker worker){
        super.start(worker);
        startX = null;
        startY = null;
        oneMove = false;
    }

    @Override
    public void move(int x, int y) {
        if(!running){
            throw new TurnNotStartedException("Turn not started!");
        }
        if(!canMove){
            throw new RuntimeException("You can't move!");
        }

        if(!canGoUp){
            try{
                if (startX == x && startY == y) {
                    throw new InvalidMoveException("Can't move worker on this box!");
                }
                moveAction.moveNoGoUp(worker, x, y);
                canMove = false;
            }catch (NullPointerException e){
                startX = worker.getXPos();
                startY = worker.getYPos();
                moveAction.moveNoGoUp(worker, x, y);
                oneMove = true;
            }
        }else {
            try{
                if (startX == x && startY == y) {
                    throw new InvalidMoveException("Can't move worker on this box!");
                }
                moveAction.move(worker, x, y);
                canMove = false;
            }catch (NullPointerException e){
                startX = worker.getXPos();
                startY = worker.getYPos();
                moveAction.move(worker, x, y);
                oneMove = true;
            }
        }
        canBuild = true;
    }

    @Override
    public void build(int x, int y){
        canMove = false; //una volta costruito non posso più muovere
        super.build(x, y);
    }

    @Override
    public void end(){
        if(!running){
            throw new TurnNotStartedException("Turn not started!");
        }else if(!oneMove){
            throw new RuntimeException("Can't end turn! You have to move!");
        }else if(canBuild){
            throw new RuntimeException("Can't end turn! You have to build!");
        }
        running = false;
    }
}
