package entity.characters;

import entity.map.Map;
import entity.skill.Skill;
import game.model.Model;
import game.property.Direction;
import game.property.Hitbox;
import game.property.PowerState;
import game.property.Side;
import game.property.State;
import game.storage.SkillsData;
import game.updater.Updater;
import input.GameHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import scene.GameMain;
import scene.MainMenu;
import scene.SceneManager;

public class Player extends Characters {
	public static final double LIMIT_NORMAL_ATTACK = 10;
	public static final double MOVEDOWN_SPEED = 1.5;
	public static final int MAX_JUMP = 2;

	protected static final double[] fullCooldown = new double[5];
	static {
		fullCooldown[0] = SkillsData.data[0].getCooldown();
		fullCooldown[1] = SkillsData.data[1].getCooldown();
		fullCooldown[2] = SkillsData.data[2].getCooldown();
		fullCooldown[3] = SkillsData.data[3].getCooldown();
	}
	protected Image imageSlide;
	protected int jump;
	protected double mana, maxMana;
	protected State state;
	protected double score;
	protected double[] cooldown;
	protected double distance;

	public Player(double x, double y, int idx) {
		super(x, y, idx);

		width = 120;
		height = 200;
		hb = new Hitbox(20, 20, 100, 180);
		speedX = 0;
		speedY = 0;
		accelX = 0;
		accelY = Map.GRAVITY;
		side = Side.PLAYER;
		direction = Direction.RIGHT;

		jump = 0;
		mana = 100;
		maxMana = 100;
		state = State.RUNNING;
		score = 0;
		cooldown = new double[5];
		distance = 0;

		userInterface.setName(MainMenu.getRegisterName().getText());
		userInterface.getHpBar().setPrefSize(200, 15);
	}

	public void draw() {
		if (direction != imageDirection) {
			if (state == State.SLIDING) {
				canvas.setScaleX(1);
				canvas.setScaleY(-1);
			} else {
				canvas.setScaleX(-1);
				canvas.setScaleY(1);
			}
		} else {
			canvas.setScaleX(1);
			canvas.setScaleY(1);
		}
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation %= nImage;
		gc.drawImage(images[currentAnimation], 0, 0, width, height);
	}

	public void move() {
		changeSpeed(accelX, accelY);
		if (speedX < 0) {
			direction = Direction.LEFT;
		} else if (speedX > 0) {
			direction = Direction.RIGHT;
		}

		positionX += speedX;
		positionY += speedY;
		if (positionX < 0) {
			positionX = 0;
		} else if (positionX > SceneManager.SCREEN_WIDTH - width) {
			positionX = SceneManager.SCREEN_WIDTH - width;
		}

		if (state == State.SLIDING) {
			positionY = Map.FLOOR_HEIGHT - width;
			speedY = 0;
		} else if (positionY >= Map.FLOOR_HEIGHT - height) {
			positionY = Map.FLOOR_HEIGHT - height;
			speedY = 0;
			if (GameMain.getSpeed() != 0 || speedX != 0) {
				state = State.RUNNING;
			} else {
				state = State.STILL;
			}
		}

		userInterface.updateHp(positionX - 25, positionY - 50);
		userInterface.updateMana(positionX - 25, positionY - 32);
		userInterface.updateNamePos(positionX - 25, positionY - 80);
		updatePosition();
	}

	public void changeImage() {
		currentAnimation++;
		currentAnimation %= nImage;
		draw();
	}

	public void affectTo(Characters e) {
		// takes damage to e = atk
		e.decreaseHp(atk);
	}

	public void jump() {
		// first jump
		if (state == State.RUNNING || state == State.STILL) {
			state = State.JUMPING;
			jump = 1;
			speedY = -18.5;
		}
		// other jumps
		else if (jump < Player.MAX_JUMP) {
			jump++;
			speedY = -15;
		}
	}

	public void goDown() {
		if (state != State.JUMPING) {
			slide();
		} else {
			speedY += MOVEDOWN_SPEED;
		}
	}

	public void slide() {
		state = State.SLIDING;
		canvas.setRotate(-90);
		speedY = 0;
		draw();
	}

	public void makeShield() {
		if (mana >= 0.5) {
			Skill shield = new Skill(positionX - 150, positionY - 50, 4, this);
			Model.getContainer().add(shield);
		}
	}

	public void useShield() {
		mana -= 0.5;
		powerState = PowerState.IMMORTAL;
		if (mana <= 0) {
			// force release spacebar
			GameHandler.keyReleased(new KeyEvent(null, null, KeyEvent.KEY_RELEASED, null, "SPACE", KeyCode.SPACE, false,
					false, false, false));
		}
	}

	public void useFireball() {
		if (state != State.SLIDING && cooldown[0] <= 0) {
			resetCooldown(0);
			Skill fireball = new Skill(positionX, positionY + height / 2, 0, this);
			if (direction != imageDirection) {
				fireball.getCanvas().setScaleX(-1);
				fireball.setSpeedX(-fireball.getSpeedX());
			}
			Model.getContainer().add(fireball);
		}
	}

	public void useLightning() {
		if (state != State.SLIDING && cooldown[1] <= 0) {
			resetCooldown(1);
			Timeline timerLightning = new Timeline(new KeyFrame(Duration.millis(100), e -> {
				Skill lightning = new Skill(nearestMonsterPosition(), 0, 1, this);
				if (direction != imageDirection) {
					lightning.getCanvas().setScaleX(-1);
					lightning.setSpeedX(-lightning.getSpeedX());
				}
				Model.getContainer().add(lightning);
			}));
			timerLightning.setCycleCount(6);
			timerLightning.play();
		}
	}

	public double nearestMonsterPosition() {
		double pos = 800;
		double minDistance = 1000;
		if (direction != imageDirection) {
			pos = 200;
		}
		for (Monster monster : Model.getContainer().getMonsterList()) {
			double distance = Math.abs(monster.getPositionX() - positionX);
			if (distance < minDistance) {
				minDistance = distance;
				pos = monster.getPositionX() + monster.getWidth() / 2;
			}
		}
		return pos;
	}

	public void useThunderbolt() {
		if (state != State.SLIDING && cooldown[2] <= 0) {
			resetCooldown(2);
			Skill thunderbolt = new Skill(positionX, 0, 2, this);
			if (direction != imageDirection) {
				thunderbolt.getCanvas().setScaleX(-1);
				thunderbolt.setSpeedX(-thunderbolt.getSpeedX());
			}
			Model.getContainer().add(thunderbolt);
		}
	}

	public void useSlashy() {
		if (state != State.SLIDING && cooldown[3] <= 0) {
			resetCooldown(3);
			Skill slashy = new Skill(0, 0, 3, this);
			Model.getContainer().add(slashy);
		}
	}

	public void backToRunningPosition() {
		setDistance(0);
		speedX = 0;
		positionX = 90;
		direction = Direction.RIGHT;
		if (state == State.SLIDING) {
			canvas.setRotate(0);
			state = State.RUNNING;
		}
	}

	public void injured() {
		powerState = PowerState.IMMORTAL;
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000 / 10), e -> {
			if (canvas.getOpacity() == 1)
				canvas.setOpacity(0.5);
			else
				canvas.setOpacity(1);
		}));
		timeline.setCycleCount(16);
		timeline.play();
		timeline.setOnFinished(e -> {
			powerState = PowerState.NORMAL;
			isCollided = false;
		});
	}

	public void die() {
		hp = 0.00001;
		Updater.playerDead();
		GameMain.pauseGame();
		// GameMain.setSpeed(2);
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(Updater.LOOP_TIME), e -> {
			positionY += 10;
			canvas.setTranslateY(positionY);
			canvas.setRotate(canvas.getRotate() + 15);
		}));
		timeline.setCycleCount((int) Updater.FPS / 2);
		timeline.play();
		timeline.setOnFinished(e -> {
			GameMain.continueGame();
			userInterface.dead();
			Model.getContainer().remove(this);
		});
	}

	public boolean isDead() {
		return hp == 0.00001 || canvas.getOpacity() == 0;
	}

	public int getJump() {
		return jump;
	}

	public void addJump(int jump) {
		this.jump += jump;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public double getMana() {
		return mana;
	}

	public void setMana(double mana) {
		this.mana = mana;
	}

	public void addMana(double mana) {
		this.mana += mana;
		if (this.mana < 0) {
			this.mana = 0;
		} else if (this.mana > maxMana) {
			this.mana = maxMana;
		}
		userInterface.updateMana(this.mana / maxMana);
	}

	public void setJump(int j) {
		jump = j;
	}

	public double getScore() {
		return score;
	}

	public void addScore(double s) {
		score += s;
		userInterface.updateScore(score);
	}

	public double getCooldown(int index) {
		return cooldown[index];
	}

	public void resetCooldown(int index) {
		cooldown[index] = fullCooldown[index];
		userInterface.updateCooldown(index, 1);
	}

	public void decreaseCooldown(double cd) {
		for (int i = 0; i < 4; ++i) {
			cooldown[i] -= cd;
			if (cooldown[i] < 0)
				cooldown[i] = 0;
			userInterface.updateCooldown(i, cooldown[i] / fullCooldown[i]);
		}
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double d) {
		distance = d;
		userInterface.updateDistance(d / GameMain.STAGE_DISTANCE);
	}

	public void addDistance(double d) {
		distance += d;
		userInterface.updateDistance(distance / GameMain.STAGE_DISTANCE);
	}
}