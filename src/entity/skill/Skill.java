package entity.skill;

import entity.Entity;
import entity.characters.Characters;
import entity.characters.monster.Monster;
import entity.characters.player.Player;
import property.Animatable;
import property.Movable;
import property.Side;

public abstract class Skill extends Entity implements Movable, Animatable{
	protected Characters owner;
	protected double speedX, speedY;
	protected int currentAnimation;

	public Skill(double x, double y, double w, double h) {
		super(x, y, w, h);
	}
	
	public Skill() {}

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
	
	public void changeImage() {
		draw();
	}

	public abstract boolean isDead();

	public Characters getOwner() {
		return owner;
	}

	public void setOwner(Characters owner) {
		if (owner instanceof Player) {
			side = Side.PLAYER;
			this.owner = (Player) owner;
		} else if (owner instanceof Monster) {
			side = Side.MONSTER;
			this.owner = (Monster) owner;
		} else {
			side = Side.NEUTRAL;
		}
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

	public int getCurrentAnimation() {
		return currentAnimation;
	}

	public void setCurrentAnimation(int currentAnimation) {
		this.currentAnimation = currentAnimation;
	}
}
