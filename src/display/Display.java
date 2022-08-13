package display;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Display extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Canvas canvas;
	
	public Display(String title, int width, int height) {
		super(title);
		Dimension d = new Dimension(width, height);
		
		setSize(d);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		canvas = new Canvas();
		canvas.setBackground(Color.BLACK);
		setSize(d);
		canvas.setFocusable(false);
		add(canvas);

		setVisible(true);
		
		canvas.createBufferStrategy(3);
	}

	public Canvas getCanvas() {
		// TODO Auto-generated method stub
		return canvas;
	}

}
