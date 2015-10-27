package info.gridworld.actor;

import info.gridworld.grid.Location;

import java.util.ArrayList;

public interface IZombie {

    /**
     * Gets the actors around the zombies
     * The zombie can look ahead by 1 and also right and left
     * @return actors
     */
    ArrayList<Actor> getActors();

    /**
     *
     * @return loc - list of locations
     */
    ArrayList<Location> getMoveLocations();

    /**
     * Moves zombie left or right based on random value called
     * @param loc
     */
    void makeMove(Location loc);

    /**
     * Finds different beings adjacent to the zombie
     * Allows for the zomibe to look in a diagonal location
     * Does not allow of the zombies to move that way though
     * @param directions
     * @return locs
     */
    ArrayList<Location> getLocationsInDirections(int[] directions);
}
