package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.InvalidBuildException;

/**
 * <p>
 * Strategy Pattern interface for the Build
 * </p>
 */

public interface Build {

    /**
     *
     * @param worker the {@link Worker} that builds
     * @param x x coordinate where I want to build
     * @param y y coordinate where I want to build
     * @throws InvalidBuildException if I try to build at more than one block of distance.
     * @throws InvalidBuildException if I try to build over a dome.
     * @throws InvalidBuildException if I try to build over a pawn.
     */
    void build(Worker worker, int x, int y);
    void buildDome(Worker worker, int x, int y);
}
