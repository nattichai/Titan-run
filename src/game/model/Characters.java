package game.model;

import game.model.character.Player;
import game.model.gui.GUIDamage;
import game.property.Animatable;
import game.property.Attackable;
import game.property.Direction;
import game.property.Movable;
import game.property.PowerState;
import game.property.Side;
import game.property.UserInterface;
import game.storage.CharactersData;
import javafx.scene.image.Image;

public abstract class Characters extends Entity implements Movable, Animatable, Attackable {
	protected int nImage;
	protected Image[] images;
	protected double speedX, speedY;
	protected double accelX, accelY;
	protected int currentAnimation;
	protected double hp, maxHp;
	protected double atk;
	protected int skillIndex;
	protected Direction direction;
	protected Direction imageDirection;
	protected PowerState powerState;
	protected UserInterface userInterface;

	public Characters(double x, double y, int idx) {
		super(x, y, CharactersData.data[idx].getWidth(), CharactersData.data[idx].getHeight());

		CharactersData character = CharactersData.data[idx];
		nImage = character.getnImage();
		images = character.getImages();
		width = character.getWidth();
		height = character.getHeight();
		hb = character.getHb();
		speedX = character.getSpeedX();
		speedY = character.getSpeedY();
		accelX = character.getAccelX();
		accelY = character.getAccelY();
		currentAnimation = 0;
		hp = character.getHp();
		maxHp = character.getMaxHp();
		atk = character.getAtk();
		skillIndex = character.getSkillIndex();
		side = Side.MONSTER;
		direction = Direction.LEFT;
		imageDirection = character.getImageDirection();
		powerState = character.getPowerState();
		userInterface = new UserInterface(this);
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

	public void increaseHp(double d) {
		hp += d;
		if (hp >= maxHp) {
			hp = maxHp;
		}
		userInterface.updateHp(hp / maxHp);
		GUIDamage damageUI = new GUIDamage(positionX + width / 2, positionY, "" + (int) d, 2);
		Model.getContainer().add(damageUI);
	}

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
			int type = 0; // MONSTER GET DAMAGED
			if (this instanceof Player) { // PLAYER GET DAMAGED
				type = 1;
			}
			GUIDamage damageUI = new GUIDamage(positionX + width / 2, positionY, "" + (int) d, type);
			Model.getContainer().add(damageUI);
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

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
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