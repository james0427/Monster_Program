package info.gridworld.actor;

import info.gridworld.grid.Location;
import java.awt.*;
import java.util.Random;

public class Human extends Being implements IHuman {

    /**
     * Private data members
     */
    private static Location endLocation;

    /**
     * Constructs a human along with random end point coords
     * Sets default color of the Human to Orange
     */
    public Human() {
        super();
        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        endLocation = new Location(x, y);
        setColor(Color.orange);
    }

    /**
     * Parametrized constructor
     * @param color
     * @param x
     * @param y
     * @param direction
     */
    public Human(Color color, int x, int y, int direction) {
        super(color, x, y, direction);
        endLocation = new Location(x, y);
    }

    /**
     * Sets the end location of where the human will stop
     * @param x
     * @param y
     */
    @Override
    public void setEndLocation(int x, int y) {
        endLocation = new Location(x, y);
    }

    /**
     * Returns the end location (x and y) on the grid
     * @return endLocation
     */
    public static Location getEndLocation() {
        return endLocation;
    }

    /**
     * Moves if it can move, turns otherwise.
     */
    @Override
    public void act()
    {
        if (canMove())
            move();
        else
            makeMoveToEnd(endLocation);
    }

    /**
     * Moves the Human towards the end point location
     * @param loc
     */
    @Override
    public void makeMoveToEnd(Location loc) {
        setDirection(getLocation().getDirectionToward(loc));
        super.move();
    }

    /**
     * Turns the Human 45 degrees if needed.
     */
    @Override
    public void turn()
    {
        setDirection(getLocation().getDirectionToward(endLocation));
    }
}
