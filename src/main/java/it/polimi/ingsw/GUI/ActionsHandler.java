package it.polimi.ingsw.GUI;

import it.polimi.ingsw.Client.Box;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.Actions;
import javafx.event.ActionEvent;

public class ActionsHandler {

    MainController mainController;
    PlayingStageController playingStageController;

    int placeCounter;
    int indexForPlace[];

    public ActionsHandler(MainController mc,PlayingStageController ps){
        mainController = mc;
        playingStageController = ps;
        placeCounter = 0;
        indexForPlace = new int[2];
    }

    public void handlePlace(ActionEvent actionEvent) {

       placeCounter ++;

       int x = playingStageController.x;
       int y = playingStageController.y;

        switch(mainController.client.getStatus().getColor()){
            case GREEN:
                playingStageController.greenPawns[x][y].setOpacity(1);
                break;
            case RED:
                playingStageController.redPawns[x][y].setOpacity(1);
                break;
            case BLUE:
                playingStageController.bluePawns[x][y].setOpacity(1);
                break;
        }
        if(placeCounter == 1){
            indexForPlace[0] = x; //x1
            indexForPlace[1] = y;    //y1
        }
        if(placeCounter == 2){
            try {
                mainController.notify(new PlayerPlacePawnsMessage(indexForPlace[0], indexForPlace[1], x, y));
            }catch(Exception e){System.err.print(e.getMessage());}
        }
    }

    public void handleBuild(ActionEvent actionEvent){
        mainController.notify(new PlayerBuildMessage(playingStageController.x,playingStageController.y));
    }

    public void handleSelect(ActionEvent actionEvent){
        int x = PlayingStageController.x;
        int y = PlayingStageController.y;

        Box[][] board = mainController.client.getBoard().getBoard();

        try {
            if (board[x][y].getPlayer() == mainController.client.getStatus().getColor()) {
                mainController.notify(new PlayerSelectMessage(board[x][y].getWorker()));
            }
        }catch(NullPointerException e){
            //in questa cella non c'è nessuna pedina
        }
    }

    public void handleMove(ActionEvent e){
        mainController.notify(new PlayerMoveMessage(playingStageController.x,playingStageController.y));
    }

    public void handleUndo(ActionEvent e){
        if(PlayingStageController.list.contains(Actions.UNDO)){
            mainController.notify(new PlayerUndoMessage());
        }
    }

    public void handleEnd(ActionEvent e){
        if(PlayingStageController.list.contains(Actions.END)){
            mainController.notify(new PlayerEndMessage());
        }
    }
}
