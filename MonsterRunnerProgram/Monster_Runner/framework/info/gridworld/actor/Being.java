package info.gridworld.actor;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.*;


public class Being extends Actor implements IBeing {

    /**
     * Private data members
     */
    private int healthPoints;
    private int direction;

    /**
     * Constructs a being with 10 health, and default point of north
     * With other values initialized in super constructor.
     */
    public Being() {
        super();
        healthPoints = 10;
        direction = Location.NORTH;
    }

    /**
     * Parametrized constructor
     * @param c
     * @param x
     * @param y
     * @param direction
     */
    public Being(Color c, int x, int y, int direction) {
        this.healthPoints = 10;
        this.direction = direction;
        super.setColor(c);
        super.setX(x);
        super.setY(y);
    }

    /**
     * Returns health points of being
     * @return healthPoints
     */
    @Override
    public int getHealthPoints() {
        return healthPoints;
    }

    /**
     * Sets health points of being
     * @param healthPoints
     */
    @Override
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    /**
     * Gets the current direction of this actor.
     * @return the direction of this actor
     */
    @Override
    public int getDirection()
    {
        return direction;
    }

    /**
     * Sets the current direction of this actor.
     * @param newDirection the new direction.
     */
    @Override
    public void setDirection(int newDirection)
    {
        direction = newDirection % 360;
        if (direction < 0)
            direction += 360;
    }

    /**
     * Moves a being of the grid
     */
    @Override
    public void move()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (gr.isValid(next))
            moveTo(next);
        else
            removeSelfFromGrid();
    }

    /**
     * Move or turns a being based on obstacle
     */
    @Override
    public void act()
    {
        if (canMove())
            move();
        else
            turn();
    }

    /**
     * Turns the being half right
     */
    @Override
    public void turn()
    {
        setDirection(getDirection() + Location.HALF_RIGHT);
    }

    /**
     * Checks whether a being can move or if there is a obstacle
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
        return (neighbor == null) || (neighbor instanceof Food);

    }

}
