package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player;

public class PlayerBuildDomeMessage implements Message{

    Player player;
    int x,y;

    public PlayerBuildDomeMessage(Player player, int x, int y){
        this.player = player;
        this.x = x;
        this.y = y;
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