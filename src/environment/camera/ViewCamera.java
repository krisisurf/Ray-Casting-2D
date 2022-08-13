package environment.camera;

public class ViewCamera {

	private float xOffset, yOffset;
	
	public ViewCamera(float xOffset, float yOffset) {
		setOffset(xOffset, yOffset);
	}
	
	public void setOffset(float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public int toScreenX(float objectX) {
		return (int) (objectX - xOffset);
	}
	
	public int toScreenY(float objectY) {
		return (int) (objectY - yOffset);
	}
	
	public float getXOffset() {
		return xOffset;
	}
	
	public float getYOffset() {
		return yOffset;
	}
}
