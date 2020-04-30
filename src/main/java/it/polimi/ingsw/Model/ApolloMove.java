package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidMoveException;

/**
 * Represents the move in case the
 * {@link Player} has the card APOLLO
 */
public class ApolloMove extends DefaultMove {

    final Player player;

    /**
     * Constructor of the class {@link ApolloMove}
     *
     * @param player
     */
    public ApolloMove(Player player) {
        super(player);
        this.player = player;
    }

    /**
     * Moves the selected {@link Worker} with default move rules.
     *
     * @param worker selected worker
     * @param x      x coordinate where the {@link Player} wants to move
     * @param y      y coordinate where the {@link Player} wants to move
     * @throws InvalidMoveException if the distance is too high
     * @throws InvalidMoveException if it's the same of the actual position
     * @throws InvalidMoveException if I'm trying to move on a level which is too high
     * @throws InvalidMoveException if the box is already occupied
     */
    @Override
    public void move(Worker worker, int x, int y) {
        try {
            int wX = worker.getXPos();
            int wY = worker.getYPos();

            Box workerBox = board.getBox(wX, wY);
            Box nextBox = board.getBox(x, y);

            if (x > wX + 1 || x < wX - 1 || y > wY + 1 || y < wY - 1) {
                throw new InvalidMoveException("Move too far from the worker position!");
            } else if ((x == wX && y == wY)) {
                throw new InvalidMoveException("Invalid move!");
            } else if (!workerBox.compare(nextBox)) {
                throw new InvalidMoveException("Level in box " + x + " " + y + "is too high!");
            } else {
                if (!nextBox.isFree()) {
                    Worker other = nextBox.getPawn();
                    // Controllo se l'altra pedina è una pedina avversari
                    if (!other.getId().equals(player.getWorker1().getId()) && !other.getId().equals(player.getWorker2().getId())) {
                       //salvataggio stato celle
                        board.addAction(worker,wX,wY,workerBox.getBlock());
                        board.addAction(nextBox.getPawn(),x,y,nextBox.getBlock());

                        board.placePawn(other, wX, wY);         //posiziono la pedina avversaria nella mia posizione
                        other.updateLastBox(nextBox);           // aggiorno l'ultima box della pedina avversaria
                        board.placePawn(worker, x, y);          // posiziono la mia pedina nella nuova posizione
                        worker.updateLastBox(workerBox);        // aggiorno l'ultima box della mia pedina

                    } else {
                        throw new InvalidMoveException("Can't place pawn here! It's your worker!");
                    }
                } else {
                    //salvataggio stato celle
                    board.addAction(worker,wX,wY,workerBox.getBlock());
                    board.addAction(nextBox.getPawn(),x,y,nextBox.getBlock());

                    board.placePawn(worker, x, y);
                    workerBox.removePawn();                     // rimuovo pedina dalla vecchia pos
                    worker.updateLastBox(workerBox);            // aggiorno l'ultima box nel worker
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Out of board limits");
        } catch (NullPointerException e) {
            System.out.println("Pawns not in board");
        }
    }
}
