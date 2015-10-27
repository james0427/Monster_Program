package info.gridworld.actor;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.*;

public interface IActor {
    /**
     * Gets the color of this actor.
     * @return the color of this actor
     */
    Color getColor();

    /**
     * Sets the color of this actor.
     * @param newColor the new color
     */
    void setColor(Color newColor);

    /**
     * Gets the grid in which this actor is located.
     * @return the grid of this actor
     */
    Grid<Actor> getGrid();

    /**
     * Gets the location of this actor.
     * @return the location of this actor, or <code>null</code> if this actor is
     * not contained in a grid
     */
    Location getLocation();

    /**
     * Puts this actor into a grid. If there is another actor at the given
     * location, it is removed. <br />
     * Precondition: (1) This actor is not contained in a grid (2)
     * loc is valid in gr
     * @param gr the grid into which this actor should be placed
     * @param loc the location into which the actor should be placed
     */
    void putSelfInGrid(Grid<Actor> gr, Location loc);

    /**
     * Get the name of the actor
     * @return Name
     */
    String getName();

    /**
     * Set the name of the actor
     * @param name
     */
    void setName(String name);

    /**
     * Return x coord of the actor
     * @return x
     */
    int getX();

    /**
     * Sets the x coord of the actor
     * @param x
     */
    void setX(int x);

    /**
     * Return y coord of the actor
     * @return y
     */
    int getY();

    /**
     * sets Y coord of the actor
     * @param y
     */
    void setY(int y);

    /**
     * Removes this actor from its grid. <br />
     */
    void removeSelfFromGrid();

    /**
     * Moves this actor to a new location. If there is another actor at the
     * given location, it is removed.
     * Precondition: (1) This actor is contained in a grid (2)
     * newLocation is valid in the grid of this actor
     * @param newLocation the new location
     */
    void moveTo(Location newLocation);

    /**
     * Act method for child classes to ocerride
     */
    void act();

    /**
     * Creates a string that describes this actor.
     * @return a string with the grid, location, color, name, x coord, and y coord
     */
    @Override
    String toString();
}
