package it.polimi.ingsw.Model;


import it.polimi.ingsw.Messages.BoardUpdate;

public class Board {
    /**
     * Matrix 5 x 5 that represents the field.
     */
    private final Box[][] board = new Box[5][5];


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
    }
    public void initializePawn(Worker worker1,Worker worker2, int x1, int y1,int x2,int y2) throws IndexOutOfBoundsException {
        Game game = worker1.getPlayer().getSession();
        board[x1][y1].setPawn(worker1);
        board[x2][y2].setPawn(worker2);
        worker1.setPos(x1, y1);
        worker2.setPos(x2,y2);

        game.notify(new BoardUpdate(this.data(),game.getPlayers()));


    }

    public Integer[] data() {
        Integer[] coordinates = new Integer[25];
        int c = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                coordinates[c] = board[i][j].getBlock().getValue();
                c++;
            }
        }

        return coordinates;
    }
}
