package com.raycast.project.states;

import java.awt.Graphics;

public abstract class State {

	public static State currentState;
	
	public abstract void update();
	public abstract void render(Graphics g);
}
