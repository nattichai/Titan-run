package entity.gui;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class GUIText extends GUI {
	protected String text;
	protected Paint paint;
	protected double fontSize;

	public GUIText(double x, double y, double w, double h, String s, Paint p, double fs) {
		super(x, y, w, h);

		text = s;
		paint = p;
		fontSize = fs;
		canvas.getGraphicsContext2D().setTextAlign(TextAlignment.CENTER);
		canvas.getGraphicsContext2D().setTextBaseline(VPos.CENTER);

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(paint);
		gc.setFont(new Font(fontSize));
		gc.fillText(text, canvas.getWidth() / 2, canvas.getHeight() / 2);
	}

	public void setText(String string) {
		text = string;
	}

}
