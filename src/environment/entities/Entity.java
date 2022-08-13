package environment.entities;

import java.awt.*;

import environment.Handler;
import environment.entities.utils.Shape;

public abstract class Entity {

    protected Handler handler;
    protected float x, y;
    private int width, height;

    public Entity(Handler handler, float x, float y, int width, int height) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
