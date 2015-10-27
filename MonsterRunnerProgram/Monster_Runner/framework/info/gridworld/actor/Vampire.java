package info.gridworld.actor;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.*;
import java.util.ArrayList;

public class Vampire extends Monster implements IVampire {

    /**
     * Constructs a instance of vampire
     */
    public Vampire() {
        super();
    }

    /**
     * Parametrized constructor
     * @param c
     * @param x
     * @param y
     * @param direction
     */
    public Vampire(Color c, int x, int y, int direction) {
        super(c, x, y,direction);
    }

    /**
     * Gets available actors surrounding the vampires by 2 spots
     * @return list of actors around vampire
     */
    @Override
    public ArrayList<Actor> getActors()
    {
        ArrayList<Actor> actors = new ArrayList<>();

        int[] dirs =
                { Location.AHEAD+2,Location.NORTH,Location.SOUTH,Location.EAST,
                        Location.WEST,Location.NORTHEAST,Location.SOUTHEAST,Location.SOUTHWEST,
                        Location.NORTHWEST};
        for (Location loc : getLocationsInDirections(dirs))
        {
            Actor a = getGrid().get(loc);
            if (a != null)
                actors.add(a);
        }

        return actors;
    }

    /**
     * Gets the available moves the vampire can make on the grid
     * @return locs
     */
    @Override
    public ArrayList<Location> getMoveLocations()
    {
        ArrayList<Location> locs = new ArrayList<>();
        int[] dirs =
                { Location.LEFT, Location.RIGHT+1, Location.AHEAD+1, Location.FULL_CIRCLE, Location.SOUTH };
        for (Location loc : getLocationsInDirections(dirs))
            if (getGrid().get(loc) == null)
                locs.add(loc);

        return locs;
    }

    /**
     * Gets the available moves of the vampire
     * Moves the vampire to the location passed in
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
     * Gets location of monsters adjacent to the vampire
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
