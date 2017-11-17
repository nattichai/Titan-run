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
	
	private double xSpeed;
	private double width;
	
	public Map(Pair pos, Pair size) {
		super(pos, size);
		
		xSpeed = -10;
		state = State.STILL;
	}
	
	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Image image = new Image(ClassLoader.getSystemResource("images/map/map2.jpg").toString());
		width = image.getWidth();
		
		gc.drawImage(image, 0, 0);
		gc.drawImage(image, width, 0);
	}

	public void moveX() {
		recycle();
		position.first += xSpeed;
		updatePosition();
	}
	
	public void updatePosition() {
		canvas.setTranslateX(position.first);
	}
	
	public void recycle() {
		if (position.first + width <= 0) {
			position.first = 0;
//			Container.getContainer().remove(this);
//			Map map = new Map(new Pair(0, 0), new Pair(Main.SCREEN_WIDTH * 3, Main.SCREEN_HEIGHT));
//			Container.getContainer().add(map);
		}
	}

	public double getXSpeed() {
		return xSpeed;
	}

	public void setXSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}
}
