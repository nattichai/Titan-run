package entity.map;

import java.util.Random;

import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import property.Movable;

public class Map extends Entity implements Movable{
	public static final double FLOOR_HEIGHT = 600;
	public static final double GRAVITY = 0.8;
	public static final double PASSIVE_DAMAGE = 0.05;
	public static final double PASSIVE_SCORE = 1;
	
	protected double speedX, speedY;
	protected double width;
	
	public Map(double x, double y, double w, double h) {
		super(x, y, w, h);
		
		speedX = 0;
		speedY = 0;
		
		draw();
	}
	
	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		int rnd = new Random().nextInt(1) + 2;
		Image image = new Image(ClassLoader.getSystemResource("images/map/map" + rnd + ".jpg").toString());
		width = image.getWidth();
		
		gc.drawImage(image, 0, 0);
		gc.drawImage(image, width, 0);
	}

	public void move() {
		positionX += speedX;
		positionY += speedY;
		
		if (positionX + width <= 0) {
			positionX = 0;
		}
		
		updatePosition();
	}
	
	public void updatePosition() {
		canvas.setTranslateX(positionX);
		canvas.setTranslateY(positionY);
	}

	public boolean isDead() {
		return false;
	}

	public double getSpeedX() {
		return speedX;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}
}
