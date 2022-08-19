package example;

import com.raycast.project.environment.Handler;
import com.raycast.project.environment.ViewController;
import com.raycast.project.environment.camera.ViewCamera;
import com.raycast.project.environment.entities.*;
import com.raycast.project.environment.entities.utils.EntityModification;
import com.raycast.project.environment.entities.utils.Shape;
import com.raycast.project.environment.ui.PopupOptionPane;
import com.raycast.project.environment.world.World;
import com.raycast.project.input.KeyManager;
import com.raycast.project.input.MouseManager;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Optional;

public final class MainFunctionalitiesExample {

    /**
     * <h1>Initiating the world</h1>
     * <p>Different entities are added and showing the main things which are handled by this project.</p>
     * <p>You can use (W, A, S, D) keys to move the RayCasting Point</p>
     *
     * @param viewController the ViewController to which the changes will be created.
     * @see com.raycast.project.environment.ViewController
     */
    public static void run(ViewController viewController) {
        World world = viewController.getHandler().getWorld();
        addEntitiesToWorld(world);

        selectWhichEntityToUse(viewController);
    }

    /**
     * Creates a {@link com.raycast.project.environment.ui.PopupOptionPane} asks which type of entity will be added to the world.
     *
     * @param viewController
     */
    private static void selectWhichEntityToUse(ViewController viewController) {
        World world = viewController.getHandler().getWorld();

        new PopupOptionPane("Ray-Cast 2D", "Which entity you would like to test?",
                null,
                new PopupOptionPane.PopupOption("RayCastPointEntity") {
                    @Override
                    public void onClick() {
                        rayCastPointEntity(world);
                    }
                },
                new PopupOptionPane.PopupOption("LampEntity") {
                    @Override
                    public void onClick() {
                        lampEntity(world);
                    }
                },
                new PopupOptionPane.PopupOption("Quit") {
                    @Override
                    public void onClick() {
                        viewController.stop();
                        System.exit(1);
                    }
                }
        );
    }

    /**
     * <h2>Adds a RayCastEPointEntity to the center of the screen.</h2>
     * <p>NOTE, that if the ViewCamera offset is changed, the RayCastPointEntity is not going to be in the center of the screen.</p>
     */
    private static void rayCastPointEntity(World world) {
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
    private static void lampEntity(World world) {
        Handler handler = world.getHandler();
        LampEntity lampEntity = new LampEntity(handler, (float) handler.getWidth() / 2, (float) handler.getHeight() / 2, 500, 250);
        world.addEntity(lampEntity);

        // Make lampEntity movable
        EntityModification mod = entityModificationForMoving(lampEntity, 2.5f);
        lampEntity.addModification(mod);
    }

    /**
     * <h2>Adds different obstacle entities to the world.</h2>
     *
     * @see com.raycast.project.environment.world.World
     * @see com.raycast.project.environment.entities.Entity
     * @see com.raycast.project.environment.entities.EntityManager
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
            private float speed = 0.7f;

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
        // Check com.raycast.project.environment.entities.com.raycast.project.utils.Shape more detailed preview
        com.raycast.project.environment.entities.utils.Shape customShape = new com.raycast.project.environment.entities.utils.Shape(new int[]{250, 800, 800, 280, 280, 250}, new int[]{100, 100, 130, 130, 200, 200}, 6, null);
        Entity customShapeEntity = new CustomShapeEntity(handler, customShape);
        world.addEntity(customShapeEntity);

        // Add a modification for editing to all entities on the world
        for (Entity e : world.getEntities())
            e.addModification(entityModificationForEditing(e));
    }

    /**
     * Creates an entity modification object which allows entity to be moved with [W, A, S, D] keyboard keys
     *
     * @param entityToMove entity for which the move will work. <p>Note that the modification will be not applied yet. It will be only, when the modification get added to an entity.</p>.
     * @param speed        speed with which the entity will be moved after [ W | A | S | D ] key get pressed.
     * @return new instance of EntityModification with purpose for moving entity
     * @see com.raycast.project.environment.entities.utils.EntityModification
     * @see com.raycast.project.environment.entities.Entity
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

    private static EntityModification entityModificationForEditing(Entity entity) {

        return new EntityModification() {
            private final Point screenPointPressed = new Point();
            private final Point2D.Float worldPointPressed = new Point2D.Float();
            private boolean isBeingEdited = false;

            @Override
            public void earlyUpdate() {

            }

            @Override
            public void earlyRender(Graphics g) {

            }

            @Override
            public void lateUpdate() {
                if (isBeingEdited)
                    return;

                Handler handler = entity.getHandler();

                MouseManager mouseManager = handler.getMouseManager();
                ViewCamera viewCamera = handler.getViewCamera();

                if (!mouseManager.isLeftPressed())
                    return;

                screenPointPressed.setLocation(mouseManager.getMouseX(), mouseManager.getMouseY());
                worldPointPressed.setLocation(viewCamera.toWorldX(screenPointPressed.x), viewCamera.toWorldY(screenPointPressed.y));

                // Not every entity has a shape
                Optional<com.raycast.project.environment.entities.utils.Shape> optionalShape = entity.getShape();
                if (optionalShape.isEmpty())
                    return;

                Shape shape = optionalShape.get();
                if (!shape.getPolygon().contains(worldPointPressed))
                    return;

                isBeingEdited = true;

                PopupOptionPane.PopupOption cancelOption = new PopupOptionPane.PopupOption("Cancel") {
                    @Override
                    public void onClick() {
                        isBeingEdited = false;
                    }
                };

                new PopupOptionPane("Editing entity", "Select an option",
                        cancelOption,
                        new PopupOptionPane.PopupOption("Delete") {
                            @Override
                            public void onClick() {
                                isBeingEdited = false;
                                handler.getWorld().getEntities().remove(entity);
                            }
                        },
                        cancelOption
                );
            }

            @Override
            public void lateRender(Graphics g) {

            }
        };
    }
}
