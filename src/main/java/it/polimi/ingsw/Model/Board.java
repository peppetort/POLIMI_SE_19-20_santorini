package it.polimi.ingsw.Model;

/**
 * Rappresenta il campo di gioco {@link Game}
 */
public class Board {
    /**
     * Rappresenta la matrice di celle {@link Box} che compongono la board
     */
    private Box[][] board=new Box[5][5];

    /**
     * Costruttore che inizializza ogni cella {@link Box} della board
     */
    public Board()
    {
        int x;
        int y;
        for(y=0;y<5;y++)
            for(x=0;x<5;x++) {
                board[x][y]=new Box();
            }
    }

    /**
     * <p>
     *     Ritorna la cella di coordinate richieste
     * </p>
     * @param x coordinate x della cella a cui si vuole accedere
     * @param y coordinate y della cella a cui si vuole accedere
     * @return la cella {@link Box} richiesta
     * @throws IndexOutOfBoundsException se x e/o y escono fuori dai confini del campo
     */
    public Box getBox(int x,int y) throws IndexOutOfBoundsException
    {
        return board[x][y];
    }

    /**
     * <p>
     *     posizione il worker passato nella cella a cui si vuole accedere modificando:
     *     la posizione del worker {@link Worker}
     *     il la presenza della pedina nella cella {@link Box}
     * </p>
     * @param worker pedina di cui si modifica la posizione
     * @param x coordinate x della cella a cui si vuole accedere
     * @param y coordinate y della cella a cui si vuole accedere
     * @throws IndexOutOfBoundsException se x e/o y escono fuori dai confini del campo
     */
    public void placePawn(Worker worker,int x,int y) throws IndexOutOfBoundsException
    {
        board[x][y].setPawn(worker);
        worker.setPos(x,y);
    }
}
