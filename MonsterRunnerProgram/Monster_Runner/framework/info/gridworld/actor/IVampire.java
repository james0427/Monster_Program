package info.gridworld.actor;

import info.gridworld.grid.Location;

import java.util.ArrayList;

public interface IVampire {

    /**
     * Gets available actors surrounding the vampires by 2 spots
     * @return list of actors around vampire
     */
    ArrayList<Actor> getActors();

    /**
     * Gets the available moves the vampire can make on the grid
     * @return locs
     */
    ArrayList<Location> getMoveLocations();

    /**
     * Gets the available moves of the vampire
     * Moves the vampire to the location passed in
     * @param loc
     */
    void makeMove(Location loc);

    /**
     * Gets location of monsters adjacent to the vampire
     * @param directions
     * @return locs
     */
    ArrayList<Location> getLocationsInDirections(int[] directions);
}
