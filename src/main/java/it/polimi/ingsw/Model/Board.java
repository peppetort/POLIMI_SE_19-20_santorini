package it.polimi.ingsw.Model;

import it.polimi.ingsw.Messages.BoardUpdateBuildMessage;
import it.polimi.ingsw.Messages.BoardUpdatePlaceMessage;
import it.polimi.ingsw.Messages.BoardUndoMessage;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Observer.Observable;

import java.util.ArrayList;

public class Board extends Observable<Message> {
    //classe privata per salvare le mosse avvenute
    private class boxChanged
    {
        int x;
        int y;
        Worker worker;
        Block building;
        public boxChanged(int x,int y,Block b,Worker w)
        {
            this.building=b;
            this.worker=w;
            this.x=x;
            this.y=y;
        }
    }

    /**
     * Matrix 5 x 5 that represents the field.
     */
    private final Box[][] board = new Box[5][5];
    //arraylist che tiene in momoria lo stato delle celle che vengono cabiate durante la mossa
    private final ArrayList<boxChanged> action = new ArrayList<>();

    /**
     * Constructor which initializes each {@link Box} at TERRAIN level
     */
    public Board() {
        int x;
        int y;
        for (y = 0; y < 5; y++)
            for (x = 0; x < 5; x++) {
                board[x][y] = new Box();
            }
    }

    /**
     * @param x x coordinate of the board matrix.
     * @param y y coordinate of the board matrix.
     * @return {@link Box} at x-y coordinates.
     * @throws IndexOutOfBoundsException if x or y are out of the board boundaries.
     */
    public Box getBox(int x, int y) throws ArrayIndexOutOfBoundsException {
        return board[x][y];
    }

    public void build(int x, int y, Block b) throws  ArrayIndexOutOfBoundsException{
        board[x][y].setBlock(b);

        BoardUpdateBuildMessage message = new BoardUpdateBuildMessage(x, y, b.getValue());
        notify(message);
    }


    /**
     * <p>
     * Place the {@link Worker} reference in the x-y coordinates in board.
     * </p>
     *
     * @param worker reference of the selected {@link Worker}
     * @param x      x coordinate
     * @param y     y coordinate
     * @throws IndexOutOfBoundsException if x or y is/are out of board boundaries.
     */
    public void placePawn(Worker worker, int x, int y) throws IndexOutOfBoundsException {
        board[x][y].setPawn(worker);
        worker.setPos(x, y);
        Player player = worker.getPlayer();
        int workerId = Integer.parseInt(worker.getId().substring(worker.getId().length() -1 ));
        BoardUpdatePlaceMessage message = new BoardUpdatePlaceMessage(player.getColor(), workerId, x, y);
        notify(message);
    }

    public void initializePawn(Worker worker1,Worker worker2, int x1, int y1,int x2,int y2) throws IndexOutOfBoundsException {
        board[x1][y1].setPawn(worker1);
        board[x2][y2].setPawn(worker2);
        worker1.setPos(x1, y1);
        worker2.setPos(x2,y2);

        Player player = worker1.getPlayer();

        int worker1Id = Integer.parseInt(worker1.getId().substring(worker1.getId().length() -1 ));
        BoardUpdatePlaceMessage message1 = new BoardUpdatePlaceMessage(player.getColor(), worker1Id, x1, y1);
        notify(message1);

        int worker2Id = Integer.parseInt(worker2.getId().substring(worker2.getId().length() -1 ));
        BoardUpdatePlaceMessage message2 = new BoardUpdatePlaceMessage(player.getColor(), worker2Id, x2, y2);
        notify(message2);
    }

    public void addAction(Worker w,int x,int y,Block b) {
        action.add(0, new boxChanged(x, y, b, w));
    }

    public void removeAction() {
        action.clear();
    }

    //metodo che fa ritornare la board all'inizio del turno
    //notify per aggiornare il client
    public void restore() {
           for (boxChanged b : action) {
               Integer workerId=null;
               Color color=null;
               if(b.worker!=null) {
                   workerId = Integer.parseInt(b.worker.getId().substring(b.worker.getId().length() - 1));
                   color=b.worker.getPlayer().getColor();
                   board[b.x][b.y].setPawn(b.worker);
                   b.worker.setPos(b.x,b.y);
               }
               else {
                   board[b.x][b.y].removePawn();
               }
               BoardUndoMessage message = new BoardUndoMessage(color,workerId, b.x, b.y, b.building.getValue());
               board[b.x][b.y].setBlock(b.building);
               notify(message);

           }
           action.clear();
    }

}
