package game.model;

import game.model.character.Boss;
import game.model.character.Monster;
import game.model.character.Player;
import game.property.Animatable;
import game.property.Movable;
import game.property.Side;
import game.storage.SkillsData;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import scene.SceneManager;

public class Skill extends Entity implements Movable, Animatable {
	protected int index;
	protected int nImage;
	protected Image[] images;
	protected double speedX, speedY;
	protected double accelX, accelY;
	protected double damage;
	protected double cooldown;
	protected double collisionDelay;
	protected int currentAnimation;
	protected int lastAnimation;
	protected Characters owner;
	protected boolean isStickToOwner;
	protected boolean isOnceCollision;
	protected AudioClip audioClip;

	public Skill(double x, double y, int idx, Characters owner) {
		super(x, y, SkillsData.data[idx].getWidth(), SkillsData.data[idx].getHeight());

		index = idx;
		SkillsData skill = SkillsData.data[index];
		nImage = skill.getnImage();
		images = skill.getImages();
		width = skill.getWidth();
		height = skill.getHeight();
		if (skill.getHb() != null) {
			hb = skill.getHb();
		}
		speedX = skill.getSpeedX();
		speedY = skill.getSpeedY();
		accelX = skill.getAccelX();
		accelY = skill.getAccelY();
		damage = skill.getDamage();
		cooldown = skill.getCooldown();
		collisionDelay = skill.getCollisionDelay();
		currentAnimation = skill.getCurrentAnimation();
		lastAnimation = skill.getLastAnimation();
		isStickToOwner = skill.isStickToOwner();
		isOnceCollision = skill.isOnceCollision();
		setOwner(owner);
		audioClip = skill.getAudioClip();

	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.drawImage(images[currentAnimation], 0, 0, width, height);
	}

	public void changeSpeed(double accelX, double accelY) {
		this.accelX += accelX;
		this.accelY += accelY;
	}

	public void move() {
		if (!isStickToOwner) {
			positionX += speedX;
			positionY += speedY;
		} else {
			positionX = owner.getPositionX() - 150;
			positionY = owner.getPositionY() - 50;
		}

		updatePosition();
	}

	public void updatePosition() {
		canvas.setTranslateX(positionX);
		canvas.setTranslateY(positionY);
	}

	public void changeImage() {
		currentAnimation++;
		currentAnimation %= nImage;
		draw();
	}

	public void affectTo(Characters character) {
		// takes damage = atk * skill's damage multipler
		if (character instanceof Boss) {
			((Boss) character).decreaseHp(owner.getAtk() * damage);
		} else {
			character.decreaseHp(owner.getAtk() * damage);
		}
	}

	public boolean isCollision(Entity e) {
		if ((collisionDelay == 0 && isCollided) || side == e.side || (side == Side.NEUTRAL && e.side == Side.MONSTER))
			return false;
		if ((!isCollided || !e.isCollided) && positionX + hb.x < e.positionX + e.hb.x + e.hb.w
				&& positionX + hb.x + hb.w > e.positionX + e.hb.x
				&& positionY + hb.y < e.positionY + e.hb.y + e.hb.h
				&& positionY + hb.y + hb.h > e.positionY + e.hb.y) {

			// collision delay
			if (collisionDelay > 0) {
				isCollided = true;
				e.isCollided = true;
				new Timeline(new KeyFrame(Duration.millis(collisionDelay), f -> {
					isCollided = false;
					e.isCollided = false;
				})).play();
			} else {
				isCollided = true;
			}
			return true;
		}
		return false;
	}

	public boolean isDead() {
		if (owner.isDead() || positionX < -width - 500 || positionX > SceneManager.SCREEN_WIDTH + 500
				|| positionY < -height - 500 || positionY > SceneManager.SCREEN_HEIGHT + 500
				|| currentAnimation >= lastAnimation || (isOnceCollision && isCollided) || canvas.getOpacity() == 0
				|| (owner instanceof Boss && !((Boss) owner).isReady())) {
			Model.getContainer().getSkillPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}

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

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public int getCurrentAnimation() {
		return currentAnimation;
	}

	public void setCurrentAnimation(int currentAnimation) {
		this.currentAnimation = currentAnimation;
	}

	public int getIndex() {
		return index;
	}

	public AudioClip getAudioClip() {
		return audioClip;
	}

}
