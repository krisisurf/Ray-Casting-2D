package com.raycast.project.environment.entities;

import com.raycast.project.environment.Handler;
import com.raycast.project.environment.camera.ViewCamera;
import com.raycast.project.environment.entities.utils.Shape;

import java.awt.*;

public class RectangleEntity extends Entity {

    private static final Color color = Color.DARK_GRAY;

    public RectangleEntity(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        initShape();
    }

    void initShape(){
        int xw = (int) getX() + getWidth();
        int yh = (int) getY() + getHeight();
        int[] xpoints = {(int) getX(), xw, xw, (int) getX()};
        int[] ypoints = {(int) getY(), (int) getY(), yh, yh};

        setShape(new Shape(xpoints, ypoints, 4, this));
    }


    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        ViewCamera cam = handler.getViewCamera();
        g.fillRect(cam.toScreenX(getX()), cam.toScreenY(getY()), getWidth(), getHeight());
    }
}
