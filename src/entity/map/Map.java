package entity.map;

import java.util.Random;

import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import property.Movable;
import property.State;
import utility.Pair;

public class Map extends Entity implements Movable{
	public static final double FLOOR_HEIGHT = 600;
	public static final double GRAVITY = 0.8;
	public static final double PASSIVE_DAMAGE = 0.01;
	public static final double PASSIVE_SCORE = 1;
	
	protected double speedX, speedY;
	protected double width;
	
	public Map(Pair pos, Pair size) {
		super(pos, size);
		
		speedX = -10;
		speedY = 0;
		state = State.STILL;
		
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
		position.first += speedX;
		position.second += speedY;
		
		if (position.first + width <= 0) {
			position.first = 0;
		}
		
		updatePosition();
	}
	
	public void updatePosition() {
		canvas.setTranslateX(position.first);
		canvas.setTranslateY(position.second);
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
