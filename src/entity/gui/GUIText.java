package entity.gui;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class GUIText extends GUI {
	public static final Font FONT1 = new Font("Monospace", 40);
	protected String text;

	public GUIText(double x, double y, double w, double h, String s) {
		super(x, y, w, h);

		text = s;
		canvas.getGraphicsContext2D().setTextAlign(TextAlignment.CENTER);
		canvas.getGraphicsContext2D().setTextBaseline(VPos.CENTER);

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(Color.WHITE);
		gc.setFont(FONT1);
		gc.fillText(text, canvas.getWidth() / 2, canvas.getHeight() / 2);
	}

	public void setText(String string) {
		text = string;
	}

}
