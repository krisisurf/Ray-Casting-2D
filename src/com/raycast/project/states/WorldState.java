package com.raycast.project.states;

import java.awt.*;

import com.raycast.project.environment.Handler;
import com.raycast.project.environment.world.World;

public class WorldState extends State {

    private final Handler handler;
    private final World world;

    public WorldState(Handler handler) {
        this.handler = handler;

        world = new World(handler);
        handler.setWorld(world);
    }


    @Override
    public void update() {
        world.update();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
    }

}
