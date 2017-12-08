package game.model.gui;

import game.model.GUI;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;

public class GUIProgress extends GUI {
	private Paint paint;
	private double startAngle;
	private double progress;
	private double range;

	public GUIProgress(double x, double y, double w, double h, double d) {
		super(x, y, w, h);

		paint = Color.BLACK;
		startAngle = 90;
		progress = d;
		range = 360;
		canvas.setOpacity(0.8);

		draw();
	}
	
	public GUIProgress(double x, double y, double w, double h, Paint p, double s, double d, double r) {
		super(x, y, w, h);

		paint = p;
		startAngle = s;
		progress = d;
		range = r;
		
		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(paint);
		gc.fillArc(0, 0, canvas.getWidth(), canvas.getHeight(), startAngle, (int) (range * progress), ArcType.ROUND);
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

}
