package it.polimi.ingsw.GUI;

import it.polimi.ingsw.Client.Box;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.Actions;
import javafx.event.ActionEvent;

public class ActionsHandler {

    MainController mainController;
    PlayingStageController playingStageController;

    int placeCounter;
    int[] indexForPlace;

    public ActionsHandler(MainController mc,PlayingStageController ps){
        mainController = mc;
        playingStageController = ps;
        placeCounter = 0;
        indexForPlace = new int[2];
    }

    public void handlePlace(ActionEvent actionEvent) {



       int x = PlayingStageController.x;
       int y = PlayingStageController.y;

       if(mainController.client.getBoard().getBoard()[x][y].getPlayer() == null) {
           PlayingStageController.pawns[x][y].setColor(mainController.client.getStatus().getColor());
           placeCounter ++;

           if(placeCounter == 1){
               indexForPlace[0] = x; //x1
               indexForPlace[1] = y;    //y1
           }
       }

        if(placeCounter == 2){
            placeCounter = 0;
            try {
                mainController.notify(new PlayerPlacePawnsMessage(indexForPlace[0], indexForPlace[1], x, y));
            }catch(Exception e){System.err.print(e.getMessage());}
        }
    }

    public void handleBuild(ActionEvent actionEvent){
        mainController.notify(new PlayerBuildMessage(PlayingStageController.x, PlayingStageController.y));
    }

    public void handleSelect(ActionEvent actionEvent){
        int x = PlayingStageController.x;
        int y = PlayingStageController.y;
        Box[][] board = mainController.client.getBoard().getBoard();
        if(board[x][y].getPlayer() != null && board[x][y].getPlayer() == mainController.client.getStatus().getColor()){
            mainController.client.getStatus().setSelected(board[x][y].getWorker());
            mainController.notify(new PlayerSelectMessage(board[x][y].getWorker()));
        }
    }

    public void handleMove(ActionEvent e){
        mainController.notify(new PlayerMoveMessage(PlayingStageController.x, PlayingStageController.y));
        e.consume();
    }

    public void handleUndo(ActionEvent e){
        if(PlayingStageController.list.contains(Actions.UNDO)){
            mainController.notify(new PlayerUndoMessage());
        }
        e.consume();
    }

    public void handleEnd(ActionEvent e){
        if(PlayingStageController.list.contains(Actions.END)){
            mainController.notify(new PlayerEndMessage());
        }
        e.consume();
    }
}
