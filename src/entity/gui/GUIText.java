package entity.gui;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class GUIText extends GUI {
	protected String text;
	protected Paint paint;
	protected Font font;

	public GUIText(double x, double y, double w, double h, String s, Paint p, double fs) {
		super(x, y, w, h);

		text = s;
		paint = p;
		font = new Font(fs);
		canvas.getGraphicsContext2D().setTextAlign(TextAlignment.CENTER);
		canvas.getGraphicsContext2D().setTextBaseline(VPos.CENTER);

		draw();
	}

	public GUIText(double x, double y, double w, double h, String s, Paint p, Font f) {
		super(x, y, w, h);

		text = s;
		paint = p;
		font = f;
		canvas.getGraphicsContext2D().setTextAlign(TextAlignment.CENTER);
		canvas.getGraphicsContext2D().setTextBaseline(VPos.CENTER);

		drawDamage();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(paint);
		gc.setFont(font);
		gc.fillText(text, canvas.getWidth() / 2, canvas.getHeight() / 2);
	}

	public void drawDamage() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		gc.setFont(font);

		gc.setStroke(Color.BLACK);
		gc.setLineWidth(8);
		gc.strokeText(text, canvas.getWidth() / 2, canvas.getHeight() / 2);

		gc.setStroke(Color.WHITE);
		gc.setLineWidth(4);
		gc.strokeText(text, canvas.getWidth() / 2, canvas.getHeight() / 2);

		gc.setFill(paint);
		gc.fillText(text, canvas.getWidth() / 2, canvas.getHeight() / 2);
	}

	public void setText(String string) {
		text = string;
	}

	public String getText() {
		return text;
	}

}
