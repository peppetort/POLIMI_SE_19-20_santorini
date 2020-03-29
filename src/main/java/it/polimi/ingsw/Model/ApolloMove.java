package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.AthenaGoUpException;
import it.polimi.ingsw.Exceptions.InvalidMoveException;
import it.polimi.ingsw.Exceptions.LevelNotCompatibleException;

/**
 * Rappresenta la classe che modellizza la mossa del
 * {@link Player} nel caso in cui abbia la carta APOLLO
 */
public class ApolloMove extends DefaultMove{

    private Board board;
    private Player player;

    /**
     * Rappresenta il costruttore della classe {@link ApolloMove}
     *
     * @param player giocatore che ha istanziato la classe
     */
    public ApolloMove(Player player){
        super(player);
        this.player = player;
        this.board = player.getSession().getBoard();
    }

    /**
     * Muove la pedina specificata nella posizione specificata, secondo le regole della
     * classe {@link DefaultMove} con la condizione aggiuntiva di poter scambiare la propria
     * posizione con quella di una pedina avversaria
     *
     * @param worker è la pedina da muovere
     * @param x è la posizione X della {@link Board} sulla quale si vuole posizionare la pedina
     * @param y è la posizione Y della {@link Board} sulla quale si vuole posizionare la pedina
     * @throws RuntimeException se la posizione specificata eccede quelle che la pedina può assumere oppure
     * se conincide con l'attuale posizione della pedina
     * @throws RuntimeException se si cerca di muovere la pedina su un livello troppo altro
     * @throws RuntimeException se si cerca di posizionare la pedina nella posizione dell'altra pedina dello stesso giocatore
     */
    @Override
    public void move(Worker worker, int x, int y) {
        try {
            int wX = worker.getXPos();
            int wY = worker.getYPos();

            Box workerBox = board.getBox(wX, wY);
            Box nextBox = board.getBox(x, y);

            if( x > wX+1 || x < wX-1 || y > wY+1 || y < wY-1 || (x == wX && y == wY)){
                throw new InvalidMoveException("Move too far from the worker position!");
            }else if(!workerBox.compare(nextBox)){
                throw new LevelNotCompatibleException("Level in box " + x + " "+ y + "is too high!");
            }else{
                if(!nextBox.isFree()){
                    Worker other = nextBox.getPawn();
                    // Controllo se l'altra pedina è una pedina avversaria
                    if(!other.getId().equals(player.getWorker1().getId()) && !other.getId().equals(player.getWorker2().getId())){
                        board.placePawn(other, wX, wY);         //posiziono la pedina avversaria nella mia posizione
                        other.updateLastBox(nextBox);           // aggiorno l'ultima box della pedina avversaria
                        board.placePawn(worker, x, y);          // posiziono la mia pedina nella nuova posizione
                        worker.updateLastBox(workerBox);        // aggiorno l'ultima box della mia pedina
                    }else {
                        throw new RuntimeException("Can't place pawn here! It's your worker!");
                    }
                }else {
                    board.placePawn(worker, x, y);
                    workerBox.removePawn();                     // rimuovo pedina dalla vecchia pos
                    worker.updateLastBox(workerBox);            // aggiorno l'ultima box nel worker
                }
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("Out of board limits");
        }catch (NullPointerException e){
            System.out.println("Pawns not in board");
        }
    }
}
