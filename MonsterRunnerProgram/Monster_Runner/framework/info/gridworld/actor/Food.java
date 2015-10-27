package info.gridworld.actor;

import java.awt.*;

public class Food extends Actor implements IFood {

    /**
     * Private data member
     */
    private static final Color DEFAULT_COLOR = Color.ORANGE;

    /**
     * Sets default color of food
     * Also other values initialized in super constructor.
     */
    public Food()
    {
        super();
        setColor(DEFAULT_COLOR);
    }

    /**
     * parametrized constructor
     * @param color
     */
    public Food(Color color)
    {
        super();
        setColor(color);
    }

}
