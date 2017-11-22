package entity.obstacle;

import dataStorge.Container;
import entity.Entity;
import javafx.scene.image.Image;
import property.Movable;

public abstract class Obstacle extends Entity implements Movable {
	public static final int OBSTACLE_WIDTH = 200;
	public static final double OBSTACLE_DAMAGE = 10;

	protected Image obstacle;
	protected double speedX, speedY;
	protected double height;

	public Obstacle(double x, double y, double w, double h) {
		super(x, y, w, h);

		speedX = -10;
		speedY = 0;
	}

	public Obstacle() {
		// TODO Auto-generated constructor stub
	}

	public abstract void draw();

	public void move() {
		positionX += speedX;
		positionY += speedY;

		updatePosition();
	}

	public void updatePosition() {
		canvas.setTranslateX(positionX);
		canvas.setTranslateY(positionY);
	}

	public boolean isDead() {
		if (positionX + Obstacle.OBSTACLE_WIDTH + 200 <= 0) {
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
