package entity.map;

import javafx.scene.canvas.*;
import javafx.scene.image.Image;

import main.*;
import entity.*;
import property.*;
import utility.*;

public class Map extends Entity implements Movable{
	public static final double FLOOR_HEIGHT = 600;
	public static final double GRAVITY = 0.8;
	
	double xSpeed;
	
	public Map(Pair pos, Pair size) {
		super(pos, size);
		
		xSpeed = -10;
		state = State.STILL;
	}
	
	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Image image = new Image(ClassLoader.getSystemResource("map2.jpg").toString());
		gc.drawImage(image, position.first, position.second, 1333, Main.SCREEN_HEIGHT);
		gc.drawImage(image, position.first + 1333, position.second, 1333, Main.SCREEN_HEIGHT);
	}

	public void moveX() {
		position.first += xSpeed;
		recycle();
		updatePosition();
	}
	
	public void updatePosition() {
		canvas.setTranslateX(position.first);
	}
	
	public void recycle() {
		if (position.first + 1333 <= 0) {
			position.first = 0;
		}
	}

	public double getXSpeed() {
		return xSpeed;
	}

	public void setXSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}
}
