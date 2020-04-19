package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidMoveException;

/**
 * Represents the move in case the
 * {@link Player} has the card MINOTAUR
 */
public class MinotaurMove extends DefaultMove {

    private final Player player;

    /**
     * Constructor of the class {@link MinotaurMove}
     * @param player
     */
    public MinotaurMove(Player player) {
        super(player);
        this.player = player;
    }

    /**
     * Moves the selected {@link Worker} with the MINOTAUR's rules
     *
     * @param worker selected worker
     * @param x      x coordinate where the {@link Player} wants to move
     * @param y      y coordinate where the {@link Player} wants to move
     * @throws InvalidMoveException if the distance is too high
     * @throws InvalidMoveException if it's the same of the actual position
     * @throws InvalidMoveException if I'm trying to move on a level which is too high
     * @throws InvalidMoveException if the box is already occupied
     * @throws InvalidMoveException if the oppenent's pawn can't get back
     */
    @Override
    public void move(Worker worker, int x, int y) throws IndexOutOfBoundsException, NullPointerException {
        int wX = worker.getXPos();
        int wY = worker.getYPos();

        Box workerBox = board.getBox(wX, wY);
        Box nextBox = board.getBox(x, y);

        if (x > wX + 1 || x < wX - 1 || y > wY + 1 || y < wY - 1 || (x == wX && y == wY)) {
            throw new InvalidMoveException("Invalid move!");
        } else if (!workerBox.compare(nextBox)) {
            throw new InvalidMoveException("Level not compatible!");
        } else {
            if (!nextBox.isFree()) {
                Worker other = nextBox.getPawn();
                // Controllo se l'altra pedina è una pedina avversaria
                try {
                    if (!other.getId().equals(player.getWorker1().getId()) && !other.getId().equals(player.getWorker2().getId())) {
                        if (x == wX && y == wY + 1) {                       // Movimento Nord
                            if (board.getBox(x, y + 1).isFree()) {
                                board.placePawn(other, x, y + 1);      //la pedina avversaria indietreggia
                                other.updateLastBox(nextBox);           // aggiorno l'ultima box della pedina avversaria
                                board.placePawn(worker, x, y);         //posizione la mia pedina nella nuova posizione
                                worker.updateLastBox(workerBox);       // aggiorno l'ultima box della mia pedina
                                workerBox.removePawn();
                            } else {
                                throw new InvalidMoveException("Opponent's worker can't back away!");
                            }
                        } else if (x == wX && y == wY - 1) { // Movimento Sud
                            if (board.getBox(x, y - 1).isFree()) {
                                board.placePawn(other, x, y - 1); //la pedina avversaria indietreggia
                                other.updateLastBox(nextBox);           // aggiorno l'ultima box della pedina avversaria
                                board.placePawn(worker, x, y); //posizione la mia pedina nella nuova posizione
                                worker.updateLastBox(workerBox);       // aggiorno l'ultima box della mia pedina
                                workerBox.removePawn();
                            } else {
                                throw new InvalidMoveException("Opponent's worker can't back away!");
                            }
                        } else if (x == wX + 1 && y == wY) { // Movimento Est
                            if (board.getBox(x + 1, y).isFree()) {
                                board.placePawn(other, x + 1, y); //la pedina avversaria indietreggia
                                other.updateLastBox(nextBox);           // aggiorno l'ultima box della pedina avversaria
                                board.placePawn(worker, x, y); //posizione la mia pedina nella nuova posizione
                                worker.updateLastBox(workerBox);       // aggiorno l'ultima box della mia pedina
                                workerBox.removePawn();
                            } else {
                                throw new InvalidMoveException("Opponent's worker can't back away!");
                            }
                        } else if (x == wX - 1 && y == wY) { // Movimento Ovest
                            if (board.getBox(x - 1, y).isFree()) {
                                board.placePawn(other, x - 1, y); //la pedina avversaria indietreggia
                                other.updateLastBox(nextBox);           // aggiorno l'ultima box della pedina avversaria
                                board.placePawn(worker, x, y); //posizione la mia pedina nella nuova posizione
                                worker.updateLastBox(workerBox);       // aggiorno l'ultima box della mia pedina
                                workerBox.removePawn();
                            } else {
                                throw new InvalidMoveException("Opponent's worker can't back away!");
                            }
                        } else if (x == wX + 1 && y == wY + 1) { // Movimento Nord-Est
                            if (board.getBox(x + 1, y + 1).isFree()) {
                                board.placePawn(other, x + 1, y + 1); //la pedina avversaria indietreggia
                                other.updateLastBox(nextBox);           // aggiorno l'ultima box della pedina avversaria
                                board.placePawn(worker, x, y); //posizione la mia pedina nella nuova posizione
                                worker.updateLastBox(workerBox);       // aggiorno l'ultima box della mia pedina
                                workerBox.removePawn();
                            } else {
                                throw new InvalidMoveException("Opponent's worker can't back away!");
                            }
                        } else if (x == wX + 1 && y == wY - 1) { // Movimento Sud-Est
                            if (board.getBox(x + 1, y - 1).isFree()) {
                                board.placePawn(other, x + 1, y - 1); //la pedina avversaria indietreggia
                                other.updateLastBox(nextBox);           // aggiorno l'ultima box della pedina avversaria
                                board.placePawn(worker, x, y); //posizione la mia pedina nella nuova posizione
                                worker.updateLastBox(workerBox);       // aggiorno l'ultima box della mia pedina
                                workerBox.removePawn();
                            } else {
                                throw new InvalidMoveException("Opponent's worker can't back away!");
                            }
                        } else if (x == wX - 1 && y == wY + 1) { // Movimento Nord-Ovest
                            if (board.getBox(x - 1, y + 1).isFree()) {
                                board.placePawn(other, x - 1, y + 1); //la pedina avversaria indietreggia
                                other.updateLastBox(nextBox);           // aggiorno l'ultima box della pedina avversaria
                                board.placePawn(worker, x, y); //posizione la mia pedina nella nuova posizione
                                worker.updateLastBox(workerBox);       // aggiorno l'ultima box della mia pedina
                                workerBox.removePawn();
                            } else {
                                throw new InvalidMoveException("Opponent's worker can't back away!");
                            }
                        } else if (x == wX - 1 && y == wY - 1) { // Movimento Sud-Ovest
                            if (board.getBox(x - 1, y - 1).isFree()) {
                                board.placePawn(other, x - 1, y - 1); //la pedina avversaria indietreggia
                                other.updateLastBox(nextBox);           // aggiorno l'ultima box della pedina avversaria
                                board.placePawn(worker, x, y); //posizione la mia pedina nella nuova posizione
                                worker.updateLastBox(workerBox);       // aggiorno l'ultima box della mia pedina
                                workerBox.removePawn();
                            } else {
                                throw new InvalidMoveException("Opponent's worker can't back away!");
                            }
                        }
                    } else {
                        throw new InvalidMoveException("Can't place pawn here");
                    }
                } catch (IndexOutOfBoundsException e) {
                    throw new InvalidMoveException("Opponent's worker can't back away!");
                }


            } else {
                board.placePawn(worker, x, y);
                workerBox.removePawn(); //rimuovo pedina dalla vecchia pos
                worker.updateLastBox(workerBox);            // aggiorno l'ultima box nel worker
            }
        }

    }

}
