package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Model.Player;


/**
 * message from client to server used to move the worker selected
 *
 */


public class PlayerMoveMessage implements Message{

    private Player player;
    private int x,y;

    public PlayerMoveMessage(int x, int y){
        this.x = x;
        this.y = y;
    }

    public PlayerMoveMessage(Player player,int x, int y){
        this.player = player;
        this.x = x;
        this.y = y;
    }

    public void setPlayer(Player player){
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
