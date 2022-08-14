package environment.entities;

import java.awt.*;

import environment.Handler;
import environment.entities.utils.Shape;

public abstract class Entity {

    protected Handler handler;
    protected float x, y;
    private int width, height;

    private boolean isUpdatable;
    private boolean isVisible;

    public Entity(Handler handler, float x, float y, int width, int height) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.isUpdatable = true;
        this.isVisible = true;
    }

    public final void updateFirst() {
        if (isUpdatable)
            update();
    }

    public final void renderFirst(Graphics g) {
        if (isVisible)
            render(g);
    }

    public abstract void update();

    public abstract void render(Graphics g);

    /**
     * @return dynamic entity shape calculated with position offset
     */
    public abstract Shape getShape();

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
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
