package info.gridworld.gui;

import info.gridworld.actor.*;
import info.gridworld.grid.*;
import info.gridworld.world.World;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Modifier;
import java.util.*;
import javax.swing.Timer;
import javax.swing.*;

/**
 * The GUIController controls the behavior in a WorldFrame.
 */
public class GUIController<T> {

    /**
     * Private data members/controls of the GUI
     */
    private static final int MIN_DELAY_MSECS = 10, MAX_DELAY_MSECS = 1000;
    private static final int INITIAL_DELAY = MIN_DELAY_MSECS
            + (MAX_DELAY_MSECS - MIN_DELAY_MSECS) / 2;
    private Timer timer;
    private JButton stepButton, runButton, stopButton, randomGenerateButton;
    private JComboBox<String> actorComboBox;
    private JComponent controlPanel;
    private GridPanel display;
    private WorldFrame<T> parentFrame;
    private int numStepsToRun, numStepsSoFar;
    private Actor human;
    private DisplayMap displayMap;
    private boolean running;
    private Set<Class> occupantClasses;
    private JButton resetButton;

    /**
     * About instructions when the application starts. These instructions
     * help the player to play this awesome game.
     */
    public final String Instructions = "Monster Runner Game\n" +
            "The point of the game:\n" +
            "Get the Human to their end location tile\n" +
            "------------------------------------------\n" +
            "Select a location on the grid and then \n" +
            "use the ComboBox and select monster then place\n" +
            "The human must be set first to allow the end point to be calculated.\n" +
            "Once you have the Human placed select where to place your Monsters\n" +
            "Hit run and see if your Human can get past\n" +
            " the Monsters to the end location\n" +
            "\n" +
            "Ways to Play:\n" +
            "-------------\n" +
            "Select a location and then select which monster to place.\n" +
            "Select a Monster and then select a random location.\n" +
            "Place rocks to slow down Monsters.\n" +
            "Place food around the map for the monsters and humans\n" +
            "\n" +
            "Be careful and watch where you place your Human because,\n" +
            "monsters can kill the Human and end the game\n" +
            "If you understand hit 'Okay' and have fun!";


    /**
     * Parametrized constructor.
     * Creates a new controller tied to the specified display and gui
     * frame.
     *
     * @param parent     the frame for the world window
     * @param disp       the panel that displays the grid
     * @param displayMap the map for occupant displays
     */
    public GUIController(WorldFrame<T> parent, GridPanel disp,
                         DisplayMap displayMap) {
        display = disp;
        parentFrame = parent;
        this.displayMap = displayMap;

        //Make controls after members have been initialized
        makeControls();

        /**
         * Initializing a set of occupant classes
         */
        occupantClasses = new TreeSet<Class>(new Comparator<Class>() {
            public int compare(Class a, Class b) {
                return a.getName().compareTo(b.getName());
            }
        });

        /**
         * Initialze a world object and from that
         * initialize a grid object
         */
        World<T> world = parentFrame.getWorld();
        Grid<T> gr = world.getGrid();

        /**
         * Add occupants to the world
         */
        for (Location loc : gr.getOccupiedLocations())
            addOccupant(gr.get(loc));

        /**
         * Get class names of occupant classes on world and add them to
         * the occupantClasses Set
         */
        for (String name : world.getOccupantClasses()) {
            try {
                occupantClasses.add(Class.forName(name));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        /**
         * Timer to delay the event
         */
        timer = new Timer(INITIAL_DELAY, evt -> step());

        /**
         * Mouse event listener for display panel
         */
        display.addMouseListener(new MouseAdapter() {

            /**
             * When mouse is pressed on a cell on the grid
             * set location and also paint that location
             * @param evt
             */
            public void mousePressed(MouseEvent evt) {
                Grid<T> gr = parentFrame.getWorld().getGrid();
                Location loc = display.locationForPoint(evt.getPoint());
                if (loc != null && gr.isValid(loc) && !isRunning()) {
                    display.setCurrentLocation(loc);
                    locationClicked();
                }
            }
        });


        /**
         * Stop timer and enable/disable buttons on the frame
         */
        stop();
    }

    /**
     * Advances the world one step. When step is called
     * all actors move one step in the world
     */

    public void step() {

        //Repaint the frame after the step has been initiated
        parentFrame.getWorld().step();
        parentFrame.repaint();

        /**
         * Code to handle stoppage of game after certain conditions. If human has been eaten
         * stop the game and exit the application. Else if human has reached end point then stop
         * the application. You see a dialoq box telling you whether you have won or lost the game.
         */
        if (!(parentFrame.getWorld().getGrid().isPresent((Human) human))) {

            stop();
            parentFrame.pack();
            parentFrame.setLocationRelativeTo(null);

            JOptionPane.showMessageDialog(controlPanel, "Sorry you've been eaten", "You've lost!!!!",
                    JOptionPane.PLAIN_MESSAGE);

            System.exit(0);
        } else if (human != null && parentFrame.getWorld().getGrid().isHumanAtEnd((Human) human)) {

            stop();
            parentFrame.pack();
            parentFrame.setLocationRelativeTo(null);

            JOptionPane.showMessageDialog(controlPanel, "Good Job! You've made it to the end.", "You've Won!!!!",
                    JOptionPane.PLAIN_MESSAGE);

            System.exit(0);
        }

        if (++numStepsSoFar == numStepsToRun)
            stop();

        /**
         * Get the new grid after all the changes have been applied
         */
        Grid<T> gr = parentFrame.getWorld().getGrid();

        /**
         * Get the latest state of the occupant classes because some classes
         * like food can be removed from the grid
         */
        for (Location loc : gr.getOccupiedLocations())
            addOccupant(gr.get(loc));
    }

    /**
     * Add occupant to the set of occupant classes
     * @param occupant
     */
    private void addOccupant(T occupant) {
        Class cl = occupant.getClass();
        do {
            if ((cl.getModifiers() & Modifier.ABSTRACT) == 0)
                occupantClasses.add(cl);
            cl = cl.getSuperclass();
        }
        while (cl != Object.class);
    }

    /**
     * Starts a timer to repeatedly carry out steps
     */
    public void run() {
        stopButton.setEnabled(true);
        stepButton.setEnabled(false);
        runButton.setEnabled(false);
        numStepsSoFar = 0;
        timer.start();
        running = true;
    }

    /**
     * Stops any existing timer currently carrying out steps.
     */
    public void stop() {
        timer.stop();
        stopButton.setEnabled(false);
        runButton.setEnabled(true);
        stepButton.setEnabled(true);
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    /**
     * Builds the panel with the various controls (buttons and
     * slider).
     */
    private void makeControls() {

        /**
         * Initialize various controls
         */
        controlPanel = new JPanel();
        parentFrame.pack();
        parentFrame.setLocationRelativeTo(null);
        stepButton = new JButton("Step");
        randomGenerateButton = new JButton("Random Location");
        runButton = new JButton("Run");
        stopButton = new JButton("Stop");
        resetButton = new JButton("Reset");
        String[] actors = {null, "Human", "Zombie", "Vampire", "Rock", "Food"};
        actorComboBox = new JComboBox<>(actors);

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.setBorder(BorderFactory.createEtchedBorder());

        Dimension spacer = new Dimension(5, stepButton.getPreferredSize().height + 10);

        /**
         * Add all the components to the Panel
         */
        controlPanel.add(Box.createRigidArea(spacer));
        controlPanel.add(stepButton);
        controlPanel.add(Box.createRigidArea(spacer));
        controlPanel.add(runButton);
        controlPanel.add(Box.createRigidArea(spacer));
        controlPanel.add(stopButton);
        controlPanel.add(resetButton);
        controlPanel.add(actorComboBox);
        controlPanel.add(randomGenerateButton);
        JOptionPane.showMessageDialog(controlPanel, Instructions, "Monster Runner Instructions",
                JOptionPane.PLAIN_MESSAGE);
        runButton.setEnabled(false);
        stepButton.setEnabled(false);
        stopButton.setEnabled(false);

        controlPanel.add(Box.createRigidArea(spacer));
        controlPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        /**
         * Event listeners for various buttons
         */

        /**
         * Random pics a random location on the grid and paints the
         * selected actor on the map
         */
        randomGenerateButton.addActionListener((ActionEvent e) -> {
            Random random = new Random();
            int randomX = random.nextInt(10);
            int randomY = random.nextInt(10);
            if (actorComboBox.getSelectedItem() != null) {
                Location location = new Location(randomX, randomY);
                Actor actor = initializeActorBasedOnComboBoxSelection();
                parentFrame.getWorld().add(location, (T) actor);
                parentFrame.repaint();
            }
        });

        /**
         * Reset button repaints the whole frame and opens a new instance
         * of the world
         */
        resetButton.addActionListener((ActionEvent e) -> {
            parentFrame.dispose();
            ActorWorld t = new ActorWorld();
            t.show();
        });

        /**
         * Actor combo box that provides set number of options for
         * selection of actors. After selection, actor is initialized and
         * painted on the screen
         */
        actorComboBox.addActionListener((ActionEvent e) -> {
            if (actorComboBox.getSelectedItem() != null) {
                Actor actor = initializeActorBasedOnComboBoxSelection();
                Location location = display.getCurrentLocation();
                parentFrame.getWorld().add(location, (T) actor);
                parentFrame.repaint();
            }

        });

        /**
         * Step button moves the world one step at a time.
         */
        stepButton.addActionListener((ActionEvent e) -> step());

        /**
         * Run button moves all the actors on the board until either the human
         * is killed or human reaches end point.
         */
        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                run();
            }
        });

        /**
         * Stop button pauses the game.
         */
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });

    }

    /**
     * This method initialized an actor instance based on the
     * combo box selection the player makes. Once the player has
     * selected a human the human is removed from the combo box so that
     * you cannot have more than 1 instance of a human actor
     * @return actor
     */
    private Actor initializeActorBasedOnComboBoxSelection() {
        Actor actor;

        /**
         * Switch statement to initialize the actor instance
         * and return it.
         */
        switch (actorComboBox.getSelectedItem().toString()) {
            case "Human":
                actor = new Human();
                actor.setName("Human");
                human = actor;
                actorComboBox.removeItemAt(1);
                break;
            case "Zombie":
                actor = new Zombie();
                actor.setName("Zombie");
                actor.setColor(Color.green);
                break;
            case "Vampire":
                actor = new Vampire();
                actor.setName("Vampire");
                actor.setColor(Color.WHITE);
                break;
            case "Rock":
                actor = new Rock();
                actor.setName("Rock");
                break;
            default:
                actor = new Food();
                actor.setName("Food");
        }
        return actor;
    }

    /**
     * Returns the panel containing the controls.
     *
     * @return the control panel
     */

    public JComponent controlPanel() {
        return controlPanel;
    }

    /**
     * Callback on mousePressed when editing a grid.
     */

    private void locationClicked() {
        World<T> world = parentFrame.getWorld();
        Location loc = display.getCurrentLocation();
        if (loc != null && !world.locationClicked())
            parentFrame.repaint();
    }
}