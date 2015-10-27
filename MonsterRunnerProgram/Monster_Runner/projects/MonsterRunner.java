/*
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2005-2006 Cay S. Horstmann (http://horstmann.com)
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
 * @author Chris Nevison
 * @author Barbara Cloud Wells
 * @author Cay Horstmann
 */

/**
 * Names: James Miller and Sanan Aamir
 * Credits: Cay Hortsmann and Dr. Catherine Stringfellow
 * Date: 10/26/2015
 * Description: This program loads a game that contains a Grid world where
 * a user can place various actors like Humans, Monsters, Rocks and Food. Once the
 * application is pulled up, the user selects the location of the actors. After selecting
 * a human the end location of for the human is set. The Human has to find a way to
 * get to the end before the monsters eat him/her. On the way the human can also eat some food
 * if he/she likes hamburgers.
 * Once the Application starts, the background music stars to play.
 */

import info.gridworld.actor.ActorWorld;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
/**
 * This class runs an empty world
 * Click on empty locations to add additional actors.
 * Click on monster and then random location for random placement
 */
public class MonsterRunner {
    public static void main(String[] args) {
        ActorWorld world = new ActorWorld();
        world.show();
        musicPlayer();
    }

    /**
     * Music Player
     */

    public static void musicPlayer(){
        try{
            File file = new File
                    ("/Users/James/Desktop/Contemp/Gridworld_Complete/" +
                            "MonsterRunnerProgram/Monster_Runner/projects/halloween_midnight.mp3");

            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            try{
                Player player = new Player(bis);
                player.play();
            }
            catch(JavaLayerException ex){

            }

        }catch(IOException e){

        }
    }

}