package environment.entities;

import java.awt.*;
import java.util.Optional;

import environment.Handler;
import environment.entities.utils.Shape;

public abstract class Entity {

    protected Handler handler;
    private float x, y;
    private int width, height;

    private boolean isUpdatable = true;
    private boolean isVisible = true;

    private Shape shape;

    // Between updates variables
    private boolean isMoved;

    public Entity(Handler handler, float x, float y, int width, int height) {
        this.handler = handler;
        setLocation(x, y);
        setSize(width, height);
    }

    Entity(Handler handler){
        this.handler = handler;
    }

    public final void updateFirst() {
        if (!isUpdatable)
            return;

        if (shape != null && isMoved) {
            shape.setLocation((int) x, (int) y);
            isMoved = false;
        }

        update();
    }

    public final void renderFirst(Graphics g) {
        if (isVisible)
            render(g);
    }

    public abstract void update();

    public abstract void render(Graphics g);

    public Optional<Shape> getShape() {
        return Optional.ofNullable(shape);
    }

    public void setShape(Shape shape) {
        this.shape = shape;
        setLocation(x, y);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public final void setLocation(float x, float y) {
        this.x = x;
        this.y = y;

        isMoved = true;
    }

    public void setLocationOffset(float offsetX, float offsetY) {
        setLocation(x + offsetX, y + offsetY);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isUpdatable() {
        return isUpdatable;
    }

    public void setUpdatable(boolean updatable) {
        isUpdatable = updatable;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
