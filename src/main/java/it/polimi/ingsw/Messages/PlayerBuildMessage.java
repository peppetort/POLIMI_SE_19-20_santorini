package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player;

public class PlayerBuildMessage implements Message{

    private Player player;
    private int x,y;

    public PlayerBuildMessage(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}