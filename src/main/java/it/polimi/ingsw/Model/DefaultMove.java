package it.polimi.ingsw.Model;

/**
 * Rappresenta la classe utilizzata dal {@Link Player} nel caso in cui non possegga una carta in grado di modificare
 * la sua mossa di movimento.
 */
public class DefaultMove implements Move {

    private Board board;
    /**
     * Rappresenta il metodo costruttore.
     * @param player rappresenta il {@Link Player} che si muoverà secondo le regole base del gioco.
     */
    public DefaultMove(Player player){
        this.board = player.getSession().getBoard();
    }

    /**
     * Rappresenta il metodo per effettuare un movimento secondo le regole base del gioco.
     * @param worker è la pedina da muovere
     * @param x è la posizione X della {@link Board} sulla quale si vuole posizionare la pedina
     * @param y è la posizione Y della {@link Board} sulla quale si vuole posizionare la pedina
     * @throws RuntimeException se le coordinate non sono valide (non è una cella adiacente oppure è la stessa
     * delle coordinate attuali del {@Link Worker})
     * @throws RuntimeException se la cella su cui voglio muovermi è occupata da un altro {@Link Worker}
     * @throws RuntimeException se per muovermi su quella cella devo compiere un salto di due o più livelli
     * @throws IndexOutOfBoundsException se voglio muovermi fuori dai bordi di {@Link Board}
     * @throws NullPointerException se il riferimento a {@Link Worker} è null
     */
    @Override
    public void move(Worker worker, int x, int y) {
        try {
            int wX = worker.getXPos();
            int wY = worker.getYPos();

            Box workerBox = board.getBox(wX, wY);
            Box nextBox = board.getBox(x, y);

            if( x > wX+1 || x < wX-1 || y > wY+1 || y < wY-1 || (x == wX && y == wY)){
                throw new RuntimeException("Invalid move!");
            }else if(!nextBox.isFree()){
                throw new RuntimeException("Box already occupied!");
            }else if(!workerBox.compare(nextBox)){
                throw new RuntimeException("Level not compatible!");
            }else{
                board.placePawn(worker, x, y);
                workerBox.removePawn();  //rimuovo pedina dalla vecchia pos
                worker.updateLastBox(workerBox); // aggiorno l'ultima box nel worker
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("Out of board limits");
        }catch (NullPointerException e){
            System.out.println("Pawns not in board!");
        }

    }
}
