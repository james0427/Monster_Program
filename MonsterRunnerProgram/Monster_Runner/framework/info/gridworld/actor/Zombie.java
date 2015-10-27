package info.gridworld.actor;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.*;
import java.util.ArrayList;

public class Zombie extends Monster implements IZombie {

    public Zombie() {
        super();
    }

    public Zombie(Color c, int x, int y,int direction) {
        super(c, x, y,direction);
    }

    /**
     * Gets the actors around the zombies
     * The zombie can look ahead by 1 and also right and left
     * @return actors
     */
    @Override
    public ArrayList<Actor> getActors()
    {
        ArrayList<Actor> actors = new ArrayList<>();
        int[] dirs =
                { Location.AHEAD, Location.EAST, Location.WEST };
        for (Location loc : getLocationsInDirections(dirs))
        {
            Actor a = getGrid().get(loc);
            if (a != null)
                actors.add(a);
        }

        return actors;
    }

    /**
     *
     * @return loc - list of locations
     */
    @Override
    public ArrayList<Location> getMoveLocations()
    {
        ArrayList<Location> locs = new ArrayList<>();
        int[] dirs =
                { Location.LEFT, Location.RIGHT };
        for (Location loc : getLocationsInDirections(dirs))
            if (getGrid().get(loc) == null)
                locs.add(loc);

        return locs;
    }

    /**
     * Moves zombie left or right based on random value called
     * @param loc
     */
    @Override
    public void makeMove(Location loc)
    {
        if (loc.equals(getLocation()))
        {
            double r = Math.random();
            int angle;
            if (r < 0.5)
                angle = Location.LEFT;
            else
                angle = Location.RIGHT;
            setDirection(getDirection() + angle);
        }
        else
            super.makeMove(loc);
    }

    /**
     * Finds different beings adjacent to the zombie
     * Allows for the zomibe to look in a diagonal location
     * Does not allow of the zombies to move that way though
     * @param directions
     * @return locs
     */
    @Override
    public ArrayList<Location> getLocationsInDirections(int[] directions)
    {
        ArrayList<Location> locs = new ArrayList<>();
        Grid gr = getGrid();
        Location loc = getLocation();

        for (int d : directions)
        {
            Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
            if (gr.isValid(neighborLoc))
                locs.add(neighborLoc);
        }
        return locs;
    }
}
