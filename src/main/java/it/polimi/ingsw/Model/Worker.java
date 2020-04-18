package it.polimi.ingsw.Model;

/**
 * Rappresenta una pedina del {@link Player}
 */
public class Worker {
    private String id;
    /**
     * Rappresenta la posizone X del giocatore sul {@link Board}
     * <p>
     * Viene salvato per poter accedere direttamente alla posizione del giocatore senza
     * dover cercare nella {@link Board}.
     * Ogni volta che {@link Worker} viene mosso, viene aggionato.
     * Nel caso il {@link Worker} non sia stato ancora posizionato sulla
     * {@link Board} assume valore null
     * </p>
     */

    private Player player;

    private Integer x;
    /**
     * Rappresenta la posizone Y del giocatore sul {@link Board}
     * <p>
     * Viene salvato per poter accedere direttamente alla posizione del giocatore senza
     * dover cercare nella {@link Board}.
     * Ogni volta che {@link Worker} viene mosso, viene aggionato.
     * Nel caso il {@link Worker} non sia stato ancora posizionato sulla
     * {@link Board} assume valore null
     * </p>
     */
    private Integer y;
    /**
     * Rappresenta l'ultima {@link Box} su cui è stato il giocatore (non quella attuale)
     * <p>
     * Viene salvato perché serve per verificare la condizione di vittoria nel caso in cui
     * il {@link Player} abbia la classe {@link PanWin}.
     * Ogni volta che {@link Worker} viene mosso, viene aggionato.
     * Nel caso il {@link Worker} non sia stato ancora posizionato sulla
     * {@link Board} assume valore null
     * </p>
     */
    private Box lastBox;

    /**
     * Costruttore della classe {@link Worker}
     * <p>
     * Setta l'attributo {@link #id} ed inizializza gli attributi
     * {@link #x}, {@link #y} e {@link #lastBox} a null
     * </p>
     *
     * @param id identificativo della pedina
     */
    public Worker(int id, Player player) {
        this.id = player.getUsername() + id;
        this.x = null;
        this.y = null;
        this.lastBox = null;
        this.player = player;
    }

    public String getId() {
        return this.id;
    }

    /**
     * Setta gli attributi {@link #x} e {@link #y} del {@link Worker}
     * <p>
     * è chiamato ogni volta che sulla {@link Board} il {@link Worker} è mosso
     * </p>
     *
     * @param x posizione X sulla {@link Board}
     * @param y posizione Y sulla {@link Board}
     * @throws IndexOutOfBoundsException se x e/o y escono fuori dai confini del campo
     */
    public void setPos(int x, int y) {
        if (x >= 0 && x < 5 && y >= 0 && y < 5) {
            this.x = x;
            this.y = y;
        } else {
            throw new IndexOutOfBoundsException("Invalid index!");
        }
    }

    /**
     * Restituisce l'attributo {@link #x}
     *
     * @return l'attributo {@link #x}
     * @throws NullPointerException se la posizone non è inizializzata
     */
    public Integer getXPos() {
        return this.x;
    }

    /**
     * Restituisce l'attributo {@link #y}
     *
     * @return l'attributo {@link #y}
     * @throws NullPointerException se la posizone non è inizializzata
     */
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
     * Aggiorna l'ultima posizione del {@link Worker}
     * <p>
     * Viene creato un nuovo oggetto {@link Box} e vengono copiati
     * gli attributi di quello passato come parametro.
     * </p>
     *
     * @param box l'oggeto {@link Box}
     */
    public void updateLastBox(Box box) {
        lastBox = new Box();
        lastBox.setPawn(box.getPawn());
        lastBox.build(box.getBlock());
    }


    private boolean canMove(God card, boolean canGoUp) {
        Board board = player.getSession().getBoard();
        Box myBox = board.getBox(x, y);
        Box otherBox;
        Worker otherWorker;

        switch (card){
            case APOLLO:
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        try {
                            if (i != 0 || j != 0) {
                                otherBox = board.getBox(x + i, y + j);
                                otherWorker = otherBox.getPawn();

                                if(canGoUp){
                                    if(myBox.compare(otherBox) && (otherBox.isFree() || (otherWorker.player != player && otherWorker.canBuild()))){
                                        return true;
                                    }
                                }else {
                                    if(myBox.getDifference(otherBox)>=0 && (otherBox.isFree() || (otherWorker.player != player && otherWorker.canBuild()))){
                                        return true;
                                    }
                                }
                            }
                        } catch (IndexOutOfBoundsException ignored) {}
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

                                if(canGoUp){
                                    if(myBox.compare(otherBox) && (otherBox.isFree() || (otherWorker.player != player && otherWorker.canMove(true, false)))){
                                        return true;
                                    }
                                }else {
                                    if(myBox.getDifference(otherBox)>=0 && (otherBox.isFree() || (otherWorker.player != player && otherWorker.canMove(false, false)))){
                                        return true;
                                    }                                }
                            }
                        } catch (IndexOutOfBoundsException ignored) {}
                    }
                }
                return false;
            default:
                throw new RuntimeException("No card action known!");
        }
    }

    private boolean canMove(boolean canGoUp, boolean card) {
        Board board = player.getSession().getBoard();
        Box myBox = board.getBox(x, y);
        Box otherBox;

        if(card) {
            try {
                return canMove(player.getCard().getName(), canGoUp);
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
        }else {
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

    public boolean canMove(boolean canGoUp){
        return canMove(canGoUp, true);
    }

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
                } catch (IndexOutOfBoundsException ignored) {}
            }
        }
        return false;
    }

    public boolean moveGoUp() {
        Board board = player.getSession().getBoard();
        Box otherBox;
        Box box = board.getBox(x,y);

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                try {
                    if (i != 0 || j != 0) {
                        otherBox = board.getBox(x + i, y + j);
                        if (otherBox.getBlock() != Block.DOME && otherBox.isFree() && otherBox.getDifference(box)==1) {
                            return true;
                        }
                    }
                } catch (IndexOutOfBoundsException ignored) {}
            }
        }
        return false;
    }

}
