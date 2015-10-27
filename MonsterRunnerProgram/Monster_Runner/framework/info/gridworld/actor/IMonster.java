package info.gridworld.actor;

import info.gridworld.grid.Location;

import java.util.ArrayList;

/**
 * Created by James on 10/25/15.
 */
public interface IMonster {

    /**
     * Checks surrounding grid locations for spots
     * Then makes corresponding moves based upon the locations
     */
    void act();

    /**
     * Gets the actors currently on the grid.
     * Looks for grid locations that are surrounding the area for open spots
     * @return List of actor
     */
    ArrayList<Actor> getActors();

    /**
     * Gets the actors currently on the grid
     * Watches to see if a current monster can eat a human or food
     * Once the Human or food as been ate it is removed from the grid
     * @param actors
     */
    void processActors(ArrayList<Actor> actors);

    /**
     * Locates moves available on the grid
     * @return location
     */
    ArrayList<Location> getMoveLocations();

    /**
     * Selects an available move that can be made
     * @param locs
     * @return location of r
     */
    Location selectMoveLocation(ArrayList<Location> locs);

    /**
     * Checks whether a monster can move to the next grid spot
     * @return true or false
     */
    boolean canMove();

    /**
     * Moves the Monster to a location of the grid
     * If the location is null the Monster is removed from the grid
     * Also over writes other Monsters of the grid spot if needed
     * @param loc
     */
    void makeMove(Location loc);
}
