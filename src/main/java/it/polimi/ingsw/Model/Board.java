package it.polimi.ingsw.Model;

import it.polimi.ingsw.Messages.BoardUpdateBuildMessage;
import it.polimi.ingsw.Messages.BoardUpdatePlaceMessage;
import it.polimi.ingsw.Messages.BoardUndoMessage;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Observer.Observable;

import java.util.ArrayList;

public class Board extends Observable<Message> {

    /**
     * Represents class to keep track of the changes on the board
     */
    private static class boxChanged
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

    /**
     * List that contains changes that take place on the board
     */
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

    /**
     *Build specified block into specified coordinates and notify massage with changes
     *
     * @param x coordinate for the board
     * @param y coordinate for the board
     * @param b block to build
     * @throws ArrayIndexOutOfBoundsException if chosen coordinates go outside the board limits
     */
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

    /**
     * Place specified workers into specified boxes.
     * It's used at the beginning of the game.
     *
     * @param worker1 first {@link Worker} owned by the {@link Player}
     * @param worker2 second {@link Worker} owned by the {@link Player}
     * @param x1 first {@link Worker} coordinate
     * @param y1 first {@link Worker} coordinate
     * @param x2 second {@link Worker} coordinate
     * @param y2 second {@link Worker} coordinate
     * @throws IndexOutOfBoundsException if x or y is/are out of board boundaries.
     */
    public void initializePawn(Worker worker1,Worker worker2, int x1, int y1,int x2,int y2) throws IndexOutOfBoundsException {

        if(x1 == x2 && y1 == y2){
            throw new RuntimeException("You cannot place the pawns in the same position");
        }

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

    /**
     * Create a new {@link boxChanged} object and adds it to the list of changes.
     *
     * @param w {@link Worker} responsible of the change
     * @param x changed coordinate
     * @param y changed coordinate
     * @param b changed {@link Block}
     */
    public void addAction(Worker w,int x,int y,Block b) {
        action.add(0, new boxChanged(x, y, b, w));
    }

    /**
     * Clears the list of changes
     */
    public void removeAction() {
        action.clear();
    }

    /**
     * Restores the board to following the list of changes until the first recorded change and notify all restored one by one.
     */
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
