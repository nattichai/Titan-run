package entity.characters;

import entity.Entity;
import javafx.scene.control.ProgressBar;
import property.Animatable;
import property.Attackable;
import property.Movable;
import property.PowerState;
import property.State;

public abstract class Characters extends Entity implements Movable, Animatable, Attackable {
	protected double speedX, speedY;
	protected int currentAnimation;
	protected double hp, maxHp;
	protected ProgressBar hpBar;
	protected double atk;
	protected State state;
	protected PowerState powerState;

	public Characters(double x, double y, double w, double h) {
		super(x, y, w, h);

		speedX = 0;
		speedY = 0;
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
		if (hp == 0.00001) {
			return;
		}
		hp -= d;
		if (hp <= 0) {
			die();
			return;
		}
		hpBar.setProgress(hp / maxHp);
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

	public ProgressBar getHpBar() {
		return hpBar;
	}

	public void setHpBar(ProgressBar hpBar) {
		this.hpBar = hpBar;
	}

	public double getAtk() {
		return atk;
	}

	public void setAtk(double atk) {
		this.atk = atk;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public PowerState getPowerState() {
		return powerState;
	}

	public void setPowerState(PowerState powerState) {
		this.powerState = powerState;
	}

}