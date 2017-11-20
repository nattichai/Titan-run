package entity.textmodel;

import entity.Entity;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import main.Main;

public class TextModel extends Entity{
	
	protected ImageView imageView;
	protected Image image;
	protected String text;

	public TextModel(double x, double y, double w, double h, Image i, String t) {
		super(x, y, w, h);
		
		image = i;
		imageView = new ImageView(image);
		text = t;
		
		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		if (image != null) {
			gc.drawImage(image, (Main.SCREEN_WIDTH - image.getWidth()) / 2, (Main.SCREEN_HEIGHT - image.getHeight()) / 2);
		} else {
			gc.setFill(Color.WHITE);
			gc.setFont(new Font("Monospace", 50));
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText(text, canvas.getWidth() / 2, canvas.getHeight() / 2);
		}
	}

	public boolean isDead() {
		return false;
	}

}
