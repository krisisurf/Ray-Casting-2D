package environment.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import environment.Handler;

public class EntityManager {

	private Handler handler;
	private List<Entity> entities;
	
	public EntityManager(Handler handler) {
		this.handler = handler;
		entities = new ArrayList<>();
	}
	
	public void update() {
		for(Entity e : entities)
			e.fixedUpdate();
	}
	
	public void render(Graphics g) {
		for(Entity e : entities)
			e.fixedRender(g);
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	public List<Entity> getEntities(){
		return entities;
	}
}
