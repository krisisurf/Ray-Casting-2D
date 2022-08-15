package environment;

import environment.entities.*;
import environment.entities.utils.EntityModification;
import environment.entities.utils.Shape;
import environment.world.World;
import input.KeyManager;

import javax.swing.*;
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

        int result = JOptionPane.showOptionDialog(null,
                "Which entity you would like to test?",
                "Ray-Cast 2D",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{"RayCastPointEntity",
                        "LampEntity",
                        "Quit"},
                null);

        if (result == 0)
            rayCastPointEntity(world);
        else if (result == 1)
            lampEntity(world);
        else if (result == 2) {
            viewController.stop();
            System.exit(1);
        }
    }

    /**
     * <h2>Adds a RayCastEPointEntity to the center of the screen.</h2>
     * <p>NOTE, that if the ViewCamera offset is changed, the RayCastPointEntity is not going to be in the center of the screen.</p>
     */
    public static void rayCastPointEntity(World world) {
        Handler handler = world.getHandler();
        RayCastPointEntity rayCastPointEntity = new RayCastPointEntity(handler, (float) handler.getWidth() / 2, (float) handler.getHeight() / 2);
        world.addEntity(rayCastPointEntity);

        // Make rayCastPointEntity movable
        EntityModification mod = entityModificationForMoving(rayCastPointEntity, 2.5f);
        rayCastPointEntity.addModification(mod);
    }

    /**
     * <h2>Adds a LampEntity to the center of the screen.</h2>
     * <p>NOTE, that if the view camera offset is changed, the lamp is not going to be in the center of the screen.</p>
     */
    public static void lampEntity(World world) {
        Handler handler = world.getHandler();
        LampEntity lampEntity = new LampEntity(handler, (float) handler.getWidth() / 2, (float) handler.getHeight() / 2, 500, 250);
        world.addEntity(lampEntity);

        // Make lampEntity movable
        EntityModification mod = entityModificationForMoving(lampEntity, 2.5f);
        lampEntity.addModification(mod);
    }

    /**
     * <h2>Adds different obstacle entities to the world.</h2>
     * @see environment.world.World
     * @see environment.entities.Entity
     * @see environment.entities.EntityManager
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
        // Creates an animation for moving left and right through the world
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
    }

    /**
     * Creates an entity modification object which allows entity to be moved with [W, A, S, D] keyboard keys
     *
     * @param entityToMove entity for which the move will work. <p>Note that the modification will be not applied yet. It will be only, when the modification get added to an entity.</p>.
     * @param speed        speed with which the entity will be moved after [ W | A | S | D ] key get pressed.
     * @return new instance of EntityModification with purpose for moving entity
     * @see environment.entities.utils.EntityModification
     * @see environment.entities.Entity
     * @see Entity#fixedUpdate()
     */
    private static EntityModification entityModificationForMoving(Entity entityToMove, float speed) {

        return new EntityModification() {
            @Override
            public void earlyUpdate() {
                KeyManager keyManager = entityToMove.getHandler().getKeyManager();

                if (keyManager.isDown())
                    entityToMove.setLocationOffset(0, speed);
                else if (keyManager.isUp())
                    entityToMove.setLocationOffset(0, -speed);
                else if (keyManager.isLeft())
                    entityToMove.setLocationOffset(-speed, 0);
                else if (keyManager.isRight())
                    entityToMove.setLocationOffset(speed, 0);
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
    }
}
