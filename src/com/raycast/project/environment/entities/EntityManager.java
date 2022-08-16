package com.raycast.project.environment.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.raycast.project.environment.Handler;

public class EntityManager {

	private Handler handler;
	private List<Entity> entities;
	
	public EntityManager(Handler handler) {
		this.handler = handler;
		entities = new ArrayList<>();
	}
	
	public void update() {
		for(int i = 0; i < entities.size(); i++)
			entities.get(i).fixedUpdate();
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < entities.size(); i++)
			entities.get(i).fixedRender(g);
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	public List<Entity> getEntities(){
		return entities;
	}
}
