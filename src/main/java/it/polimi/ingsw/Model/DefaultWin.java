package it.polimi.ingsw.Model;

/**
 * Represents the class which models the default win condition
 */
public class DefaultWin implements Win {

    final Board board;
    final Worker worker1;
    final Worker worker2;

    /**
     * Constructor of the class {@link DefaultWin}.
     *
     * @param player {@link Player} that has no card.
     */
    public DefaultWin(Player player) {
        this.board = player.getSession().getBoard();
        this.worker1 = player.getWorker1();
        this.worker2 = player.getWorker2();
    }

    /**
     * @return true if the {@link Player} has won.
     * @throws NullPointerException if lastBox of {@link Worker} is null
     */
    @Override
    public boolean winChecker() {

        try {
            int w1X = worker1.getXPos();
            int w1Y = worker1.getYPos();
            int w2X = worker2.getXPos();
            int w2Y = worker2.getYPos();

            return board.getBox(w1X, w1Y).getBlock() == Block.LTHREE || board.getBox(w2X, w2Y).getBlock() == Block.LTHREE;

        } catch (NullPointerException e) {
            return false;
        }
    }
}
