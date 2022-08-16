package environment;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import display.Display;
import environment.camera.ViewCamera;
import input.KeyManager;
import input.MouseManager;
import states.WorldState;
import states.State;

public class ViewController implements Runnable {

    private Handler handler;

    //Display
    private String title;
    private Display display;
    private BufferStrategy bs;

    //Thread
    private Thread thread;
    private boolean running;

    //Input
    private KeyManager keyManager;
    private MouseManager mouseManager;

    //Camera
    private ViewCamera viewCamera;

    public ViewController(String title, int displayWidth, int displayHeight) {
        this.title = title;
        display = new Display(title, displayWidth, displayHeight);

        init();
    }

    private void init() {
        handler = new Handler(this);
        viewCamera = new ViewCamera(0, 0);

        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        display.addKeyListener(keyManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        State.currentState = new WorldState(handler);
    }

    public void update() {
        keyManager.update();
        mouseManager.update();

        if (State.currentState != null)
            State.currentState.update();
    }

    public void render() {
        bs = display.getCanvas().getBufferStrategy();
        Graphics g = bs.getDrawGraphics();

        g.clearRect(0, 0, display.getWidth(), display.getHeight());

        if (State.currentState != null)
            State.currentState.render(g);

        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime(), now;
        double amountOfTicks = 90.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                // System.out.println("FPS: " + frames + " TICKS: " + updates);
                String info = "   FPS: " + frames + " TICKS: " + updates;
                display.setTitle(title + info);
                frames = 0;
                updates = 0;
            }
        }
    }

    public synchronized void start() {
        if (running)
            return;

        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        if (!running)
            return;

        try {
            running = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Display getDisplay() {
        return display;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public ViewCamera getViewCamera() {
        return viewCamera;
    }

    public void setViewCamera(ViewCamera viewCamera) {
        this.viewCamera = viewCamera;
    }

    public Handler getHandler() {
        return handler;
    }
}
