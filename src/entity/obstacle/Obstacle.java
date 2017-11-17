package entity.obstacle;

import entity.Entity;
import javafx.scene.image.Image;
import main.Container;
import property.Movable;
import property.State;
import utility.Pair;

public abstract class Obstacle extends Entity implements Movable {
	public static final int OBSTACLE_WIDTH = 200;
	public static final double OBSTACLE_DAMAGE = 10;

	protected Image obstacle;
	protected double speedX, speedY;
	protected double height;

	public Obstacle(Pair pos, Pair size) {
		super(pos, size);

		speedX = -10;
		speedY = 0;
		state = State.STILL;
	}

	public abstract void draw();

	public void move() {
		position.first += speedX;
		position.second += speedY;

		updatePosition();
	}

	public void updatePosition() {
		canvas.setTranslateX(position.first);
		canvas.setTranslateY(position.second);
	}
	
	public abstract boolean isCollision(Pair pair, State state);

	public boolean isDead() {
		if (position.first + Obstacle.OBSTACLE_WIDTH + 200 <= 0) {
			Container.getContainer().getObstaclePane().getChildren().remove(canvas);
			return true;
		}
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

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

}
