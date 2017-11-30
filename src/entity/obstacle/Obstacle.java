package entity.obstacle;

import entity.Entity;
import entity.characters.Characters;
import game.GameMain;
import game.model.Model;
import game.property.Movable;
import javafx.scene.image.Image;

public abstract class Obstacle extends Entity implements Movable {
	protected static final int OBSTACLE_WIDTH = 200;
	protected static final double OBSTACLE_DAMAGE = 10;

	protected Image obstacle;
	protected double speedX, speedY;
	protected double height;

	public Obstacle(double x, double y, double w, double h) {
		super(x, y, w, h);

		speedX = -GameMain.SPEED;
		speedY = 0;
	}

	public Obstacle() {
		// TODO Auto-generated constructor stub
	}

	public abstract void draw();

	public void changeSpeed(double accelX, double accelY) {
	}

	public void move() {
		positionX += speedX;
		positionY += speedY;

		updatePosition();
	}

	public void updatePosition() {
		canvas.setTranslateX(positionX);
		canvas.setTranslateY(positionY);
	}

	public void affectTo(Characters player) {
		if (this instanceof HoleObstacle) // FALL IN A HOLE
			player.die();
		else
			player.decreaseHp(Obstacle.OBSTACLE_DAMAGE); // DAMAGED BY HITTING
	}

	public boolean isDead() {
		if (positionX + Obstacle.OBSTACLE_WIDTH + 200 <= 0) {
			Model.getContainer().getObstaclePane().getChildren().remove(canvas);
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
