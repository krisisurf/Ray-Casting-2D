package com.raycast.project.environment;

import example.MainFunctionalitiesExample;

public class Launcher {

    /**
     * <h1>Ray-Casting 2D</h1>
     * <p>Basic REAL-TIME ray-casting system for 2D environment made in java.</p>
     * <p>NOTE: A more detailed object-oriented structure might exist because the original idea wasn't exactly like it is now.</p>
     *
     * @param args nothing to do with the args
     */
    public static void main(String[] args) {
        ViewController viewController = new ViewController("Ray-Casting-2D DEMO", 1000, 800);
        viewController.start();

        // Example of the main functionalities
        MainFunctionalitiesExample.run(viewController);
    }
}
