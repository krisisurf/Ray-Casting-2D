package environment;

import environment.camera.ViewCamera;
import environment.world.World;
import input.KeyManager;

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

	public void setGameCamera(ViewCamera viewCamera) {
		viewController.setViewCamera(viewCamera);
	}
	
	public ViewCamera getGameCamera() {
		return viewController.getViewCamera();
	}
	
	public KeyManager getKeyManager() {
		return viewController.getKeyManager();
	}
}
