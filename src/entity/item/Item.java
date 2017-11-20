package entity.item;

import entity.Entity;
import entity.characters.player.Player;
import main.Container;
import property.Animatable;
import property.Movable;
import property.State;
import utility.Pair;

public abstract class Item extends Entity implements Movable, Animatable {
	public static final int ITEM_WIDTH = 200;
	
	protected double speedX, speedY;
	protected double height;
	protected int currentAnimation;
	protected boolean isCollected;

	public Item(double x, double y, double w, double h) {
		super(x, y, w, h);

		speedX = 0;
		speedY = 0;
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
	
	public abstract void changeImage();
	
	public abstract void effect(Player player);
	
	public abstract boolean isCollision(Pair pair, State state);

	public boolean isDead() {
		if (isCollected || positionX + ITEM_WIDTH < 0) {
			Container.getContainer().getItemPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}

	public void setCollected(boolean isCollected) {
		this.isCollected = isCollected;
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
