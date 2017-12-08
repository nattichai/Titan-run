package game.model.gui;

import game.model.GUI;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class GUIShape extends GUI {
	private Paint paint;
	private double opacity;

	public GUIShape(double x, double y, double w, double h, Paint p, double o) {
		super(x, y, w, h);
		paint = p;
		opacity = o;
		draw();
	}
	
	public GUIShape(double x, double y, double w, double h, Paint p, double o, String s) {
		super(x, y, w, h);
		paint = p;
		opacity = o;
		if (s.equals("Oval")) {
			drawOval();
		}
	}

	public void draw() {
		canvas.setOpacity(opacity);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(paint);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	
	public void drawOval() {
		canvas.setOpacity(opacity);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(paint);
		gc.fillOval(0, 0, canvas.getWidth(), canvas.getHeight());
	}

}
