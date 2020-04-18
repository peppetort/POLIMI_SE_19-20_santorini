package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class BoardUpdate implements Serializable,Message{
    private Integer[] boardData;
    private HashMap<String,Integer[]> workers = new HashMap<>();
    private HashMap<String,Color> colors = new HashMap<>();

    public HashMap<String, Color> getColors() {
        return colors;
    }

    public BoardUpdate(Integer[] boardData, ArrayList<Player> players){
        this.boardData = boardData;
        for (Player p : players) {
            if(p.getWorker1().getXPos() != null){
                workers.put(p.getUsername(), new Integer[]{
                        p.getWorker1().getXPos(),
                        p.getWorker1().getYPos(),
                        p.getWorker2().getXPos(),
                        p.getWorker2().getYPos()
                });
                colors.put(p.getUsername(),p.getColor());
            }
        }
    }

    public Integer[] getBoardData() {
        return boardData;
    }

    public HashMap<String,Integer[]> getWorkers(){
        return workers;
    }


}
