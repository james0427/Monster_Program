package info.gridworld.actor;

import info.gridworld.grid.Location;

public interface IHuman {

    /**
     * Sets the end location of where the human will stop
     * @param x
     * @param y
     */
    void setEndLocation(int x, int y);

    /**
     * Moves the Human towards the end point location
     * @param loc
     */
    void makeMoveToEnd(Location loc);

    /**
     * Turns the Human 45 degrees if needed.
     */
    void turn();
}
