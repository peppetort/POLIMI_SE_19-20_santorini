package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidBuildException;

/**
 * Rappresenta la classe che modellizza l'azione di costruzione del
 * {@link Player} nel caso in cui abbia la carta ATLAS
 */
public class AtlasBuild extends DefaultBuild{

    private Board board;

    /**
     * Rappresenta il costruttore della classe {@link AtlasBuild}
     *
     * @param player giocatore che ha istanziato la classe
     */
    public AtlasBuild(Player player) {
        super(player);
        board = player.getSession().getBoard();
    }

    /**
     * Costruisce una cupola sulla posizione specificata senza che sulla
     * {@link Box} corrispondente ci sia necessariamente un {@link Block}
     * LTHREE
     *
     * @param worker è la pedina che costruisce
     * @param x è la posizione X della {@link Board} sulla quale si vuole costruire
     * @param y è la posizione Y della {@link Board} sulla quale si vuole costruire
     * @throws RuntimeException se la {@link Box} sulla quale si vuole costruire non è vicino alla pedina
     * @throws RuntimeException se la {@link Box} sulla quale si vuole costruire ha già una cupola
     * @throws RuntimeException se la {@link Box} sulla quale si vuole costruire è occupata da un'altra pedina
     */
    public void buildDome(Worker worker, int x, int y){
        try {
            int wX = worker.getXPos();
            int wY = worker.getYPos();

            Box box = board.getBox(x, y);

            if( x > wX+1 || x < wX-1 || y > wY+1 || y < wY-1 || (x == wX && y == wY) || !box.isFree()) {
                throw new InvalidBuildException("Invalid build!");
            }else {
                switch (box.getBlock()) {
                    case TERRAIN:
                    case LONE:
                    case LTWO:
                    case LTHREE:
                        box.build(Block.DOME);
                        break;
                    case DOME:
                        throw new InvalidBuildException("Can't build here! There is a DOME");
                    default:
                        throw new RuntimeException("Unexpected case!");
                }
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("Out of board limits");
        }catch (NullPointerException e){
            System.out.println("Worker not in board");
        }
    }
}
