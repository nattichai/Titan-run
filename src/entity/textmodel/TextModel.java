package entity.textmodel;

import entity.Entity;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import main.Main;
import utility.Pair;

public class TextModel extends Entity{
	
	Image textImage;
	String text;

	public TextModel(Pair pos, Pair size, Image image, String t) {
		super(pos, size);
		
		textImage = image;
		text = t;
		
		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		if (textImage != null) {
			gc.drawImage(textImage, (Main.SCREEN_WIDTH - textImage.getWidth()) / 2, (Main.SCREEN_HEIGHT - textImage.getHeight()) / 2);
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
