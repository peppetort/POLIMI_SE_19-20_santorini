package it.polimi.ingsw.Model;

/**
 * Rappresenta una pedina del {@link Player}
 */
public class Worker {
    private String id;
    /**
     * Rappresenta la posizone X del giocatore sul {@link Board}
     * <p>
     *      Viene salvato per poter accedere direttamente alla posizione del giocatore senza
     *      dover cercare nella {@link Board}.
     *      Ogni volta che {@link Worker} viene mosso, viene aggionato.
     *     Nel caso il {@link Worker} non sia stato ancora posizionato sulla
     *     {@link Board} assume valore null
     * </p>
     */
    private Integer x;
    /**
     * Rappresenta la posizone Y del giocatore sul {@link Board}
     * <p>
     *     Viene salvato per poter accedere direttamente alla posizione del giocatore senza
     *     dover cercare nella {@link Board}.
     *     Ogni volta che {@link Worker} viene mosso, viene aggionato.
     *     Nel caso il {@link Worker} non sia stato ancora posizionato sulla
     *     {@link Board} assume valore null
     * </p>
     */
    private Integer y;
    /**
     * Rappresenta l'ultima {@link Box} su cui è stato il giocatore (non quella attuale)
     * <p>
     *     Viene salvato perché serve per verificare la condizione di vittoria nel caso in cui
     *     il {@link Player} abbia la classe {@link PanWin}.
     *     Ogni volta che {@link Worker} viene mosso, viene aggionato.
     *     Nel caso il {@link Worker} non sia stato ancora posizionato sulla
     *     {@link Board} assume valore null
     * </p>
     */
    private Box lastBox;

    /**
     * Costruttore della classe {@link Worker}
     * <p>
     *     Setta l'attributo {@link #id} ed inizializza gli attributi
     *     {@link #x}, {@link #y} e {@link #lastBox} a null
     * </p>
     *
     * @param id identificativo della pedina
     */
    public Worker(String id){
        this.id = id;
        this.x = null;
        this.y = null;
        this.lastBox = null;
    }

    public String getId(){
        return this.id;
    }

    /**
     * Setta gli attributi {@link #x} e {@link #y} del {@link Worker}
     * <p>
     *     è chiamato ogni volta che sulla {@link Board} il {@link Worker} è mosso
     * </p>
     *
     * @param x posizione X sulla {@link Board}
     * @param y posizione Y sulla {@link Board}
     *
     * @throws IndexOutOfBoundsException se x e/o y escono fuori dai confini del campo
     */
    public void setPos(int x, int y){
        if(x>=0 && x<5 && y>=0 && y<5) {
            this.x = x;
            this.y = y;
        }else {
            throw new IndexOutOfBoundsException("Invalid index!");
        }
    }

    /**
     * Restituisce l'attributo {@link #x}
     *
     * @return l'attributo {@link #x}
     *
     * @throws NullPointerException se la posizone non è inizializzata
     */
    public int getXPos(){
        if(this.x == null){
            throw new NullPointerException("Position not Initialized!");
        }else {
            return this.x;
        }
    }

    /**
     * Restituisce l'attributo {@link #y}
     *
     * @return l'attributo {@link #y}
     *
     * @throws NullPointerException se la posizone non è inizializzata
     */
    public int getYPos(){
        if(this.y == null){
            throw new NullPointerException("Position not Initialized!");
        }else {
            return this.y;
        }
    }

    public Box getLastBox(){
        return this.lastBox;
    }

    /**
     * Aggiorna l'ultima posizione del {@link Worker}
     * <p>
     *     Viene creato un nuovo oggetto {@link Box} e vengono copiati
     *     gli attributi di quello passato come parametro.
     * </p>
     *
     * @param box l'oggeto {@link Box}
     */
    public void updateLastBox(Box box){
        lastBox = new Box();
        lastBox.setPawn(box.getPawn());
        lastBox.build(box.getBlock());
    }

    //TODO: canMove(board): boolean controlla se il worker può fare almeno una mossa
    public boolean canMove(Board board)
    {
        try {
            if (board.getBox(x, y).compare(board.getBox(x + 1, y)) && board.getBox(x + 1, y).getPawn() == null) {
                return true;
            }
        }catch (IndexOutOfBoundsException e)
        {}
        try {
            if (board.getBox(x, y).compare(board.getBox(x - 1, y)) && board.getBox(x - 1, y).getPawn() == null) {
                return true;
            }
        }catch (IndexOutOfBoundsException e){}
        try {
            if (board.getBox(x, y).compare(board.getBox(x + 1, y+1)) && board.getBox(x + 1, y+1).getPawn() == null) {
                return true;
            }
        }catch (IndexOutOfBoundsException e){}
        try {
            if (board.getBox(x, y).compare(board.getBox(x + 1, y-1)) && board.getBox(x + 1, y-1).getPawn() == null) {
                return true;
            }
        }catch (IndexOutOfBoundsException e){}
        try {
            if (board.getBox(x, y).compare(board.getBox(x -1, y-1)) && board.getBox(x - 1, y-1).getPawn() == null) {
                return true;
            }
        }catch (IndexOutOfBoundsException e){}
        try {
            if (board.getBox(x, y).compare(board.getBox(x - 1, y+1)) && board.getBox(x - 1, y+1).getPawn() == null) {
                return true;
            }
        }catch (IndexOutOfBoundsException e){}
        try {
            if (board.getBox(x, y).compare(board.getBox(x , y+1)) && board.getBox(x , y+1).getPawn() == null) {
                return true;
            }
        }catch (IndexOutOfBoundsException e){}
        try {
            if (board.getBox(x, y).compare(board.getBox(x , y-1)) && board.getBox(x , y-1).getPawn() == null) {
                return true;
            }
        }catch (IndexOutOfBoundsException e){}
        return false;
    }
}
