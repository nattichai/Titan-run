package entity.gui;

import entity.Entity;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import main.Main;

public class GUI extends Entity{
	public static final Font FONT1 = new Font("Monospace", 40);
	
	protected Image image;
	protected String text;
	protected double progress;

	public GUI(double x, double y, double w, double h, Image i) {
		super(x, y, w, h);
		
		image = i;
		
		drawImage();
	}
	
	public GUI(double x, double y, double w, double h, String s) {
		super(x, y, w, h);
		
		text = s;
		canvas.getGraphicsContext2D().setTextAlign(TextAlignment.CENTER);
		canvas.getGraphicsContext2D().setTextBaseline(VPos.CENTER);
		
		drawText();
	}
	
	public GUI(double x, double y, double w, double h, Double d) {
		super(x, y, w, h);
		
		progress = d;
		canvas.setOpacity(0.3);
		
		drawProgress();
	}
	
	public void draw() {}

	public void drawImage() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.drawImage(image, (Main.SCREEN_WIDTH - image.getWidth()) / 2, (Main.SCREEN_HEIGHT - image.getHeight()) / 2);
	}
	
	public void drawText() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(Color.WHITE);
		gc.setFont(FONT1);
		gc.fillText(text, canvas.getWidth()/2, canvas.getHeight()/2);
	}
	
	public void drawProgress() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(Color.WHITE);
		gc.fillArc(0, 0, 75, 75, 90,(int)(360 * progress), ArcType.ROUND);
	}

	public boolean isDead() {
		return false;
	}

	public void setText(String string) {
		text = string;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

}
