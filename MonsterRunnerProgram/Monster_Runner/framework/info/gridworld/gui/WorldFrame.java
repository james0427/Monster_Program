/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2002-2006 College Entrance Examination Board 
 * (http://www.collegeboard.com).
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * @author Julie Zelenski
 * @author Chris Nevison
 * @author Cay Horstmann
 */

package info.gridworld.gui;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.*;

/**
 * The WorldFrame displays a World and allows manipulation of its occupants.
 * <br />
 * This code is not tested on the AP CS A and AB exams. It contains GUI
 * implementation details that are not intended to be understood by AP CS
 * students.
 */
public class WorldFrame<T> extends JFrame
{
    private GUIController<T> control;
    private GridPanel display;
    private World<T> world;
    private ResourceBundle resources;
    private DisplayMap displayMap;
    private Set<Class> gridClasses;


    private static int count = 0;

    /**
     * Constructs a WorldFrame that displays the occupants of a world
     * @param world the world to display
     */
    public WorldFrame(World<T> world)
    {
        this.world = world;
        count++;
        resources = ResourceBundle
                .getBundle(getClass().getName() + "Resources");

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent event)
            {
                count--;
                if (count == 0)
                    System.exit(0);
            }
        });

        displayMap = new DisplayMap();

        String title = "Monster Runner";
        setTitle(title);
        setLocation(25, 15);

        URL appIconUrl = getClass().getResource("GridWorld.gif");
        if (appIconUrl != null)
        {
            ImageIcon appIcon = new ImageIcon(appIconUrl);
            setIconImage(appIcon.getImage());
        }

        JPanel content = new JPanel();
        content.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        content.setLayout(new BorderLayout());
        setContentPane(content);

        display = new GridPanel(displayMap, resources);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(event -> {
            if (getFocusOwner() == null) return false;
            String text = KeyStroke.getKeyStrokeForEvent(event).toString();
            final String PRESSED = "pressed ";
            int n = text.indexOf(PRESSED);
            if (n < 0) return false;
            // filter out modifier keys; they are neither characters or actions
            if (event.getKeyChar() == KeyEvent.CHAR_UNDEFINED && !event.isActionKey())
                return false;
            text = text.substring(0, n)  + text.substring(n + PRESSED.length());
            boolean consumed = getWorld().keyPressed();
            if (consumed) repaint();
            return consumed;
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewport(new PseudoInfiniteViewport(scrollPane));
        scrollPane.setViewportView(display);
        content.add(scrollPane, BorderLayout.CENTER);

        gridClasses = new TreeSet<>((a, b) -> {
            return a.getName().compareTo(b.getName());
        });
        for (String name : world.getGridClasses())
            try
            {
                gridClasses.add(Class.forName(name));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

        Grid<T> gr = world.getGrid();
        gridClasses.add(gr.getClass());

        control = new GUIController<>(this, display, displayMap);
        content.add(control.controlPanel(), BorderLayout.SOUTH);

        pack();
        repaint(); // to show message
        display.setGrid(gr);
    }


    /**
     * Gets the world that this frame displays
     * @return the world
     */
    public World<T> getWorld()
    {
        return world;
    }

    /**
     * Sets a new grid for this world. Occupants are transferred from
     * the old world to the new.
     * @param newGrid the new grid
     */
    public void setGrid(Grid<T> newGrid)
    {
        Grid<T> oldGrid = world.getGrid();
        Map<Location, T> occupants = new HashMap<>();
        for (Location loc : oldGrid.getOccupiedLocations())
            occupants.put(loc, world.remove(loc));

        world.setGrid(newGrid);
        for (Location loc : occupants.keySet())
        {
            if (newGrid.isValid(loc))
                world.add(loc, occupants.get(loc));
        }

        display.setGrid(newGrid);
        repaint();
    }

}
