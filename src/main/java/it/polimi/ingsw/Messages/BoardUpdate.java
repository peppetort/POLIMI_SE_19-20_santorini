package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player;

import java.io.Serializable;

public class BoardUpdate implements Serializable,Message{
    public String boardData;
    public BoardUpdate(String boardData){
        this.boardData = boardData;
    }
    public String getBoardData() {
        return boardData;
    }
}
