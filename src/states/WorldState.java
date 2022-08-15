package states;

import java.awt.*;

import environment.Handler;
import environment.world.World;

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
