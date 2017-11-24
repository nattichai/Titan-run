package game.model.entity.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class GUIProgress extends GUI {
	protected double progress;

	public GUIProgress(double x, double y, double w, double h, Double d) {
		super(x, y, w, h);

		progress = d;
		canvas.setOpacity(0.3);

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(Color.WHITE);
		gc.fillArc(0, 0, 75, 75, 90, (int) (360 * progress), ArcType.ROUND);
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

}
