package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidMoveException;

/**
 * Rappresenta la classe che modellizza la mossa del
 * {@link Player} nel caso in cui abbia la carta MINOTAUR
 */
public class MinotaurMove extends DefaultMove {

    private final Player player;

    /**
     * Rappresenta il costruttore della classe {@link MinotaurMove}
     *
     * @param player giocatore che ha istanziato la classe
     */
    public MinotaurMove(Player player) {
        super(player);
        this.player = player;
    }

    /**
     * Muove la pedina specificata nella posizione specificata, secondo le regole della
     * classe {@link DefaultMove} con la condizione aggiuntiva di poter prendere la posizione
     * di una pedina avversaria nel caso quest'ultima abbia la possibilità si indietreggiare
     * in una {@link Box} della {@link Board} non occupata e di livello compatibile
     *
     * @param worker è la pedina da muovere
     * @param x      è la posizione X della {@link Board} sulla quale si vuole posizionare la pedina
     * @param y      è la posizione Y della {@link Board} sulla quale si vuole posizionare la pedina
     * @throws RuntimeException se la posizione specificata eccede quelle che la pedina può assumere oppure
     *                          se conincide con l'attuale posizione della pedina
     * @throws RuntimeException se si cerca di muovere la pedina su un livello troppo altro
     * @throws RuntimeException se si cerca di posizionare la pedina nella posizione dell'altra pedina dello stesso giocatore
     * @throws RuntimeException se la pedina avversaria non può indietreggiare
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
