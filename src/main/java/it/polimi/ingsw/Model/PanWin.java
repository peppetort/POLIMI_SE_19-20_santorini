package it.polimi.ingsw.Model;

/**
 * <p>
 * Extends the class {@link DefaultWin} because it adds a post-condition and has the same pre-conditions
 * </p>
 */
public class PanWin extends DefaultWin {

    /**
     * Constructor of the class {@link PanWin}
     *
     * @param player {@link Player} that has the {@link Card} PAN
     */
    public PanWin(Player player) {
        super(player);
    }

    /**
     * @return true if the {@link Player} has won.
     * @throws NullPointerException if lastBox of {@link Worker} is null
     */
    @Override
    public boolean winChecker() {
        try {
            if (super.winChecker()) {
                return true;
            } else {

                Box w1LastBox = worker1.getLastBox();
                Box w1ActualBox = board.getBox(worker1.getXPos(), worker1.getYPos());
                Box w2LastBox = worker2.getLastBox();
                Box w2ActualBox = board.getBox(worker2.getXPos(), worker2.getYPos());

                if (w1LastBox == null && w2LastBox != null) {
                    return w2LastBox.getDifference(w2ActualBox) == 2;
                } else if (w1LastBox != null && w2LastBox == null) {
                    return w1LastBox.getDifference(w1ActualBox) == 2;
                } else if (w1LastBox != null) {
                    return w1LastBox.getDifference(w1ActualBox) == 2 || w2LastBox.getDifference(w2ActualBox) == 2;
                } else {
                    return false;
                }
            }
        } catch (NullPointerException e) {
            return false;
        }
    }
}
