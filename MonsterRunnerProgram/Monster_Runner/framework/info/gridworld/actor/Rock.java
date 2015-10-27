package info.gridworld.actor;

import java.awt.Color;

public class Rock extends Actor implements IRock {

    /**
     * Private data members
     */
    private static final Color DEFAULT_COLOR = Color.BLACK;

    /**
     * Constructs a black rock.
     */
    public Rock()
    {
        super();
        setColor(DEFAULT_COLOR);
    }

    /**
     * Constructs a rock of a given color.
     * @param rockColor the color of this rock
     */
    public Rock(Color rockColor)
    {
        setColor(rockColor);
    }

}
