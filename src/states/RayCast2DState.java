package states;

import java.awt.*;

import environment.Handler;
import environment.entities.*;
import environment.entities.utils.Shape;
import environment.world.World;

public class RayCast2DState extends State {

    private final Handler handler;
    private final World world;

    // Demonstration purpose
    private Entity cross;
    private float speed = -0.3f;

    public RayCast2DState(Handler handler) {
        this.handler = handler;

        world = new World(handler);
        handler.setWorld(world);

        //world.addEntity(new LampEntity(handler, handler.getWidth() / 2, handler.getHeight() / 2, 48, 350));
        world.addEntity(new RayCastPointEntity(handler, handler.getWidth() / 2, handler.getHeight() / 2));

        world.addEntity(new RectangleEntity(handler, 100, 250, 40, 400));
        world.addEntity(new RectangleEntity(handler, 900, 250, 30, 200));
        world.addEntity(new RectangleEntity(handler, 200, 550, 450, 30));
        world.addEntity(new RectangleEntity(handler, 150, 650, 780, 20));

        cross = new CrossShapeEntity(handler, 680, 440, 150, 150, 20);
        world.addEntity(cross);

        // By default, the origin point is point with index 0
        // Check environment.entities.utils.Shape more detailed preview
        Shape customShape = new Shape(new int[]{250, 800, 800, 280, 280, 250}, new int[]{100, 100, 130, 130, 200, 200}, 6, null);
        Entity customShapeEntity = new CustomShapeEntity(handler, customShape);
        world.addEntity(customShapeEntity);
    }


    @Override
    public void update() {
        world.update();

        // Demonstration purpose
        if (cross.getX() < 250 || cross.getX() > 680) speed = -speed;

        cross.setLocation(cross.getX() + speed, cross.getY());
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
    }

}
