package it.polimi.ingsw.Model;


public class TurnUtils extends DefaultTurn {
    public TurnUtils(Player player) {
        super(player);
    }

    public void setCanGoUp(boolean val) {
        canGoUp = val;
    }

    public boolean getCanGoUp() {
        return canGoUp;
    }
}
