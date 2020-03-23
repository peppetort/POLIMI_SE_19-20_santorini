package it.polimi.ingsw.Model;

/**
 * Rappresenta la classe utilizzata dal {@Link Player} nel caso in cui non possegga una carta in grado di modificare
 * la sua mossa "costruzione".
 */
public class DefaultBuild implements Build {

    private Board board;

    /**
     * Rappresenta il metodo costruttore.
     * @param player rappresenta il {@Link Player} che costruirà gli edifici usando le regole base del gioco.
     */
    public DefaultBuild(Player player){
        this.board = player.getSession().getBoard();
    }

    /**
     * Rappresenta il metodo per eseguire la costruzione secondo le regole base del gioco.
     * @param worker è la pedina che costruisce
     * @param x è la posizione X della {@link Board} sulla quale si vuole costruire
     * @param y è la posizione Y della {@link Board} sulla quale si vuole costruire
     * @throws RuntimeException se provo a costruire a più di una cella di distanza dalla {@Link Worker}
     * @throws RuntimeException se provo a costruire sulle stesse coordinate di {@Link Worker}
     * @throws RuntimeException se provo a costruire sulle stesse coordinate di un altro {@Link Worker}
     * @throws RuntimeException se provo a costruire sopra una cupola
     * @throws RuntimeException se incontro errori a RunTime
     * @throws IndexOutOfBoundsException se provo a costruire fuori dai bordi di {@Link Board}
     * @throws NullPointerException se il riferimento di {@Link Worker} è null
     */
    @Override
    public void build(Worker worker,int x, int y) {
        try {
            Box box = board.getBox(x, y);
            int wX,wY;
            wX = worker.getXPos();
            wY= worker.getXPos();
            if( x > wX+1 || x < wX-1 || y > wY+1 || y < wY-1 ) {
                throw new RuntimeException("Impossibile costruire a due celle di distanza");
            }
            if(x == wX && y == wY) {
                throw new RuntimeException("Impossibile costruire sulla stessa cella della pedina");
            }
            if(!board.getBox(x,y).isFree()) {
                throw new RuntimeException("Impossibile costruire sulla stessa cella di un'altra pedina");
            }
            else {
                switch (box.getBlock()) {
                    case TERRAIN:
                        box.build(Block.LONE);
                        break;
                    case LONE:
                        box.build(Block.LTWO);
                        break;
                    case LTWO:
                        box.build(Block.LTHREE);
                        break;
                    case LTHREE:
                        box.build(Block.DOME);
                        break;
                    case DOME:
                        throw new RuntimeException("Can't build here! There is a DOME");
                    default:
                        throw new RuntimeException("Unexpected case!");
                }
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("Out of board limits");
        }catch (NullPointerException e){
            System.out.println("Worker not placed!");
        }
    }

    void buildDome(int x, int y){};
}
