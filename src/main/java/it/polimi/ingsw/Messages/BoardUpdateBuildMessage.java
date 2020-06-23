package it.polimi.ingsw.Messages;

/**
 * message from server to client used for
 * update the building on the client board
 *
 */

public class BoardUpdateBuildMessage implements Message{

    private final int level;
    private final int x;
    private final int y;

    public BoardUpdateBuildMessage(int x, int y, int level){
        this.level = level;
        this.x = x;
        this.y = y;
    }

    public int getLevel(){
        return this.level;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
}
