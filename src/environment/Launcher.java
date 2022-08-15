package environment;

import environment.entities.*;
import environment.entities.utils.EntityModification;
import environment.entities.utils.Shape;
import environment.world.World;
import input.KeyManager;

import java.awt.*;

public class Launcher {

    /**
     * <h1>Ray-Casting 2D</h1>
     * <h2>You can use (W, A, S, D) keys to move the RayCasting Point</h2>
     * <p>Basic REAL-TIME ray-casting system for 2D environment made in java.</p>
     * <p>NOTE: A more detailed object-oriented structure might exist because the original idea wasn't exactly like it is now.</p>
     *
     * @param args nothing to do with the args
     */
    public static void main(String[] args) {
        ViewController viewController = new ViewController("Ray-Casting-2D DEMO", 1000, 800);
        viewController.start();

        World world = viewController.getHandler().getWorld();
        addEntitiesToWorld(world);

        /**
         * There are two ray cast entities to play with:
         * Uncomment the one you want to use!
         */
        //rayCastPointEntity(world);
        lampEntity(world);
    }

    /**
     * <h2>Adds a RayCastEPointEntity to the center of the screen.</h2>
     * <p>NOTE, that if the ViewCamera offset is changed, the RayCastPointEntity is not going to be in the center of the screen.</p>
     */
    public static void rayCastPointEntity(World world) {
        Handler handler = world.getHandler();
        RayCastPointEntity rayCastPointEntity = new RayCastPointEntity(handler, (float) handler.getWidth() / 2, (float) handler.getHeight() / 2);
        world.addEntity(rayCastPointEntity);
        rayCastPointEntity.addModification(entityModificationForMoving(rayCastPointEntity));
    }

    /**
     * <h2>Adds a LampEntity to the center of the screen.</h2>
     * <p>NOTE, that if the view camera offset is changed, the lamp is not going to be in the center of the screen.</p>
     */
    public static void lampEntity(World world) {
        Handler handler = world.getHandler();
        LampEntity lampEntity = new LampEntity(handler, (float) handler.getWidth() / 2, (float) handler.getHeight() / 2, 500, 250);
        lampEntity.addModification(entityModificationForMoving(lampEntity));
        world.addEntity(lampEntity);
    }

    private static EntityModification entityModificationForMoving(Entity entityToMove) {
        EntityModification movingModification = new EntityModification() {
            @Override
            public void earlyUpdate() {
                move(entityToMove, 3.5f);
            }

            @Override
            public void earlyRender(Graphics g) {

            }

            @Override
            public void lateUpdate() {

            }

            @Override
            public void lateRender(Graphics g) {

            }
        };

        return movingModification;
    }

    /**
     * <h2>Adds different obstacles to the world.</h2>
     */
    private static void addEntitiesToWorld(World world) {
        Handler handler = world.getHandler();

        // Adding rectangle entities to the world
        world.addEntity(new RectangleEntity(handler, 100, 250, 40, 400));
        world.addEntity(new RectangleEntity(handler, 900, 250, 30, 200));
        world.addEntity(new RectangleEntity(handler, 200, 550, 450, 30));
        world.addEntity(new RectangleEntity(handler, 150, 650, 780, 20));


        // Adding cross shaped entity which is moving, because of the added EntityModification
        CrossShapeEntity cross = new CrossShapeEntity(handler, 680, 440, 150, 150, 20);
        world.addEntity(cross);
        cross.addModification(new EntityModification() {
            float speed = 0.7f;

            @Override
            public void earlyUpdate() {
                // Nothing happens in here
            }

            @Override
            public void earlyRender(Graphics g) {
                // Nothing happens in here
            }

            @Override
            public void lateUpdate() {
                if (cross.getX() < 250 || cross.getX() > 680) speed = -speed;

                cross.setLocation(cross.getX() + speed, cross.getY());
            }

            @Override
            public void lateRender(Graphics g) {
                // Nothing happens in here
            }
        });

        // Adding custom shaped entity
        // By default, the origin point is point with index 0
        // Check environment.entities.utils.Shape more detailed preview
        environment.entities.utils.Shape customShape = new Shape(new int[]{250, 800, 800, 280, 280, 250}, new int[]{100, 100, 130, 130, 200, 200}, 6, null);
        Entity customShapeEntity = new CustomShapeEntity(handler, customShape);
        world.addEntity(customShapeEntity);

        // TODO: Make new custom shaped entity
//        environment.entities.utils.Shape customShape2 = new Shape(new int[]{}, new int[]{100, 100, 130, 130, 200, 200}, 6, null);
//        Entity customShapeEntity2 = new CustomShapeEntity(handler, customShape2);
//        world.addEntity(customShapeEntity2);
    }

    private static void move(Entity entity, float speed) {
        KeyManager keyManager = entity.getHandler().getKeyManager();

        if (keyManager.isDown())
            entity.setLocationOffset(0, speed);
        else if (keyManager.isUp())
            entity.setLocationOffset(0, -speed);
        else if (keyManager.isLeft())
            entity.setLocationOffset(-speed, 0);
        else if (keyManager.isRight())
            entity.setLocationOffset(speed, 0);
    }
}
