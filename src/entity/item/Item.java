package entity.item;

import dataStorge.Container;
import entity.Entity;
import property.Animatable;
import property.Movable;

public abstract class Item extends Entity implements Movable, Animatable {
	protected double speedX, speedY;
	protected double height;
	protected int currentAnimation;

	public Item(double x, double y, double w, double h) {
		super(x, y, w, h);

		speedX = -10;
		speedY = 0;
	}

	public Item() {
		// TODO Auto-generated constructor stub
	}

	public abstract void draw();
	
	public void changeSpeed(double accelX, double accelY) {}

	public void move() {
		positionX += speedX;
		positionY += speedY;

		updatePosition();
	}

	public void updatePosition() {
		canvas.setTranslateX(positionX);
		canvas.setTranslateY(positionY);
	}
	
	public abstract void changeImage();

	public boolean isDead() {
		if (isCollided || positionX + 200 < 0) {
			Container.getContainer().getItemPane().getChildren().remove(canvas);
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
	
}
