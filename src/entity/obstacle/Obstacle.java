package entity.obstacle;

import java.util.Random;

import entity.Entity;
import entity.map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.Main;
import property.Movable;
import property.State;
import utility.Pair;

public class Obstacle extends Entity implements Movable {
	
	public static final int OBSTACLE_WIDTH = 200;
	public static final int AIR_HEIGHT = 500;
	
	private double xSpeed;
	private double height;
	private boolean air;

	public Obstacle(Pair pos, Pair size) {
		super(pos, size);
		
		xSpeed = -10;
		state = State.STILL;
	}
	
	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());		//clear canvas
		
		if (new Random().nextBoolean()) {
			air = false;
			int rnd = new Random().nextInt(3) + 1;
			Image obstacle = new Image(ClassLoader.getSystemResource("images/obstacle/obstacle" + rnd + ".png").toString());
			height = obstacle.getHeight();
			gc.drawImage(obstacle, 0, Map.FLOOR_HEIGHT - height, OBSTACLE_WIDTH, height);
		} else {
			air = true;
			int rnd = new Random().nextInt(2) + 1;
			Image obstacle = new Image(ClassLoader.getSystemResource("images/obstacle/air" + rnd + ".png").toString());
			height = obstacle.getHeight();
			gc.drawImage(obstacle, 0, AIR_HEIGHT - height);
		}
	}

	public double getHeight() {
		return height;
	}

	public void moveX() {
		position.first += xSpeed;
		updatePosition();
	}
	
	public void updatePosition() {
		canvas.setTranslateX(position.first);
	}

	public double getXSpeed() {
		return xSpeed;
	}

	public void setXSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}

	public boolean isAir() {
		return air;
	}

}
