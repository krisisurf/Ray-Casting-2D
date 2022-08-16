package com.raycast.project.environment;

import com.raycast.project.environment.camera.ViewCamera;
import com.raycast.project.environment.world.World;
import com.raycast.project.input.KeyManager;
import com.raycast.project.input.MouseManager;

public class Handler {

    private ViewController viewController;
    private World world;

    public Handler(ViewController viewController) {
        this.viewController = viewController;
    }

    public int getWidth() {
        return viewController.getDisplay().getWidth();
    }

    public int getHeight() {
        return viewController.getDisplay().getHeight();
    }

    public void setWorld(World w) {
        this.world = w;
    }

    public World getWorld() {
        return world;
    }

    public void setViewCamera(ViewCamera viewCamera) {
        viewController.setViewCamera(viewCamera);
    }

    public ViewCamera getViewCamera() {
        return viewController.getViewCamera();
    }

    public KeyManager getKeyManager() {
        return viewController.getKeyManager();
    }

    public MouseManager getMouseManager() {
        return viewController.getMouseManager();
    }

    public ViewController getViewController() {
        return viewController;
    }
}
