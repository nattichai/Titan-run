package entity.item;

import entity.Entity;
import entity.player.Player;
import javafx.scene.image.Image;
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

	public Item(Pair pos, Pair size) {
		super(pos, size);

		speedX = -10;
		speedY = 0;
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
	
	public abstract void changeImage();
	
	public abstract void effect(Player player);
	
	public abstract boolean isCollision(Pair pair, State state);

	public boolean isDead() {
		if (isCollected || position.first + ITEM_WIDTH < 0) {
			Container.getContainer().getItemPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}

	public void setCollected(boolean isCollected) {
		this.isCollected = isCollected;
	}
	
}
