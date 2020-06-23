package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Model.Player;


/**
 * message from client to server used to place its own {@link it.polimi.ingsw.Model.Worker}
 * on the board at the beginning of the game
 *
 */

public class PlayerPlacePawnsMessage implements Message {
    private int x1, y1;
    private int x2, y2;
    private Player player;
    public PlayerPlacePawnsMessage(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public PlayerPlacePawnsMessage(Player player,int x1, int y1, int x2, int y2) {
        this.player = player;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
