package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidBuildException;

/**
 * Represents the build if the
 * {@link Player} has the card ATLAS
 */
public class AtlasBuild extends DefaultBuild {


    /**
     * Constructor of the class {@link AtlasBuild}
     * @param player
     */
    public AtlasBuild(Player player) {
        super(player);
    }

    /**
     *
     * @param worker the {@link Worker} that builds
     * @param x x coordinate where I want to build
     * @param y y coordinate where I want to build
     * @throws InvalidBuildException if I try to build at more than one block of distance.
     * @throws InvalidBuildException if I try to build over a dome.
     * @throws InvalidBuildException if I try to build over a pawn.
     */
    public void buildDome(Worker worker, int x, int y) throws IndexOutOfBoundsException, NullPointerException {

        int wX = worker.getXPos();
        int wY = worker.getYPos();

        Box box = board.getBox(x, y);

        if (x > wX + 1 || x < wX - 1 || y > wY + 1 || y < wY - 1 ) {
            throw new InvalidBuildException("Build too far from the worker position!");
        }else if(x == wX && y == wY) {
            throw new InvalidBuildException("Invalid build!");
        }else if(!box.isFree()){
            throw new InvalidBuildException("Can't build here! There is a worker");
        } else {
            switch (box.getBlock()) {
                case TERRAIN:
                case LONE:
                case LTWO:
                case LTHREE:
                    board.build(x, y, Block.DOME);
                    break;
                case DOME:
                    throw new InvalidBuildException("Can't build here! There is a DOME");
                default:
                    throw new RuntimeException("Unexpected case!");
            }
        }

    }
}
