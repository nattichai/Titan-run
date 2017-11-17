package entity.item;

import entity.Entity;
import javafx.scene.image.Image;
import property.Movable;
import utility.Pair;

public class Item extends Entity implements Movable {
	public static final int OBSTACLE_WIDTH = 200;
	public static final double OBSTACLE_DAMAGE = 10;

	protected Image obstacle;
	protected double speedX, speedY;

	public Item(Pair pos, Pair size) {
		super(pos, size);
		
		speedX = -10;
		speedY = 0;
	}

	public void draw() {
		
	}
	
	public void move() {
		position.first += speedX;
		position.second += speedY;
		
		updatePosition();
	}

	public void updatePosition() {
		canvas.setTranslateX(position.first);
		canvas.setTranslateY(position.second);
	}

	public boolean isDead() {
		return false;
	}
}
