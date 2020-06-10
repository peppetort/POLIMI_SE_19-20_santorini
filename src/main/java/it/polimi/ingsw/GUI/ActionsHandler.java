package it.polimi.ingsw.GUI;

import it.polimi.ingsw.Client.Box;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.Actions;
import javafx.event.ActionEvent;

/**
 * Class used to handle actions triggered by user-input in the playing stage.
 */

public class ActionsHandler {

    MainController mainController;
    PlayingStageController playingStageController;

    int placeCounter;
    int[] indexForPlace;

    /**
     * Constructore for the class {@link ActionsHandler}
     * @param mc
     * @param ps
     */
    public ActionsHandler(MainController mc,PlayingStageController ps){
        mainController = mc;
        playingStageController = ps;
        placeCounter = 0;
        indexForPlace = new int[2];
    }

    /**
     * Method used to handle the pawns placing. It checks if there's already a pawn and send a {@link PlayerPlacePawnsMessage}
     *  to the {@link MainController} when you place correctly two workers.
     * @param actionEvent
     */
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

    /**
     * Method used to handle a build in the playing stage. It sends a {@link PlayerBuildMessage} to the {@link MainController}.
     * @param actionEvent
     */
    public void handleBuild(ActionEvent actionEvent){
        mainController.notify(new PlayerBuildMessage(PlayingStageController.x, PlayingStageController.y));
    }

    /**
     * Method used to handle a build_dome in the playing stage. It sends a {@link PlayerBuildDomeMessage} to the
     * {@link MainController}. Used for Atlas which can build a dome over terrain.
     * @param actionEvent
     */
    public void handleBuildDome(ActionEvent actionEvent){
        mainController.notify(new PlayerBuildDomeMessage(PlayingStageController.x, PlayingStageController.y));
    }

    /**
     * Method used to handle the selection of a worker. It sends a {@link PlayerSelectMessage} to the
     * {@link MainController} if the selected worker
     * is correct (same color of the player's color).
     * @param actionEvent
     */
    public void handleSelect(ActionEvent actionEvent){
        int x = PlayingStageController.x;
        int y = PlayingStageController.y;
        Box[][] board = mainController.client.getBoard().getBoard();
        if(board[x][y].getPlayer() != null && board[x][y].getPlayer() == mainController.client.getStatus().getColor()){
            mainController.client.getStatus().setSelected(board[x][y].getWorker());
            mainController.notify(new PlayerSelectMessage(board[x][y].getWorker()));
        }
    }

    /**
     * Method used to handle movement for workers. It notifies a {@link PlayerMoveMessage} to the {@link MainController}.
     * @param e
     */
    public void handleMove(ActionEvent e){
        mainController.notify(new PlayerMoveMessage(PlayingStageController.x, PlayingStageController.y));
        e.consume();
    }

    /**
     * Method used to send an undo to server. Can triggered if the button is available (only in my turn and in a time
     * window of 5 seconds). It notifies a {@link PlayerUndoMessage} to the {@link MainController}.
     * @param e
     */
    public void handleUndo(ActionEvent e){
        if(PlayingStageController.list.contains(Actions.UNDO)){
            mainController.notify(new PlayerUndoMessage());
        }
        e.consume();
    }

    /**
     * Method used to notifies a {@link PlayerEndMessage} to the {@link MainController}.
     * Can triggered if the button is available (only in my turn and after the mandatory moves.
     * @param e
     */
    public void handleEnd(ActionEvent e){
        if(PlayingStageController.list.contains(Actions.END)){
            mainController.notify(new PlayerEndMessage());
        }
        e.consume();
    }
}
