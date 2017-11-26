package entity.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class GUIRectangle extends GUI {
	Paint paint;
	double opacity;

	public GUIRectangle(double x, double y, double w, double h, Paint p, double o) {
		super(x, y, w, h);
		paint = p;
		opacity = o;
		draw();
	}

	public void draw() {
		canvas.setOpacity(opacity);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(paint);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

}
