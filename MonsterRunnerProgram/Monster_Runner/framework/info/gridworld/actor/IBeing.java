package info.gridworld.actor;


public interface IBeing {

    /**
     * Returns health points of being
     * @return healthPoints
     */
    int getHealthPoints();

    /**
     * Sets health points of being
     * @param healthPoints
     */
    void setHealthPoints(int healthPoints);

    /**
     * Gets the current direction of this actor.
     * @return the direction of this actor
     */
    int getDirection();

    /**
     * Sets the current direction of this actor.
     * @param newDirection the new direction.
     */
    void setDirection(int newDirection);

    /**
     * Moves a being of the grid
     */
    void move();

    /**
     * Move or turns a being based on obstacle
     */
    void act();

    /**
     * Turns the being half right
     */
    void turn();

    /**
     * Checks whether a being can move or if there is a obstacle
     * @return true or false
     */
    boolean canMove();
}
