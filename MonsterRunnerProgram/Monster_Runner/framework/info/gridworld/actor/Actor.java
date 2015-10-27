package info.gridworld.actor;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;

public class Actor implements IActor {

    /**
     * Private data members for class Actor
     */
    private Location location;
    private Color color;
    private String Name;
    private int x;
    private int y;
    private Grid<Actor> grid;

    /**
     * Constructs a gray actor
     */
    public Actor()
    {
        color = Color.GRAY;
        grid = null;
        location = null;
    }

    /**
     * Parameterized constructor
     * @param c
     * @param grid
     * @param location
     */
    public Actor(Color c, Grid grid, Location location) {
        color = c;
        grid = grid;
        location = location;
    }

    /**
     * Gets the color of this actor.
     * @return the color of this actor
     */
    @Override
    public Color getColor()
    {
        return color;
    }

    /**
     * Sets the color of this actor.
     * @param newColor the new color
     */
    @Override
    public void setColor(Color newColor)
    {
        color = newColor;
    }

    /**
     * Gets the grid in which this actor is located.
     * @return the grid of this actor
     */
    @Override
    public Grid<Actor> getGrid()
    {
        return grid;
    }

    /**
     * Gets the location of this actor.
     * @return the location of this actor, or <code>null</code> if this actor is
     * not contained in a grid
     */
    @Override
    public Location getLocation()
    {
        return location;
    }

    /**
     * Puts this actor into a grid. If there is another actor at the given
     * location, it is removed. <br />
     * Precondition: (1) This actor is not contained in a grid (2)
     * loc is valid in gr
     * @param gr the grid into which this actor should be placed
     * @param loc the location into which the actor should be placed
     */
    @Override
    public void putSelfInGrid(Grid<Actor> gr, Location loc)
    {
        Actor actor = gr.get(loc);
        if (actor != null)
            actor.removeSelfFromGrid();
        gr.put(loc, this);
        grid = gr;
        location = loc;
    }

    /**
     * Get the name of the actor
     * @return Name
     */
    @Override
    public String getName() {
        return Name;
    }

    /**
     * Set the name of the actor
     * @param name
     */
    @Override
    public void setName(String name) {
        Name = name;
    }

    /**
     * Return x coord of the actor
     * @return x
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * Sets the x coord of the actor
     * @param x
     */
    @Override
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Return y coord of the actor
     * @return y
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * sets Y coord of the actor
     * @param y
     */
    @Override
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Removes this actor from its grid. <br />
     */
    @Override
    public void removeSelfFromGrid()
    {
        grid.remove(location);
        grid = null;
        location = null;
    }

    /**
     * Moves this actor to a new location. If there is another actor at the
     * given location, it is removed.
     * Precondition: (1) This actor is contained in a grid (2)
     * newLocation is valid in the grid of this actor
     * @param newLocation the new location
     */
    @Override
    public void moveTo(Location newLocation)
    {
        if (newLocation.equals(location))
            return;
        grid.remove(location);
        Actor other = grid.get(newLocation);
        if (other != null)
            other.removeSelfFromGrid();
        location = newLocation;
        grid.put(location, this);
    }

    /**
     * Act method for child classes to ocerride
     */
    @Override
    public void act()
    {
    }

    /**
     * Creates a string that describes this actor.
     * @return a string with the grid, location, color, name, x coord, and y coord
     */
    @Override
    public String toString() {
        return "Actor{" +
                "grid=" + grid +
                ", location=" + location +
                ", color=" + color +
                ", Name='" + Name + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}