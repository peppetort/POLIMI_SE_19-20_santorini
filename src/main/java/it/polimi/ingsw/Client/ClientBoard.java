package it.polimi.ingsw.Client;

import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Observer.Observable;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientBoard extends Observable {

    private final Box[][] board = new Box[5][5];
    private final HashMap<Color, Box[]> playersLatestBoxes = new HashMap<>();
    public Box[][] getBoard() {
        return board;
    }

    public ClientBoard(ArrayList<Color> players) {
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                board[x][y] = new Box();
            }
        }

        for (Color p : players) {
            playersLatestBoxes.put(p, new Box[2]);
        }
        notify(1);
    }

    public void lose(Color player) {
        Box[] workers = playersLatestBoxes.get(player);
        workers[0].clear();
        workers[1].clear();
        playersLatestBoxes.remove(player);
        notify(1);
    }

    public void placePlayer(int x, int y, Color player, int worker) {
        board[x][y].setPlayer(player, worker);

        Box worker1 = playersLatestBoxes.get(player)[0];
        Box worker2 = playersLatestBoxes.get(player)[1];

        if (worker == 1) {
            if (worker1 != null && worker1.getPlayer().equals(player)) {
                worker1.clear();
            }
            worker1 = board[x][y];
            Box[] newArray = {worker1, worker2};
            playersLatestBoxes.replace(player, newArray);
        } else if (worker == 2) {
            if (worker2 != null && worker2.getPlayer().equals(player)) {
                worker2.clear();
            }
            worker2 = board[x][y];
            Box[] newArray = {worker1, worker2};
            playersLatestBoxes.replace(player, newArray);
        }

        notify(1);
    }

    public void setLevel(int x, int y, int level) {
        board[x][y].setLevel(level);
       notify(1);
    }

    public void restore(int x,int y,Color player, Integer worker, int level) {
        //ristabilisce il livello della costruzione a quello dell'inizio del turno
        board[x][y].setLevel(level);
		if (worker != null) {
			placePlayer(x, y, player, worker);
		}
    }

}
