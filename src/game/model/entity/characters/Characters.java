package game.model.entity.characters;

import game.model.entity.Entity;
import game.model.property.Animatable;
import game.model.property.Attackable;
import game.model.property.Movable;
import game.model.property.PowerState;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;

public abstract class Characters extends Entity implements Movable, Animatable, Attackable {
	protected int nImage;
	protected Image[] images;
	protected double speedX, speedY;
	protected double accelX, accelY;
	protected int currentAnimation;
	protected double hp, maxHp;
	protected ProgressBar hpBar;
	protected double atk;
	protected PowerState powerState;

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
		if (hp == 0.00001 || powerState == PowerState.IMMORTAL) {
			return;
		}
		hp -= d;
		if (hp <= 0) {
			hp = 0.00001;
			die();
		}
		hpBar.setProgress(hp / maxHp);
		if (hp / maxHp >= 0.75)
			hpBar.setStyle("-fx-accent:green;");
		else if (hp / maxHp >= 0.5)
			hpBar.setStyle("-fx-accent:yellow;");
		else if (hp / maxHp >= 0.25)
			hpBar.setStyle("-fx-accent:orange;");
		else
			hpBar.setStyle("-fx-accent:red;");
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

	public PowerState getPowerState() {
		return powerState;
	}

	public void setPowerState(PowerState powerState) {
		this.powerState = powerState;
	}

}
