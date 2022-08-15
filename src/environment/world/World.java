package environment.world;

import environment.Handler;
import environment.entities.Entity;
import environment.entities.EntityManager;

import java.awt.*;
import java.util.List;

public class World {

	private Handler handler;
	private EntityManager entityManager;
	
	public World(Handler handler) {
		this.handler = handler;
		this.entityManager = new EntityManager(handler);
	}

	public void update() {
		entityManager.update();
	}

	public void render(Graphics g) {
		entityManager.render(g);
	}

	public void addEntity(Entity e){
		entityManager.addEntity(e);
	}

	public List<Entity> getEntities() {
		return entityManager.getEntities();
	}

	public Handler getHandler() {
		return handler;
	}
}
