package it.polimi.ingsw.Messages;

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
