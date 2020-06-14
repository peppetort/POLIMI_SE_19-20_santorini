package it.polimi.ingsw.Model;


/**
 * Rappresenta una pedina del {@link Player}
 */
public class Worker {
    private final Player player;
    private final String id;

    /**
     * X position on the {@link Board}
     * <p>
     * If not setted is initialized at null value. It's setted when the {@link Player} place it on the {@link Board} and
     * updated at every move.
     * </p>
     */

    private Integer x;

    /**
     * X position on the {@link Board}
     * <p>
     * If not setted is initialized at null value. It's setted when the {@link Player} place it on the {@link Board} and
     * updated at every move.
     * </p>
     */
    private Integer y;

    /**
     * last {@link Box} where the {@link Worker} has been
     * <p>
     * It's null if the {@link Worker} hasn't moved yet.
     * </p>
     */
    private Box lastBox;

    /**
     * Constructor of the class {@link Worker}
     * <p>
     * @param id it can be 1 or 2
     * @param player (the {@link Player} has 2 pawns)
     */
    public Worker(int id, Player player) {
        this.id = player.getUsername() + id;
        this.player = player;
        this.x = null;
        this.y = null;
        this.lastBox = null;
    }

    public String getId() {
        return this.id;
    }

    /**
     * @param x x coordinate
     * @param y y coordinate
     * @throws IndexOutOfBoundsException if x || y are out of the {@link Board} boundaries
     */
    public void setPos(int x, int y) {
        if (x >= 0 && x < 5 && y >= 0 && y < 5) {
            this.x = x;
            this.y = y;
        } else {
            throw new IndexOutOfBoundsException("Invalid index!");
        }
    }


    public Integer getXPos() {
        return this.x;
    }


    public Integer getYPos() {
        return this.y;
    }

    public Player getPlayer() {
        return player;
    }

    public Box getLastBox() {
        return this.lastBox;
    }

    /**
     *Update the last position of the {@link Worker}. This method has to be called before doing the move because
     * it takes the {@link Worker} reference from the parameter.
     *
     * @param box last box where the {@link Worker} was.
     */
    public void updateLastBox(Box box) {
        lastBox = new Box();
        lastBox.setPawn(box.getPawn());
        lastBox.setBlock(box.getBlock());
    }


    private boolean canMove(God card, boolean canGoUp) {
        Board board = player.getSession().getBoard();
        Box myBox = board.getBox(x, y);
        Box otherBox;
        Worker otherWorker;

        switch (card) {
            case APOLLO:
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        try {
                            if (i != 0 || j != 0) {
                                otherBox = board.getBox(x + i, y + j);
                                otherWorker = otherBox.getPawn();

                                if (canGoUp) {
                                    if (myBox.compare(otherBox) && (otherBox.isFree() || (otherWorker.player != player && otherWorker.canBuild()))) {
                                        return true;
                                    }
                                } else {
                                    if (myBox.getDifference(otherBox) >= 0 && (otherBox.isFree() || (otherWorker.player != player && otherWorker.canBuild()))) {
                                        return true;
                                    }
                                }
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                        }
                    }
                }
                return false;
            case MINOTAUR:
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        try {
                            if (i != 0 || j != 0) {
                                otherBox = board.getBox(x + i, y + j);
                                otherWorker = otherBox.getPawn();

                                if (canGoUp) {
                                    if (myBox.compare(otherBox) && (otherBox.isFree() || (otherWorker.player != player && otherWorker.canMove(true, false)))) {
                                        return true;
                                    }
                                } else {
                                    if (myBox.getDifference(otherBox) >= 0 && (otherBox.isFree() || (otherWorker.player != player && otherWorker.canMove(false, false)))) {
                                        return true;
                                    }
                                }
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                        }
                    }
                }
                return false;
            default:
                throw new RuntimeException("No card action known!");
        }
    }

    //TODO: rivedere (getCard() lancia simpleGameException)
    private boolean canMove(boolean canGoUp, boolean card) {
        Board board = player.getSession().getBoard();
        Box myBox = board.getBox(x, y);
        Box otherBox;

        if (card) {
            try {
                return canMove(player.getCard(), canGoUp);
            } catch (RuntimeException e) {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        try {
                            if (i != 0 || j != 0) {
                                otherBox = board.getBox(x + i, y + j);
                                if (canGoUp) {
                                    if (myBox.compare(otherBox) && otherBox.isFree()) {
                                        return true;
                                    }
                                } else {
                                    if (myBox.getDifference(otherBox) >= 0 && otherBox.isFree()) {
                                        return true;
                                    }
                                }
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                        }
                    }
                }
            }
        } else {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    try {
                        if (i != 0 || j != 0) {
                            otherBox = board.getBox(x + i, y + j);
                            if (canGoUp) {
                                if (myBox.compare(otherBox) && otherBox.isFree()) {
                                    return true;
                                }
                            } else {
                                if (myBox.getDifference(otherBox) >= 0 && otherBox.isFree()) {
                                    return true;
                                }
                            }
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }
            }
        }

        return false;
    }

    /**
     * Iterates over all {@link Box} adjacent to the worker {@link Worker#x} {@link Worker#y} and
     * check if the {@link Worker} can make at least one move.
     * Evaluates both Player's {@link God} and canGoUp value
     *
     *
     * @return true if there is at least one possibility otherwise false
     */
    public boolean canMove(boolean canGoUp) {
        return canMove(canGoUp, true);
    }

    /**
     * Iterates over all {@link Box} adjacent to the worker {@link Worker#x} {@link Worker#y} and
     * verify that it can be built on at least one cell
     *
     * @return true if there is at least one possibility otherwise false
     */
    public boolean canBuild() {
        Board board = player.getSession().getBoard();
        Box otherBox;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                try {
                    if (i != 0 || j != 0) {
                        otherBox = board.getBox(x + i, y + j);
                        if (otherBox.getBlock() != Block.DOME && otherBox.isFree()) {
                            return true;
                        }
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
        }
        return false;
    }

}
