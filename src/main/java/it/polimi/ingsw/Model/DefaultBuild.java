package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidBuildException;

/**
 * Represents the build if the
 * {@link Player} has no {@link God} / has a {@link God} that doesn't modifies the build rules.
 */
public class DefaultBuild implements Build {

    final Board board;

    /**
     * Constructor method
     *
     * @param player represents the {@link Player} that will build with default rules.
     */
    public DefaultBuild(Player player) {
        this.board = player.getSession().getBoard();
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

    @Override
    public void build(Worker worker, int x, int y) throws IndexOutOfBoundsException, NullPointerException {

        Box box = board.getBox(x, y);
        Block block;
        int wX, wY;
        wX = worker.getXPos();
        wY = worker.getYPos();
        if (x > wX + 1 || x < wX - 1 || y > wY + 1 || y < wY - 1) {
            throw new InvalidBuildException("Build too far from the worker position!");
        }else if(x == wX && y == wY) {
            throw new InvalidBuildException("Invalid build!");
        }else if(!box.isFree()){
            throw new InvalidBuildException("Can't build here! There is a worker");
        } else {
            switch (box.getBlock()) {
                case TERRAIN:
                    block = Block.LONE;
                    break;
                case LONE:
                    block = Block.LTWO;
                    break;
                case LTWO:
                    block = Block.LTHREE;
                    break;
                case LTHREE:
                    block = Block.DOME;
                    break;
                case DOME:
                    throw new InvalidBuildException("Can't build here! There is a DOME");
                default:
                    throw new InvalidBuildException("Unexpected case!");
            }
            //salvataggio stato della cella prima di costruirci
            board.addAction(box.getPawn(),x,y,box.getBlock());

            board.build(x, y, block);
        }
    }

}
