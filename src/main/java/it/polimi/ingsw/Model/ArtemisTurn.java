package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidBuildException;
import it.polimi.ingsw.Exceptions.TurnNotStartedException;

public class ArtemisTurn extends DefaultTurn {

    Integer startX;
    Integer startY;
    boolean oneMove;

    public ArtemisTurn(Player player) {
        super(player);
    }

    @Override
    public void start(){
        super.start();
        startX = null;
        startY = null;
        oneMove = false;
    }

    @Override
    public void move(Worker worker, int x, int y) {
        if(!running){
            throw new TurnNotStartedException("Turn not started!");
        }
        if(!canMove){
            throw new RuntimeException("You can't move!");
        }

        if(!canGoUp){
            try{
                if (startX == x && startY == y) {
                    throw new InvalidBuildException("Can't build here!");
                }
                moveAction.moveNoGoUp(worker, x, y);
                canMove = false;
            }catch (NullPointerException e){
                moveAction.moveNoGoUp(worker, x, y);
                startX = x;
                startY = y;
                oneMove = true;
            }
        }else {
            try{
                if (startX == x && startY == y) {
                    throw new InvalidBuildException("Can't build here!");
                }
                moveAction.move(worker, x, y);
                canMove = false;
            }catch (NullPointerException e){
                moveAction.move(worker, x, y);
                startX = x;
                startY = y;
                oneMove = true;
            }
        }
        canBuild = true;
    }

    @Override
    public void build(Worker worker, int x, int y){
        canMove = false; //una volta costruito non posso più muovere
        super.build(worker, x, y);
    }

    @Override
    public void end(){
        if(!oneMove){
            throw new RuntimeException("Can't end turn! You have to move!");
        }else if(canBuild){
            throw new RuntimeException("Can't end turn! You have to build!");
        }
        running = false;
    }
}
