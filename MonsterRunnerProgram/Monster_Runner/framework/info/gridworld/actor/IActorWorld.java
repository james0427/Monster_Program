package info.gridworld.actor;

import info.gridworld.grid.Location;

public interface IActorWorld {

    void show();

    void step();

    void add(Location loc, Actor occupant);

    Actor remove(Location loc);
}
