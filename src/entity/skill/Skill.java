package entity.skill;

import entity.Entity;
import javafx.scene.image.Image;
import property.Animatable;
import property.Movable;
import utility.Pair;

public abstract class Skill extends Entity implements Movable, Animatable{
	protected double speedX, speedY;
	protected int currentAnimation;

	public Skill(Pair pos, Pair size) {
		super(pos, size);
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
	
	public void changeImage() {
		draw();
	}

	public abstract boolean isDead();
	
}
