package entity.characters;

import entity.Entity;
import game.property.Animatable;
import game.property.Attackable;
import game.property.Movable;
import game.property.PowerState;
import game.property.UserInterface;
import javafx.scene.image.Image;

public abstract class Characters extends Entity implements Movable, Animatable, Attackable {
	protected int nImage;
	protected Image[] images;
	protected double speedX, speedY;
	protected double accelX, accelY;
	protected int currentAnimation;
	protected double hp, maxHp;
	protected double atk;
	protected PowerState powerState;
	protected UserInterface userInterface;

	public Characters(double x, double y, double w, double h) {
		super(x, y, w, h);
		currentAnimation = 0;
	}

	public abstract void draw();

	public void changeSpeed(double accelX, double accelY) {
		speedX += accelX;
		speedY += accelY;
	}

	public abstract void move();

	public void updatePosition() {
		canvas.setTranslateX(positionX);
		canvas.setTranslateY(positionY);
	}

	public abstract void changeImage();

	public void decreaseHp(double d) {
		if (hp <= 0.00001 || powerState == PowerState.IMMORTAL) {
			return;
		}
		hp -= d;
		if (hp <= 0) {
			hp = 0.00001;
			die();
		}
		userInterface.updateHp(hp / maxHp);
		if (d > 1) {
			injured();
		}
	}

	public abstract void injured();

	public abstract void die();

	public abstract boolean isDead();

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

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		if (hp > maxHp)
			hp = maxHp;
		this.hp = hp;
	}

	public double getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(double maxHp) {
		this.maxHp = maxHp;
	}

	public double getAtk() {
		return atk;
	}

	public void setAtk(double atk) {
		this.atk = atk;
	}

	public PowerState getPowerState() {
		return powerState;
	}

	public void setPowerState(PowerState powerState) {
		this.powerState = powerState;
	}

	public UserInterface getUserInterface() {
		return userInterface;
	}

}
