package info.gridworld.actor;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.*;
import java.util.ArrayList;

public class Monster extends Being implements IMonster {
    /**
     * Constructs Monster
     */
    public Monster() {
        super();
    }

    /**
     * Parametrized constructor
     * @param c
     * @param x
     * @param y
     * @param direction
     */
    public Monster(Color c, int x, int y, int direction) {
        super(c, x, y, direction);
    }

    /**
     * Checks surrounding grid locations for spots
     * Then makes corresponding moves based upon the locations
     */
    @Override
    public void act()
    {
        if (getGrid() == null)
            return;
        ArrayList<Actor> actors = getActors();
        processActors(actors);
        ArrayList<Location> moveLocs = getMoveLocations();
        Location loc = selectMoveLocation(moveLocs);
        makeMove(loc);
    }

    /**
     * Gets the actors currently on the grid.
     * Looks for grid locations that are surrounding the area for open spots
     * @return List of actor
     */
    @Override
    public ArrayList<Actor> getActors()
    {
        return getGrid().getNeighbors(getLocation());
    }

    /**
     * Gets the actors currently on the grid
     * Watches to see if a current monster can eat a human or food
     * Once the Human or food as been ate it is removed from the grid
     * @param actors
     */
    @Override
    public void processActors(ArrayList<Actor> actors)
    {
        for (Actor a : actors)
        {
            if (!(a instanceof Rock) && !(a instanceof Monster))
                a.removeSelfFromGrid();
        }
    }

    /**
     * Locates moves available on the grid
     * @return location
     */
    @Override
    public ArrayList<Location> getMoveLocations()
    {
        return getGrid().getEmptyAdjacentLocations(getLocation());
    }

    /**
     * Selects an available move that can be made
     * @param locs
     * @return location of r
     */
    @Override
    public Location selectMoveLocation(ArrayList<Location> locs)
    {
        int n = locs.size();
        if (n == 0)
            return getLocation();
        int r = (int) (Math.random() * n);
        return locs.get(r);
    }

    /**
     * Checks whether a monster can move to the next grid spot
     * @return true or false
     */
    @Override
    public boolean canMove()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return false;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (!gr.isValid(next))
            return false;
        Actor neighbor = gr.get(next);

        return ((neighbor == null) || (neighbor instanceof Food)) || !(neighbor instanceof Monster);
    }

    /**
     * Moves the Monster to a location of the grid
     * If the location is null the Monster is removed from the grid
     * Also over writes other Monsters of the grid spot if needed
     * @param loc
     */
    @Override
    public void makeMove(Location loc)
    {
        if (loc == null)
            removeSelfFromGrid();
        else
            moveTo(loc);
    }
}
