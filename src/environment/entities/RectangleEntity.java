package environment.entities;

import environment.Handler;
import environment.camera.ViewCamera;
import environment.entities.utils.Shape;

import java.awt.*;

public class RectangleEntity extends Entity {

    private static final Color color = Color.DARK_GRAY;

    public RectangleEntity(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
    }

    public RectangleEntity(Handler handler, Rectangle rectangle) {
        super(handler, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        ViewCamera cam = handler.getGameCamera();
        g.fillRect(cam.toScreenX(x), cam.toScreenY(y), getWidth(), getHeight());
    }

    @Override
    public Shape getShape() {
        int xw = (int) x + getWidth();
        int yh = (int) y + getHeight();
        int[] xpoints = {(int) x, xw, xw, (int) x};
        int[] ypoints = {(int) y, (int) y, yh, yh};

        return new Shape(xpoints, ypoints, 4);
    }
}
